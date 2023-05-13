package com.example.watertracker;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class CoreDatabase extends SQLiteOpenHelper {
    static final private String DATABASE_NAME = "WaterTracker.db";
    static final private int DATABASE_VERSION = 1;
    static final private String TABLE_USERNAMES = "Usernames";
    static final private String TABLE_RECORD = "DailyWaterIntake";
    static private String lastUsername = "";

    Context context;
    SQLiteDatabase db;


    public CoreDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_USERNAMES + " (username TEXT PRIMARY KEY);");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_RECORD + "( " +
                "`habit name` TEXT PRIMARY KEY, `habit count` INTEGER, `date` TEXT);");
    }

    @Override
    public void onUpgrade(android.database.sqlite.SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS `DB_TABLE`");
        onCreate(db);
    }

    public void insertUsername(String username) {
        this.db = this.getWritableDatabase();
//        db.execSQL("INSERT INTO TABLE_NAME (value) VALUES ('"+username+"');");
        // insert username into database
        db.execSQL("INSERT INTO " + TABLE_USERNAMES + " (username) VALUES ('" + username + "');");
        //db.close();
        lastUsername = username;
    }


    public Boolean Usernames_is_empty() {
        this.db = this.getReadableDatabase();
        boolean empty = true;
        Cursor cur = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_USERNAMES, null);
        if (cur != null && cur.moveToFirst()) {
            empty = (cur.getInt(0) == 0);
        }
        cur.close();

        return empty;

    }
}
