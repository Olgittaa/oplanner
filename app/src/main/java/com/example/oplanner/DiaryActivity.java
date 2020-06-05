package com.example.oplanner;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.oplanner.Diary.AddDayDialog;
import com.example.oplanner.Diary.Day;
import com.example.oplanner.Diary.DiaryListAdapter;
import com.example.oplanner.Diary.DiaryViewModel;
import com.example.oplanner.Diary.OnDayClickListener;
import com.example.oplanner.settings.AppPreferences;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DiaryActivity extends AppCompatActivity implements OnDayClickListener {
    DiaryListAdapter diaryListAdapter;
    DiaryViewModel diaryViewModel;
    @BindView(R.id.diaryRecyclerView)
    RecyclerView diaryRecyclerView;

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
        setContentView(R.layout.activity_diary);
        ButterKnife.bind(this);
        AppPreferences appPreferences = new AppPreferences(this);
        setLocale(appPreferences.getLang());
        diaryRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        diaryListAdapter = new DiaryListAdapter(this);
        diaryRecyclerView.setAdapter(diaryListAdapter);

        ViewModelProvider.AndroidViewModelFactory viewModelFactory = new ViewModelProvider.AndroidViewModelFactory(getApplication());
        ViewModelProvider viewModelProvider = new ViewModelProvider(this, viewModelFactory);
        diaryViewModel = viewModelProvider.get(DiaryViewModel.class);
        diaryViewModel.getDays().observe(this, days -> diaryListAdapter.submitList(days));

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
                } else if (id == R.id.mainMenu) {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
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


    @OnClick(R.id.fab)
    public void onAddTask(View view) {
        new AddDayDialog(this) {
            @Override
            protected void setData() {
            }

            @Override
            protected void onPositiveButtonClick() {
                if (!this.description.getText().toString().trim().isEmpty()) {
                    Day day = new Day();
                    day.setDescription(this.description.getText().toString());
                    day.setDate(new Date());

                    day.setImage(getImageByButton(toggleGroup.getCheckedButtonId()));
                    diaryViewModel.addDay(day);
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
    public void onDayClick(Day day) {
        new AddDayDialog(this) {
            @Override
            protected void setData() {
                this.description.setText(day.getDescription());
                checkButtonByImage(day.getImage());
                this.negativebtn.setText(R.string.delete);
            }

            @Override
            protected void onPositiveButtonClick() {
                if (!this.description.getText().toString().trim().isEmpty()) {
                    day.setDescription(this.description.getText().toString());
                    day.setImage(getImageByButton(toggleGroup.getCheckedButtonId()));
                    diaryViewModel.refreshDay(day);
                    this.dismiss();
                }
            }

            @Override
            protected void onNegativeButtonClick() {
                diaryViewModel.deleteDay(day);
                this.dismiss();
            }
        };
    }
}