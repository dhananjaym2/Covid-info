package covid.info.data.db;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import static covid.info.DataConstants.COUNT_NOT_AVAILABLE;

@Entity()
public class CovidCaseByCountry {

  //private int id;

  private String Country;

  private String CountryCode;

  @NonNull
  @PrimaryKey()
  private String Slug;

  private int NewConfirmed = COUNT_NOT_AVAILABLE;

  private int TotalConfirmed = COUNT_NOT_AVAILABLE;

  private int NewDeaths = COUNT_NOT_AVAILABLE;

  private int NewRecovered = COUNT_NOT_AVAILABLE;

  private int TotalRecovered = COUNT_NOT_AVAILABLE;

  private String Date;

  public CovidCaseByCountry(String Country, String CountryCode, String Slug, int NewConfirmed,
      int TotalConfirmed, int NewDeaths, int NewRecovered, int TotalRecovered, String Date) {
    setCountry(Country);
    setCountryCode(CountryCode);
    setSlug(Slug);
    setNewConfirmed(NewConfirmed);
    setTotalConfirmed(TotalConfirmed);
    setNewDeaths(NewDeaths);
    setNewRecovered(NewRecovered);
    setTotalRecovered(TotalRecovered);
    setDate(Date);
  }

  /*public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }*/

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
}