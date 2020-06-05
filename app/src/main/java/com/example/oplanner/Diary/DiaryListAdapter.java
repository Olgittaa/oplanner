package com.example.oplanner.Diary;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;

import com.example.oplanner.R;
import com.example.oplanner.tasks.OnTaskClickListener;
import com.example.oplanner.tasks.Task;

public class DiaryListAdapter extends ListAdapter<Day, DiaryViewHolder> {
    private OnDayClickListener dayClickListener;

    public DiaryListAdapter(OnDayClickListener dayClickListener) {
        super(new DayDiff());
        this.dayClickListener = dayClickListener;
    }

    @NonNull
    @Override
    public DiaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @LayoutRes int layout  = R.layout.days;
        View itemView = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new DiaryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DiaryViewHolder holder, int position) {
        holder.setDay(getItem(position), dayClickListener);
    }
}