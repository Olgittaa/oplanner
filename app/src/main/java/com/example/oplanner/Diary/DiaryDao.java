package com.example.oplanner.Diary;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.oplanner.ideas.Idea;
import com.example.oplanner.tasks.Task;

import java.util.List;

@Dao
public interface DiaryDao {

    @Query("SELECT * FROM day ORDER BY date DESC")
    LiveData<List<Day>> getAllDays();

    @Insert
    void save(Day day);

    @Update
    void refreshDay(Day day);

    @Delete
    void deleteDay(Day day);
}
