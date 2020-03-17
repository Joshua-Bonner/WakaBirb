package com.hnb.wakabirb.roomdb;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ScoreViewModel extends AndroidViewModel {
    private LiveData<List<Score>> scores;

    public ScoreViewModel (Application application) {
        super(application);
    }

    public void filterScores(String name) {
        if(name.equals("")) {
            getAllScores();
        }
        else {
            scores = ScoreDatabase.getDatabase(getApplication()).ScoreDAO().getScoresByName(name);
        }
    }

    public void getAllScores() {
        scores = ScoreDatabase.getDatabase(getApplication()).ScoreDAO().getAllScores();
    }
}
