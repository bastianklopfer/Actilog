package com.example.bastian.actilog;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DataBaseHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME;
    public static final String TABLE_NAME;
    public static final String TABLE2_NAME;


    public static final String COL_1;
    public static final String COL_2;
    public static final String COL_3;
    public static final String COL_4;
    public static final String COL_5;
    public static final String COL_6;
    public static final String COL_7;
    public static final String COL_8;

    public static final String COL2_1;
    public static final String COL2_2;
    public static final String COL2_3;


    static {
        DATABASE_NAME = "actilog.db";

        TABLE_NAME = "activity_table";
        TABLE2_NAME = "category_table";

        COL_1 = "ID";
        COL_2 = "NAME";
        COL_3 = "CATID";
        COL_4 = "STARTDATE";
        COL_5 = "STARTTIME";
        COL_6 = "ENDDATE";
        COL_7 = "ENDTIME";
        COL_8 = "ACTDURATION";

        COL2_1 = "ID";
        COL2_2 = "CATEGORYNAME";
        COL2_3 = "PARENTID";
    }


    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, CATID INTEGER NOT NULL,STARTDATE INTEGER NOT NULL, STARTTIME INTEGER NOT NULL, ENDDATE INTEGER NOT NULL, ENDTIME INTEGER NOT NULL, ACTDURATION INTEGER)");
        db.execSQL("create table " + TABLE2_NAME + "(CATEGORY_ID INTEGER PRIMARY KEY AUTOINCREMENT, CATEGORYNAME TEXT, PARENTID INTEGER)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS" + TABLE2_NAME);
        onCreate(db);

    }

    public int insertData (String name, String catId, String startDate, String startTime, String endDate, String endTime, String actDuration)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();


            contentValues.put(COL_2, name);
            contentValues.put(COL_3, catId);
          contentValues.put(COL_4, startDate);
            contentValues.put(COL_5, startTime);
            contentValues.put(COL_6, endDate);
            contentValues.put(COL_7, endTime);
            contentValues.put(COL_8, actDuration);

        long result = db.insert(TABLE_NAME, null, contentValues);

        if(result == -1)
        {
            return 0;
        } else
            return 1;
        }

   public int addCategory (String categoryName, String parentID){
       if(categoryName != null && !categoryName.isEmpty()) {
           SQLiteDatabase db = this.getWritableDatabase();
           ContentValues contentValues2 = new ContentValues();

           contentValues2.put(COL2_2, categoryName);
           contentValues2.put(COL2_3, parentID);

           long result2 = db.insert(TABLE2_NAME, null, contentValues2);

           if (result2 == -1) {
               return 0;
           } else
               return 1;
       }
       else return 0;

   }

   public Cursor readCategories ()
   {
       SQLiteDatabase db = this.getWritableDatabase();
       return db.rawQuery("select * from " + TABLE2_NAME, null);
   }

    public Cursor readActivities ()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("select * from " + TABLE_NAME, null);
    }

}



