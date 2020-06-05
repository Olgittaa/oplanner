package com.example.oplanner.tasks;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.Date;
import java.util.List;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM task WHERE date BETWEEN :daystart AND :dayend order by date")
    LiveData<List<Task>> listFor1Day(long daystart, long dayend);

    @Query("SELECT * FROM task WHERE date > :daystart order by date")
    LiveData<List<Task>> listLater(long daystart);

    @Insert
    void save(Task task);

    @Update
    void refreshTask(Task task);

    @Delete
    void deleteTask(Task task);
}
