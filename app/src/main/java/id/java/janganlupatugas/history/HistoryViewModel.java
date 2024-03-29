package id.java.janganlupatugas.history;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;



public class HistoryViewModel extends AndroidViewModel {
    private HistoryRepository repository;
    private LiveData<List<History>> allHistory;
    public HistoryViewModel(@NonNull Application application) {
        super(application);
        repository = new HistoryRepository(application);
        allHistory = repository.getAllHistory();
    }

    public LiveData<List<History>> getAllHistory(){return allHistory;}

    public void insert(History history){
        repository.insert(history);
    }

    public void delete(History history){repository.delete(history);}

}
