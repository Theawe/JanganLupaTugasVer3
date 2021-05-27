package id.java.janganlupatugas;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class TugasRepository {

    private TugasDao tugasDao;
    private LiveData<List<Tugas>> allTugas;

    TugasRepository(Application application){
        TugasDatabase db = TugasDatabase.getDatabase(application);
        tugasDao = db.tugasDao();
        allTugas = tugasDao.getAlphabetizedWords();
    }
    LiveData<List<Tugas>> getAllTugas(){
        return allTugas;
    }
    void insert(Tugas tugas){
        TugasDatabase.databaseWriteExecutor.execute(() ->{
            tugasDao.insert(tugas);
        });
    }
    void delete(Tugas tugas){
        TugasDatabase.databaseWriteExecutor.execute(()->{
            tugasDao.delete(tugas);
        });
    }


}
