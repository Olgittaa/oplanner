package com.example.oplanner.tasks;

import android.annotation.SuppressLint;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Entity
public class Task {

    @PrimaryKey(autoGenerate = true)
    private int id = 0;
    private String description;

    @TypeConverters(DataConverter.class)
    private Date date = new Date();

    private boolean done = false;

    @TypeConverters(DataConverter.class)
    private List<String> subtasks;

    public void setId(int id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public List<String> getSubtasks() {
        if (subtasks == null) {
            subtasks = new ArrayList<>();
            return subtasks;
        }
        return subtasks;
    }

    public void setSubtasks(List<String> subtasks) {
        this.subtasks = subtasks;
    }
}
