package com.hnb.wakabirb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;


public class GameActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    Boolean musicOn;
    Boolean soundEffectsOn;
    public static final String mOnKey = "musicOnKey";
    public static final String seOnKey = "seOnKey";
    MediaPlayer backgroundMusic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        TextView score = findViewById(R.id.score);
        final TextView time = findViewById(R.id.time);
        final int[] counter = {10};

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        musicOn = sharedPreferences.getBoolean(mOnKey,true);
        soundEffectsOn = sharedPreferences.getBoolean(seOnKey, true);

        CountDownTimer timer = new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                time.setText(String.valueOf(counter[0]));
                counter[0]--;
            }

            @Override
            public void onFinish() {
                if(backgroundMusic != null && backgroundMusic.isPlaying()){
                    backgroundMusic.stop();
                }

                startActivity(new Intent(GameActivity.this, ResultsActivity.class));
            }
        }.start();

        if(musicOn && backgroundMusic == null){
            backgroundMusic = new MediaPlayer();
            backgroundMusic = MediaPlayer.create(this, R.raw.legrandchase);
            backgroundMusic.start();
            backgroundMusic.setVolume(0.060f,0.060f);
            backgroundMusic.setLooping(true);

        }
    }




}
