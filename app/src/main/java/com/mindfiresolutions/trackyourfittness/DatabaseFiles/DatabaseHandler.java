package com.mindfiresolutions.trackyourfittness.DatabaseFiles;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mindfiresolutions.trackyourfittness.Model.HealthStatus;
import com.mindfiresolutions.trackyourfittness.R;
import com.mindfiresolutions.trackyourfittness.Utilities.LoggerUtility;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import static com.mindfiresolutions.trackyourfittness.Utilities.Constants.DATE_FORMAT;
import static com.mindfiresolutions.trackyourfittness.Utilities.Constants.DATE_KEY;
import static com.mindfiresolutions.trackyourfittness.Utilities.Constants.DISTANCE_KEY;
import static com.mindfiresolutions.trackyourfittness.Utilities.Constants.HIGH_BP_KEY;
import static com.mindfiresolutions.trackyourfittness.Utilities.Constants.ID;
import static com.mindfiresolutions.trackyourfittness.Utilities.Constants.LOW_BP_KEY;
import static com.mindfiresolutions.trackyourfittness.Utilities.Constants.WATER_INTAKE_KEY;
import static com.mindfiresolutions.trackyourfittness.Utilities.Constants.WEIGHT_KEY;

/**
 * Created by Vishal Prasad on 5/24/2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final String  TAG = "SQLite log: ";
    private static DatabaseHandler sInstance;

    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "FitnessManager";

    // table name
    private static final String TABLE_NAME = "FitnessDetails";


    public static synchronized DatabaseHandler getInstance(Context context) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new DatabaseHandler(context.getApplicationContext());
        }
        return sInstance;
    }

    /**
     * Constructor should be private to prevent direct instantiation.
     * make call to static method "getInstance()" instead.
     */
    private DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_NAME + "("+ ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DATE_KEY + " TEXT, " + WEIGHT_KEY + " REAL, "
                + WATER_INTAKE_KEY + " REAL, " + HIGH_BP_KEY + " REAL,"+ LOW_BP_KEY + " REAL,"
                + DISTANCE_KEY + " REAL "+")";
        LoggerUtility.makeLog("SQLite Log: ",CREATE_CONTACTS_TABLE);
        db.execSQL(CREATE_CONTACTS_TABLE);
        db.close();
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    // Adding new contact
    public void addHealthStatus(HealthStatus entry) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DATE_KEY, getCurrentDate()); // HealthStatus Date
        values.put(WATER_INTAKE_KEY, entry.getmWaterIntake()); // HealthStatus Weight
        values.put(WEIGHT_KEY, entry.getmWeight()); // HealthStatus Weight
        values.put(HIGH_BP_KEY, entry.getmHighBP()); // HealthStatus Weight
        values.put(LOW_BP_KEY, entry.getmLowBP()); // HealthStatus Weight
        values.put(DISTANCE_KEY, entry.getmDistance()); // HealthStatus Weight

        // Inserting Row
        db.insert(TABLE_NAME, null, values);
        LoggerUtility.makeLog(TAG   ,"Data Entered into Table");
        db.close(); // Closing database connection
    }

    public String getCurrentDate() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT);
        return (df.format(c.getTime()));
    }

    // Login function
    public ArrayList<HashMap<String,String>> getLastEntry(){

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        cursor.moveToLast();
                ArrayList<HashMap<String,String>> lastEntry = new ArrayList<>();
                HashMap<String,String> entry = new HashMap<>();
                entry.put(ID,cursor.getString(0));
                entry.put(WEIGHT_KEY,cursor.getString(2));
                entry.put(WATER_INTAKE_KEY,cursor.getString(3));
                entry.put(HIGH_BP_KEY,cursor.getString(4));
                entry.put(LOW_BP_KEY,cursor.getString(5));
                entry.put(DISTANCE_KEY,cursor.getString(6));
                // Adding entry to list
                lastEntry.add(entry);

                cursor.close();
                db.close();
                return lastEntry ;

    }


    public boolean hasTodaysEntry(String currentDate){

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, ID+" DESC");
        cursor.moveToFirst();
        LoggerUtility.makeLog(TAG,"SQLite Cursor : "+cursor.getString(1)+" " +cursor.getString(2)+" "
                +cursor.getString(3)+" " +cursor.getString(4)+" " +cursor.getString(5)+" " +cursor.getString(6));
            if((cursor.getString(1).equals(currentDate) ))
            {
                cursor.close();
                return true;
            }
            cursor.close();
            db.close();
            return false ;

    }


    public ArrayList<HashMap<String, String>> getHealthDetailsList()
    {
        ArrayList<HashMap<String, String>> healthDetailsList = new ArrayList<>();
        HashMap<String, String> contact = new HashMap<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, ID+" DESC");

        if(cursor.moveToFirst())
            do{

                HashMap<String, String> HealthDetailsEntry =  new HashMap<>();
                HealthDetailsEntry.put(ID,cursor.getString(cursor.getColumnIndex(ID)));
                HealthDetailsEntry.put(DATE_KEY,cursor.getString(cursor.getColumnIndex(DATE_KEY)));
                HealthDetailsEntry.put(WEIGHT_KEY,cursor.getString(cursor.getColumnIndex(WEIGHT_KEY)));
                HealthDetailsEntry.put(WATER_INTAKE_KEY,cursor.getString(cursor.getColumnIndex(WATER_INTAKE_KEY)));
                HealthDetailsEntry.put(HIGH_BP_KEY,cursor.getString(cursor.getColumnIndex(HIGH_BP_KEY)));
                HealthDetailsEntry.put(LOW_BP_KEY,cursor.getString(cursor.getColumnIndex(LOW_BP_KEY)));
                HealthDetailsEntry.put(DISTANCE_KEY,cursor.getString(cursor.getColumnIndex(DISTANCE_KEY)));
                healthDetailsList.add(HealthDetailsEntry);

            }
            while(cursor.moveToNext());

        cursor.close();
        db.close();
        return healthDetailsList;

    }


    public int updateIntoDatabase(HealthStatus entry, String id)
    // Updating single entry
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DATE_KEY, getCurrentDate()); // HealthStatus Date
        values.put(WATER_INTAKE_KEY, entry.getmWaterIntake()); // HealthStatus Weight
        values.put(WEIGHT_KEY, entry.getmWeight()); // HealthStatus Weight
        values.put(HIGH_BP_KEY, entry.getmHighBP()); // HealthStatus Weight
        values.put(LOW_BP_KEY, entry.getmLowBP()); // HealthStatus Weight
        values.put(DISTANCE_KEY, entry.getmDistance()); // HealthStatus Weight
        // updating row
        return db.update(TABLE_NAME, values, ID + " = ?", new String[] { id });



    }
}
