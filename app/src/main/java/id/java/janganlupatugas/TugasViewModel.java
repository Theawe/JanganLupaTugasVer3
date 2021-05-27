package id.java.janganlupatugas;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class TugasViewModel extends AndroidViewModel {

    private TugasRepository repository;
    private LiveData<List<Tugas>> allTugas;
    public TugasViewModel(@NonNull Application application) {
        super(application);
        repository = new TugasRepository(application);
        allTugas = repository.getAllTugas();
    }

    public LiveData<List<Tugas>> getAllTugas(){return allTugas;}

    public void insert(Tugas tugas){
        repository.insert(tugas);
    }

    public void delete(Tugas tugas){repository.delete(tugas);}


}
