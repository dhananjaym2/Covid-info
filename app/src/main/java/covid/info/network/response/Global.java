package covid.info.network.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class Global {

  @SerializedName("NewConfirmed")
  @Expose
  private int NewConfirmed;

  @SerializedName("TotalConfirmed")
  @Expose
  private int TotalConfirmed;

  @SerializedName("NewDeaths")
  @Expose
  private int NewDeaths;

  @SerializedName("NewRecovered")
  @Expose
  private int NewRecovered;

  @SerializedName("TotalRecovered")
  @Expose
  private int TotalRecovered;
}