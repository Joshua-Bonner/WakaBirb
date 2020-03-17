package com.hnb.wakabirb.roomdb;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.GregorianCalendar;

@Entity(tableName="scores")
public class Score {

    public Score(String name, int points) {
        id = 0;
        this.name = name;
        this.points = points;
        date = new GregorianCalendar();
    }

    public Score(int id, String name, int points, GregorianCalendar date) {
        this.id = id;
        this.name = name;
        this.points = points;
        this.date = date;
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "scoreid")
    public int id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "points")
    public int points;

    @ColumnInfo(name = "scoredate")
    public GregorianCalendar date;

}
