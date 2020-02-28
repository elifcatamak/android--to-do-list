package com.example.todolistpractice.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolistpractice.MainActivity;
import com.example.todolistpractice.R;
import com.example.todolistpractice.data.ToDoListHandler;
import com.example.todolistpractice.model.ToDoList;

import java.text.MessageFormat;
import java.util.List;

public class ListsRVAdapter extends RecyclerView.Adapter<ListsRVAdapter.ViewHolder> {
    private Context context;
    private List<ToDoList> toDoLists;
    private ToDoListHandler toDoListHandler;

    public ListsRVAdapter(Context context, List<ToDoList> toDoLists) {
        this.context = context;
        this.toDoLists = toDoLists;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row, parent,false);

        return new ViewHolder(context, view);
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

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView listName;
        private TextView dateAdded;
        private Button deleteButton;
        private Button updateButton;
        private AlertDialog alertDialog;
        private AlertDialog.Builder builder;

        public ViewHolder(Context ctx, @NonNull View itemView) {
            super(itemView);

            context = ctx;

            listName = itemView.findViewById(R.id.listRow_listName);
            dateAdded = itemView.findViewById(R.id.listRow_listDateAdded);
            deleteButton = itemView.findViewById(R.id.listRow_deleteButton);

            deleteButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.listRow_deleteButton:
                    showSurePopUp();
                    break;
                default:
                    break;
            }
        }

        private void showSurePopUp() {
            builder = new AlertDialog.Builder(context);

            View view = LayoutInflater.from(context).inflate(R.layout.popup_sure, null);

            Button yesButton = view.findViewById(R.id.popupSure_yesButton);
            Button noButton = view.findViewById(R.id.popupSure_noButton);

            builder.setView(view);
            alertDialog = builder.create();
            alertDialog.show();

            yesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteList(toDoLists.get(getAdapterPosition()).getId());
                }
            });

            noButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                }
            });
        }

        private void deleteList(int id) {
            toDoListHandler = new ToDoListHandler(context);
            toDoListHandler.deleteToDoList(id);

            toDoLists.remove(getAdapterPosition());

            notifyItemRemoved(getAdapterPosition());

            alertDialog.dismiss();

            if(getItemCount() == 0)
                context.startActivity(new Intent(context, MainActivity.class));
        }
    }
}
