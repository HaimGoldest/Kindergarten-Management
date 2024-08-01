package com.example.kindergarten_management.helpers;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Utility class for managing shared preferences in the Time Travel Tours application.
 * This class follows the Singleton pattern to ensure a single instance is used throughout the app.
 */
public class SharedPreferencesHelper {
    private static SharedPreferencesHelper instance = null;
    private static final String PREFS_NAME = "TimeTravelPrefs";
    private static Context context;


    private SharedPreferencesHelper(Context newContext) {
        context = newContext;
    }

    /**
     * Retrieves the singleton instance of SharedPreferencesHelper.
     *
     * @param context The application context.
     * @return The singleton instance of SharedPreferencesHelper.
     */
    public static SharedPreferencesHelper getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPreferencesHelper(context);
        }

        return instance;
    }

    /**
     * Saves a key-value pair to the shared preferences.
     *
     * @param key The key for the preference.
     * @param value The value to be saved.
     */
    public void SavePreference(String key, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    /**
     * Retrieves a value from the shared preferences.
     *
     * @param key The key of the preference to retrieve.
     * @return The value associated with the key, or null if not found.
     */
    public String getPreference(String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, null);
    }
}
