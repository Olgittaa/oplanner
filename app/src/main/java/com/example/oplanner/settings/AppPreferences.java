package com.example.oplanner.settings;

import android.content.*;

public class AppPreferences {
    private static final String APP_SHARED_PREFS = "Preferences";
    private SharedPreferences.Editor prefsEditor;

    private static final String lang = "language";
    private static final String reminderStatus = "notificationStatus";
    private static final String hour = "hour";
    private static final String min = "minute";

    private SharedPreferences preferences;

    public AppPreferences(Context context) {
        preferences = context.getSharedPreferences(APP_SHARED_PREFS, Context.MODE_PRIVATE);
        prefsEditor = preferences.edit();
    }

    public void setLang(String language) {
        prefsEditor.putString(lang, language);
        prefsEditor.commit();
    }

    public String getLang() {
        return preferences.getString(lang, "");
    }

    public boolean getReminderStatus() {
        return preferences.getBoolean(reminderStatus, false);
    }

    public void setReminderStatus(boolean status) {
        prefsEditor.putBoolean(reminderStatus, status);
        prefsEditor.commit();
    }

    public int getHour() {

        return preferences.getInt(hour, 0);
    }

    public void setHour(int h) {
        prefsEditor.putInt(hour, h);
        prefsEditor.commit();
    }

    public int getMin() {
        return preferences.getInt(min, 0);
    }

    public void setMin(int m) {
        prefsEditor.putInt(min, m);
        prefsEditor.commit();
    }

    public void reset() {
        prefsEditor.clear();
        prefsEditor.commit();
    }
}