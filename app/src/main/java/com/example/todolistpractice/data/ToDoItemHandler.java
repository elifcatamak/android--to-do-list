package com.example.todolistpractice.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.todolistpractice.model.ToDoItem;
import com.example.todolistpractice.util.Constants;
import com.example.todolistpractice.util.UsefulMethods;

import java.util.ArrayList;
import java.util.List;

public class ToDoItemHandler extends DatabaseHandler {
    private static final String TAG = "ToDoItemHandler";

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

        long id = db.insert(Constants.TABLENAME_TODOITEM, null, values);

        toDoItem.setId(id);

        db.close();
    }

    public ToDoItem getToDoItem(long id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Constants.TABLENAME_TODOITEM,
                new String[]{Constants.COLNAME_ID, Constants.COLNAME_NAME, Constants.COLNAME_ISDONE,
                        Constants.COLNAME_DATEADDED, Constants.COLNAME_LISTID},
                Constants.COLNAME_ID + "=?", new String[]{String.valueOf(id)},
                null, null, null);

        ToDoItem toDoItem = new ToDoItem();

        if (cursor != null && cursor.moveToFirst()){
            toDoItem.setId(cursor.getLong(cursor.getColumnIndex(Constants.COLNAME_ID)));
            toDoItem.setName(cursor.getString(cursor.getColumnIndex(Constants.COLNAME_NAME)));
            toDoItem.setDone(cursor.getInt(cursor.getColumnIndex(Constants.COLNAME_ISDONE)) == 1);
            toDoItem.setListId(cursor.getLong(cursor.getColumnIndex(Constants.COLNAME_LISTID)));

            String dateString = UsefulMethods.convertDate(cursor.getLong(cursor.getColumnIndex(Constants.COLNAME_DATEADDED)));
            toDoItem.setDateAdded(dateString);
        }

        if(cursor != null)
            cursor.close();

        Log.d(TAG, "getToDoItem: id=" + toDoItem.getId());

        return toDoItem;
    }

    public List<ToDoItem> getToDoItemsByListId(long listId){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Constants.TABLENAME_TODOITEM,
                new String[]{Constants.COLNAME_ID, Constants.COLNAME_NAME, Constants.COLNAME_ISDONE,
                        Constants.COLNAME_DATEADDED, Constants.COLNAME_LISTID},
                Constants.COLNAME_LISTID + "=?", new String[]{String.valueOf(listId)},
                null, null,
                Constants.COLNAME_DATEADDED + " DESC");

        List<ToDoItem> toDoItems = new ArrayList<>();

        if (cursor != null && cursor.moveToFirst()){
            do{
                ToDoItem toDoItem = new ToDoItem();
                toDoItem.setId(cursor.getLong(cursor.getColumnIndex(Constants.COLNAME_ID)));
                toDoItem.setName(cursor.getString(cursor.getColumnIndex(Constants.COLNAME_NAME)));
                toDoItem.setDone(cursor.getInt(cursor.getColumnIndex(Constants.COLNAME_ISDONE)) == 1);
                toDoItem.setListId(cursor.getLong(cursor.getColumnIndex(Constants.COLNAME_LISTID)));

                String dateString = UsefulMethods.convertDate(cursor.getLong(cursor.getColumnIndex(Constants.COLNAME_DATEADDED)));
                toDoItem.setDateAdded(dateString);

                toDoItems.add(toDoItem);

            }while(cursor.moveToNext());
        }

        if(cursor != null)
            cursor.close();

        Log.d(TAG, "getToDoItemsByListId: listSize=" + toDoItems.size());

        return toDoItems;
    }

    public int updateToDoItem(ToDoItem toDoItem){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constants.COLNAME_NAME, toDoItem.getName());
        values.put(Constants.COLNAME_ISDONE, toDoItem.isDone() ? 1 : 0);
        values.put(Constants.COLNAME_DATEADDED, System.currentTimeMillis());

        int rowCount =  db.update(Constants.TABLENAME_TODOITEM, values, Constants.COLNAME_ID + "=?",
                new String[]{String.valueOf(toDoItem.getId())});

        Log.d(TAG, "updateToDoItem: rowCount=" + rowCount);
        
        return rowCount;
    }

    public void deleteToDoItem(long id){
        SQLiteDatabase db = this.getWritableDatabase();

        int rowCount = db.delete(Constants.TABLENAME_TODOITEM, Constants.COLNAME_ID + "=?",
                new String[]{String.valueOf(id)});

        Log.d(TAG, "deleteToDoItem: rowCount=" + rowCount);

        db.close();
    }

    public int getCountByListId(long listId){
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + Constants.TABLENAME_TODOITEM + " WHERE " +
                Constants.COLNAME_LISTID + "=?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(listId)});

        if(cursor == null)
            return 0;

        int result = cursor.getCount();

        cursor.close();

        Log.d(TAG, "getCountByListId: " + result);

        return result;
    }
}
