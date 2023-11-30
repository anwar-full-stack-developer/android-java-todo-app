package com.example.todo.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

// below line is for setting table name.
@Entity(tableName = "todo_table")
public class TodoModal {
    @PrimaryKey(autoGenerate = true)

    // variable for our id.
    private int id;

    private String task;

    private String status;

    private String details;

    public TodoModal(String task, String status, String details) {
        this.task = task;
        this.status = status;
        this.details = details;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
