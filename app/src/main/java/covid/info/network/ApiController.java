package covid.info.network;

import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import covid.info.network.response.Countries;
import covid.info.network.response.DailyCovidCaseSummaryResponse;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static covid.info.utils.AppDateUtils.getCurrentDate;
import static covid.info.utils.AppDateUtils.getYesterdayDate;

public class ApiController {

  private final String logTag = ApiController.class.getSimpleName();
  private ApiInterface apiInterface;

  public ApiController() {
    Gson gson = new GsonBuilder().setLenient().setPrettyPrinting().create();

    final String BASE_URL = "https://api.covid19api.com/";

    Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson)).build();
    apiInterface = retrofit.create(ApiInterface.class);
  }

  public void getSpecificCasesByCountry(String country, String status,
      Callback<List<Countries>> apiCallBack) {
    Call<List<Countries>> call = apiInterface.getActiveCasesByCountry(country, status,
        getCurrentDate(), getYesterdayDate());
    Log.d(logTag, "GET: " + call.request().url().toString());

    call.enqueue(apiCallBack);
  }

  public void getDailyCovidCaseSummary(Callback<DailyCovidCaseSummaryResponse> callback) {
    Call<DailyCovidCaseSummaryResponse> call = apiInterface.getCountryWiseDailyCovidCaseSummary();
    Log.d(logTag, "GET: " + call.request().url().toString());

    call.enqueue(callback);
  }
}