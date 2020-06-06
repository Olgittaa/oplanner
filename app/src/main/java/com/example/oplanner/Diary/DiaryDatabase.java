package com.example.oplanner.Diary;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.oplanner.tasks.DataConverter;

@Database(
        entities = {Day.class}, version = 1)
@TypeConverters(DataConverter.class)
public abstract class DiaryDatabase extends RoomDatabase {
    public abstract DiaryDao diaryDao();

    private static volatile DiaryDatabase db;

    public static DiaryDatabase getDb(Context context) {
        DiaryDatabase result = db;

        if (result == null) {
            synchronized (DiaryDatabase.class) {
                if (db == null) {
                    db = result = Room.databaseBuilder(context.getApplicationContext(),
                            DiaryDatabase.class, "diary-database").build();
                }
            }
        }
        return result;
    }
}