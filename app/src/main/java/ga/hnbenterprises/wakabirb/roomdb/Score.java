package ga.hnbenterprises.wakabirb.roomdb;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.GregorianCalendar;

@Entity(tableName = "scores")
public class Score {

    public Score(String name, int points, GregorianCalendar date) {
        this.name = name;
        this.points = points;
        this.date = date;
    }

    @PrimaryKey
    @NonNull    //AS was yelling cause its a PK
    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "points")
    public int points;


    @ColumnInfo(name = "scoredate")
    public GregorianCalendar date;

}
