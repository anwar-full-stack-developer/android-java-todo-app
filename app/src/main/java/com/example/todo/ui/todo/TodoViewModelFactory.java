package com.example.todo.ui.todo;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.todo.data.todo.TodoDataSource;
import com.example.todo.data.todo.TodoRepository;


/**
 * ViewModel provider factory to instantiate TodoViewModel.
 * Required given TodoViewModel has a non-empty constructor
 */
public class TodoViewModelFactory implements ViewModelProvider.Factory {

    private Application application;
    public TodoViewModelFactory(@NonNull Application application){
        this.application = application;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(TodoViewModel.class)) {
            return  (T) new TodoViewModel(TodoRepository.getInstance(new TodoDataSource(application)));
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}