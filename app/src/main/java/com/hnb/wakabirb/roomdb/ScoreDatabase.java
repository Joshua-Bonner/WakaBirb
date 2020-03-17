package com.hnb.wakabirb.roomdb;


import android.content.Context;
import android.os.AsyncTask;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Score.class}, version = 1, exportSchema = false)

public abstract class ScoreDatabase extends RoomDatabase {
    public abstract ScoreDAO ScoreDAO();

    private static ScoreDatabase INSTANCE;

    public static synchronized ScoreDatabase getDatabase(final Context context) {
        if(INSTANCE == null) {
            synchronized (ScoreDatabase.class) {
                if(INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), ScoreDatabase.class, "score_database:").addCallback(createScoreDatabaseCallback).build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback createScoreDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }
    };

    public static void insert(Score score) {
        new AsyncTask<Score, Void, Void>() {
            protected Void doInBackground(Score... scores) {
                INSTANCE.ScoreDAO().insert(scores);
                return null;
            }
        }.execute(score);
    }
}
