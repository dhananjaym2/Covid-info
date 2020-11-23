package covid.info.data;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import androidx.lifecycle.MutableLiveData;
import covid.info.data.db.AppDatabase;
import covid.info.data.db.CovidCaseByCountry;
import covid.info.data.db.CovidCaseByCountryDao;
import covid.info.data.db.SlugWiseCase;
import covid.info.data.db.SlugWiseCaseDao;
import covid.info.list.data.ChatMessage;
import covid.info.network.ApiController;
import covid.info.network.response.Countries;
import covid.info.network.response.DailyCovidCaseSummaryResponse;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static covid.info.DataConstants.COUNT_NOT_AVAILABLE;
import static covid.info.data.CaseTypeConstants.cases;
import static covid.info.data.CaseTypeConstants.deaths;
import static covid.info.data.Sender.localDatabase;
import static covid.info.data.Sender.server;
import static covid.info.data.error.handling.ErrorCodeConstants.caseTypeNotSupported;
import static covid.info.data.error.handling.ErrorCodeConstants.countryNotFoundInLocalDb;
import static covid.info.data.error.handling.ErrorCodeConstants.internetNotConnected;
import static covid.info.data.error.handling.ErrorCodeConstants.networkError;
import static covid.info.network.ApiConstants.confirmedCases;
import static covid.info.network.ApiConstants.deathCases;

public class CaseRepository {

  private ApiController apiController;
  private CovidCaseByCountryDao covidCaseByCountryDao;
  private SlugWiseCaseDao slugWiseCaseDao;
  private MutableLiveData<ChatMessage> chatMessageLiveData;
  private final String logTag = CaseRepository.class.getSimpleName();

  public CaseRepository(Context context) {
    apiController = new ApiController();
    AppDatabase database = AppDatabase.getDatabase(context);
    covidCaseByCountryDao = database.covidCaseByCountry();
    slugWiseCaseDao = database.countryWiseCase();
    chatMessageLiveData = new MutableLiveData<>();
  }

  public MutableLiveData<ChatMessage> getChatMessageLiveData() {
    return chatMessageLiveData;
  }

  /*public void insert(final CovidCaseByCountry covidCaseByCountry) {
    AppDatabase.databaseWriteExecutor.execute(new Runnable() {
      @Override public void run() {
        covidCaseByCountryDao.insert(covidCaseByCountry);
      }
    });
  }*/

  private void insertAllCountriesData(final List<Countries> countries, final String caseType,
      final String country, final boolean isConnectedToInternet) {
    AppDatabase.databaseExecutor.execute(new Runnable() {
      @Override public void run() {
        for (Countries country : countries) {
          Log.d(logTag, "Inserting in DB:" + country.getCountry() + country.getSlug());
          covidCaseByCountryDao.insert(new CovidCaseByCountry(country.getCountry(),
              country.getCountryCode(), country.getSlug(), country.getNewConfirmed(),
              country.getTotalConfirmed(), country.getNewDeaths(), country.getNewRecovered(),
              country.getTotalRecovered(), country.getDate()));
        }

        if (!TextUtils.isEmpty(caseType) && !TextUtils.isEmpty(country)) {
          // search for slug before getting the count of cases/deaths
          getSlugOfCountryFromDB(caseType, country, isConnectedToInternet);
        }
      }
    });
  }

  private void getSlugWiseCaseCountFromDB(final String caseType, final String Slug,
      final boolean isConnectedToInternet) {
    AppDatabase.databaseExecutor.execute(new Runnable() {
      @Override public void run() {
        SlugWiseCase slugWiseCase = slugWiseCaseDao.fetchCaseByCountrySlug(Slug);

        if (slugWiseCase != null) {
          // TODO check if count is NOT recently updated then call API.
          // TO send following values back to the UI
          if (caseType.equalsIgnoreCase(cases) && slugWiseCase.getCases() != COUNT_NOT_AVAILABLE) {
            postChatMessage(String.valueOf(slugWiseCase.getCases()), localDatabase);
          } else if (caseType.equalsIgnoreCase(deaths)
              && slugWiseCase.getDeaths() != COUNT_NOT_AVAILABLE) {
            postChatMessage(String.valueOf(slugWiseCase.getDeaths()), localDatabase);
          } else {
            Log.d(logTag, "Either caseType doesn't match OR COUNT_NOT_AVAILABLE");
            checkInternetBeforeFetchSpecificAPI();
          }
        } else {
          checkInternetBeforeFetchSpecificAPI();
        }
      }

      private void checkInternetBeforeFetchSpecificAPI() {
        if (isConnectedToInternet) {
          // get case data from API
          callSpecificCaseCountAPI(caseType, Slug);
        } else {
          postChatMessage(internetNotConnected.name(), localDatabase);
        }
      }
    });
  }

