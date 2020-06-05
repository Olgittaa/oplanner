package com.example.oplanner.ideas;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import java.util.List;

@Dao
public interface IdeasDao {

    @Query("SELECT * FROM idea ORDER BY timestamp DESC")
    LiveData<List<Idea>> getAllIdeas();

    @Insert
    void save(Idea idea);

    @Delete
    void deleteIdea(Idea idea);

    @Update
    void refreshIdea(Idea idea);
}
