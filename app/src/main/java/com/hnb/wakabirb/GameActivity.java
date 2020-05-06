package com.hnb.wakabirb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import static com.hnb.wakabirb.MainActivity.backgroundMusic;

public class GameActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    Boolean musicOn;
    Boolean soundEffectsOn;
    Boolean loaded;
    public static final String mOnKey = "musicOnKey";
    public static final String seOnKey = "seOnKey";

    MediaPlayer birbDeath;
    Random birbRand = new Random();
    final int[] birbDeaths = {R.raw.birb_death, R.raw.birb_death1, R.raw.birb_death2, R.raw.birb_death3,
            R.raw.birb_death4, R.raw.birb_death5, R.raw.birb_death6};
    public static int gameScore;
    int birbIndex, birbIndex1, birbIndex2, birbIndex3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        TextView score = findViewById(R.id.score);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        musicOn = sharedPreferences.getBoolean(mOnKey, true);
        soundEffectsOn = sharedPreferences.getBoolean(seOnKey, true);

        if (musicOn) {
            backgroundMusic.setVolume(0.25f, 0.25f);
            backgroundMusic.start();
        }

        final TextView time = findViewById(R.id.time);
        final int[] counter = {30};
        final int[] birbs = {R.drawable.birb, R.drawable.birb_green, R.drawable.birb_hair,
                R.drawable.birb_orange, R.drawable.birb_purple, R.drawable.birb_white,
                R.drawable.birb_rainbow};

        final ImageButton birbNests[] = {(ImageButton) findViewById(R.id.birbNest1), (ImageButton) findViewById(R.id.birbNest2), (ImageButton) findViewById(R.id.birbNest3),
                (ImageButton) findViewById(R.id.birbNest4), (ImageButton) findViewById(R.id.birbNest5), (ImageButton) findViewById(R.id.birbNest6),
                (ImageButton) findViewById(R.id.birbNest7), (ImageButton) findViewById(R.id.birbNest8), (ImageButton) findViewById(R.id.birbNest9),
                (ImageButton) findViewById(R.id.birbNest10), (ImageButton) findViewById(R.id.birbNest11), (ImageButton) findViewById(R.id.birbNest12),
                (ImageButton) findViewById(R.id.birbNest13), (ImageButton) findViewById(R.id.birbNest14), (ImageButton) findViewById(R.id.birbNest15),
                (ImageButton) findViewById(R.id.birbNest16), (ImageButton) findViewById(R.id.birbNest17), (ImageButton) findViewById(R.id.birbNest18),
                (ImageButton) findViewById(R.id.birbNest19), (ImageButton) findViewById(R.id.birbNest20), (ImageButton) findViewById(R.id.birbNest21),
                (ImageButton) findViewById(R.id.birbNest22), (ImageButton) findViewById(R.id.birbNest23), (ImageButton) findViewById(R.id.birbNest24)};

        CountDownTimer timer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                ImageButton birbNotWhacked = birbNests[birbIndex];
                birbNotWhacked.setClickable(false);
                birbNotWhacked.setImageResource(R.drawable.birb_nest);
                int rand = new Random().nextInt(birbNests.length);
                birbIndex = rand;
                ImageButton whackDatBirb = birbNests[rand];
                whackDatBirb.setImageResource(birbs[birbRand.nextInt(birbs.length)]);
                whackDatBirb.setClickable(true);

                ImageButton birbNotWhacked1 = birbNests[birbIndex1];
                birbNotWhacked1.setClickable(false);
                birbNotWhacked1.setImageResource(R.drawable.birb_nest);
                int rand1 = new Random().nextInt(birbNests.length);
                birbIndex1 = rand1;
                ImageButton whackDatBirb1 = birbNests[rand1];
                whackDatBirb1.setImageResource(birbs[birbRand.nextInt(birbs.length)]);
                whackDatBirb1.setClickable(true);

                ImageButton birbNotWhacked2 = birbNests[birbIndex2];
                birbNotWhacked2.setClickable(false);
                birbNotWhacked2.setImageResource(R.drawable.birb_nest);
                int rand2 = new Random().nextInt(birbNests.length);
                birbIndex2 = rand2;
                ImageButton whackDatBirb2 = birbNests[rand2];
                whackDatBirb2.setImageResource(birbs[birbRand.nextInt(birbs.length)]);
                whackDatBirb2.setClickable(true);

                ImageButton birbNotWhacked3 = birbNests[birbIndex3];
                birbNotWhacked3.setClickable(false);
                birbNotWhacked3.setImageResource(R.drawable.birb_nest);
                int rand3 = new Random().nextInt(birbNests.length);
                birbIndex3 = rand3;
                ImageButton whackDatBirb3 = birbNests[rand3];
                whackDatBirb3.setImageResource(birbs[birbRand.nextInt(birbs.length)]);
                whackDatBirb3.setClickable(true);

                time.setText(String.valueOf(counter[0]));
                counter[0]--;
            }

            @Override
            public void onFinish() {
                Intent resultIntent = new Intent(GameActivity.this, ResultsActivity.class);
                resultIntent.putExtra("signedIn", getIntent().getBooleanExtra("signedIn", false));
                resultIntent.putExtra("name", getIntent().getStringExtra("name"));
                resultIntent.putExtra("score", gameScore);
                startActivity(resultIntent);
                finish();
            }
        }.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (backgroundMusic != null) {
            backgroundMusic.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (musicOn) {
            backgroundMusic.start();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void onBirbWhacked(View imageButton) {
        if (soundEffectsOn) {
            birbDeath = new MediaPlayer();
            birbDeath = MediaPlayer.create(this, birbDeaths[birbRand.nextInt(birbDeaths.length)]);
            birbDeath.start();
        }
        ImageButton whackedBirb = (ImageButton) imageButton;
        int birbNestId = getResources().getIdentifier((String) imageButton.getTag(), "id", getPackageName());
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
