package com.example.todo.ui.todo;

import com.example.todo.R;

import android.service.autofill.UserData;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.example.todo.data.model.TodoModal;

import java.util.ArrayList;
import java.util.List;

public class TodoRVAdapter extends RecyclerView.Adapter<TodoRVAdapter.ViewHolder>{

    // creating a variable for on item click listener.
    private OnItemClickListener listener;

    List<TodoModal> list = new ArrayList<>();

    // creating a constructor class for our adapter class.
    public TodoRVAdapter(List<TodoModal> list){
        this.list = list;
    }
    public TodoRVAdapter(){
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void submitList(List<TodoModal> models) {
        list.clear();
        list.addAll(models);
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // below line is use to inflate our layout
        // file for each item of our recycler view.
        View item = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.todo_rv_item, parent, false);
        return new ViewHolder(item);
    }


    // creating a call back for item of recycler view.
    private static final DiffUtil.ItemCallback<TodoModal> DIFF_CALLBACK = new DiffUtil.ItemCallback<TodoModal>() {
        @Override
        public boolean areItemsTheSame(TodoModal oldItem, TodoModal newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(TodoModal oldItem, TodoModal newItem) {
            // below line is to check the todo name, description and todo duration.
            return oldItem.getDetails().equals(newItem.getDetails()) &&
                    oldItem.getStatus().equals(newItem.getStatus()) &&
                    oldItem.getTask().equals(newItem.getTask());
        }
    };

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // below line of code is use to set data to
        // each item of our recycler view.
        TodoModal model = getTodoAt(position);
        holder.task.setText(model.getTask());
        holder.status.setText(model.getStatus());
        holder.details.setText(model.getDetails());
    }

    // creating a method to get todo modal for a specific position.
    public TodoModal getTodoAt(int position) {
        return getItem(position);
    }

    private TodoModal getItem(int position) {
        return list.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // view holder class to create a variable for each view.
        TextView task, status, details;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing each view of our recycler view.
            task = itemView.findViewById(R.id.idTodoTask);
            status = itemView.findViewById(R.id.idTodoStatus);
            details = itemView.findViewById(R.id.idTodoDetails);

            // adding on click listener for each item of recycler view.
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // inside on click listener we are passing
                    // position to our item of recycler view.
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(TodoModal model);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
