package com.hnb.wakabirb.roomdb;

import java.util.List;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface ScoreDAO {
    @Query("SELECT * FROM scores WHERE name = :name ORDER BY points")
    LiveData<List<Score>> getScoresByName(String name);

    @Query("SELECT * FROM scores ORDER BY points")
    LiveData<List<Score>> getAllScores();

    @Insert
    void insert(Score... scores);

}
