package id.java.janganlupatugas;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Tugas.class}, version = 1, exportSchema = false)
public abstract class TugasDatabase extends RoomDatabase {
    public abstract TugasDao tugasDao();
    private static volatile TugasDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static synchronized TugasDatabase getDatabase(Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    TugasDatabase.class,"tugas_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }

//    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
//        @Override
//        public void onCreate(@NonNull SupportSQLiteDatabase db) {
//            super.onCreate(db);
//
//            // If you want to keep data through app restarts,
//            // comment out the following block
//            databaseWriteExecutor.execute(() -> {
//                // Populate the database in the background.
//                // If you want to start with more words, just add them.
//                TugasDao dao = INSTANCE.tugasDao();
//                dao.deleteAll();
//
//                Tugas word = new Tugas("Hello","test123","08/20/21");
//                dao.insert(word);
//                word = new Tugas("World","haha","08/20/21");
//                dao.insert(word);
//            });
//        }
//    };
}
