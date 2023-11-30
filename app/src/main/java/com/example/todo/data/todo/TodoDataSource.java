package com.example.todo.data.todo;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.todo.data.AppDatabase;
import com.example.todo.data.model.TodoDao;
import com.example.todo.data.model.TodoModal;

import java.util.List;

public class TodoDataSource {
    private final TodoDao todoDao;
    private LiveData<List<TodoModal>> allTodos;
    public TodoDataSource(@NonNull Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        todoDao = database.todoDao();
        allTodos = todoDao.getAllTodos();

    }

    // below method is to read all the todos.
    public LiveData<List<TodoModal>> getAllTodos() {
        return allTodos;
    }

    // creating a method to insert the data to our database.
    public void insert(TodoModal model) {
        todoDao.insert(model);
    }

    // creating a method to update data in database.
    public void update(TodoModal model) {
        todoDao.update(model);
    }

    // creating a method to delete the data in our database.
    public void delete(TodoModal model) {
        todoDao.delete(model);
    }

    // below is the method to delete all the todos.
    public void deleteAllTodos() {
        todoDao.deleteAllTodos();
    }

}
