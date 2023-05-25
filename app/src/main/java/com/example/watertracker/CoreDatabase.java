package com.example.watertracker;

import android.annotation.SuppressLint;
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
    static final private String TABLE_CUPS = "Cups";

    Context context;
    SQLiteDatabase db;

    public CoreDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_USERNAMES + " (username TEXT PRIMARY KEY);");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_RECORD + " (DailyWater TEXT PRIMARY KEY);");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_CUPS + " (Cups TEXT PRIMARY KEY);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS `DB_TABLE`");
        onCreate(db);
    }

    public void insertCups(Double cups) {
        this.db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Cups",cups);
        db.insert(TABLE_CUPS, null, values);
    }

    public void insertUsername(String username) {
        this.db = this.getWritableDatabase();
        // insert username into database
        ContentValues values = new ContentValues();
        values.put("username",username);
        db.insert(TABLE_USERNAMES,null,values);
    }

    public void insertRecord(int waterCount) {
        this.db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("DailyWater", waterCount);
        db.insert(TABLE_RECORD, null, values);
    }


    public Integer getRecord(){
        this.db = this.getReadableDatabase();
        String tableName = "DailyWaterIntake";
        String columnName = "DailyWater";
        Integer lastItem = null;

        Cursor cursor = db.query(tableName, new String[]{columnName}, null, null, null, null, columnName + " DESC", "1");

        if (cursor != null && cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(columnName);
            lastItem = cursor.getInt(columnIndex);
            cursor.close();
        }

        return lastItem;
    }

    public Double getCups(){
        this.db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CUPS, null);
        double cups = 0;
        if (cursor != null && cursor.moveToFirst()) {
            int Index = cursor.getColumnIndex("Cups");
            cups = cursor.getInt(Index);
        }
        return cups;
    }

    public boolean Record_is_empty() {
        this.db = this.getReadableDatabase();
        boolean empty = true;
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_RECORD, null);
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
