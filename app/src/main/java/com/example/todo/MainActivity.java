package com.example.todo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.todo.databinding.ActivityMainBinding;
import com.example.todo.ui.todo.TodoActivity;


public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);


        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        final Button btnLogin = binding.btnNavToTodo;
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("MainActivity", "Todo Button Clicked");
                Toast.makeText(getApplicationContext(), "Todo button Clicked", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(v.getContext(), TodoActivity.class);
                //pass parameter
                intent.putExtra("dispatchFrom", "MainActivity_to_TodoActivity");
                v.getContext().startActivity(intent);
            }
        });
    }
}