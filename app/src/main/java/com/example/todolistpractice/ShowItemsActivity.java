package com.example.todolistpractice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class ShowItemsActivity extends AppCompatActivity {
    private static final String TAG = "ShowItemsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_items);

        Bundle bundle = getIntent().getExtras();

        if(bundle != null){
            Log.d(TAG, "onCreate: ListId: " + bundle.getInt("listId"));
        }
    }
}
