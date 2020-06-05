package com.example.oplanner.tasks;

import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.oplanner.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TaskViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.taskText)
    TextView text;

    @BindView(R.id.checkboxImageView)
    ImageView checkboxImageView;

    @BindView(R.id.subtaskImage)
    ImageView subtaskImage;

    public TaskViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setTask(final Task task, final OnTaskClickListener taskClickListener) {
        text.setText(task.getDescription());
        if (task.isDone()) {
            checkboxImageView.setImageResource(R.drawable.done);
        } else {
            checkboxImageView.setImageResource(R.drawable.todo);
        }

        if (task.getSubtasks().size()>0) {
            subtaskImage.setImageResource(R.drawable.list);
        }

        checkboxImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskClickListener.onImageViewClick(checkboxImageView, task);
            }
        });

        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskClickListener.onTaskEditClick(subtaskImage, task);
            }
        });
    }
}
