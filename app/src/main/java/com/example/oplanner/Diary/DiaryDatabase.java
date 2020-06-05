package com.example.oplanner.Diary;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.oplanner.tasks.DataConverter;

@Database(
        entities = {Day.class}, version = 2)
@TypeConverters(DataConverter.class)
public abstract class DiaryDatabase extends RoomDatabase {
    public abstract DiaryDao diaryDao();

    static final Migration FROM_1_TO_2 = new Migration(1, 2) {
        @Override
        public void migrate(final SupportSQLiteDatabase database) {

        }
    };
    private static volatile DiaryDatabase db;

    public static DiaryDatabase getDb(Context context) {
        DiaryDatabase result = db;

        if (result == null) {
            synchronized (DiaryDatabase.class) {
                if (db == null) {
                    db = result = Room.databaseBuilder(context.getApplicationContext(),
                            DiaryDatabase.class, "diary-database").addMigrations(FROM_1_TO_2).build();
                }
            }
        }
        return result;
    }
}