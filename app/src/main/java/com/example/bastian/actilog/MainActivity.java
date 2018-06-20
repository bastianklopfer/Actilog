package com.example.bastian.actilog;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DataBaseHelper myDb;
    private FloatingActionButton plusFab;
    int inserted;
    boolean pinentered = false;
    boolean secure;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        myDb = new DataBaseHelper(this);

        PresetTable();
        OpenLogin();
    }

    public void OpenLogin(){
        //if Pin is enabled, open Login page. but only when starting the app.
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        secure = prefs.getBoolean("PIN_switch", false);
        //Toast.makeText(this,sSetting, Toast.LENGTH_LONG).show();

        if (!secure)
        {
           // Toast.makeText(this,"PIN disabled", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        }

        if (secure && !pinentered)
        {
            pinentered = true;
            //Toast.makeText(this,"PIN enabled", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);

        }

    }

    public void PresetTable() {

        boolean firstRun = getSharedPreferences("preferences", MODE_PRIVATE).getBoolean("firstrun", true);
        if (firstRun) {
            //set the firstrun to false so the next run can see it.
            getSharedPreferences("preferences", MODE_PRIVATE).edit().putBoolean("firstrun", false).commit();
        }

        if (firstRun) {
            inserted = myDb.addCategory("Uni", "0");
            inserted = myDb.addCategory("Work", "0");
            inserted = myDb.addCategory("Sport", "0");
            inserted = myDb.addCategory("Freetime", "0");
        }
    }


}