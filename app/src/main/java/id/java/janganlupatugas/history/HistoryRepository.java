package id.java.janganlupatugas.history;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;



public class HistoryRepository {
    private HistoryDao historyDao;
    private LiveData<List<History>> allHistory;

    HistoryRepository(Application application){
        HistoryDatabase db = HistoryDatabase.getDatabase(application);
        historyDao = db.historyDao();
        allHistory = historyDao.getAlphabetizedWords();
    }
    LiveData<List<History>> getAllHistory(){
        return allHistory;
    }
    void insert(History history){
        HistoryDatabase.databaseWriteExecutor.execute(() ->{
            historyDao.insert(history);
        });
    }
    void delete(History history){
        HistoryDatabase.databaseWriteExecutor.execute(()->{
            historyDao.delete(history);
        });
    }
}
