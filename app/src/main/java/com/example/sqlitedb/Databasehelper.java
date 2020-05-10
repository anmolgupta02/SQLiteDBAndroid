package com.example.sqlitedb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Databasehelper extends SQLiteOpenHelper {


    public final static String DATABASE_NAME = "Test.db";
    public final static String TABLE_NAME = "studentRecord_table";
    public final static String COL_1 = "ID";
    public final static String COL_2 = "NAME";
    public final static String COL_3 = "EMAIL";
    public final static String COL_4 = "COURSE_COUNT";

    public Databasehelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS "
                + TABLE_NAME +
                " (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                " NAME TEXT," +
                " EMAIL TEXT," +
                " COURSE_COUNT INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertInto(String Name, String Email, String courseCount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_2, Name);
        contentValues.put(COL_3, Email);
        contentValues.put(COL_4, courseCount);

        long Result = db.insert(TABLE_NAME, null, contentValues);

        if (Result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean updateData(String ID, String Name, String Email, String courseCount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_1, ID);
        contentValues.put(COL_2, Name);
        contentValues.put(COL_3, Email);
        contentValues.put(COL_4, courseCount);

        db.update(TABLE_NAME, contentValues, "ID=?", new String[]{ID});
        return true;
    }

    public Cursor getData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE ID='" + id + "'";
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    public Integer deleteData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID=?", new String[]{id});
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return cursor;
    }

    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
    }
}
