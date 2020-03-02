package com.example.todolistpractice.ui;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolistpractice.R;
import com.example.todolistpractice.data.ToDoItemHandler;
import com.example.todolistpractice.model.ToDoItem;

import java.util.List;

public class ItemsRVAdapter extends RecyclerView.Adapter<ItemsRVAdapter.ViewHolder> {
    Context context;

    List<ToDoItem> toDoItemList;
    ToDoItemHandler toDoItemHandler;

    public ItemsRVAdapter(Context context, List<ToDoItem> toDoItemList) {
        this.context = context;
        this.toDoItemList = toDoItemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);

        return new ViewHolder(view, this.context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ToDoItem toDoItem = toDoItemList.get(position);

        holder.checkedTextView.setText(toDoItem.getName());
        holder.checkedTextView.setChecked(toDoItem.isDone());

        if(toDoItem.isDone())
            holder.checkedTextView.setCheckMarkDrawable(android.R.drawable.checkbox_on_background);
        else
            holder.checkedTextView.setCheckMarkDrawable(android.R.drawable.checkbox_off_background);
    }

    @Override
    public int getItemCount() {
        return toDoItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private static final String TAG = "ViewHolder";
        private Context context;

        private CheckedTextView checkedTextView;

        public ViewHolder(@NonNull View itemView, Context ctx) {
            super(itemView);

            this.context = ctx;

            checkedTextView = itemView.findViewById(R.id.itemRow_text);

            checkedTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int adapterPos = getAdapterPosition();
                    ToDoItem currentItem = toDoItemList.get(adapterPos);

                    updateItemStatus(currentItem, adapterPos);
                }
            });
        }

        private void updateItemStatus(ToDoItem currentItem, int adapterPos) {
            boolean isChecked = checkedTextView.isChecked();

            Log.d(TAG, "updateItemStatus: wasChecked=" + isChecked);

            if(!isChecked){
                checkedTextView.setCheckMarkDrawable(android.R.drawable.checkbox_on_background);
                checkedTextView.setChecked(true);

                currentItem.setDone(true);
            }
            else
            {
                checkedTextView.setCheckMarkDrawable(android.R.drawable.checkbox_off_background);
                checkedTextView.setChecked(false);

                currentItem.setDone(false);
            }

            toDoItemHandler = new ToDoItemHandler(context);
            toDoItemHandler.updateToDoItem(currentItem);

            Log.d(TAG, "updateItemStatus: itemId=" + currentItem.getId() + ", isDoneNow=" + currentItem.isDone());

            notifyItemChanged(adapterPos, currentItem);
        }
    }
}
