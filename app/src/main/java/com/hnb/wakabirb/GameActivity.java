package com.hnb.wakabirb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        TextView score = findViewById(R.id.score);
        final TextView time = findViewById(R.id.time);
        final int[] counter = {10};

        CountDownTimer timer = new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                time.setText(String.valueOf(counter[0]));
                counter[0]--;
            }

            @Override
            public void onFinish() {
                startActivity(new Intent(GameActivity.this, ResultsActivity.class));
            }
        }.start();
    }


}
