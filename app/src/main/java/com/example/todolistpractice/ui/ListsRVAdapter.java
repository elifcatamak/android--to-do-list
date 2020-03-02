package com.example.todolistpractice.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolistpractice.R;
import com.example.todolistpractice.ShowItemsActivity;
import com.example.todolistpractice.data.ToDoListHandler;
import com.example.todolistpractice.model.ToDoList;
import com.example.todolistpractice.util.Constants;
import com.google.android.material.snackbar.Snackbar;

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
        ToDoList toDoList = toDoLists.get(position);

        holder.listName.setText(MessageFormat.format("List name: {0}", toDoList.getListName()));
        holder.dateAdded.setText(MessageFormat.format("Date added: {0}", toDoList.getDateAdded()));
    }

    @Override
    public int getItemCount() {
        return toDoLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private static final String TAG = "ViewHolder";
        private Context context;

        private TextView listName;
        private TextView dateAdded;
        private Button deleteButton;
        private Button updateButton;
        private Button detailsButton;
        private TextView popupTitle;
        private EditText popupListName;
        private Button popupButton;

        private AlertDialog alertDialog;
        private AlertDialog.Builder builder;

        public ViewHolder(Context ctx, @NonNull View itemView) {
            super(itemView);

            context = ctx;

            listName = itemView.findViewById(R.id.listRow_listName);
            dateAdded = itemView.findViewById(R.id.listRow_listDateAdded);
            deleteButton = itemView.findViewById(R.id.listRow_deleteButton);
            updateButton = itemView.findViewById(R.id.listRow_editButton);
            detailsButton = itemView.findViewById(R.id.listRow_detailsButton);

            deleteButton.setOnClickListener(this);
            updateButton.setOnClickListener(this);
            detailsButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPos = getAdapterPosition();
            ToDoList toDoList = toDoLists.get(adapterPos);

            switch (v.getId()){
                case R.id.listRow_deleteButton:
                    showSurePopUp(toDoList, adapterPos);
                    break;
                case R.id.listRow_editButton:
                    showAlertDialog(toDoList, adapterPos);
                    break;
                case R.id.listRow_detailsButton:
                    showListItems(toDoList);
                    break;
                default:
                    break;
            }
        }

        private void showListItems(ToDoList toDoList) {
            Intent intent = new Intent(context, ShowItemsActivity.class);
            intent.putExtra(Constants.COLNAME_ID, toDoList.getId());
            intent.putExtra(Constants.COLNAME_NAME, toDoList.getListName());

            context.startActivity(intent);
        }

        private void showAlertDialog(final ToDoList toDoList, final int adapterPos) {
            builder = new AlertDialog.Builder(context);

            View view = LayoutInflater.from(context).inflate(R.layout.popup_todolist,null);

            popupTitle = view.findViewById(R.id.popup_title);
            popupListName = view.findViewById(R.id.popup_listName);
            popupButton = view.findViewById(R.id.popup_button);

            popupTitle.setText(R.string.popup_updateTitle);
            popupButton.setText(R.string.popup_updateButton);

            popupListName.setText(toDoList.getListName());

            popupButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String newListName = popupListName.getText().toString().trim();

                    if(!newListName.isEmpty()){
                        updateList(toDoList, adapterPos, newListName);
                    }
                    else {
                        Snackbar.make(v, "List name cannot be empty", Snackbar.LENGTH_SHORT).show();
                    }
                }
            });

            builder.setView(view);
            alertDialog = builder.create();
            alertDialog.show();
        }

        private void updateList(ToDoList toDoList, int adapterPos, String newListName) {
            if(!newListName.equals(toDoList.getListName()))
            {
                Log.d(TAG, "updateList: listId=" + toDoList.getId());

                toDoList.setListName(newListName);

                toDoListHandler = new ToDoListHandler(context);
                toDoListHandler.updateToDoList(toDoList);

                notifyItemChanged(adapterPos, toDoList);
            }

            alertDialog.dismiss();
        }

        private void showSurePopUp(final ToDoList toDoList, final int adapterPos) {
            builder = new AlertDialog.Builder(context);

            View view = LayoutInflater.from(context).inflate(R.layout.popup_sure, null);

            Button yesButton = view.findViewById(R.id.popupSure_yesButton);
            Button noButton = view.findViewById(R.id.popupSure_noButton);
            TextView titleText = view.findViewById(R.id.popupSure_text);

            titleText.setText(MessageFormat.format("Are you sure to delete {0}?", toDoList.getListName()));

            builder.setView(view);
            alertDialog = builder.create();
            alertDialog.show();

            yesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteList(toDoList.getId(), adapterPos);
                }
            });

            noButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                }
            });
        }

        private void deleteList(long listId, int adapterPos) {
            Log.d(TAG, "deleteList: listId=" + listId);

            toDoListHandler = new ToDoListHandler(context);
            toDoListHandler.deleteToDoList(listId);

            toDoLists.remove(adapterPos);

            notifyItemRemoved(adapterPos);

            alertDialog.dismiss();
        }
    }
}
