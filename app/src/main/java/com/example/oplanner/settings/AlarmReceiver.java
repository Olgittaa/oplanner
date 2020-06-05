package com.example.oplanner.settings;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.oplanner.MainActivity;
import com.example.oplanner.SettingsActivity;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction() != null && context != null) {
            if (intent.getAction().equalsIgnoreCase(Intent.ACTION_BOOT_COMPLETED)) {
                AppPreferences appPreferences = new AppPreferences(context);
                NotificationScheduler.setReminder(context, AlarmReceiver.class, appPreferences.getHour(), appPreferences.getMin());
                return;
            }
        }
        NotificationScheduler.showNotification(context, SettingsActivity.class,
                "You have 5 unwatched videos", "Watch them now?");
    }
}


