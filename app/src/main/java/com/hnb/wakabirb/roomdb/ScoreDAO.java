package com.hnb.wakabirb.roomdb;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface ScoreDAO {
    @Query("SELECT * FROM scores WHERE name = :name ORDER BY points")
    Score getScoresByName(String name);

    @Query("SELECT * FROM scores ORDER BY points")
    LiveData<List<Score>> getAllScores();

    @Query("Select * from scores where points=(Select max(points) from scores)")
    List<Score> getMaxScores();


    @Insert
    void insert(Score... scores);

    @Update
    void update(Score... scores);

}
