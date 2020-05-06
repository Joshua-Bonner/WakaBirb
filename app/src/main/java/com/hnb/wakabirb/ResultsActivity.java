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
import com.hnb.wakabirb.roomdb.Score;
import com.hnb.wakabirb.roomdb.ScoreDatabase;

import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

import static com.hnb.wakabirb.MainActivity.backgroundMusic;

public class ResultsActivity extends AppCompatActivity {


    private GoogleSignInClient signInClient;
    Boolean switchedActivity;
    SharedPreferences sharedPreferences;
    Boolean musicOn;
    Boolean soundEffectsOn;
    Boolean signedIn;

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
        signedIn = getIntent().getBooleanExtra("signedIn", false) && signInAccount != null; //make sure the user signed in and we still have an account

        if (signedIn) {
            Games.getLeaderboardsClient(this, signInAccount).submitScore(getString(R.string.leaderboard_top_scores), getIntent().getIntExtra("score", 0)); //submit the score to google play games
        }

        ScoreDatabase.getDatabase(this);

        //first update the player's top score
        ScoreDatabase.getScore(getIntent().getStringExtra("name"), new ScoreDatabase.ScoreListener() {
            @Override
            public void onScoreReturned(Score score) {
                if (score == null) { //the user has never played a game before, so we create a new one
                    score = new Score(getIntent().getStringExtra("name"), getIntent().getIntExtra("score", 0), new GregorianCalendar());
                    ScoreDatabase.insert(score);
                } else if (getIntent().getIntExtra("score", 0) > score.points) {
                    score.points = getIntent().getIntExtra("score", 0);
                    ScoreDatabase.update(score);
                }
                ((TextView) findViewById(R.id.playerTopScore)).setText("Your Top Score: " + score.points);
            }
        });

        //next update the device's top score

        ScoreDatabase.getMaxScores(new ScoreDatabase.ScoreListListener() {
            @Override
            public void onScoreListReturned(List<Score> scores) {
                if (scores.size() > 0) { //we have at least 1 max score
                    //let's pick a random user and display their score in case there's more than 1
                    Score toDisplay = scores.get(new Random().nextInt(scores.size()));
                    if (getIntent().getIntExtra("score", 0) < toDisplay.points) { //see if the user that just played is the high score or not
                        ((TextView) findViewById(R.id.deviceTopScore)).setText("Local Top Score: " + toDisplay.points + " By: " + toDisplay.name);
                    } else {
                        ((TextView) findViewById(R.id.deviceTopScore)).setText("Local Top Score: " + getIntent().getIntExtra("score", 0) + " By: " + getIntent().getStringExtra("name"));
                    }
                } else {
                    ((TextView) findViewById(R.id.deviceTopScore)).setText("Local Top Score: " + getIntent().getIntExtra("score", 0) + " By: " + getIntent().getStringExtra("name")); //we don't have any scores yet so we'll set it to the user's score
                }
            }
        });

        //then we'll update the global top score if the user is signed in


        //now we hide the leaderboard button if the player isn't signed in
        if (!signedIn) {
            findViewById(R.id.leaderboard).setVisibility(View.GONE);
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
        if (!switchedActivity) backgroundMusic.stop();
    }

    public void onClickPlayAgain(View view) {
        backgroundMusic.reset();
        Intent mainActivity = new Intent(ResultsActivity.this, MainActivity.class);
        switchedActivity = true;
        startActivity(mainActivity);
        finish();
    }

    public void onClickQuitGame(View view) {
        backgroundMusic.stop();
        this.finishAffinity();
        System.exit(0);
    }
}
