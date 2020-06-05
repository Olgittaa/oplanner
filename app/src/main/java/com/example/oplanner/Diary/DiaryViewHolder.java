package com.example.oplanner.Diary;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.oplanner.R;
import com.example.oplanner.tasks.OnTaskClickListener;
import com.example.oplanner.tasks.Task;

import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DiaryViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.dateText)
    TextView dateText;

    @BindView(R.id.descriptionText)
    TextView descriptionText;

    @BindView(R.id.emogiImage)
    ImageView emogiImage;

    public DiaryViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setDay(final Day day, final OnDayClickListener dayClickListener) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy");
        dateText.setText(formatter.format(day.getDate()));
        if (day.getDescription().length() > 18) {
            descriptionText.setText(day.getDescription().substring(0, 18));
        } else {
        descriptionText.setText(day.getDescription());
        }
        emogiImage.setImageResource(day.getImage());

        dateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dayClickListener.onDayClick(day);
            }
        });
        dateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dayClickListener.onDayClick(day);
            }
        });
        emogiImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dayClickListener.onDayClick(day);
            }
        });
    }
}
