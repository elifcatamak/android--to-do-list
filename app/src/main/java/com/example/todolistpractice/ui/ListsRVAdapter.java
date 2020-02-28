package com.example.todolistpractice.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolistpractice.R;
import com.example.todolistpractice.model.ToDoList;

import java.text.MessageFormat;
import java.util.List;

public class ListsRVAdapter extends RecyclerView.Adapter<ListsRVAdapter.ViewHolder> {
    Context context;
    List<ToDoList> toDoLists;

    public ListsRVAdapter(Context context, List<ToDoList> toDoLists) {
        this.context = context;
        this.toDoLists = toDoLists;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row, parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.listName.setText(MessageFormat.format("List name: {0}", toDoLists.get(position).getListName()));
        holder.dateAdded.setText(MessageFormat.format("Date added: {0}", toDoLists.get(position).getDateAdded()));
    }

    @Override
    public int getItemCount() {
        return toDoLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView listName;
        private TextView dateAdded;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            listName = itemView.findViewById(R.id.listRow_listName);
            dateAdded = itemView.findViewById(R.id.listRow_listDateAdded);
        }
    }
}
