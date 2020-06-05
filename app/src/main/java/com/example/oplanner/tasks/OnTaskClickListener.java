package com.example.oplanner.tasks;

import android.widget.ImageView;

public interface OnTaskClickListener{
    void onImageViewClick(ImageView checkboxImageView, Task task);

    void onTaskEditClick(ImageView subtaskImage, Task task);
}