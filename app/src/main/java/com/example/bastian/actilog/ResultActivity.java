package com.example.bastian.actilog;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.lang.reflect.Array;

import static java.lang.Math.round;

public class ResultActivity extends AppCompatActivity {

    DataBaseHelper myDb;
    float [] proportions = new float[99];
    int [] parentDurations = new int[98];
    String [] categoryArray;
    String [] childNameArray;
    String[] idArray;
    String[] childArray;
    String[] parentArray;
    int x; //so many categories have been selected
    int y; //so many categories are there, including child categories

    TextView tv11,tv21,tv31,tv41,tv51,tv61,tv71,tv81,tv91,tv101,tv111,tv121;
    TextView tv12,tv22,tv32,tv42,tv52,tv62,tv72,tv82,tv92,tv102,tv112,tv122;
    TextView tv13,tv23,tv33,tv43,tv53,tv63,tv73,tv83,tv93,tv103,tv113,tv123;
    TextView datesTV;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        myDb = new DataBaseHelper(this);

        tv11 = (TextView) findViewById(R.id.textView11);
        tv12 = (TextView) findViewById(R.id.textView12);
        tv13 = (TextView) findViewById(R.id.textView13);
        tv21 = (TextView) findViewById(R.id.textView21);
        tv22 = (TextView) findViewById(R.id.textView22);
        tv23 = (TextView) findViewById(R.id.textView23);
        tv31 = (TextView) findViewById(R.id.textView31);
        tv32 = (TextView) findViewById(R.id.textView32);
        tv33 = (TextView) findViewById(R.id.textView33);
        tv41 = (TextView) findViewById(R.id.textView41);
        tv42 = (TextView) findViewById(R.id.textView42);
        tv43 = (TextView) findViewById(R.id.textView43);
        tv51 = (TextView) findViewById(R.id.textView51);
        tv52 = (TextView) findViewById(R.id.textView52);
        tv53 = (TextView) findViewById(R.id.textView53);
        tv61 = (TextView) findViewById(R.id.textView61);
        tv62 = (TextView) findViewById(R.id.textView62);
        tv63 = (TextView) findViewById(R.id.textView63);
        tv71 = (TextView) findViewById(R.id.textView71);
        tv72 = (TextView) findViewById(R.id.textView72);
        tv73 = (TextView) findViewById(R.id.textView73);
        tv81 = (TextView) findViewById(R.id.textView81);
        tv82 = (TextView) findViewById(R.id.textView82);
        tv83 = (TextView) findViewById(R.id.textView83);
        tv91 = (TextView) findViewById(R.id.textView91);
        tv92 = (TextView) findViewById(R.id.textView92);
        tv93 = (TextView) findViewById(R.id.textView93);
        tv101 = (TextView) findViewById(R.id.textView101);
        tv102 = (TextView) findViewById(R.id.textView102);
        tv103 = (TextView) findViewById(R.id.textView103);
        tv111 = (TextView) findViewById(R.id.textView111);
        tv112 = (TextView) findViewById(R.id.textView112);
        tv113 = (TextView) findViewById(R.id.textView113);
        tv121 = (TextView) findViewById(R.id.textView121);
        tv122 = (TextView) findViewById(R.id.textView122);
        tv123 = (TextView) findViewById(R.id.textView123);
        datesTV = (TextView) findViewById(R.id.datesTV);



        setTitle("Review Results");

        String categories = getIntent().getStringExtra("CATS");
        String startDate = getIntent().getStringExtra("START");
        String endDate = getIntent().getStringExtra("END");

        startDate = String.valueOf(startDate.charAt(6))+String.valueOf(startDate.charAt(7)) + "."+ String.valueOf(startDate.charAt(4)) +String.valueOf(startDate.charAt(5)) + "."+ String.valueOf(startDate.charAt(0)) +String.valueOf(startDate.charAt(1)) + String.valueOf(startDate.charAt(2)) + String.valueOf(startDate.charAt(3));
        endDate = String.valueOf(endDate.charAt(6))+String.valueOf(endDate.charAt(7)) + "."+ String.valueOf(endDate.charAt(4)) +String.valueOf(endDate.charAt(5)) + "."+ String.valueOf(endDate.charAt(0)) +String.valueOf(endDate.charAt(1)) + String.valueOf(endDate.charAt(2)) + String.valueOf(endDate.charAt(3));


        datesTV.setText("Chosen categories:\n" + categories + "\nStart date:\t\t" + startDate + "\nEnd date:\t\t\t" +endDate);