  private void postChatMessage(String message, Sender sender) {
    Log.i(logTag, "postChatMessage(): " + message + " from: " + sender);
    chatMessageLiveData.postValue(new ChatMessage(message, sender));
  }

  private void getDailyCovidCaseSummary(final String caseType, final String country,
      final boolean isConnectedToInternet) {
    final Callback<DailyCovidCaseSummaryResponse> summaryCallback =
        new Callback<DailyCovidCaseSummaryResponse>() {
          @Override public void onResponse(Call<DailyCovidCaseSummaryResponse> call,
              Response<DailyCovidCaseSummaryResponse> response) {

            if (response.body() != null) {
              // TO SAVE data for further use
              insertAllCountriesData(response.body().getCountries(), caseType, country,
                  isConnectedToInternet);
              //setAutoFillHints(response.body().getCountries());
              //Log.d(logTag, String.valueOf(response.body().getCountries().size()));
            }
          }

          //private void setAutoFillHints(List<Countries> response) {
          //  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
          //    chatMsgFromUser.setAutofillHints(response.get(0).getCountry());
          //  }
          //}

          @Override public void onFailure(Call<DailyCovidCaseSummaryResponse> call, Throwable t) {
            t.printStackTrace();
            postChatMessageNetworkError();
          }
        };

    apiController.getDailyCovidCaseSummary(summaryCallback);
  }

  private void postChatMessageNetworkError() {
    postChatMessage(networkError.name(), server);
  }

  public void getCountriesDataIfNotAvailable(final String caseType, final String country,
      final boolean isConnectedToInternet) {
    AppDatabase.databaseExecutor.execute(new Runnable() {
      @Override public void run() {
        int isCountriesTableEmpty = covidCaseByCountryDao.isCountriesTableEmpty();

        if (isCountriesTableEmpty != 0) {
          Log.d(logTag, "CountriesTable NOT Empty, data is available");
          if (caseType != null) {
            getSlugOfCountryFromDB(caseType, country, isConnectedToInternet);
          }
        } else {
          Log.d(logTag, "CountriesTableEmpty");
          if (isConnectedToInternet) {
            // get data from API
            getDailyCovidCaseSummary(caseType, country, isConnectedToInternet);
          } else {
            if (caseType != null) {
              // user requested, but the data is not available and internet disconnected, so inform
              // the user
              postChatMessage(internetNotConnected.name(), localDatabase);
            }
            // user didn't request, but the data is not available and internet disconnected, so no
            // need to inform user, because this is done in background.
          }
        }
      }
    });
  }

  private void callSpecificCaseCountAPI(String caseType, String Slug) {
    if (caseType == null) {
      Log.d(logTag, "caseType == null, no need to call any API");
    } else if (caseType.equals(cases)) {
      apiController.getSpecificCasesByCountry(Slug, confirmedCases, specificCaseCallback);
    } else if (caseType.equals(deaths)) {
      apiController.getSpecificCasesByCountry(Slug, deathCases, specificCaseCallback);
    } else {
      postChatMessage(caseTypeNotSupported.name(), localDatabase);
    }
  }

