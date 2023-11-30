package com.example.todo.ui.todo;

import android.content.Context;
import android.os.Bundle;

import android.app.Activity;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo.data.model.TodoModal;
import com.example.todo.databinding.ActivityTodoBinding;

import java.util.List;
import java.util.concurrent.Executors;

public class TodoActivity extends AppCompatActivity {
    // creating a variables for our recycler view.
    private RecyclerView todosRV;
    private static final int ADD_TODO_REQUEST = 1;
    private static final int EDIT_TODO_REQUEST = 2;
    private static final String REQUEST_CODE = "REQUEST_CODE";
    private TodoViewModel todoViewModel;
    private ActivityTodoBinding binding;

    private ActivityResultLauncher<Intent> launcherNewTodo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityTodoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        todoViewModel = new ViewModelProvider(this, new TodoViewModelFactory(getApplication()))
                .get(TodoViewModel.class);

        final Button btnNewTodo = binding.btnNewTodo;
        btnNewTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TodoActivity", "Todo Button Clicked");
                Toast.makeText(getApplicationContext(), "Todo button Clicked", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(v.getContext(), NewTodoActivity.class);
                //pass parameter
                intent.putExtra("dispatchFrom", "TodoActivity_to_NewTodoActivity");
                intent.putExtra(REQUEST_CODE, ADD_TODO_REQUEST);
                launcherNewTodo.launch(intent);
//                v.getContext().startActivity(intent);
            }
        });


        todosRV = binding.idRVTodo;

// setting layout manager to our adapter class.
        todosRV.setLayoutManager(new LinearLayoutManager(this));
        todosRV.setHasFixedSize(true);

//        // initializing adapter for recycler view.
        final TodoRVAdapter adapter = new TodoRVAdapter();

//        // setting adapter class for recycler view.
        todosRV.setAdapter(adapter);


        // below line is use to get all the todos from view modal.
        todoViewModel.getAllTodos().observe(this, new Observer<List<TodoModal>>() {
            @Override
            public void onChanged(List<TodoModal> models) {
                // when the data is changed in our models we are
                // adding that list to our adapter class.
                adapter.submitList(models);
            }
        });

        // below method is use to add swipe to delete method for item of recycler view.
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                // on recycler view item swiped then we are deleting the item of our recycler view.
                //avoid main thread, execute task in background task on a separate thread
                Executors.newSingleThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        todoViewModel.delete(adapter.getTodoAt(viewHolder.getAdapterPosition()));
                    }
                });
                Toast.makeText(TodoActivity.this, "Todo deleted", Toast.LENGTH_SHORT).show();
            }
        }).
                // below line is use to attach this to recycler view.
                        attachToRecyclerView(todosRV);

        // below line is use to set item click listener for our item of recycler view.
        adapter.setOnItemClickListener(new TodoRVAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(TodoModal model) {
                // after clicking on item of recycler view
                // we are opening a new activity and passing
                // a data to our activity.
                Intent intent = new Intent(TodoActivity.this, NewTodoActivity.class);
                intent.putExtra(NewTodoActivity.EXTRA_ID, model.getId());
                intent.putExtra(NewTodoActivity.EXTRA_TASK, model.getTask());
                intent.putExtra(NewTodoActivity.EXTRA_STATUS, model.getStatus());
                intent.putExtra(NewTodoActivity.EXTRA_DETAILS, model.getDetails());

                // below line is to start a new activity and
                // adding a edit Todo constant.
                //pass parameter
                intent.putExtra("dispatchFrom", "TodoActivity_to_NewTodoActivity");
                intent.putExtra(REQUEST_CODE, EDIT_TODO_REQUEST);
                launcherNewTodo.launch(intent);
            }
        });


        launcherNewTodo = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    Intent data = result.getData();
                    Log.d("TodoActivity", "New Todo call back");
                    Log.d("TodoActivity", "RequestCode: "
                            + data.getIntExtra(REQUEST_CODE, 0)
                            + ", ResultCode: "
                            + result.getResultCode()

                    );
                    if (result.getResultCode() == RESULT_OK) {
//                        Intent data = result.getData();
                        int requestCode = data.getIntExtra(REQUEST_CODE, 0);
                        onActivityResultCB(requestCode, result.getResultCode(), data);
                    }
                }
        );
    }


    //    @Override
    protected void onActivityResultCB(int requestCode, int resultCode, @Nullable Intent data) {
        Log.d("TodoActivity", "RequestCode: "
                + data.getIntExtra(REQUEST_CODE, 0)
                + ", ResultCode: "
                + resultCode

        );
        if (requestCode == ADD_TODO_REQUEST && resultCode == RESULT_OK) {
            Log.d("TodoActivity", "Adding New Todo");
            String task = data.getStringExtra(NewTodoActivity.EXTRA_TASK);
            String status = data.getStringExtra(NewTodoActivity.EXTRA_STATUS);
            String details = data.getStringExtra(NewTodoActivity.EXTRA_DETAILS);
            TodoModal model = new TodoModal(task, status, details);
            //avoid main thread, execute task in background task on a separate thread
            Executors.newSingleThreadExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    todoViewModel.insert(model);
                }
            });

            Toast.makeText(this, "Todo saved", Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_TODO_REQUEST && resultCode == RESULT_OK) {
            Log.d("TodoActivity", "Update New Todo");
            int id = data.getIntExtra(NewTodoActivity.EXTRA_ID, -1);
            if (id == -1) {
                Toast.makeText(this, "Todo can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }

            String task = data.getStringExtra(NewTodoActivity.EXTRA_TASK);
            String status = data.getStringExtra(NewTodoActivity.EXTRA_STATUS);
            String details = data.getStringExtra(NewTodoActivity.EXTRA_DETAILS);
            TodoModal model = new TodoModal(task, status, details);
            model.setId(id);

            //avoid main thread, execute task in background task on a separate thread
            Executors.newSingleThreadExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    todoViewModel.update(model);
                }
            });

            Toast.makeText(this, "Todo updated", Toast.LENGTH_SHORT).show();
        } else {
            Log.d("TodoActivity", "No action");
            Toast.makeText(this, "Todo not saved", Toast.LENGTH_SHORT).show();
        }
    }
}