package com.example.oplanner.Diary;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import java.util.Objects;

public class DayDiff extends DiffUtil.ItemCallback<Day> {

    @Override
    public boolean areItemsTheSame(@NonNull Day oldItem, @NonNull Day newItem) {
        return Objects.equals(oldItem.getId(), newItem.getId());
    }

    @Override
    public boolean areContentsTheSame(@NonNull Day oldItem, @NonNull Day newItem) {
        return Objects.equals(oldItem.getDescription(), newItem.getDescription())
                && Objects.equals(oldItem.getDate(), newItem.getDate())
                && Objects.equals(oldItem.getImage(), newItem.getImage());
    }
}