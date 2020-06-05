package com.example.oplanner.tasks;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;

import com.example.oplanner.R;

public class TaskListAdapter extends ListAdapter<Task, TaskViewHolder> {
    private OnTaskClickListener taskClickListener;

    public TaskListAdapter(OnTaskClickListener taskClickListener) {
        super(new TaskDiff());
        this.taskClickListener = taskClickListener;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @LayoutRes int layout = R.layout.tasks;
        View itemView = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new TaskViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        holder.setTask(getItem(position), taskClickListener);
    }
}