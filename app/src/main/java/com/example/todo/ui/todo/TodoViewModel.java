package com.example.todo.ui.todo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.todo.data.model.TodoModal;
import com.example.todo.data.todo.TodoRepository;

import java.util.List;

public class TodoViewModel extends ViewModel {

    private TodoRepository repository;

    private LiveData<List<TodoModal>> allTodos;

    public TodoViewModel(TodoRepository todoRepository) {
        this.repository = todoRepository;
//        repository = TodoRepository.getInstance(application);
        this.allTodos = repository.getAllTodos();
    }

    // below method is use to insert the data to our repository.
    public void insert(TodoModal model) {
        repository.insert(model);
    }

    // below line is to update data in our repository.
    public void update(TodoModal model) {
        repository.update(model);
    }

    // below line is to delete the data in our repository.
    public void delete(TodoModal model) {
        repository.delete(model);
    }

    // below method is to delete all the todos in our list.
    public void deleteAllTodos() {
        repository.deleteAllTodos();
    }

    // below method is to get all the todos in our list.
    public LiveData<List<TodoModal>> getAllTodos() {
        return allTodos;
    }
}
