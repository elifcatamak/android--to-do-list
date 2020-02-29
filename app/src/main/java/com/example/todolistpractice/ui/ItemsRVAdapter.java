package com.example.todolistpractice.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolistpractice.R;
import com.example.todolistpractice.model.ToDoItem;

import java.util.List;

public class ItemsRVAdapter extends RecyclerView.Adapter<ItemsRVAdapter.ViewHolder> {
    Context context;
    List<ToDoItem> toDoItemList;

    public ItemsRVAdapter(Context context, List<ToDoItem> toDoItemList) {
        this.context = context;
        this.toDoItemList = toDoItemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ToDoItem toDoItem = toDoItemList.get(position);

        holder.checkedTextView.setText(toDoItem.getName());
    }

    @Override
    public int getItemCount() {
        return toDoItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CheckedTextView checkedTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            checkedTextView = itemView.findViewById(R.id.itemRow_text);
        }
    }
}
