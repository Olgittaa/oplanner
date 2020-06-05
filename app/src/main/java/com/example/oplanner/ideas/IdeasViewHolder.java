package com.example.oplanner.ideas;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.oplanner.R;
import com.google.android.material.textview.MaterialTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IdeasViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.titleText)
    MaterialTextView title;

    @BindView(R.id.deleteImage)
    ImageView deleteImage;


    public IdeasViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setIdea(final Idea idea, final OnIdeaClickListener ideaClickListener) {
        title.setText(idea.getTitle());
        deleteImage.setImageResource(R.drawable.ic_delete_black);
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ideaClickListener.onIdeaClick(idea);
            }
        });
        deleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ideaClickListener.onDeleteClick(idea);
            }
        });
    }
}
