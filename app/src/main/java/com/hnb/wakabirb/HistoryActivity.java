package com.hnb.wakabirb;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import static com.hnb.wakabirb.MainActivity.backgroundMusic;

public class HistoryActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    Boolean musicOn;
    public static final String mOnKey = "musicOnKey";
    public static final String seOnKey = "seOnKey";

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        TextView tv = findViewById(R.id.birbHistoryText);
        tv.setMovementMethod(new ScrollingMovementMethod());

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        musicOn = sharedPreferences.getBoolean(mOnKey, true);

        if(musicOn){
            backgroundMusic.setVolume(0.5f,0.5f);
            backgroundMusic.start();
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        if(backgroundMusic != null){
            backgroundMusic.pause();
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        if(musicOn){
            backgroundMusic.start();
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        backgroundMusic.stop();
    }

    public void onClickGoBack(View view) {
        Intent otherIntent = new Intent(this, OtherActivity.class);
        startActivity(otherIntent);
    }
}
