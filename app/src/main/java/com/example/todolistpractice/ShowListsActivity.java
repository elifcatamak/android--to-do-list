package com.example.todolistpractice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.todolistpractice.data.ToDoListHandler;
import com.example.todolistpractice.model.ToDoList;
import com.example.todolistpractice.ui.ListsRVAdapter;

import java.util.ArrayList;
import java.util.List;

public class ShowListsActivity extends AppCompatActivity {
    private static final String TAG = "ShowListsActivity";
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private ToDoListHandler toDoListHandler;
    private List<ToDoList> toDoLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_lists);

        recyclerView = findViewById(R.id.showLists_recyclerView);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        toDoListHandler = new ToDoListHandler(this);

        toDoLists = new ArrayList<>();
        toDoLists = toDoListHandler.getAllToDoLists();

        for(ToDoList t: toDoLists)
            Log.d(TAG, "onCreate: " + t.getListName());

        adapter = new ListsRVAdapter(ShowListsActivity.this, toDoLists);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

}
