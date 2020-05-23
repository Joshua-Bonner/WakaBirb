package ga.hnbenterprises.wakabirb;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import ga.hnbenterprises.wakabirb.R;

public class HistoryActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    Boolean musicOn;
    Boolean switchedActivity;
    public static final String mOnKey = "musicOnKey";
    public static final String seOnKey = "seOnKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        TextView tv = findViewById(R.id.birbHistoryText);
        tv.setMovementMethod(new ScrollingMovementMethod());

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        musicOn = sharedPreferences.getBoolean(mOnKey, true);

        if (musicOn) {
            MainActivity.backgroundMusic.setVolume(0.5f, 0.5f);
            MainActivity.backgroundMusic.start();
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (MainActivity.backgroundMusic != null) {
            MainActivity.backgroundMusic.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (musicOn) {
            MainActivity.backgroundMusic.start();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!switchedActivity) MainActivity.backgroundMusic.stop();
    }

    public void onClickGoBack(View view) {
        Intent otherIntent = new Intent(this, OtherActivity.class);
        switchedActivity = true;
        startActivity(otherIntent);
        finish();
    }
}
