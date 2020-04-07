package com.hnb.wakabirb;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.LeaderboardsClient;
import com.google.android.gms.games.PlayersClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class ResultsActivity extends AppCompatActivity {


    private GoogleSignInClient signInClient;

    SharedPreferences sharedPreferences;
    Boolean musicOn;
    Boolean soundEffectsOn;

    public static final String mOnKey = "musicOnKey";
    public static final String seOnKey = "seOnKey";

    MediaPlayer backgroundMusic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        musicOn = sharedPreferences.getBoolean(mOnKey,true);
        soundEffectsOn = sharedPreferences.getBoolean(seOnKey, true);

        if(musicOn && backgroundMusic == null){
            backgroundMusic = new MediaPlayer();
            backgroundMusic = MediaPlayer.create(this, R.raw.legrandchase);
            backgroundMusic.start();
            backgroundMusic.setLooping(true);
        }
        signInClient = GoogleSignIn.getClient(this, new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN).build()); //sign in to google play games
        //sign in to your google account
        //update your leaderboard score
        GoogleSignInAccount signInAccount = null;
        if(GoogleSignIn.getLastSignedInAccount(this) == null) {
            GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN).requestEmail().build();
            GoogleSignInClient signInClient = GoogleSignIn.getClient(this, signInOptions);
            Task<GoogleSignInAccount> task = signInClient.silentSignIn();
            if(task.isSuccessful()) {
                signInAccount = task.getResult();
            }
            else {
                task.addOnCompleteListener(new OnCompleteListener<GoogleSignInAccount>() {
                    @Override
                    public void onComplete(@NonNull Task<GoogleSignInAccount> task) {
                        try {
                            GoogleSignInAccount account = task.getResult(ApiException.class);
                        } catch (ApiException ex) {
                            Log.e("account_sign_in", ex.toString());
                            Log.e("account_sign_in", "" + ex.getStatusCode());
                        }
                    }
                });
            }
        }
        else { //they're already signed in
            signInAccount = GoogleSignIn.getLastSignedInAccount(this);
        }
        Games.getLeaderboardsClient(this, signInAccount).submitScore(getString(R.string.leaderboard_top_scores), getIntent().getIntExtra("score", 420));
    }
}
