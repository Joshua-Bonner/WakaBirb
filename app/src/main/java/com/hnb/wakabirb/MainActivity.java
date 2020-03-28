package com.hnb.wakabirb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;

    Boolean musicOn;
    Boolean soundEffectsOn;
    public static final String myPreference = "mypref";
    public static final String mOnString = "musicOnKey";
    public static final String seOn = "seOnKey";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);


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

        sharedPreferences = getSharedPreferences(myPreference, Context.MODE_PRIVATE);
        if (sharedPreferences.contains(mOnString)){
            musicOn = sharedPreferences.getBoolean(mOnString, true);    //get value set by user, else set musicOn to true
        }
        if ( sharedPreferences.contains(seOn)){
            soundEffectsOn = sharedPreferences.getBoolean(seOn, true);  //get value set by user, else set soundEffectsOn to true
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
}
