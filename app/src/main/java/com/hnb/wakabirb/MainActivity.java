package com.hnb.wakabirb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    Boolean musicOn;
    Boolean soundEffectsOn;

    public static final String mOnKey = "musicOnKey";
    public static final String seOnKey = "seOnKey";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Deprecated but in our notes and works compared to other examples I found online
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        musicOn = sharedPreferences.getBoolean(mOnKey,true);
        soundEffectsOn = sharedPreferences.getBoolean(seOnKey, true);

        System.out.println("*****************************Shared Preferences stuff*****************************************");
        System.out.println("##########is music enabled: " + musicOn);
        System.out.println("##########is sound effects enabled: " + soundEffectsOn);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle(null);


        EditText userName = findViewById(R.id.EditUsrName);
        final Button startGameBtn = findViewById(R.id.StartButton);

        // THE FOLLOWING INSURES THAT A USER CANNOT ENTER INTO THE GAME WITHOUT FIRST
        // PUTTING IN A VALUE FOR USERNAME
        userName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence userName, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence userName, int start, int before, int count) {
                if(userName.toString().trim().length()==0) {
                    startGameBtn.setEnabled(false);
                    startGameBtn.setVisibility(View.INVISIBLE);
                    Toast.makeText(MainActivity.this, "Text can not be empty", Toast.LENGTH_SHORT).show();
                } else {
                    startGameBtn.setVisibility(View.VISIBLE);
                    startGameBtn.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable userName) {
                // TODO: QUERY FOR THE EXISTENCE OF THE USERNAME AND RETRIEVE ASSOCIATED DATA
            }
        });


            if(musicOn){
                //TODO: start background music

            }

            if(soundEffectsOn){
                //TODO: start sound effects?? maybe here maybe not....
            }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void onClickStartGame(View button){
        Intent gameIntent = new Intent(this, GameActivity.class);
        startActivity(gameIntent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        int id = menuItem.getItemId();

        if(id == R.id.menu_settings){
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
        }
        return true;
    }

    @Override
    protected void onPause(){
        super.onPause();
        SharedPreferences.Editor preferencesEditor = sharedPreferences.edit();
        preferencesEditor.putBoolean(mOnKey, musicOn);
        preferencesEditor.putBoolean(seOnKey, soundEffectsOn);
        preferencesEditor.apply();  //apply saves async whereas commit saves sync
    }
}
