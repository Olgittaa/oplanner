package com.example.oplanner.tasks;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(
        entities = {Task.class}, version = 1)
@TypeConverters(DataConverter.class)
public abstract class TasksDatabase extends RoomDatabase {
    public abstract TaskDao taskDao();

    private static volatile TasksDatabase db;

    public static TasksDatabase getDb(Context context) {
        TasksDatabase result = db;

        if (result == null) {
            synchronized (TasksDatabase.class) {
                if (db == null) {
                    db = result = Room.databaseBuilder(context.getApplicationContext(),
                            TasksDatabase.class, "task-database")
                            .build();
                }
            }
        }
        return result;
    }
}