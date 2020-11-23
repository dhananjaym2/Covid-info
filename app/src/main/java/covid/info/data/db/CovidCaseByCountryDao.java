package covid.info.data.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface CovidCaseByCountryDao {

  @Insert(onConflict = REPLACE)
  void insert(CovidCaseByCountry covidCaseByCountry);

  @Query("SELECT * FROM CovidCaseByCountry ORDER BY Slug DESC LIMIT :limitCountOfRecords")
  LiveData<List<CovidCaseByCountry>> getAllCovidCaseByCountry(int limitCountOfRecords);

  @Query("SELECT * FROM CovidCaseByCountry WHERE country = :country LIMIT 2")
  CovidCaseByCountry fetchCovidCaseByCountry(String country);

  @Query("SELECT COUNT(*) FROM CovidCaseByCountry")
  int isCountriesTableEmpty();

  @Query("SELECT * FROM CovidCaseByCountry WHERE country LIKE :country OR Slug LIKE :country ORDER BY Slug LIMIT 2")
  CovidCaseByCountry getCountrySlug(String country);
}