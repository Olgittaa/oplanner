package com.example.oplanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.oplanner.settings.AppPreferences;
import com.example.oplanner.tasks.AddTaskDialog;
import com.example.oplanner.tasks.OnTaskClickListener;
import com.example.oplanner.tasks.Task;
import com.example.oplanner.tasks.TaskListAdapter;
import com.example.oplanner.tasks.TaskViewModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements OnTaskClickListener {

    TaskListAdapter taskListAdapter;
    TaskViewModel taskViewModel;

    @BindView(R.id.toggleGroup)
    MaterialButtonToggleGroup toggleGroup;

    @BindView(R.id.text)
    TextView text;

    @BindView(R.id.tasksRecyclerView)
    RecyclerView tasksRecyclerView;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @BindView(R.id.navigation)
    NavigationView navigation;

    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;

    @BindView(R.id.toolbar)
    MaterialToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        AppPreferences appPreferences = new AppPreferences(this);
        setLocale(appPreferences.getLang());
        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        taskListAdapter = new TaskListAdapter(this);
        tasksRecyclerView.setAdapter(taskListAdapter);

        ViewModelProvider.AndroidViewModelFactory viewModelFactory = new ViewModelProvider.AndroidViewModelFactory(getApplication());
        ViewModelProvider viewModelProvider = new ViewModelProvider(this, viewModelFactory);
        taskViewModel = viewModelProvider.get(TaskViewModel.class);
        taskViewModel.getTasksForToday().observe(this, tasks -> taskListAdapter.submitList(tasks));

        text.setText(R.string.what_to_do);
        toggleGroup.check(R.id.todaybtn);
        toggleGroup.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                onToggleButtonClick(checkedId, isChecked);
            }
        });
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                toolbar, 0, 0);
        drawerToggle.syncState();
        toolbar.setNavigationIcon(R.drawable.ic_menu);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                return false;
            }
        });

        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (item.isChecked()) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                    return false;
                }

                if (id == R.id.ideas) {
                    startActivity(new Intent(getApplicationContext(), IdeasActivity.class));
                } else if (id == R.id.diary) {
                    startActivity(new Intent(getApplicationContext(), DiaryActivity.class));
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    public void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = getBaseContext().getResources().getConfiguration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
    }


    private void onToggleButtonClick(int checkedId, boolean isChecked) {
        if (isChecked) {
            if (checkedId == R.id.todaybtn) {
                text.setText(R.string.what_to_do);
                taskViewModel.getTasksForToday().observe(this, tasks -> taskListAdapter.submitList(tasks));

            } else if (checkedId == R.id.tomorrowbtn) {
                text.setText(R.string.tomorrow);
                taskViewModel.getTasksForTomorrow().observe(this, tasks -> taskListAdapter.submitList(tasks));

            } else if (checkedId == R.id.laterbtn) {
                text.setText(R.string.later);
                taskViewModel.getTasksLater().observe(this, tasks -> taskListAdapter.submitList(tasks));
            }
        }
    }

    @OnClick(R.id.fab)
    public void onAddTask(View view) {
        new AddTaskDialog(this) {

            @Override
            protected void setData() {
                listSubtask = new ArrayList<>();
            }

            @Override
            protected void onPositiveButtonClick() {
                if (!this.editText.getText().toString().trim().isEmpty() && this.dateChanged) {
                    Task task = new Task();
                    task.setDescription(this.editText.getText().toString());
                    Date date = this.setDate(this.datebtn.getText().toString());
                    task.setDate(date);
                    task.setSubtasks(this.listSubtask);
                    taskViewModel.addTask(task);
                    this.dismiss();
                }
            }

            @Override
            protected void onNegativeButtonClick() {
                this.dismiss();
            }
        };
    }

    @Override
    public void onImageViewClick(ImageView checkboxImageView, Task task) {
        if (task.isDone()) {
            task.setDone(false);
            checkboxImageView.setImageResource(R.drawable.todo);
        } else if (!task.isDone()) {
            task.setDone(true);
            checkboxImageView.setImageResource(R.drawable.done);
        }
        taskViewModel.refreshTask(task);
    }

    @Override
    public void onTaskEditClick(ImageView subtaskImage, Task task) {
        new AddTaskDialog(this) {

            @Override
            protected void setData() {
                this.editText.setText(task.getDescription());
                this.datebtn.setText(this.getDate(task.getDate()));
                this.listSubtask = task.getSubtasks();
                this.negativebtn.setText(R.string.delete);
            }

            @Override
            protected void onPositiveButtonClick() {
                if (!this.editText.getText().toString().trim().isEmpty()) {
                    task.setDescription(this.editText.getText().toString());
                    task.setSubtasks(this.listSubtask);
                    taskViewModel.refreshTask(task);

                    if (task.getSubtasks().size() != 0) {
                        subtaskImage.setImageResource(R.drawable.list);
                    }
                    this.dismiss();
                }
            }

            @Override
            protected void onNegativeButtonClick() {
                taskViewModel.deleteTask(task);
                this.dismiss();
            }
        };
    }

    @Override
    public void onStop() {
        super.onStop();
        finish();
    }
}