package com.example.oplanner.ideas;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.oplanner.tasks.Task;

import java.util.List;

public class IdeasViewModel extends AndroidViewModel {
    private LiveData<List<Idea>> ideas;
    private IdeasDao ideasDao;
    private IdeaDatabase db;
    private MutableLiveData<Idea> selectedIdea = new
            MutableLiveData<>();

    public IdeasViewModel(@NonNull Application application) {
        super(application);
        db = IdeaDatabase.getDb(application);
        ideasDao = db.ideasDao();
        ideas = ideasDao.getAllIdeas();
    }

    public LiveData<List<Idea>> getIdeas() {
        return ideas;
    }

    public LiveData<Idea> getSelectedIdea() {
        return selectedIdea;
    }

    public void setSelectedIdea(Idea selectedIdea) {
        this.selectedIdea.postValue(selectedIdea);
    }

    public void addIdea(Idea idea) {
        db.getTransactionExecutor().execute(() -> ideasDao.save(idea));
    }

    public void deleteIdea(Idea idea) {
        db.getTransactionExecutor().execute(() -> ideasDao.deleteIdea(idea));
    }

    public void refreshIdea(Idea idea) {
        db.getTransactionExecutor().execute(() -> ideasDao.refreshIdea(idea));
    }
}