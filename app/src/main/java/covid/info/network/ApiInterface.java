package covid.info.network;

import covid.info.network.response.Countries;
import covid.info.network.response.DailyCovidCaseSummaryResponse;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
  //Required URL format:
  //https://api.covid19api.com/country/south-africa/status/confirmed/live?from=2020-03-01T00:00:00Z&to=2020-04-01T00:00:00Z
  @GET("country/{country}/status/{status}/live")
  Call<List<Countries>> getActiveCasesByCountry(
      @Path(value = "country", encoded = true) String country,
      @Path(value = "status", encoded = true) String status,
      @Query(value = "from", encoded = true) String startTime,
      @Query(value = "to", encoded = true) String endTime);
  //cases should be one of these: confirmed, recovered, deaths

  @GET("/summary") Call<DailyCovidCaseSummaryResponse> getCountryWiseDailyCovidCaseSummary();
}