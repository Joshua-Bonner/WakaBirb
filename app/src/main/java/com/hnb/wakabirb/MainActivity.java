package com.hnb.wakabirb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText userName = findViewById(R.id.EditUsrName);
        final Button startGameBtn = findViewById(R.id.StartButton);

        //Add textWatcher to notify the user
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

            }
        });
    }

    public void StartGame(View button){
        Intent gameIntent = new Intent(this, GameActivity.class);
        startActivity(gameIntent);
    }
}
