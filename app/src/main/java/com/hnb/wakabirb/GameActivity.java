package com.hnb.wakabirb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // RUNS GAME ACTIVITY FOR A SET PERIOD OF TIME THEN AUTOMATICALLY TRANSITIONS INTO RESULTS ACTIVITY
        new Timer().schedule(new TimerTask(){
            public void run() {
                GameActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        startActivity(new Intent(GameActivity.this, ResultsActivity.class));
                    }
                });
            }
        }, 10000);
    }


}
