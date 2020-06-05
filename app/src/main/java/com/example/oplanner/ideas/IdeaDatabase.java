package com.example.oplanner.ideas;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.oplanner.tasks.DataConverter;
import com.example.oplanner.tasks.Task;
import com.example.oplanner.tasks.TaskDao;

@Database(
        entities = {Idea.class}, version = 2)
@TypeConverters(DataConverter.class)
public abstract class IdeaDatabase extends RoomDatabase {
    public abstract IdeasDao ideasDao();

    static final Migration FROM_1_TO_2 = new Migration(1, 2) {
        @Override
        public void migrate(final SupportSQLiteDatabase database) {

        }
    };
    private static volatile IdeaDatabase db;

    public static IdeaDatabase getDb(Context context) {
        IdeaDatabase result = db;

        if (result == null) {
            synchronized (IdeaDatabase.class) {
                if (db == null) {
                    db = result = Room.databaseBuilder(context.getApplicationContext(),
                            IdeaDatabase.class, "idea-database").addMigrations(FROM_1_TO_2).build();
                }
            }
        }
        return result;
    }
}