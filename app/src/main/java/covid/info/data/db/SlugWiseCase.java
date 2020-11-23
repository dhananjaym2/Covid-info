package covid.info.data.db;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import static covid.info.DataConstants.COUNT_NOT_AVAILABLE;

@Entity
public class SlugWiseCase {

  //private int id;

  @NonNull
  @PrimaryKey
  private String Slug;

  private int Cases = COUNT_NOT_AVAILABLE;

  private int Deaths = COUNT_NOT_AVAILABLE;

  private String CasesUpdateDate;

  private String DeathsUpdateDate;

  public SlugWiseCase(String Slug, int Cases, int Deaths, String CasesUpdateDate, String
      DeathsUpdateDate) {
    setSlug(Slug);
    setCases(Cases);
    setDeaths(Deaths);
    setCasesUpdateDate(CasesUpdateDate);
    setDeathsUpdateDate(DeathsUpdateDate);
  }

  @NonNull public String getSlug() {
    return Slug;
  }

  public void setSlug(@NonNull String slug) {
    Slug = slug;
  }

  public int getCases() {
    return Cases;
  }

  public void setCases(int cases) {
    Cases = cases;
  }

  public int getDeaths() {
    return Deaths;
  }

  public void setDeaths(int deaths) {
    Deaths = deaths;
  }

  public String getCasesUpdateDate() {
    return CasesUpdateDate;
  }

  public void setCasesUpdateDate(String casesUpdateDate) {
    CasesUpdateDate = casesUpdateDate;
  }

  public String getDeathsUpdateDate() {
    return DeathsUpdateDate;
  }

  public void setDeathsUpdateDate(String deathsUpdateDate) {
    DeathsUpdateDate = deathsUpdateDate;
  }
}