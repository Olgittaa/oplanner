package com.example.oplanner.ideas;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.oplanner.tasks.DataConverter;

@Database(
        entities = {Idea.class}, version = 1)
@TypeConverters(DataConverter.class)
public abstract class IdeaDatabase extends RoomDatabase {
    public abstract IdeasDao ideasDao();

    private static volatile IdeaDatabase db;

    public static IdeaDatabase getDb(Context context) {
        IdeaDatabase result = db;

        if (result == null) {
            synchronized (IdeaDatabase.class) {
                if (db == null) {
                    db = result = Room.databaseBuilder(context.getApplicationContext(),
                            IdeaDatabase.class, "idea-database").build();
                }
            }
        }
        return result;
    }
}