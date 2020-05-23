package ga.hnbenterprises.wakabirb;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import ga.hnbenterprises.wakabirb.R;

import static ga.hnbenterprises.wakabirb.MainActivity.backgroundMusic;

public class CreditsActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    Boolean musicOn;
    Boolean switchedActivity;
    public static final String mOnKey = "musicOnKey";
    public static final String seOnKey = "seOnKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        musicOn = sharedPreferences.getBoolean(mOnKey, true);

        if (musicOn) {
            backgroundMusic.setVolume(0.5f, 0.5f);
            backgroundMusic.start();
        }
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
        Intent otherIntent = new Intent(this, OtherActivity.class);
        switchedActivity = true;
        startActivity(otherIntent);
        finish();
    }
}
