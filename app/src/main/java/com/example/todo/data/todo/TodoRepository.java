package com.example.todo.data.todo;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.todo.data.model.TodoModal;

import java.util.List;

public class TodoRepository {
    private static volatile TodoRepository instance;

    private TodoDataSource dataSource;

    private LiveData<List<TodoModal>> allTodos;

    // private constructor : singleton access
    private TodoRepository(TodoDataSource dataSource) {
        this.dataSource = dataSource;
//        this.dataSource = new TodoDataSource(application);
        allTodos = dataSource.getAllTodos();
    }

    public static synchronized TodoRepository getInstance(TodoDataSource dataSource) {
        if (instance == null) {
            instance = new TodoRepository(dataSource);
        }
        return instance;
    }


    // creating a method to insert the data to our database.
    public void insert(TodoModal model) {
        dataSource.insert(model);
    }

    // creating a method to update data in database.
    public void update(TodoModal model) {
        dataSource.update(model);
    }

    // creating a method to delete the data in our database.
    public void delete(TodoModal model) {
        dataSource.delete(model);
    }

    // below is the method to delete all the courses.
    public void deleteAllTodos() {
        dataSource.deleteAllTodos();
    }

    // below method is to read all the courses.
    public LiveData<List<TodoModal>> getAllTodos() {
        return allTodos;
    }

}
