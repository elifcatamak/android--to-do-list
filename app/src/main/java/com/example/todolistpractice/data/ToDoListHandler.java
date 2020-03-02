package com.example.todolistpractice.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.todolistpractice.model.ToDoList;
import com.example.todolistpractice.util.Constants;
import com.example.todolistpractice.util.UsefulMethods;

import java.util.ArrayList;
import java.util.List;

public class ToDoListHandler extends DatabaseHandler{
    private static final String TAG = "ToDoListHandler";

    public ToDoListHandler(@Nullable Context context) {
        super(context);
    }

    public void addToDoList(ToDoList toDoList){
        SQLiteDatabase db = this.getWritableDatabase();

        long currentDate = System.currentTimeMillis();

        ContentValues values = new ContentValues();
        values.put(Constants.COLNAME_NAME, toDoList.getListName());
        values.put(Constants.COLNAME_DATEADDED, currentDate);

        long id = db.insert(Constants.TABLENAME_TODOLIST, null, values);

        toDoList.setId(id);
        toDoList.setDateAdded(UsefulMethods.convertDate(currentDate));

        db.close();
    }

    public ToDoList getToDoList(long id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Constants.TABLENAME_TODOLIST,
                new String[]{Constants.COLNAME_ID, Constants.COLNAME_NAME, Constants.COLNAME_DATEADDED},
                Constants.COLNAME_ID + "=?", new String[]{String.valueOf(id)},
                null, null, null);

        ToDoList toDoList = new ToDoList();

        if (cursor != null && cursor.moveToFirst()){
            toDoList.setId(cursor.getLong(cursor.getColumnIndex(Constants.COLNAME_ID)));
            toDoList.setListName(cursor.getString(cursor.getColumnIndex(Constants.COLNAME_NAME)));

            String dateString = UsefulMethods.convertDate(cursor.getLong(cursor.getColumnIndex(Constants.COLNAME_DATEADDED)));
            toDoList.setDateAdded(dateString);
        }

        if(cursor != null)
            cursor.close();

        Log.d(TAG, "getToDoList: id=" + toDoList.getId());

        return toDoList;
    }

    public List<ToDoList> getAllToDoLists(){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Constants.TABLENAME_TODOLIST,
                new String[]{Constants.COLNAME_ID, Constants.COLNAME_NAME, Constants.COLNAME_DATEADDED},
                null, null, null, null,
                Constants.COLNAME_DATEADDED + " DESC");

        List<ToDoList> toDoLists = new ArrayList<>();

        if (cursor != null && cursor.moveToFirst()){
            do{
                ToDoList toDoList = new ToDoList();
                toDoList.setId(cursor.getLong(cursor.getColumnIndex(Constants.COLNAME_ID)));
                toDoList.setListName(cursor.getString(cursor.getColumnIndex(Constants.COLNAME_NAME)));

                String dateString = UsefulMethods.convertDate(cursor.getLong(cursor.getColumnIndex(Constants.COLNAME_DATEADDED)));
                toDoList.setDateAdded(dateString);

                toDoLists.add(toDoList);

            }while(cursor.moveToNext());
        }

        if(cursor != null)
            cursor.close();

        Log.d(TAG, "getAllToDoLists: listSize=" + toDoLists.size());

        return toDoLists;
    }

    public int updateToDoList(ToDoList toDoList){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constants.COLNAME_NAME, toDoList.getListName());

        int rowCount =  db.update(Constants.TABLENAME_TODOLIST, values, Constants.COLNAME_ID + "=?",
                new String[]{String.valueOf(toDoList.getId())});

        Log.d(TAG, "updateToDoList: rowCount=" + rowCount);

        return rowCount;
    }

    public void deleteToDoList(long id){
        SQLiteDatabase db = this.getWritableDatabase();

        int rowCount = db.delete(Constants.TABLENAME_TODOLIST, Constants.COLNAME_ID + "=?",
                new String[]{String.valueOf(id)});

        Log.d(TAG, "deleteToDoList: rowCount=" + rowCount);

        db.close();
    }

    public int getCount(){
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + Constants.TABLENAME_TODOLIST;

        Cursor cursor = db.rawQuery(query, null);

        int result = cursor.getCount();

        cursor.close();

        Log.d(TAG, "getCount: " + result);

        return result;
    }
}
