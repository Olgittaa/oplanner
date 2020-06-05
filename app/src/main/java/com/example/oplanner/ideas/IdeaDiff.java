package com.example.oplanner.ideas;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.example.oplanner.tasks.Task;

import java.util.Objects;

public class IdeaDiff extends DiffUtil.ItemCallback<Idea>{
    @Override
    public boolean areItemsTheSame(@NonNull Idea oldItem, @NonNull Idea newItem) {
        return Objects.equals(oldItem.getId(), newItem.getId());
    }

    @Override
    public boolean areContentsTheSame(@NonNull Idea oldItem, @NonNull Idea newItem) {
        return Objects.equals(oldItem.getTitle(), newItem.getTitle())
                && Objects.equals(oldItem.getDescription(), newItem.getDescription())
                && Objects.equals(oldItem.getTimestamp(), newItem.getTimestamp());
    }
}
