package com.example.todolistpractice.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.todolistpractice.model.ToDoItem;
import com.example.todolistpractice.util.Constants;
import com.example.todolistpractice.util.UsefulMethods;

import java.util.ArrayList;
import java.util.List;

public class ToDoItemHandler extends DatabaseHandler {
    private List<ToDoItem> toDoItems;

    public ToDoItemHandler(@Nullable Context context) {
        super(context);
    }

    public void addToDoItem(ToDoItem toDoItem){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constants.COLNAME_NAME, toDoItem.getName());
        values.put(Constants.COLNAME_ISDONE, toDoItem.isDone() ? 1 : 0);
        values.put(Constants.COLNAME_DATEADDED, System.currentTimeMillis());
        values.put(Constants.COLNAME_LISTID, toDoItem.getListId());

        db.insert(Constants.TABLENAME_TODOITEM, null, values);

        db.close();
    }

    public ToDoItem getToDoItem(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Constants.TABLENAME_TODOITEM,
                new String[]{Constants.COLNAME_ID, Constants.COLNAME_NAME, Constants.COLNAME_ISDONE,
                        Constants.COLNAME_DATEADDED, Constants.COLNAME_LISTID},
                Constants.COLNAME_ID + "=?", new String[]{String.valueOf(id)},
                null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        ToDoItem toDoItem = new ToDoItem();
        toDoItem.setId(cursor.getInt(cursor.getColumnIndex(Constants.COLNAME_ID)));
        toDoItem.setName(cursor.getString(cursor.getColumnIndex(Constants.COLNAME_NAME)));
        toDoItem.setDone(cursor.getInt(cursor.getColumnIndex(Constants.COLNAME_ISDONE)) == 1);
        toDoItem.setListId(cursor.getInt(cursor.getColumnIndex(Constants.COLNAME_LISTID)));

        String dateString = UsefulMethods.convertDate(cursor.getLong(cursor.getColumnIndex(Constants.COLNAME_DATEADDED)));
        toDoItem.setDateAdded(dateString);

        return toDoItem;
    }

    public List<ToDoItem> getToDoItemsByListId(int listId){
        toDoItems = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Constants.TABLENAME_TODOITEM,
                new String[]{Constants.COLNAME_ID, Constants.COLNAME_NAME, Constants.COLNAME_ISDONE,
                        Constants.COLNAME_DATEADDED, Constants.COLNAME_LISTID},
                Constants.COLNAME_LISTID + "=?", new String[]{String.valueOf(listId)},
                null, null,
                Constants.COLNAME_DATEADDED + " DESC");

        if (cursor != null && cursor.moveToFirst()){
            do{
                ToDoItem toDoItem = new ToDoItem();
                toDoItem.setId(cursor.getInt(cursor.getColumnIndex(Constants.COLNAME_ID)));
                toDoItem.setName(cursor.getString(cursor.getColumnIndex(Constants.COLNAME_NAME)));
                toDoItem.setDone(cursor.getInt(cursor.getColumnIndex(Constants.COLNAME_ISDONE)) == 1);
                toDoItem.setListId(cursor.getInt(cursor.getColumnIndex(Constants.COLNAME_LISTID)));

                String dateString = UsefulMethods.convertDate(cursor.getLong(cursor.getColumnIndex(Constants.COLNAME_DATEADDED)));
                toDoItem.setDateAdded(dateString);

                toDoItems.add(toDoItem);

            }while(cursor.moveToNext());
        }

        return toDoItems;
    }

    public int updateToDoItem(ToDoItem toDoItem){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constants.COLNAME_NAME, toDoItem.getName());
        values.put(Constants.COLNAME_ISDONE, toDoItem.isDone() ? 1 : 0);
        values.put(Constants.COLNAME_DATEADDED, System.currentTimeMillis());

        return db.update(Constants.TABLENAME_TODOITEM, values, Constants.COLNAME_ID + "=?",
                new String[]{String.valueOf(toDoItem.getId())});
    }

    public void deleteToDoItem(int id){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(Constants.TABLENAME_TODOITEM, Constants.COLNAME_ID + "=?",
                new String[]{String.valueOf(id)});

        db.close();
    }

    public int getCountByListId(int listId){
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + Constants.TABLENAME_TODOITEM + " WHERE " +
                Constants.COLNAME_LISTID + "=?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(listId)});

        return cursor.getCount();
    }
}
