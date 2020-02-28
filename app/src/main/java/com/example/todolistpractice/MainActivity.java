package com.example.todolistpractice;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.todolistpractice.data.DatabaseHandler;
import com.example.todolistpractice.data.ToDoListHandler;
import com.example.todolistpractice.model.ToDoList;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private TextView popupTitle;
    private EditText popupListName;
    private Button popupButton;
    private FloatingActionButton fabAdd;

    private AlertDialog alertDialog;
    private AlertDialog.Builder builder;

    private List<ToDoList> toDoLists;
    private DatabaseHandler databaseHandler;
    private ToDoListHandler toDoListHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHandler = new DatabaseHandler(this);

        decideActivityToShow();

        fabAdd = findViewById(R.id.main_fab_add);

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog();
            }
        });
    }

    private void decideActivityToShow() {
        toDoListHandler = new ToDoListHandler(this);

        if(toDoListHandler.getCount() != 0){
            startActivity(new Intent(MainActivity.this, ShowListsActivity.class));
            finish();
        }
    }

    private void showAlertDialog() {
        builder = new AlertDialog.Builder(this);

        View view = getLayoutInflater().inflate(R.layout.popup_todolist,null);

        popupTitle = view.findViewById(R.id.popup_title);
        popupListName = view.findViewById(R.id.popup_listName);
        popupButton = view.findViewById(R.id.popup_button);

        popupTitle.setText(R.string.popup_addTitle);
        popupButton.setText(R.string.popup_saveButton);

        popupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!popupListName.getText().toString().isEmpty()){
                    saveToDoList();
                }
                else{
                    Snackbar.make(v, "List name cannot be empty", Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        builder.setView(view);
        alertDialog = builder.create();
        alertDialog.show();
    }

    private void saveToDoList() {
        ToDoList toDoList = new ToDoList(popupListName.getText().toString().trim());

        toDoListHandler = new ToDoListHandler(this);
        toDoListHandler.addToDoList(toDoList);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                alertDialog.dismiss();
                startActivity(new Intent(MainActivity.this, ShowListsActivity.class));
            }
        }, 1500);
    }
}
