package com.example.oplanner;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.oplanner.ideas.Idea;
import com.example.oplanner.ideas.IdeaListAdapter;
import com.example.oplanner.ideas.IdeasViewHolder;
import com.example.oplanner.ideas.IdeasViewModel;
import com.example.oplanner.ideas.OnIdeaClickListener;
import com.example.oplanner.tasks.TaskListAdapter;
import com.example.oplanner.tasks.TaskViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MasterFragment extends Fragment implements OnIdeaClickListener {

    RecyclerView ideasRecyclerView;
    FloatingActionButton fab;

    private IdeaListAdapter ideaListAdapter;
    private IdeasViewModel ideaViewModel;

    public MasterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_master, container, false);
        ideasRecyclerView = rootView.findViewById(R.id.ideasRecyclerView);
        fab = rootView.findViewById(R.id.fab);
        ideasRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        ideaListAdapter = new IdeaListAdapter(this);
        ideasRecyclerView.setAdapter(ideaListAdapter);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ideaViewModel = new ViewModelProvider(requireActivity()).get(IdeasViewModel.class);
        ideaViewModel.getIdeas().observe(getViewLifecycleOwner(), ideas -> ideaListAdapter.submitList(ideas));
    }

    @Override
    public void onIdeaClick(Idea idea) {
        ideaViewModel.setSelectedIdea(idea);
    }

    @Override
    public void onDeleteClick(Idea idea) {
        ideaViewModel.deleteIdea(idea);
    }
}