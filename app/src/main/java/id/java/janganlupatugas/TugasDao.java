package id.java.janganlupatugas;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TugasDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Tugas tugas);

    @Update
    void update(Tugas tugas);

    @Delete
    void delete(Tugas tugas);

    @Query("DELETE FROM tugas")
    void deleteAll();

    @Query("SELECT * FROM tugas ORDER BY tanggal ASC")
    LiveData<List<Tugas>> getAlphabetizedWords();

}
