package com.example.oplanner;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oplanner.ideas.Idea;
import com.example.oplanner.ideas.IdeaListAdapter;
import com.example.oplanner.ideas.IdeasViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.sql.Timestamp;
import java.util.Date;

import butterknife.BindView;

public class DetailFragment extends Fragment {
    TextInputEditText title;
    TextInputEditText description;
    FloatingActionButton fabAdd;

    private IdeasViewModel ideaViewModel;

    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        fabAdd = rootView.findViewById(R.id.fabAdd);
        title = rootView.findViewById(R.id.title);
        description = rootView.findViewById(R.id.description);

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!title.getText().toString().trim().isEmpty() && !description.getText().toString().trim().isEmpty()) {
                    Idea idea;
                    if (ideaViewModel.getSelectedIdea().getValue().getTitle() == "") {
                        idea = new Idea();
                        idea.setTitle(title.getText().toString());
                        idea.setDescription(description.getText().toString());
                        ideaViewModel.addIdea(idea);
                    } else {
                        idea = ideaViewModel.getSelectedIdea().getValue();
                        idea.setTitle(title.getText().toString());
                        idea.setDescription(description.getText().toString());
                        idea.setTimestamp(new Date().getTime());
                        ideaViewModel.refreshIdea(idea);
                    }
                }
            }
        });
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ViewModelProvider vmProvider = new ViewModelProvider(requireActivity());
        ideaViewModel = vmProvider.get(IdeasViewModel.class);
        ideaViewModel.getSelectedIdea().observe(getViewLifecycleOwner(), this::setIdea);
    }

    private void setIdea(Idea idea) {
        title.setText(idea.getTitle());
        description.setText(idea.getDescription());
    }
}
