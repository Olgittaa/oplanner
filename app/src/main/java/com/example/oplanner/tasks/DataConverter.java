package com.example.oplanner.tasks;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.sql.Time;
import java.util.Date;
import java.util.List;

public class DataConverter {

    @TypeConverter
    public String fromSubtaskList(List<String> subtasks) {
        if (subtasks == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<String>>() {
        }.getType();
        String json = gson.toJson(subtasks, type);
        return json;
    }

    @TypeConverter
    public List<String> toSubtaskList(String subtask) {
        if (subtask == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<String>>() {
        }.getType();
        List<String> subtaskList = gson.fromJson(subtask, type);
        return subtaskList;
    }

    @TypeConverter
    public static Date datefromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

}