package com.hnb.wakabirb;

import android.content.Intent;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.LeaderboardsClient;
import com.google.android.gms.games.PlayersClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import static com.hnb.wakabirb.MainActivity.backgroundMusic;

public class ResultsActivity extends AppCompatActivity {


    private GoogleSignInClient signInClient;

    SharedPreferences sharedPreferences;
    Boolean musicOn;
    Boolean soundEffectsOn;

    public static final String mOnKey = "musicOnKey";
    public static final String seOnKey = "seOnKey";
    private static final int RC_LEADERBOARD_UI = 9004;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        Intent intent = getIntent();
        int gameScore = intent.getIntExtra("score", 0);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        musicOn = sharedPreferences.getBoolean(mOnKey, true);
        soundEffectsOn = sharedPreferences.getBoolean(seOnKey, true);

        if (musicOn) {
            backgroundMusic.setVolume(0.50f, 0.50f);
            backgroundMusic.start();
        }

        signInClient = GoogleSignIn.getClient(this, new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN).build()); //sign in to google play games
        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(this);

        if (signInAccount != null) {
            Games.getLeaderboardsClient(this, signInAccount).submitScore(getString(R.string.leaderboard_top_scores), getIntent().getIntExtra("score", 0));
        }

        TextView finalScore = findViewById(R.id.finalScore);
        finalScore.setText("Score: " + String.valueOf(gameScore));
    }

    public void showLeaderboard(View view) {
        Games.getLeaderboardsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                .getLeaderboardIntent(getString(R.string.leaderboard_top_scores))
                .addOnSuccessListener(new OnSuccessListener<Intent>() {
                    @Override
                    public void onSuccess(Intent intent) {
                        startActivityForResult(intent, RC_LEADERBOARD_UI);
                    }
                });
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
        backgroundMusic.stop();
    }

    public void onClickPlayAgain(View view) {
        backgroundMusic.reset();
        Intent mainIntent = new Intent(this, MainActivity.class);
        startActivity(mainIntent);
    }

    public void onClickQuitGame(View view) {
        backgroundMusic.stop();
        this.finishAffinity();
        System.exit(0);
    }
}
