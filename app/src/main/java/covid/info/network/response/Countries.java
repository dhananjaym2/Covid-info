package covid.info.network.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Countries {

  @SerializedName("Country")
  @Expose
  private String Country;

  @SerializedName("CountryCode")
  @Expose
  private String CountryCode;

  @SerializedName("Slug")
  @Expose
  private String Slug;

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

  @SerializedName("Date")
  @Expose
  private String Date;

  @SerializedName("Lat")
  @Expose
  private String Lat;

  @SerializedName("Lon")
  @Expose
  private String Lon;

  @SerializedName("Cases")
  @Expose
  private int Cases;

  @SerializedName("Status")
  @Expose
  private String Status;

  public String getCountry() {
    return Country;
  }

  public void setCountry(String country) {
    Country = country;
  }

  public String getCountryCode() {
    return CountryCode;
  }

  public void setCountryCode(String countryCode) {
    CountryCode = countryCode;
  }

  public String getSlug() {
    return Slug;
  }

  public void setSlug(String slug) {
    Slug = slug;
  }

  public int getNewConfirmed() {
    return NewConfirmed;
  }

  public void setNewConfirmed(int newConfirmed) {
    NewConfirmed = newConfirmed;
  }

  public int getTotalConfirmed() {
    return TotalConfirmed;
  }

  public void setTotalConfirmed(int totalConfirmed) {
    TotalConfirmed = totalConfirmed;
  }

  public int getNewDeaths() {
    return NewDeaths;
  }

  public void setNewDeaths(int newDeaths) {
    NewDeaths = newDeaths;
  }

  public int getNewRecovered() {
    return NewRecovered;
  }

  public void setNewRecovered(int newRecovered) {
    NewRecovered = newRecovered;
  }

  public int getTotalRecovered() {
    return TotalRecovered;
  }

  public void setTotalRecovered(int totalRecovered) {
    TotalRecovered = totalRecovered;
  }

  public String getDate() {
    return Date;
  }

  public void setDate(String date) {
    Date = date;
  }

  public String getLat() {
    return Lat;
  }

  public void setLat(String lat) {
    Lat = lat;
  }

  public String getLon() {
    return Lon;
  }

  public void setLon(String lon) {
    Lon = lon;
  }

  public int getCases() {
    return Cases;
  }

  public void setCases(int cases) {
    Cases = cases;
  }

  public String getStatus() {
    return Status;
  }

  public void setStatus(String status) {
    Status = status;
  }
}
