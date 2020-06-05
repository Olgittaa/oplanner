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

import com.example.oplanner.ideas.Idea;
import com.example.oplanner.ideas.IdeasViewModel;
import com.example.oplanner.settings.AppPreferences;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IdeasActivity extends AppCompatActivity {
    @BindView(R.id.navigation)
    NavigationView navigation;

    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;

    @BindView(R.id.toolbar)
    MaterialToolbar toolbar;
    IdeasViewModel ideasViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ideas);

        ButterKnife.bind(this);

        AppPreferences appPreferences = new AppPreferences(this);
        setLocale(appPreferences.getLang());

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

                if (id == R.id.mainMenu) {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                } else if (id == R.id.diary) {
                    startActivity(new Intent(getApplicationContext(), DiaryActivity.class));
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
        if (isSmallDevice()) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.ideasFragment, new MasterFragment())
                    .commit();

            ViewModelProvider vmProvider = new ViewModelProvider(this);
            ideasViewModel = vmProvider.get(IdeasViewModel.class);
            ideasViewModel.getSelectedIdea().observe(this, this::showDetailFragment);
        }
    }

    public void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = getBaseContext().getResources().getConfiguration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
    }

    private boolean isSmallDevice() {
        return findViewById(R.id.ideasFragment) != null;
    }

    public void showDetailFragment(Idea idea) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.ideasFragment, new DetailFragment())
                .addToBackStack(null)
                .commit();
    }

    public void onFabClick(View view) {
        ideasViewModel.setSelectedIdea(new Idea());

    }
}