        getData();
        displayData();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.addactivity, menu);
        return super.onCreateOptionsMenu(menu);

    }

    public void getData(){

        //break the Category String int a String Array, to separate the individual Category Names:
        categoryArray = getIntent().getStringExtra("CATS").replace(" ","").split(",");
        //now create a String Array with the Category IDs of these categories:


        Cursor cursor = myDb.readCategories();
        StringBuffer buffer = new StringBuffer();
        x = categoryArray.length;       //when 4 categories are selected: x=4;
        for (int i =0; i<x; i++) {
            while (cursor.moveToNext()) {
                if (cursor.getString(1).equals(categoryArray[i])) {
                    buffer.append(cursor.getString(0) + "-");
                }
            }
            cursor.moveToFirst();
            cursor.moveToPrevious();
        }

        //append the buffer with the ID of the child categories which parents are selected:
        //check if any chosen category has child categories:
        //get the ID of all child categories
        Cursor cursor2 = myDb.readCategories();
        StringBuffer buffer2 = new StringBuffer();
        StringBuffer buffer3 = new StringBuffer();
        StringBuffer buffer4 = new StringBuffer();

        while (cursor2.moveToNext()) {
            if(!cursor2.getString(2).equals("0")) {
                buffer2.append(cursor2.getString(0)+ "-");
                buffer3.append(cursor2.getString(2)+ "-");
                buffer4.append(cursor2.getString(1)+ "-");

            }
        }
        String ChildString = buffer2.toString();
        childArray = ChildString.split("-");

        String ParentString = buffer3.toString();
        parentArray  = ParentString.split("-");

        String ChildNameString = buffer4.toString();
        childNameArray  = ChildNameString.split("-");


        String IdString = buffer.toString();
        idArray = IdString.split("-");

        cursor2.moveToFirst();
        cursor2.moveToPrevious();
        while (cursor2.moveToNext()) {
            for (int i = 0; i < x; i++) {
                if (cursor2.getString(2).equals(idArray[i])) { //if the parent of the child is chosen, append the childs ID.
                    buffer.append(cursor2.getString(0) + "-");
                }
            }
        }

        IdString = buffer.toString();
        idArray = IdString.split("-");

        //get the duration of each category:




                //Get the time spent on the categories. Add the time spent on child categories to the respective parent category:
        //first, get the time spent on the categories:
        String startDate = getIntent().getStringExtra("START");
        String endDate = getIntent().getStringExtra("END");
        Cursor cursor4 = myDb.getTimeSpentCategories(startDate,endDate);
        int [] catDurations = new int[99];
        y = idArray.length;
        for (int i =0; i<y; i++) {
            while (cursor4.moveToNext()) {
                if (cursor4.getString(2).equals(idArray[i])) {
                    catDurations[i] = catDurations[i] + Integer.parseInt(cursor4.getString(7));
                }
            }
            cursor4.moveToFirst();
            cursor4.moveToPrevious();
        }
        int totalDuration = 0;
        for (int i=0; i<y; i++){
            totalDuration = totalDuration + catDurations[i];
            parentDurations[i] = catDurations[i];
        }

        //Define a variable that points to the idString[], where to select the duration:
        int k = x;
        // add durations of child categories to parent categories!
        for (int i=0; i<parentArray.length; i++)
        {
            for (int j=0; j<x; j++){
                if(parentArray[i].equals(idArray[j])) {
                    parentDurations[j] = parentDurations[j] + catDurations[k];
                    k=k+1;
                }
            }
        }

        String catsAndDurations="Review of selected Categories:\n";
        for (int i =1; i<=x; i++) {
            catsAndDurations = catsAndDurations + categoryArray[i - 1] +
                    "\t\t" + idArray[i - 1] + "\n";
        }


        //calculate the proportions:
        for (int i = 0; i < x; i++) {
            proportions[i]=(float)round(((float)parentDurations[i]/totalDuration)*1000)/10;
        }
        //get the duration of the activities of each selected category
        //calculate proportions of these activities within the category.

    }

    public void displayData(){
        //display data in table:
        int k=0;
        int r=1;
        for (int i =0; i<x; i++) {

            fillRow(r,categoryArray[k], parentDurations[k],proportions[k],true);
            r++;
            k++;
            for (int j =0; j<parentArray.length; j++) {
                if (idArray[i].equals(parentArray[j]))
                {
                    fillRow(r,"\t\t\t\t" + childNameArray[j], parentDurations[Integer.parseInt(childArray[j])],9999,false);
                    r++;

                }
            }

        }
    }

    public void fillRow(int rowNr, String Name, int duration, float proportion, boolean bold){
        switch (rowNr){
            case 1: tv11.setText(Name);
                    tv12.setText("\t"+duration);
                    if (proportion != 9999) {
                        tv13.setText(String.valueOf(proportion) + "%");
                    }
                    if (bold)
                    {
                        tv11.setTypeface(null, Typeface.BOLD);
                        tv12.setTypeface(null, Typeface.BOLD);
                        tv13.setTypeface(null, Typeface.BOLD);
                    }
                    break;
            case 2: tv21.setText(Name);
                tv22.setText("\t"+duration);
                if (proportion != 9999) {
                    tv23.setText(String.valueOf(proportion) + "%");
                }
                if (bold)
                {
                    tv21.setTypeface(null, Typeface.BOLD);
                    tv22.setTypeface(null, Typeface.BOLD);
                    tv23.setTypeface(null, Typeface.BOLD);
                }

                break;
            case 3: tv31.setText(Name);
                tv32.setText("\t"+duration);
                if (proportion != 9999) {
                    tv33.setText(String.valueOf(proportion) + "%");
                }
                if (bold)
                {
                    tv31.setTypeface(null, Typeface.BOLD);
                    tv32.setTypeface(null, Typeface.BOLD);
                    tv33.setTypeface(null, Typeface.BOLD);
                }

                break;
            case 4: tv41.setText(Name);
                tv42.setText("\t"+duration);
                if (proportion != 9999) {
                    tv43.setText(String.valueOf(proportion) + "%");
                }
                if (bold)
                {
                    tv41.setTypeface(null, Typeface.BOLD);
                    tv42.setTypeface(null, Typeface.BOLD);
                    tv43.setTypeface(null, Typeface.BOLD);
                }

                break;
            case 5: tv51.setText(Name);
                tv52.setText("\t"+duration);
                if (proportion != 9999) {
                    tv53.setText(String.valueOf(proportion) + "%");
                }
                if (bold)
                {
                    tv51.setTypeface(null, Typeface.BOLD);
                    tv52.setTypeface(null, Typeface.BOLD);
                    tv53.setTypeface(null, Typeface.BOLD);
                }

                break;
            case 6: tv61.setText(Name);
                tv62.setText("\t"+duration);
                if (proportion != 9999) {
                    tv63.setText(String.valueOf(proportion) + "%");
                }
                if (bold)
                {
                    tv61.setTypeface(null, Typeface.BOLD);
                    tv62.setTypeface(null, Typeface.BOLD);
                    tv63.setTypeface(null, Typeface.BOLD);
                }

                break;
            case 7: tv71.setText(Name);
                tv72.setText("\t"+duration);
                if (proportion != 9999) {
                    tv73.setText(String.valueOf(proportion) + "%");
                }
                if (bold)
                {
                    tv71.setTypeface(null, Typeface.BOLD);
                    tv72.setTypeface(null, Typeface.BOLD);
                    tv73.setTypeface(null, Typeface.BOLD);
                }

                break;
            case 8: tv81.setText(Name);
                tv82.setText("\t"+duration);
                if (proportion != 9999) {
                    tv83.setText(String.valueOf(proportion) + "%");
                }
                if (bold)
                {
                    tv81.setTypeface(null, Typeface.BOLD);
                    tv82.setTypeface(null, Typeface.BOLD);
                    tv83.setTypeface(null, Typeface.BOLD);
                }

                break;
            case 9: tv91.setText(Name);
                tv92.setText("\t"+duration);
                if (proportion != 9999) {
                    tv93.setText(String.valueOf(proportion) + "%");
                }
                if (bold)
                {
                    tv91.setTypeface(null, Typeface.BOLD);
                    tv92.setTypeface(null, Typeface.BOLD);
                    tv93.setTypeface(null, Typeface.BOLD);
                }

                break;
            case 10: tv101.setText(Name);
                tv102.setText("\t"+duration);
                if (proportion != 9999) {
                    tv103.setText(String.valueOf(proportion) + "%");
                }
                if (bold)
                {
                    tv101.setTypeface(null, Typeface.BOLD);
                    tv102.setTypeface(null, Typeface.BOLD);
                    tv103.setTypeface(null, Typeface.BOLD);
                }

                break;
            case 11: tv111.setText(Name);
                tv112.setText("\t"+duration);
                if (proportion != 9999) {
                    tv113.setText(String.valueOf(proportion) + "%");
                }
                if (bold)
                {
                    tv111.setTypeface(null, Typeface.BOLD);
                    tv112.setTypeface(null, Typeface.BOLD);
                    tv113.setTypeface(null, Typeface.BOLD);
                }

                break;
            case 12: tv121.setText(Name);
                tv122.setText("\t"+duration);
                if (proportion != 9999) {
                    tv123.setText(String.valueOf(proportion) + "%");
                }
                if (bold)
                {
                    tv121.setTypeface(null, Typeface.BOLD);
                    tv122.setTypeface(null, Typeface.BOLD);
                    tv123.setTypeface(null, Typeface.BOLD);
                }

                break;
        }
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
}
