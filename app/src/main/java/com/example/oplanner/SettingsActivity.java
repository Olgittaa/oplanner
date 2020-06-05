package com.example.oplanner;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.ClipboardManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.oplanner.settings.AlarmReceiver;
import com.example.oplanner.settings.AppPreferences;
import com.example.oplanner.settings.NotificationScheduler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingsActivity extends Activity {
    @BindView(R.id.spinner)
    Spinner spinner;

    @BindView(R.id.reminderSwitch)
    Switch reminderSwitch;

    @BindView(R.id.time)
    TextView time;

    @BindView(R.id.timeLayout)
    LinearLayout timeLayout;

    @BindView(R.id.timeImage)
    ImageView timeImage;

    int hour;
    int minute;

    private AppPreferences appPreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        ButterKnife.bind(this);

        appPreferences = new AppPreferences(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.languages_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {

                if (pos == 1) {
                    appPreferences.setLang("en");
                    setLocale("en");
                    startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                } else if (pos == 2) {
                    appPreferences.setLang("ru");
                    setLocale("ru");
                    startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                } else if (pos == 3) {
                    appPreferences.setLang("sk");
                    setLocale("sk");
                    startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                }
            }


            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        hour = appPreferences.getHour();
        minute = appPreferences.getMin();

        time.setText(getFormatedTime(hour, minute));
        reminderSwitch.setChecked(appPreferences.getReminderStatus());

        if (!appPreferences.getReminderStatus())
            timeLayout.setAlpha(0.4f);

        reminderSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                appPreferences.setReminderStatus(isChecked);
                if (isChecked) {
                    NotificationScheduler.setReminder(SettingsActivity.this, AlarmReceiver.class, appPreferences.getHour(), appPreferences.getMin());
                    timeLayout.setAlpha(1f);
                } else {
                    NotificationScheduler.cancelReminder(SettingsActivity.this, AlarmReceiver.class);
                    timeLayout.setAlpha(0.4f);
                }
            }
        });
        timeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(hour, minute);
            }
        });

        Toast.makeText(this,appPreferences.getHour()+" "+appPreferences.getMin(), Toast.LENGTH_SHORT).show();
    }


    private void showTimePickerDialog(int h, int m) {

        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.timepicker_header, null);

        TimePickerDialog builder = new TimePickerDialog(this, R.style.Theme_AppCompat_Dialog,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int min) {
                        appPreferences.setHour(hour);
                        appPreferences.setMin(min);
                        time.setText(getFormatedTime(hour, min));
                        NotificationScheduler.setReminder(SettingsActivity.this, AlarmReceiver.class, appPreferences.getHour(), appPreferences.getMin());
                    }
                }, h, m, false);

        builder.setCustomTitle(view);
        builder.show();
    }

    public String getFormatedTime(int h, int m) {

        String dateString = h + ":" + m;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            Date d = sdf.parse(dateString);
            dateString = sdf.format(d);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateString;
    }

    public void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = getBaseContext().getResources().getConfiguration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
    }

}
