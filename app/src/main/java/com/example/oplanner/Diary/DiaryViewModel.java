package com.example.oplanner.Diary;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.oplanner.ideas.Idea;
import com.example.oplanner.tasks.DataConverter;
import com.example.oplanner.tasks.Task;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DiaryViewModel extends AndroidViewModel {
    private LiveData<List<Day>> days;
    private DiaryDao diaryDao;
    private DiaryDatabase db;

    public DiaryViewModel(@NonNull Application application) {
        super(application);
        db = DiaryDatabase.getDb(application);
        diaryDao = db.diaryDao();
        days = diaryDao.getAllDays();
    }

    public LiveData<List<Day>> getDays() {
        return days;
    }

    public void addDay(Day day) {
        db.getTransactionExecutor().execute(() -> diaryDao.save(day));
    }

    public void refreshDay(Day day) {
        db.getTransactionExecutor().execute(() -> diaryDao.refreshDay(day));
    }

    public void deleteDay(Day day) {
        db.getTransactionExecutor().execute(() -> diaryDao.deleteDay(day));
    }
}