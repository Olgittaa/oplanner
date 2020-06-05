package com.example.oplanner.ideas;

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

public class IdeaListAdapter extends ListAdapter<Idea, IdeasViewHolder> {
    private OnIdeaClickListener ideaClickListener;

    public IdeaListAdapter(OnIdeaClickListener ideaClickListener) {
        super(new IdeaDiff());
        this.ideaClickListener = ideaClickListener;
    }

    @NonNull
    @Override
    public IdeasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @LayoutRes int layout = R.layout.ideas_listview;
        View itemView = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new IdeasViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull IdeasViewHolder holder, int position) {
        Idea idea = getItem(position);
        holder.setIdea(idea, ideaClickListener);
    }
}