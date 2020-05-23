package com.hnb.wakabirb;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import static com.hnb.wakabirb.MainActivity.backgroundMusic;

public class OtherActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    Boolean musicOn;
    Boolean switchedActivity;
    public static final String mOnKey = "musicOnKey";
    public static final String seOnKey = "seOnKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        musicOn = sharedPreferences.getBoolean(mOnKey, true);

        if (musicOn) {
            backgroundMusic.setVolume(0.5f, 0.5f);
            backgroundMusic.start();

        }
    }

    public void onClickBirbHistory(View button) {
        Intent historyIntent = new Intent(this, HistoryActivity.class);
        switchedActivity = true;
        startActivity(historyIntent);
        finish();
    }

    public void onClickBirbStore(View button) {
        Intent storeIntent = new Intent(this, CreditsActivity.class);
        switchedActivity = true;
        startActivity(storeIntent);
        finish();
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
        if (!switchedActivity) backgroundMusic.stop();
    }

    public void onClickGoBack(View view) {
        Intent mainIntent = new Intent(this, MainActivity.class);
        startActivity(mainIntent);
        switchedActivity = true;
        finish();
    }

}
