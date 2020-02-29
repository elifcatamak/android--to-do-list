package com.example.todolistpractice;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.todolistpractice.data.ToDoItemHandler;
import com.example.todolistpractice.model.ToDoItem;
import com.example.todolistpractice.ui.ItemsRVAdapter;
import com.example.todolistpractice.util.Constants;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class ShowItemsActivity extends AppCompatActivity {
    private static final String TAG = "ShowItemsActivity";

    private FloatingActionButton fabAdd;
    private EditText popupEditText;
    private Button popupButton;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private AlertDialog alertDialog;
    private AlertDialog.Builder builder;

    private ToDoItemHandler toDoItemHandler;
    private List<ToDoItem> toDoItemList;
    private int listId;

    public ShowItemsActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_items);

        fabAdd = findViewById(R.id.showItems_fab_add);
        recyclerView = findViewById(R.id.showItems_recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        Bundle bundle = getIntent().getExtras();

        if(bundle != null){
            listId = bundle.getInt(Constants.COLNAME_ID);
            Log.d(TAG, "onCreate: Bundle " + listId);

            toDoItemHandler = new ToDoItemHandler(this);
            toDoItemList = toDoItemHandler.getToDoItemsByListId(listId);

            adapter = new ItemsRVAdapter(ShowItemsActivity.this, toDoItemList);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();

            fabAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showAlertDialog();
                }
            });
        }
        else
        {
            Log.d(TAG, "onCreate: Bundle " + Constants.COLNAME_ID + " is null");
        }
    }

    private void showAlertDialog() {
        builder = new AlertDialog.Builder(this);

        View view = getLayoutInflater().inflate(R.layout.popup_todoitem, null);

        popupEditText = view.findViewById(R.id.popupItem_itemName);
        popupButton = view.findViewById(R.id.popupItem_button);

        popupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!popupEditText.getText().toString().isEmpty()){
                    saveToDoItem(createItem());
                }
                else
                {
                    Snackbar.make(v, "List name cannot be empty", Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        builder.setView(view);
        alertDialog = builder.create();
        alertDialog.show();
    }

    private ToDoItem createItem() {
        return new ToDoItem(popupEditText.getText().toString().trim(), false, listId);
    }

    private void saveToDoItem(ToDoItem toDoItem) {
        toDoItemHandler = new ToDoItemHandler(this);
        toDoItemHandler.addToDoItem(toDoItem);

        alertDialog.dismiss();

        Intent intent = new Intent(ShowItemsActivity.this, ShowItemsActivity.class);
        intent.putExtra(Constants.COLNAME_ID, listId);
        startActivity(intent);
        finish();
    }
}
