package com.example.todo.ui.todo;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.todo.databinding.ActivityNewTodoBinding;


public class NewTodoActivity extends AppCompatActivity {
    // creating a constant string variable for our

    public static final String EXTRA_ID = "EXTRA_ID";
    public static final String EXTRA_TASK = "EXTRA_TASK";
    public static final String EXTRA_DETAILS = "EXTRA_DETAILS";
    public static final String EXTRA_STATUS = "EXTRA_STATUS";
    public static final String REQUEST_CODE = "REQUEST_CODE";


    // creating a variables for our button and edittext.
    private EditText editTask, editStatus, editDetails;
    private Button todoBtn;
    private int actionRequestCode = 0;
    private ActivityNewTodoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityNewTodoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // initializing our variables for each view.
        editTask = binding.idEdtTodoTask;
//        editStatus = binding.idEdtTodoStatus;
        editDetails = binding.idEdtTodoDetails;
        todoBtn = binding.idBtnSaveTodo;

        Intent intent = getIntent();

        actionRequestCode = intent.getIntExtra(REQUEST_CODE, 0);

        if (intent.hasExtra(EXTRA_ID)) {
            // if we get id for our data then we are
            // setting values to our edit text fields.
            editTask.setText(intent.getStringExtra(EXTRA_TASK));
            editDetails.setText(intent.getStringExtra(EXTRA_DETAILS));
//            editStatus.setText(intent.getStringExtra(EXTRA_STATUS));
            String todoStatus = intent.getStringExtra(EXTRA_STATUS);

            binding.radioButtonPending.setChecked(false);
            binding.radioButtonInProgress.setChecked(false);
            binding.radioButtonCompleted.setChecked(false);

            if (todoStatus.equals("Pending")){
                binding.radioButtonPending.setChecked(true);
                binding.radioButtonInProgress.setChecked(false);
                binding.radioButtonCompleted.setChecked(false);
            }
            else if(todoStatus.equals("InProgress")) {
                binding.radioButtonInProgress.setChecked(true);
                binding.radioButtonPending.setChecked(false);
                binding.radioButtonCompleted.setChecked(false);
            }
            else if(todoStatus.equals("Completed")) {
                binding.radioButtonCompleted.setChecked(true);
                binding.radioButtonInProgress.setChecked(false);
                binding.radioButtonPending.setChecked(false);
            }
            else {

            }

        }

        // adding on click listener for our save button.
        todoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // getting text value from edittext and validating if
                // the text fields are empty or not.
                String todoTask = editTask.getText().toString();
                String todoDesc = editDetails.getText().toString();
                String todoStatus = "";
//                String todoStatus = editStatus.getText().toString();

                if (binding.radioButtonPending.isChecked()){
                    todoStatus = "Pending";
                }
                if(binding.radioButtonInProgress.isChecked()) {
                    todoStatus = "InProgress";
                }
                if(binding.radioButtonCompleted.isChecked()) {
                    todoStatus = "Completed";
                }



                if (todoTask.isEmpty() || todoDesc.isEmpty() || todoStatus.isEmpty()) {
                    Toast.makeText(NewTodoActivity.this, "Please enter the valid todo details.", Toast.LENGTH_SHORT).show();
                    return;
                }
                // calling a method to save our todo.
                saveTodo(todoTask, todoStatus, todoDesc);
            }
        });
    }

    private void saveTodo(String task, String status, String details) {
        // inside this method we are passing
        // all the data via an intent.
        Intent data = new Intent();
        // in below line we are passing all our todo detail.
        data.putExtra(REQUEST_CODE, actionRequestCode);
        data.putExtra(EXTRA_TASK, task);
        data.putExtra(EXTRA_DETAILS, details);
        data.putExtra(EXTRA_STATUS, status);
        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            // in below line we are passing our id.
            data.putExtra(EXTRA_ID, id);
        }

        // at last we are setting result as data.
        setResult(RESULT_OK, data);

        finish();
        // close the activity
        // displaying a toast message after adding the data
//        Toast.makeText(this, "Todo has been saved to Room Database. ", Toast.LENGTH_SHORT).show();

    }
}