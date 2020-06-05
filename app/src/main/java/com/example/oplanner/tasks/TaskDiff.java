package com.example.oplanner.tasks;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import java.util.Objects;

public class TaskDiff extends DiffUtil.ItemCallback<Task> {

    @Override
    public boolean areItemsTheSame(@NonNull Task oldItem, @NonNull Task newItem) {
        return Objects.equals(oldItem.getId(), newItem.getId());
    }

    @Override
    public boolean areContentsTheSame(@NonNull Task oldItem, @NonNull Task newItem) {
        return Objects.equals(oldItem.getDescription(), newItem.getDescription())
                && Objects.equals(oldItem.getDate(), newItem.getDate())
                && Objects.equals(oldItem.isDone(), newItem.isDone());
    }
}