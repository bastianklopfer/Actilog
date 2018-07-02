package com.example.bastian.actilog;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {

    DataBaseHelper myDb;
    TextView dateRow1, dateRow2, dateRow3,dateRow4,dateRow5,dateRow6, dateRow7, dateRow8,dateRow9,dateRow10;
    TextView activityRow1, activityRow2, activityRow3, activityRow4, activityRow5,activityRow6, activityRow7, activityRow8, activityRow9, activityRow10;
    TextView durationRow1, durationRow2, durationRow3,durationRow4,durationRow5,durationRow6, durationRow7, durationRow8,durationRow9,durationRow10;

    private FloatingActionButton plusFab;
    private FloatingActionButton reviewFab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        myDb = new DataBaseHelper(this);

        plusFab = (FloatingActionButton) findViewById(R.id.plusFab);
        reviewFab = (FloatingActionButton) findViewById(R.id.reviewFab);
        dateRow1 = (TextView) findViewById(R.id.dateRow1);
        dateRow2 = (TextView) findViewById(R.id.dateRow2);
        dateRow3 = (TextView) findViewById(R.id.dateRow3);
        dateRow4 = (TextView) findViewById(R.id.dateRow4);
        dateRow5 = (TextView) findViewById(R.id.dateRow5);
        dateRow6 = (TextView) findViewById(R.id.dateRow6);
        dateRow7 = (TextView) findViewById(R.id.dateRow7);
        dateRow8 = (TextView) findViewById(R.id.dateRow8);
        dateRow9 = (TextView) findViewById(R.id.dateRow9);
        dateRow10 = (TextView) findViewById(R.id.dateRow10);

        activityRow1 = (TextView) findViewById(R.id.activityRow1);
        activityRow2 = (TextView) findViewById(R.id.activityRow2);
        activityRow3 = (TextView) findViewById(R.id.activityRow3);
        activityRow4 = (TextView) findViewById(R.id.activityRow4);
        activityRow5 = (TextView) findViewById(R.id.activityRow5);
        activityRow6 = (TextView) findViewById(R.id.activityRow6);
        activityRow7 = (TextView) findViewById(R.id.activityRow7);
        activityRow8 = (TextView) findViewById(R.id.activityRow8);
        activityRow9 = (TextView) findViewById(R.id.activityRow9);
        activityRow10 = (TextView) findViewById(R.id.activityRow10);

        durationRow1 = (TextView) findViewById(R.id.durationRow1);
        durationRow2 = (TextView) findViewById(R.id.durationRow2);
        durationRow3 = (TextView) findViewById(R.id.durationRow3);
        durationRow4 = (TextView) findViewById(R.id.durationRow4);
        durationRow5 = (TextView) findViewById(R.id.durationRow5);
        durationRow6 = (TextView) findViewById(R.id.durationRow6);
        durationRow7 = (TextView) findViewById(R.id.durationRow7);
        durationRow8 = (TextView) findViewById(R.id.durationRow8);
        durationRow9 = (TextView) findViewById(R.id.durationRow9);
        durationRow10 = (TextView) findViewById(R.id.durationRow10);



        setTable();


        plusFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddNew();
            }
        });
        reviewFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openReviewActivity();
            }
        });
    }

    public void openAddNew() {


        Intent intent = new Intent(this, AddNew.class);
        startActivity(intent);
    }
    public void openReviewActivity () {
        Intent intent1 = new Intent(this, ReviewActivity.class);
        startActivity(intent1);
    }

    public void setTable() {
        //retrieve the latest ten activities and set the rows individually by the fillRow function:
        //find out the highest Activity ID --> the most recent activity
        int highestId =0;
        int currentId;
        int currentId2;
        String dateR = "---";
        String nameR = "------";
        String durationR = "---";
        Cursor cursor = myDb.readActivities();


                Cursor cursor2 = myDb.readActivitiesbyDate();
           int i =1;
                while (cursor2.moveToNext()) {
                    dateR = cursor2.getString(3);
                    nameR = cursor2.getString(1);
                    durationR = (cursor2.getString(7));


                    //convert dateR into a better readable format:
                    dateR = Character.toString(dateR.charAt(6)) + Character.toString(dateR.charAt(7)) + "." + Character.toString(dateR.charAt(4)) + Character.toString(dateR.charAt(5)) + "." + Character.toString(dateR.charAt(0)) + Character.toString(dateR.charAt(1)) + Character.toString(dateR.charAt(2)) + Character.toString(dateR.charAt(3));
                    //convert duration into a better readable format:
                    int durationInt = Integer.parseInt(durationR);
                    int hours = durationInt / 60;
                    int minutes = durationInt - hours * 60;

                    if (String.valueOf(hours).length() == 1 && String.valueOf(minutes).length() == 1) {
                        durationR = "0" + Integer.toString(hours) + ":0" + Integer.toString(minutes);
                    } else if (Integer.toString(hours).length() == 2 && Integer.toString(minutes).length() == 1) {
                        durationR = Integer.toString(hours) + ":0" + Integer.toString(minutes);
                    } else if (Integer.toString(hours).length() == 1 && Integer.toString(minutes).length() == 2) {
                        durationR = "0" + Integer.toString(hours) + ":" + Integer.toString(minutes);
                    } else {
                        durationR = Integer.toString(hours) + ":" + Integer.toString(minutes);
                    }

                    fillRow(i, dateR, nameR, durationR);
                    i++;
                }

           }
    public void fillRow(int RowNr, String date, String name, String duration){
//fill the row of the table with what the function setTable gave.
switch (RowNr){
    case 1: dateRow1.setText(date);
    activityRow1.setText(name);
    durationRow1.setText(duration);
        break;
    case 2:dateRow2.setText(date);
        activityRow2.setText(name);
        durationRow2.setText(duration);
        break;
    case 3:dateRow3.setText(date);
        activityRow3.setText(name);
        durationRow3.setText(duration);
        break;
    case 4:dateRow4.setText(date);
        activityRow4.setText(name);
        durationRow4.setText(duration);
        break;
    case 5:dateRow5.setText(date);
        activityRow5.setText(name);
        durationRow5.setText(duration);
        break;
    case 6:dateRow6.setText(date);
        activityRow6.setText(name);
        durationRow6.setText(duration);
        break;
    case 7:dateRow7.setText(date);
        activityRow7.setText(name);
        durationRow7.setText(duration);
        break;
    case 8:dateRow8.setText(date);
        activityRow8.setText(name);
        durationRow8.setText(duration);
        break;
    case 9:dateRow9.setText(date);
        activityRow9.setText(name);
        durationRow9.setText(duration);
        break;
    case 10:dateRow10.setText(date);
        activityRow10.setText(name);
        durationRow10.setText(duration);
        break;

}


    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    //Following class let's the Settings button open the SettingsCass which still must be written
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == R.id.action_settings)
        {       Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
    }
}
