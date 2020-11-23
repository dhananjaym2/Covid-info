package covid.info.data.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface SlugWiseCaseDao {

  @Query("SELECT * FROM SlugWiseCase WHERE Slug = :Slug LIMIT 1")
  SlugWiseCase fetchCaseByCountrySlug(String Slug);

  @Insert(onConflict = REPLACE)
  void insert(SlugWiseCase slugWiseCase);

  @Update
  void update(SlugWiseCase slugWiseCase);

  //@Query("UPDATE SlugWiseCase SET Cases = :cases, CasesUpdateDate = :date WHERE Slug = :slug")
  //void updateCasesData(String slug, int cases, String date);
}