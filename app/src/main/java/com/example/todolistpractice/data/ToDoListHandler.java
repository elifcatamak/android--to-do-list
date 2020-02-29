package com.example.todolistpractice.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.todolistpractice.model.ToDoList;
import com.example.todolistpractice.util.Constants;
import com.example.todolistpractice.util.UsefulMethods;

import java.util.ArrayList;
import java.util.List;

public class ToDoListHandler extends DatabaseHandler{
    private List<ToDoList> toDoLists;

    public ToDoListHandler(@Nullable Context context) {
        super(context);
    }

    public void addToDoList(ToDoList toDoList){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constants.COLNAME_NAME, toDoList.getListName());
        values.put(Constants.COLNAME_DATEADDED, System.currentTimeMillis());

        db.insert(Constants.TABLENAME_TODOLIST, null, values);

        db.close();
    }

    public ToDoList getToDoList(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Constants.TABLENAME_TODOLIST,
                new String[]{Constants.COLNAME_ID, Constants.COLNAME_NAME, Constants.COLNAME_DATEADDED},
                Constants.COLNAME_ID + "=?", new String[]{String.valueOf(id)},
                null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        ToDoList toDoList = new ToDoList();
        toDoList.setId(cursor.getInt(cursor.getColumnIndex(Constants.COLNAME_ID)));
        toDoList.setListName(cursor.getString(cursor.getColumnIndex(Constants.COLNAME_NAME)));

        String dateString = UsefulMethods.convertDate(cursor.getLong(cursor.getColumnIndex(Constants.COLNAME_DATEADDED)));
        toDoList.setDateAdded(dateString);

        return toDoList;
    }

    public List<ToDoList> getAllToDoLists(){
        toDoLists = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Constants.TABLENAME_TODOLIST,
                new String[]{Constants.COLNAME_ID, Constants.COLNAME_NAME, Constants.COLNAME_DATEADDED},
                null, null, null, null,
                Constants.COLNAME_DATEADDED + " DESC");

        if (cursor != null && cursor.moveToFirst()){
            do{
                ToDoList toDoList = new ToDoList();
                toDoList.setId(cursor.getInt(cursor.getColumnIndex(Constants.COLNAME_ID)));
                toDoList.setListName(cursor.getString(cursor.getColumnIndex(Constants.COLNAME_NAME)));

                String dateString = UsefulMethods.convertDate(cursor.getLong(cursor.getColumnIndex(Constants.COLNAME_DATEADDED)));
                toDoList.setDateAdded(dateString);

                toDoLists.add(toDoList);

            }while(cursor.moveToNext());
        }

        return toDoLists;
    }

    public int updateToDoList(ToDoList toDoList){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constants.COLNAME_NAME, toDoList.getListName());
        values.put(Constants.COLNAME_DATEADDED, System.currentTimeMillis());

        return db.update(Constants.TABLENAME_TODOLIST, values, Constants.COLNAME_ID + "=?",
                new String[]{String.valueOf(toDoList.getId())});
    }

    public void deleteToDoList(int id){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(Constants.TABLENAME_TODOLIST, Constants.COLNAME_ID + "=?",
                new String[]{String.valueOf(id)});

        db.close();
    }

    public int getCount(){
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + Constants.TABLENAME_TODOLIST;

        Cursor cursor = db.rawQuery(query, null);

        return cursor.getCount();
    }
}
