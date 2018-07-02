package com.example.bastian.actilog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class ReviewActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, AdapterView.OnItemSelectedListener{

    DataBaseHelper myDb;
    Button pickStartReview;
    Button pickEndReview;
    Button addAccReview;
    TextView displayStartReview;
    TextView displayEndReview;
    TextView chosenCatsTV;
    Spinner chooseCat;
    Spinner chooseAct;

    boolean startend;

    int day, month, year, hour, minute;
    int dayFinal, monthFinal, yearFinal;

    String sDate, eDate;
    String [] categories;
    String chosenCats = "";
    String displayCats;


    //Only show categories that are not child categories?
    //Then in result activity the child categories of the certain parentcategory will be displayed separately.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        myDb = new DataBaseHelper(this);

        pickStartReview = (Button) findViewById(R.id.pickStartReview);
        pickEndReview = (Button) findViewById(R.id.pickEndReview);
        addAccReview = (Button) findViewById(R.id.addAccReview);
        chooseCat = (Spinner) findViewById(R.id.chooseCatReview);
        displayStartReview = (TextView) findViewById(R.id.displayStartReview);
        displayEndReview = (TextView) findViewById(R.id.displayEndReview);
        chosenCatsTV = (TextView) findViewById(R.id.chosenCatsTV);

        setTitle("Review Activities");

        Dates();
        ChooseCategories();
        addAccReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LaunchReview();
            }
        });

    }

    public void Dates() {
        pickStartReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startend = false;
                Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(ReviewActivity.this, ReviewActivity.this,
                        year, month, day);
                datePickerDialog.show();

            }
        });

        pickEndReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startend = true;
                Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(ReviewActivity.this, ReviewActivity.this,
                        year, month, day);
                datePickerDialog.show();
            }
        });

    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        yearFinal = i;
        monthFinal = i1 + 1;
        dayFinal = i2;

        Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);

        if (!startend) {
            int monthLength = String.valueOf(monthFinal).length();
            int dayLength = String.valueOf(dayFinal).length();
            if (monthLength == 1 && dayLength == 1) {
                sDate = "" + yearFinal + "0" + monthFinal + "0" + dayFinal;
            } else if (monthLength == 2 && dayLength == 1) {
                sDate = "" + yearFinal + "" + monthFinal + "0" + dayFinal;
            } else if (monthLength == 1 && dayLength == 2) {
                sDate = ""+yearFinal + "0" + monthFinal + "" + dayFinal;
            } else {
                sDate = ""+yearFinal + "" + monthFinal + "" + dayFinal;
            }

            displayStartReview.setText("Start date:\t\t" + yearFinal + "/" + monthFinal + "/" + dayFinal);
        }
        if (startend) {
            int monthLength = String.valueOf(monthFinal).length();
            int dayLength = String.valueOf(dayFinal).length();
            if (monthLength == 1 && dayLength == 1) {
                eDate = "" + yearFinal + "0" + monthFinal + "0" + dayFinal;
            } else if (monthLength == 2 && dayLength == 1) {
                eDate = "" + yearFinal + "" + monthFinal + "0" + dayFinal;
            } else if (monthLength == 1 && dayLength == 2) {
                eDate = ""+yearFinal + "0" + monthFinal + "" + dayFinal;
            } else {
                eDate = ""+yearFinal + "" + monthFinal + "" + dayFinal;
            }
            displayEndReview.setText("End date:\t\t" + yearFinal + "/" + monthFinal + "/" + dayFinal);
        }

        }

    public void ChooseCategories () {
        //fill in all the categories into the spinner
        Cursor cursor = myDb.readCategories();

        chooseCat.setOnItemSelectedListener(this);

        StringBuffer buffer = new StringBuffer();
        buffer.append("Click to choose Categories-");
        buffer.append("select all-");
        while (cursor.moveToNext()) {
            if(cursor.getString(2).equals("0")) {
                buffer.append(cursor.getString(1) + "-");
            }
        }
        //also add "all" and "delete" to chose all categories
        buffer.append("delete selection");
        String catString = buffer.toString();
        categories = catString.split("-");
        ArrayAdapter<String> gameKindArray= new ArrayAdapter<String>(ReviewActivity.this,android.R.layout.simple_spinner_item, categories);
        gameKindArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        chooseCat.setAdapter(gameKindArray);
        fillTV();
        //fill the chosen categories to the textView underneath
    }

    public void fillTV(){
        ArrayAdapter<String> gameKindArray= new ArrayAdapter<String>(ReviewActivity.this,android.R.layout.simple_spinner_item, categories);
        gameKindArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        chooseCat.setAdapter(gameKindArray);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        if (!item.equals("select all") && !item.equals("delete selection") && !item.equals("Click to choose Categories")){
            //selected item should get written into textview and hidden in spinner:
            chosenCats = chosenCats + item + ", ";
            displayCats = "Chosen Categories: " + chosenCats;
            chosenCatsTV.setText(displayCats);

            List<String> list = new ArrayList<String>(Arrays.asList(categories));
            list.remove(item);
            categories = list.toArray(new String[0]);
            fillTV();
        }
        if (item.equals("select all")){
            //Bring all items to text view,
            Cursor cursor = myDb.readCategories();
            StringBuffer buffer = new StringBuffer();
            while (cursor.moveToNext()) {
                if(cursor.getString(2).equals("0")) {

                    buffer.append(cursor.getString(1) + "-");
                }
            }
            String catString = buffer.toString();
            categories = catString.split("-");
            chosenCats = Arrays.toString(categories);
            //remove brackets
            chosenCats = chosenCats.replace("[", "");
            chosenCats = chosenCats.replace("]", "");

            displayCats = "Chosen Categories: " + chosenCats;
            chosenCatsTV.setText(displayCats);

            // delete "select all" and all other categories, keep delete selection from spinner
            Arrays.fill( categories, null);
            categories = new String[2];
            categories [0] = "Click to choose Categories";
            categories [1] = "delete selection";
            ArrayAdapter<String> gameKindArray= new ArrayAdapter<String>(ReviewActivity.this,android.R.layout.simple_spinner_item, categories);
            gameKindArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            chooseCat.setAdapter(gameKindArray);

        }
        if (item.equals("delete selection")){
            //Delete everything from text view and fill the spinner with all the categories
            chosenCats = "";
            displayCats = "Chosen Categories: ";
            chosenCatsTV.setText(displayCats);
            Cursor cursor = myDb.readCategories();
            StringBuffer buffer = new StringBuffer();
            buffer.append("Click to choose Categories-");
            buffer.append("select all-");
            while (cursor.moveToNext()) {
                if(cursor.getString(2).equals("0")) {
                    buffer.append(cursor.getString(1) + "-");
                }
            }
            //also add "all" and "delete" to chose all categories
            buffer.append("delete selection");
            String catString = buffer.toString();
            categories = catString.split("-");
            ArrayAdapter<String> gameKindArray= new ArrayAdapter<String>(ReviewActivity.this,android.R.layout.simple_spinner_item, categories);
            gameKindArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            chooseCat.setAdapter(gameKindArray);
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.addactivity, menu);
        return super.onCreateOptionsMenu(menu);


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == R.id.action_home)
        {       Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    public void LaunchReview() {
        if (sDate != null && !sDate.isEmpty() &&
                eDate != null && !eDate.isEmpty()
                ) {
            if (Integer.valueOf(sDate) > Integer.valueOf(eDate)) {
                Toast.makeText(ReviewActivity.this, "Start date must be before end date or the same.", Toast.LENGTH_LONG).show();
            }
            else if (chosenCats.isEmpty()) {
                Toast.makeText(ReviewActivity.this, "Chose a Category to continue", Toast.LENGTH_LONG).show();
            } else {
                Intent intent = new Intent(this, ResultActivity.class);
                intent.putExtra("CATS", chosenCats);
                intent.putExtra("START", sDate);
                intent.putExtra("END", eDate);
                startActivity(intent);
            }

        }
        else Toast.makeText(ReviewActivity.this, "Enter start and end dates.", Toast.LENGTH_LONG).show();
            }
}
