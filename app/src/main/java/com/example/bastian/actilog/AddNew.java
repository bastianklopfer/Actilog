package com.example.bastian.actilog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;


import android.text.format.DateFormat;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;


public class AddNew extends AppCompatActivity implements
        DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    DataBaseHelper myDb;
    Button pickStart;
    Button pickEnd;
    Button addAcc;
    FloatingActionButton addCatBtn;
    TextView displayStart;
    TextView displayEnd;
    Spinner chooseCat;
    AutoCompleteTextView chooseAct;


    boolean startend;

    int day, month, year, hour, minute;
    int dayFinal, monthFinal, yearFinal, hourFinal, minuteFinal;
    int monthLength, dayLength, hourLength, minLength;

    String sDate, sTime, eDate, eTime;
    String sDateAndTime, eDateAndTime;
    String[] categories, categoriesPp, activities;
    Date starting, ending;
    long duration = 0;
    String durationS;
    String categoryID, categoryIDP;
    String CatId;
    String EnteredCat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_add_new);

        myDb = new DataBaseHelper(this);

        pickStart = (Button) findViewById(R.id.pickStart);
        pickEnd = (Button) findViewById(R.id.pickEnd);
        addAcc = (Button) findViewById(R.id.addAcc);
        addCatBtn = (FloatingActionButton) findViewById(R.id.addCatBttn);
        displayStart = (TextView) findViewById(R.id.displayStart);
        displayEnd = (TextView) findViewById(R.id.displayEnd);
        chooseCat = (Spinner) findViewById(R.id.actvCat);
        chooseAct = (AutoCompleteTextView) findViewById(R.id.actvAct);

        setTitle("Add Activity");

        SetStartAndEnd();   // for better overview, create new method!
        AddData();
        ChooseCategory();
        ChooseActivity();
        CreateNewCategory();
    }

    public void SetStartAndEnd() {
        pickStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startend = false;
                Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(AddNew.this, AddNew.this,
                        year, month, day);
                datePickerDialog.show();

            }
        });

        pickEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startend = true;
                Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(AddNew.this, AddNew.this,
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

        TimePickerDialog timePickerDialog = new TimePickerDialog(AddNew.this, AddNew.this,
                hour, minute, DateFormat.is24HourFormat(this));
        timePickerDialog.show();


    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        hourFinal = i;
        minuteFinal = i1;

        monthLength = String.valueOf(monthFinal).length();
        dayLength = String.valueOf(dayFinal).length();
        hourLength = String.valueOf(hourFinal).length();
        minLength = String.valueOf(minuteFinal).length();


        if (!startend) {

            displayStart.setText("Start date & time:\t\t" + yearFinal + "/" +
                    monthFinal + "/" +
                    dayFinal + ",\t\t" +
                    hourFinal + ":" +
                    minuteFinal);


            if (monthLength == 1 && dayLength == 1) {
                sDate = yearFinal + "0" + monthFinal + "0" + dayFinal;
            } else if (monthLength == 2 && dayLength == 1) {
                sDate = yearFinal + "" + monthFinal + "0" + dayFinal;
            } else if (monthLength == 1 && dayLength == 2) {
                sDate = yearFinal + "0" + monthFinal + "" + dayFinal;
            } else {
                sDate = yearFinal + "" + monthFinal + "" + dayFinal;
            }

            if (minLength == 1 && hourLength == 2) {
                sTime = hourFinal + "0" + minuteFinal;
            }
            else if (minLength == 1 && hourLength == 1) {
                sTime = "0" + hourFinal + "0" + minuteFinal;
            }
            else if (minLength == 2 && hourLength == 1) {
                sTime = "0" + hourFinal + "" + minuteFinal;
            }
             else {
                sTime = hourFinal + "" + minuteFinal;
            }


        }

        if (startend) {


            displayEnd.setText("End date & time:\t\t" + yearFinal + "/" +
                    monthFinal + "/" +
                    dayFinal + ",\t\t" +
                    hourFinal + ":" +
                    minuteFinal);

            switch (monthLength) {
                case 1:
                    switch (dayLength) {
                        case 1:
                            eDate = yearFinal + "0" + monthFinal + "0" + dayFinal;
                            break;
                        case 2:
                            eDate = yearFinal + "0" + monthFinal + "" + dayFinal;
                            break;
                    }
                    break;
                case 2:
                    switch (dayLength) {
                        case 1:
                            eDate = yearFinal + "" + monthFinal + "0" + dayFinal;
                            break;
                        case 2:
                            eDate = yearFinal + "" + monthFinal + "" + dayFinal;
                            break;
                    }
                    break;
            }
            if (minLength == 1 && hourLength == 2) {
                eTime = hourFinal + "0" + minuteFinal;
            }
            else if (minLength == 1 && hourLength == 1) {
                eTime = "0" + hourFinal + "0" + minuteFinal;
            }
            else if (minLength == 2 && hourLength == 1) {
                eTime = "0" + hourFinal + "" + minuteFinal;
            }
            else {
                eTime = hourFinal + "" + minuteFinal;
            }
           // Toast.makeText(AddNew.this, "End Time:" + Integer.valueOf(eTime), Toast.LENGTH_LONG).show();

        }
    }

    public void AddData() {
        addAcc.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {



                if (sDate != null && !sDate.isEmpty() &&
                        sTime != null && !sTime.isEmpty() &&
                        eDate != null && !eDate.isEmpty() &&
                        eTime != null && !eTime.isEmpty()
                        ) {
                    if (Integer.valueOf(sDate) > Integer.valueOf(eDate))
                    { Toast.makeText(AddNew.this, "Start date must be before end date or the same.", Toast.LENGTH_LONG).show();}

                    else if ( (Integer.valueOf(sDate).equals(Integer.valueOf(eDate))) && (Integer.valueOf(sTime) >= Integer.valueOf(eTime)) )
                    { Toast.makeText(AddNew.this, "Start time must be before end time.", Toast.LENGTH_LONG).show();}

                    else if (chooseAct.getText().toString().isEmpty())
                    { Toast.makeText(AddNew.this, "Enter activity name.", Toast.LENGTH_LONG).show();}

                    else if (chooseCat.getSelectedItem().toString().isEmpty())
                    { Toast.makeText(AddNew.this, "Enter category name.", Toast.LENGTH_LONG).show();}

                    else
                        {//trying to calculate the duration:
                            SimpleDateFormat dateformat = new SimpleDateFormat("yyyyMMddHHmm");
                            //1. entering date and time into the date format

                                sDateAndTime = sDate + sTime;

                            try {
                                starting = dateformat.parse(sDateAndTime);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                                eDateAndTime = eDate + eTime;

                            try {
                                ending = dateformat.parse(eDateAndTime);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            //2. getting the duration in minutes by using date methods:
                           duration = (ending.getTime() - starting.getTime()) / (1000*60);
                           durationS = Long.toString(duration);

                            //Getting the ID of the inserted Category:

                            Cursor cursor4 = myDb.readCategories();

                            while (cursor4.moveToNext()) {
                                if (cursor4.getString(1).equals(chooseCat.getSelectedItem().toString()))
                                {CatId = cursor4.getString(0);}
                            }




                            //Passing the data to the DataBaseHelper class to insert it:
                                int isInserted = myDb.insertData(
                                        chooseAct.getText().toString(),
                                        CatId,
                                        sDate,
                                        sTime,
                                        eDate,
                                        eTime,
                                        durationS
                                );


                    if (isInserted == 1) {

                       Toast.makeText(AddNew.this, "Activity successfully logged.", Toast.LENGTH_LONG).show();


                        //clear inserted dates and times from text views:
                        displayStart.setText("");
                        displayEnd.setText("");
                        //clear inserted date and time values:
                        sDate = "";
                        sTime = "";
                        eDate = "";
                        eTime = "";
                        chooseAct.setText("");
                        //chooseCat.setText("");

                    }


                    if (isInserted == 0) {
                        Toast.makeText(AddNew.this, "Activity not successfully logged", Toast.LENGTH_LONG).show();
                    }
                }
            }
            else Toast.makeText(AddNew.this, "Enter start and end dates.", Toast.LENGTH_LONG).show();
            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    public void ChooseCategory() {

        chooseCat = findViewById(R.id.actvCat);

        Cursor cursor = myDb.readCategories();
        // bring Cursor cursor to Array String categories
        StringBuffer buffer = new StringBuffer();
        while (cursor.moveToNext()) {
            buffer.append(cursor.getString(1) + "-");
        }
        String catString = buffer.toString();
        categories = catString.split("-");

        //fill Spinner with categories
        ArrayAdapter<String> gameKindArray= new ArrayAdapter<String>(AddNew.this,android.R.layout.simple_spinner_item, categories);
        gameKindArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        chooseCat.setAdapter(gameKindArray);





        chooseCat.setOnTouchListener(new View.OnTouchListener() {


            @Override
            public boolean onTouch(View v, MotionEvent event) {

                ChooseCategory();
                ChooseActivity();
                return false;
            }
        });

        }

    @SuppressLint("ClickableViewAccessibility")
    public void ChooseActivity() {


        chooseAct = findViewById(R.id.actvAct);
        chooseCat = findViewById(R.id.actvCat);
                 // 1.read category from textview, then select its ID from Category table:
                String insertedCat = chooseCat.getSelectedItem().toString();
                Cursor cursor2 = myDb.readCategories();
                categoryID = "";
                    while (cursor2.moveToNext()) {

                    if (cursor2.getString(1).equals(insertedCat))
                    {categoryID = cursor2.getString(0);
                        }
                }

                //Fill buffer2 with activities that occur in selected category here:
                // 2.go to activities table and select all activities with that category ID and put those into the buffer.


                Cursor cursor3 = myDb.readActivities();
                StringBuffer buffer3 = new StringBuffer();
                while (cursor3.moveToNext()) {
                    //if the ID of the inserted Category matches an CatId of one of the activities, then append activity name to the stringbuffer.
                    if (cursor3.getString(2).equals(categoryID))
                    {buffer3.append(cursor3.getString(1) + "-");}
                }
                final String actString = buffer3.toString();
            //Toast.makeText(AddNew.this, "activities in this category:" + actString , Toast.LENGTH_LONG).show();

        activities = actString.split("-");
        //delete duplicates:
        activities = new HashSet<String>(Arrays.asList(activities)).toArray(new String[0]);

                //Creating the instance of ArrayAdapter containing list of activity names

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, activities);
        chooseAct.setThreshold(1);//will start working from first character
        chooseAct.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView


        chooseAct.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {





                ChooseActivity();

                if (actString != null && !actString.isEmpty()) {
                    chooseAct.showDropDown();
                }

                    return false;
                }
        });






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
public void CreateNewCategory(){
    addCatBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder mBuilder=new AlertDialog.Builder(AddNew.this);
            View mView = getLayoutInflater().inflate(R.layout.add_new_category, null);
            final EditText mCategory = (EditText) mView.findViewById(R.id.newCategoryName);
            final Spinner mParent = (Spinner) mView.findViewById(R.id.parentSpinner);
            Button addNewCategory = (Button) mView.findViewById(R.id.addCatName);

            mBuilder.setView(mView);
            AlertDialog dialog = mBuilder.create();
            dialog.show();


            //1.Put Categories into Spinner (only the ones which do not have a Parent Category)
            Cursor cursor7 = myDb.readCategories();
            StringBuffer buffer7 = new StringBuffer();
            while (cursor7.moveToNext()) {
                if (cursor7.getString(2).equals("0")) {

                    buffer7.append(cursor7.getString(1) + "-");
                }
            }
            String catStringPp = " -" +buffer7.toString();
            categoriesPp =catStringPp.split("-");

            ArrayAdapter<String> gameKindArray= new ArrayAdapter<String>(AddNew.this,android.R.layout.simple_spinner_item, categoriesPp);
            gameKindArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mParent.setAdapter(gameKindArray);

            //write code here
            addNewCategory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //2.Get ID of selected Category
                    String Parent = mParent.getSelectedItem().toString();

                    Cursor cursor2 = myDb.readCategories();
                    categoryIDP = "0";
                    while (cursor2.moveToNext()) {
                        if (cursor2.getString(1).equals(Parent))
                        {categoryIDP = cursor2.getString(0);
                        }
                    }


                    //if category is not created yet, make new entry:
                    Cursor cursor6 = myDb.readCategories();
                    StringBuffer buffer6 = new StringBuffer();
                    while (cursor6.moveToNext()) {
                        buffer6.append(cursor6.getString(1) + "-");
                    }
                    String catString2 = buffer6.toString();
                    categories = catString2.split("-");



                    boolean found = Arrays.asList(categories).contains( mCategory.getText().toString());
                    if (!found) {
                        int add = myDb.addCategory(mCategory.getText().toString(),categoryIDP);
                        Toast.makeText(AddNew.this, "Category successfully added.", Toast.LENGTH_LONG).show();
                        mCategory.setText("");
                    }
                }
            });
        }
    });

}
}