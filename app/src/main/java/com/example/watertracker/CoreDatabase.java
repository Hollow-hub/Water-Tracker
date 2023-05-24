package com.example.watertracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CoreDatabase extends SQLiteOpenHelper {
    static final private String DATABASE_NAME = "WaterTracker.db";
    static final private int DATABASE_VERSION = 1;
    static final private String TABLE_USERNAMES = "Usernames";
    static final private String TABLE_RECORD = "DailyWaterIntake";
    static private String lastUsername = "";

    Context context;
    SQLiteDatabase db;

    public void setLastUsername(String username) {
        this.lastUsername = username;
    }

    public CoreDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_USERNAMES + " (username TEXT PRIMARY KEY);");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_RECORD + " (DailyWaterIntake TEXT PRIMARY KEY);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS `DB_TABLE`");
        onCreate(db);
    }

    public void insertUsername(String username) {
        this.db = this.getWritableDatabase();
        // insert username into database
        ContentValues values = new ContentValues();
        values.put("username",username);
        db.insert(TABLE_USERNAMES,null,values);
        lastUsername = username;
    }

    public void insertRecord(int waterCount) {
        this.db = this.getWritableDatabase();
        String date = getDateTime();
        ContentValues values = new ContentValues();
        values.put("daily water intake", waterCount);
        values.put("date", date);
        db.insert(TABLE_RECORD, null, values);
    }


    public String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }


    public String getName() {
        return lastUsername;
    }


    public boolean Usernames_is_empty() {
        this.db = this.getReadableDatabase();
        boolean empty = true;
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_USERNAMES, null);
        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    int count = cursor.getInt(0);
                    empty = (count == 0);
                }
            } finally {
                cursor.close();
            }
        }
        return empty;
    }


    public String getUsername() {
        this.db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERNAMES, null);
        String name = "";
        if (cursor != null && cursor.moveToFirst()) {
            int usernameIndex = cursor.getColumnIndex("username");
            name = cursor.getString(usernameIndex);
        }
        return name;
    }

}
