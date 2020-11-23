package covid.info.network.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class DailyCovidCaseSummaryResponse {

  @SerializedName("Global")
  @Expose
  private Global global;

  @SerializedName("Countries")
  @Expose
  private List<Countries> Countries;

  public Global getGlobal() {
    return global;
  }

  public void setGlobal(Global global) {
    this.global = global;
  }

  public List<covid.info.network.response.Countries> getCountries() {
    return Countries;
  }

  public void setCountries(List<covid.info.network.response.Countries> countries) {
    Countries = countries;
  }
}
