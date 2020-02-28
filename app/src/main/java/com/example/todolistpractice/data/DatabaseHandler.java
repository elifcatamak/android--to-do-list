package com.example.todolistpractice.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.todolistpractice.util.Constants;

public class DatabaseHandler extends SQLiteOpenHelper {

    public DatabaseHandler(@Nullable Context context) {
        super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query;

        query = "CREATE TABLE IF NOT EXISTS " + Constants.TABLENAME_TODOLIST + " (" +
                Constants.COLNAME_ID + " INTEGER PRIMARY KEY, " +
                Constants.COLNAME_NAME + " TEXT, " +
                Constants.COLNAME_DATEADDED + " LONG)";

        db.execSQL(query);

        query = "CREATE TABLE IF NOT EXISTS " + Constants.TABLENAME_TODOITEM + " (" +
                Constants.COLNAME_ID + " INTEGER PRIMARY KEY, " +
                Constants.COLNAME_NAME + " TEXT, " +
                Constants.COLNAME_ISDONE + " INTEGER, " +
                Constants.COLNAME_DATEADDED + " LONG)";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query;

        query = "DROP TABLE IF EXISTS " + Constants.TABLENAME_TODOLIST;
        db.execSQL(query);

        query = "DROP TABLE IF EXISTS " + Constants.TABLENAME_TODOITEM;
        db.execSQL(query);

        this.onCreate(db);
    }
}
