package com.example.hector.EventMan;

import android.app.usage.UsageEvents;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;

import java.sql.SQLDataException;

public class MyDBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 10;
    private static final String DATABASE_NAME = "events.db";
    public static final  String TABLE_EVENTS = "events";
    public static final  String COLUMN_ID = "_id";
    public static final  String COLUMN_EVENT_NAME = "eventname";
    public static final  String COLUMN_EVENT_DIR = "direction";
    public static final  String COLUMN_EVENT_TIME = "evtime";
    public static final  String COLUMN_EVENT_USETIME = "evusetime";

    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE " + TABLE_EVENTS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_EVENT_NAME + " TEXT, " +
                COLUMN_EVENT_DIR + " INTEGER, " +
                COLUMN_EVENT_TIME + " INTEGER, " +
                COLUMN_EVENT_USETIME + " INTEGER " +
                ");";
        try{
        db.execSQL(query);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);
        onCreate(db);

    }

    //Add a new row to the database
    public void addEvent(Events event) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_EVENT_NAME, event.get_eventname());
        values.put(COLUMN_EVENT_DIR, event.get_direction());
        values.put(COLUMN_EVENT_TIME, event.get_evtime());
        values.put(COLUMN_EVENT_USETIME, event.get_evusetime());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_EVENTS, null, values);
        db.close();

    }
    public String getAllEvents() {

        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_EVENTS +" WHERE 1;";
       // System.out.println("!!- "  + query);
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();

        while (c.isAfterLast() == false) {
            System.out.println("!!- "  + "in while " + c.getCount());
            if (c.getString(c.getColumnIndex("eventname")) != null) {
                dbString += c.getString(c.getColumnIndex("eventname"));
                dbString += ",";
                c.moveToNext();
            }

        }
        db.close();
        return dbString;
    }

    //Delete a row from the database
    public void deleteEvent (String eventName) {

        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_EVENTS + " WHERE " + COLUMN_EVENT_NAME + "=\"" + eventName + "\";");

    }

    public String dbtostring() {

        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_EVENTS + " WHERE 1;";

        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();

        while (!c.isAfterLast()) {
            if (c.getString(c.getColumnIndex("eventname")) != null) {
                dbString += c.getString(c.getColumnIndex("eventname"));
                dbString += "\n";
            }

        }
        db.close();
        return dbString;
    }

}