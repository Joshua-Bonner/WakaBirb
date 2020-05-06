package com.hnb.wakabirb.roomdb;


import android.content.Context;
import android.os.AsyncTask;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.List;

@Database(entities = {Score.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class ScoreDatabase extends RoomDatabase {

    public interface ScoreListener {
        void onScoreReturned(Score score);
    }

    public interface ScoreListListener {
        void onScoreListReturned(List<Score> scores);
    }

    public abstract ScoreDAO ScoreDAO();

    private static ScoreDatabase INSTANCE;

    public static synchronized ScoreDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ScoreDatabase.class) {
                if (INSTANCE == null) {
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
            @Override
            protected Void doInBackground(Score... scores) {
                INSTANCE.ScoreDAO().insert(scores);
                return null;
            }
        }.execute(score);
    }

    public static void update(Score score) {
        new AsyncTask<Score, Void, Void>() {
            @Override
            protected Void doInBackground(Score... scores) {
                INSTANCE.ScoreDAO().update(scores);
                return null;
            }
        }.execute(score);
    }

    public static void getScore(String name, final ScoreListener listener) {
        new AsyncTask<String, Void, Score>() {

            @Override
            protected Score doInBackground(String... names) {
                return INSTANCE.ScoreDAO().getScoresByName(names[0]);
            }

            @Override
            protected void onPostExecute(Score score) {
                super.onPostExecute(score);
                listener.onScoreReturned(score);
            }
        }.execute(name);
    }

    public static void getMaxScores(final ScoreListListener listener) {
        new AsyncTask<Void, Void, List<Score>>() {

            @Override
            protected List<Score> doInBackground(Void... voids) {
                return INSTANCE.ScoreDAO().getMaxScores();
            }

            @Override
            protected void onPostExecute(List<Score> scores) {
                listener.onScoreListReturned(scores);
            }
        }.execute();
    }
}
