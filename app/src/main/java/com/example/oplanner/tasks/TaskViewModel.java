package com.example.oplanner.tasks;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TaskViewModel extends AndroidViewModel {
    private TaskDao taskDao;
    private TasksDatabase db;

    public TaskViewModel(@NonNull Application application) {
        super(application);
        db = TasksDatabase.getDb(application);
        taskDao = db.taskDao();
    }

    public LiveData<List<Task>> getTasksForToday() {
        Date dayStart = getStartOfDay(new Date());
        Long daystartStmp = DataConverter.dateToTimestamp(dayStart);
        Date dayEnd = getEndOfDay(new Date());
        Long dayendStmp = DataConverter.dateToTimestamp(dayEnd);
        return taskDao.listFor1Day(daystartStmp, dayendStmp);
    }

    public LiveData<List<Task>> getTasksForTomorrow() {

        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, 1);
        date = c.getTime();

        Date dayStart = getStartOfDay(date);
        Long daystartStmp = DataConverter.dateToTimestamp(dayStart);
        Date dayEnd = getEndOfDay(date);
        Long dayendStmp = DataConverter.dateToTimestamp(dayEnd);
        return taskDao.listFor1Day(daystartStmp, dayendStmp);
    }

    public LiveData<List<Task>> getTasksLater() {
        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, 2);
        date = c.getTime();

        Date dayStart = getStartOfDay(date);
        Long daystartStmp = DataConverter.dateToTimestamp(dayStart);

        return taskDao.listLater(daystartStmp);
    }

    public void addTask(Task task) {
        db.getTransactionExecutor().execute(() -> taskDao.save(task));
    }

    public void refreshTask(Task task) {
        db.getTransactionExecutor().execute(() -> taskDao.refreshTask(task));
    }

    public void deleteTask(Task task) {
        db.getTransactionExecutor().execute(() -> taskDao.deleteTask(task));
    }


    private Date getStartOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DATE);
        calendar.setTimeInMillis(0);
        calendar.set(year, month, day, 0, 0, 0);
        return calendar.getTime();
    }

    private Date getEndOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DATE);
        calendar.setTimeInMillis(0);
        calendar.set(year, month, day, 23, 59, 59);
        return calendar.getTime();
    }
}