  private Callback<List<Countries>> specificCaseCallback = new Callback<List<Countries>>() {

    @Override public void onResponse(Call<List<Countries>> call,
        final Response<List<Countries>> response) {
      if (response.isSuccessful() && response.body() != null) {

        final int countOfResults = response.body().size();
        final Countries countryLatestData = response.body().get(countOfResults - 1);
        final int countReceivedFromApi = countryLatestData.getCases();
        String slugFromApiRequest = null;
        if (call.request().url().pathSegments().size() >= 2) {
          slugFromApiRequest = call.request().url().pathSegments().get(1);
        } else {
          // This condition should not happen unless API request is modified, but added it for
          // safety check.
          Log.e(logTag, "Unable to get value for slugFromApiRequest. We will skip caching");
          return;
        }
        final String statusReceivedFromApi = countryLatestData.getStatus();
        final String dateReceivedFromApi = countryLatestData.getDate();

        postChatMessage(String.valueOf(countReceivedFromApi), server);

        if (statusReceivedFromApi.equalsIgnoreCase(confirmedCases)) {
          updateOrInsertSlugWiseCase(slugFromApiRequest, countReceivedFromApi, COUNT_NOT_AVAILABLE,
              dateReceivedFromApi);
        } else if (statusReceivedFromApi.equalsIgnoreCase(deathCases)) {
          updateOrInsertSlugWiseCase(slugFromApiRequest, COUNT_NOT_AVAILABLE, countReceivedFromApi,
              dateReceivedFromApi);
        }
      } else {
        if (response.errorBody() != null) {
          Log.e(logTag, response.errorBody().toString());
        } else {
          final String errorMsg = "API Error: response.errorBody() is null";
          Log.e(logTag, errorMsg);
        }
        postChatMessageNetworkError();
      }
    }

    @Override
    public void onFailure(Call<List<Countries>> call, Throwable t) {
      t.printStackTrace();
      postChatMessageNetworkError();
    }
  };

  private void updateOrInsertSlugWiseCase(final String slug, final int casesValueToUpdate,
      final int deathsValueToUpdate, final String date) {

    // TO SAVE (insert or update) the data as cached result in DB
    AppDatabase.databaseExecutor.execute(new Runnable() {
      @Override public void run() {
        SlugWiseCase slugWiseCase = slugWiseCaseDao.fetchCaseByCountrySlug(slug);

        if (slugWiseCase != null) {
          if (casesValueToUpdate != COUNT_NOT_AVAILABLE) {
            slugWiseCase.setCases(casesValueToUpdate);
            slugWiseCase.setCasesUpdateDate(date);
            slugWiseCaseDao.update(slugWiseCase);
            Log.d(logTag, "cases data updated in local DB");
          } else if (deathsValueToUpdate != COUNT_NOT_AVAILABLE) {
            slugWiseCase.setDeaths(deathsValueToUpdate);
            slugWiseCase.setDeathsUpdateDate(date);
            slugWiseCaseDao.update(slugWiseCase);
            Log.d(logTag, "deaths data updated in local DB");
          } else {
            Log.e(logTag,
                "Neither casesValueToUpdate nor deathsValueToUpdate is COUNT_NOT_AVAILABLE");
          }
        } else {
          slugWiseCaseDao.insert(new SlugWiseCase(slug, casesValueToUpdate, deathsValueToUpdate,
              casesValueToUpdate != COUNT_NOT_AVAILABLE ? date : "",
              deathsValueToUpdate != COUNT_NOT_AVAILABLE ? date : ""));
        }
      }
    });
  }

  public void getCountrySpecificCaseCount(String caseType, String country,
      boolean isConnectedToInternet) {
    getCountriesDataIfNotAvailable(caseType, country, isConnectedToInternet);
  }

  private void getSlugOfCountryFromDB(final String caseType, final String country,
      final boolean isConnectedToInternet) {
    AppDatabase.databaseExecutor.execute(new Runnable() {
      @Override public void run() {
        CovidCaseByCountry covidCaseByCountry = covidCaseByCountryDao.getCountrySlug(country);

        if (covidCaseByCountry != null) {
          // TO check if the DB contains the requested data
          getSlugWiseCaseCountFromDB(caseType, covidCaseByCountry.getSlug(),
              isConnectedToInternet);
        } else {
          postChatMessage(countryNotFoundInLocalDb.toString(), localDatabase);
        }
      }
    });
  }
}