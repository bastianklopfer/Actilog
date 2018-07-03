package com.example.bastian.actilog;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashSet;

import static java.lang.Math.round;

public class ResultActivity extends AppCompatActivity {

    DataBaseHelper myDb;
    float [] proportions = new float[99];
    int [] parentDurations = new int[98];
    int [] durationsByTable = new int [99];
    int [] IDsByTable = new int [99];
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
        showCategory();
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
            durationsByTable[r] = parentDurations[k];
            IDsByTable [r] = Integer.parseInt(idArray[k]);
            r++;
            k++;
            for (int j =0; j<parentArray.length; j++) {
                if (idArray[i].equals(parentArray[j]))
                {
                    int duration = 0;
                    for (int x = 0; x< idArray.length; x++)
                    {if (idArray[x].equals(childArray[j])){
                        duration = parentDurations[x];
                    }
                    }

                    fillRow(r,"\t\t" + childNameArray[j], duration,9999,false);
                    durationsByTable[r] = duration;
                    IDsByTable [r] = Integer.parseInt(childArray[j]);
                    r++;

                }
            }

        }
    }

    public void fillRow(int rowNr, String Name, int duration, float proportion, boolean bold){

        int hours = duration / 60;
        int minutes = duration - hours * 60;

        String Duration = new String();


        if (String.valueOf(hours).length() == 1 && String.valueOf(minutes).length() == 1) {
            Duration = "\t\t\t0" + Integer.toString(hours) + ":0" + Integer.toString(minutes);
        } else if (Integer.toString(hours).length() == 2 && Integer.toString(minutes).length() == 1) {
            Duration = "\t\t\t" +Integer.toString(hours) + ":0" + Integer.toString(minutes);
        } else if (Integer.toString(hours).length() == 1 && Integer.toString(minutes).length() == 2) {
            Duration = "\t\t\t0" + Integer.toString(hours) + ":" + Integer.toString(minutes);
        } else {
            Duration = "\t\t\t" +Integer.toString(hours) + ":" + Integer.toString(minutes);
        }


        switch (rowNr){
            case 1: tv11.setText(Name);
                    tv12.setText(Duration);
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
                tv22.setText(Duration);
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
                tv32.setText(Duration);
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
                tv42.setText(Duration);
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
                tv52.setText(Duration);
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
                tv62.setText(Duration);
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
                tv72.setText(Duration);
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
                tv82.setText(Duration);
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
                tv92.setText(Duration);
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
                tv102.setText(Duration);
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
                tv112.setText(Duration);
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
                tv122.setText(Duration);
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

    public void showCategory ()
    {
        tv11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setTexts(1);
            }
        });
        tv21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTexts(2);
            }
        });
        tv31.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTexts(3);
            }
        });
        tv41.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTexts(4);
            }
        });
        tv51.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTexts(5);
            }
        });
        tv61.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTexts(6);
            }
        });
        tv71.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTexts(7);
            }
        });
        tv81.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTexts(8);
            }
        });
        tv91.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTexts(9);
            }
        });
        tv101.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTexts(10);
            }
        });
        tv111.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTexts(11);
            }
        });
        tv121.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTexts(12);
            }
        });
    }
    public void setTexts (int row)
    {
        AlertDialog.Builder mBuilder=new AlertDialog.Builder(ResultActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.show_category, null);
        final TextView Header = (TextView) mView.findViewById(R.id.header);
        final TextView TableHeader = (TextView) mView.findViewById(R.id.tableHeader);
        final TextView Table = (TextView) mView.findViewById(R.id.table);
        final TextView Table2Header = (TextView) mView.findViewById(R.id.table2Header);
        final TextView Table2 = (TextView) mView.findViewById(R.id.table2);

        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();

        StringBuffer bufferTable = new StringBuffer();
        StringBuffer bufferTable2 = new StringBuffer();

        switch (row){
            case 1:        Header.setText(tv11.getText());
                break;
            case 2:        Header.setText(tv21.getText());
                break;
            case 3:        Header.setText(tv31.getText());
                break;
            case 4:        Header.setText(tv41.getText());
                break;
            case 5:        Header.setText(tv51.getText());
                break;
            case 6:        Header.setText(tv61.getText());
                break;
            case 7:        Header.setText(tv71.getText());
                break;
            case 8:        Header.setText(tv81.getText());
                break;
            case 9:        Header.setText(tv91.getText());
                break;
            case 10:        Header.setText(tv101.getText());
                break;
            case 11:        Header.setText(tv111.getText());
                break;
            case 12:        Header.setText(tv121.getText());
                break;
        }
        //get the ID of the selected category:

        int k=0;
        //does the chosen category have subcategories? (does catID appear in parentID?)
        for (int j =0; j<parentArray.length; j++) //will go as many times as there are subcategories
        //checks if selected category is "parent category":

        {
            if (IDsByTable[row] == Integer.parseInt(parentArray[j]))
            {
                TableHeader.setText("Subcategories:\n\t\t%\t\t\tHours spent\t\tName");

                int hours = durationsByTable[row+k+1] / 60;
                int minutes = durationsByTable[row+k+1] - hours * 60;
                String Duration = new String();
                if (String.valueOf(hours).length() == 1 && String.valueOf(minutes).length() == 1) {
                    Duration = "0" + Integer.toString(hours) + ":0" + Integer.toString(minutes);
                } else if (Integer.toString(hours).length() == 2 && Integer.toString(minutes).length() == 1) {
                    Duration = Integer.toString(hours) + ":0" + Integer.toString(minutes);
                } else if (Integer.toString(hours).length() == 1 && Integer.toString(minutes).length() == 2) {
                    Duration = "0" + Integer.toString(hours) + ":" + Integer.toString(minutes);
                } else {
                    Duration = Integer.toString(hours) + ":" + Integer.toString(minutes);
                }


                float percentage=(float)round(((float)durationsByTable[row+k+1]/durationsByTable[row])*1000)/10;

                //set percentage, hours and name
                if (percentage<10)
                {
                    bufferTable.append("0" + percentage + "%\t\t\t" + Duration + "\t\t\t" + childNameArray[j] + "\n");
                }
                else {                        bufferTable.append(percentage + "%\t\t\t" + Duration + "\t\t\t" + childNameArray[j] + "\n");
                }
                k++;
            }
        }

        //Now fill with the Activities:
        Table2Header.setText("Activities:\n\t\t%\t\t\tHours spent\t\tName");
        //find out category ID.

        //find all activity names(distinct)
        //Cursor cursor2 = myDb.readActivityNamesByCategory(IDsByTable[row]);
        Cursor cursor2 = myDb.readActivities();
        StringBuffer buffer8 = new StringBuffer();


        while (cursor2.moveToNext()) {
            if (Integer.parseInt(cursor2.getString(2)) == IDsByTable[row]) {
                buffer8.append(cursor2.getString(1) + "-");
            }
        }

        String catNames = buffer8.toString();
        String [] CatNames =catNames.split("-");

                //delete duplicates:
        CatNames = new HashSet<String>(Arrays.asList(CatNames)).toArray(new String[0]);
        //fill table2 with percentage, duration and activity name.

        for (int i =0; i<CatNames.length; i++)
        {
            //calculate duration and percentage of each activity:
            cursor2.moveToFirst();
            cursor2.moveToPrevious();

            int duration = 0;

            while (cursor2.moveToNext()) {
                if (Integer.parseInt(cursor2.getString(2)) == IDsByTable[row] && cursor2.getString(1).equals(CatNames[i])) {
                    duration = duration + Integer.parseInt(cursor2.getString(7));
                }
            }


            int hours = duration / 60;
            int minutes = duration - hours * 60;
            String Duration = new String();
            if (String.valueOf(hours).length() == 1 && String.valueOf(minutes).length() == 1) {
                Duration = "0" + Integer.toString(hours) + ":0" + Integer.toString(minutes);
            } else if (Integer.toString(hours).length() == 2 && Integer.toString(minutes).length() == 1) {
                Duration = Integer.toString(hours) + ":0" + Integer.toString(minutes);
            } else if (Integer.toString(hours).length() == 1 && Integer.toString(minutes).length() == 2) {
                Duration = "0" + Integer.toString(hours) + ":" + Integer.toString(minutes);
            } else {
                Duration = Integer.toString(hours) + ":" + Integer.toString(minutes);
            }

            float percentage=(float)round(((float) duration /durationsByTable[row])*1000)/10;

            if (percentage<10)
            {
                bufferTable2.append("0" + percentage + "%\t\t\t" + Duration + "\t\t\t" + CatNames[i] + "\n");
            }
            else {  bufferTable2.append(percentage + "%\t\t\t" + Duration + "\t\t\t" + CatNames[i] + "\n");
            }
        }
        Table.setText(bufferTable.toString());
        Table2.setText(bufferTable2.toString());
    }
}
