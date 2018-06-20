package com.example.bastian.actilog;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private Button logIn_btn;
    TextView enteredPin;


    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();

        setContentView(R.layout.activity_login);
        logIn_btn = (Button) findViewById(R.id.login_btn);
        logIn_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHome();
            }
        });
    }




    private void openHome() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String setPin = prefs.getString("PIN", "xxx");
        //Toast.makeText(this,setPin, Toast.LENGTH_LONG).show();

        enteredPin = findViewById(R.id.enteredPin);
        String enteredPIN = enteredPin.getText().toString();
        //Toast.makeText(this, enteredPIN , Toast.LENGTH_LONG).show();

        if (setPin.equals(enteredPIN)) {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        }

        else{
            Toast.makeText(this, "Invalid PIN" , Toast.LENGTH_LONG).show();
            enteredPin.setText("");

        }

    }
    @Override
    public void onBackPressed() {
    }
}
