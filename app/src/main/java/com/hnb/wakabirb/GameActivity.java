package com.hnb.wakabirb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class GameActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    Boolean musicOn;
    Boolean soundEffectsOn;
    public static final String mOnKey = "musicOnKey";
    public static final String seOnKey = "seOnKey";
    MediaPlayer backgroundMusic;
    int gameScore;
    int birbIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        TextView score = findViewById(R.id.score);
        final TextView time = findViewById(R.id.time);
        final int[] counter = {10};
        final ImageButton birbNests[] = {(ImageButton)findViewById(R.id.birbNest1),(ImageButton)findViewById(R.id.birbNest2),(ImageButton)findViewById(R.id.birbNest3),
                                   (ImageButton)findViewById(R.id.birbNest4),(ImageButton)findViewById(R.id.birbNest5),(ImageButton)findViewById(R.id.birbNest6),
                                   (ImageButton)findViewById(R.id.birbNest7),(ImageButton)findViewById(R.id.birbNest8),(ImageButton)findViewById(R.id.birbNest9),
                                   (ImageButton)findViewById(R.id.birbNest10),(ImageButton)findViewById(R.id.birbNest11),(ImageButton)findViewById(R.id.birbNest12),
                                   (ImageButton)findViewById(R.id.birbNest13),(ImageButton)findViewById(R.id.birbNest14),(ImageButton)findViewById(R.id.birbNest15),
                                   (ImageButton)findViewById(R.id.birbNest16),(ImageButton)findViewById(R.id.birbNest17),(ImageButton)findViewById(R.id.birbNest18),
                                   (ImageButton)findViewById(R.id.birbNest19),(ImageButton)findViewById(R.id.birbNest20),(ImageButton)findViewById(R.id.birbNest21),
                                   (ImageButton)findViewById(R.id.birbNest22),(ImageButton)findViewById(R.id.birbNest23),(ImageButton)findViewById(R.id.birbNest24)};

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        musicOn = sharedPreferences.getBoolean(mOnKey,true);
        soundEffectsOn = sharedPreferences.getBoolean(seOnKey, true);

        CountDownTimer timer = new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                ImageButton birbNotWhacked = birbNests[birbIndex];
                birbNotWhacked.setClickable(false);
                birbNotWhacked.setImageResource(R.drawable.birb_nest);
                int rand = new Random().nextInt(23);
                birbIndex = rand;
                ImageButton whackDatBirb = birbNests[rand];
                whackDatBirb.setImageResource(R.drawable.birb);
                whackDatBirb.setClickable(true);
                time.setText(String.valueOf(counter[0]));
                counter[0]--;
            }

            @Override
            public void onFinish() {
                //TODO fix merge conflict
                if(backgroundMusic != null && backgroundMusic.isPlaying()){
                    backgroundMusic.stop();
                }
                Intent resultIntent = new Intent(GameActivity.this, ResultsActivity.class);
                resultIntent.putExtra("score", gameScore);
                startActivity(resultIntent);
                backgroundMusic.stop();

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

    public void onBirbWhacked(View imageButton){
        ImageButton whackedBirb = (ImageButton)imageButton;
        int birbNestId = getResources().getIdentifier((String)imageButton.getTag(), "id", getPackageName());
        TextView score = (TextView) findViewById(birbNestId);
        int count = Integer.parseInt((String) score.getText());
        count++;
        gameScore = count;
        String value = Integer.toString(count);
        score.setText(value);
        whackedBirb.setImageResource(R.drawable.birb_nest);
        whackedBirb.setClickable(false);
    }
}
