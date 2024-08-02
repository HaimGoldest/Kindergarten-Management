package com.example.kindergarten_management.models;

import android.content.Context;

import com.example.kindergarten_management.helpers.SharedPreferencesHelper;

/**
 * Base model class that provides common properties and methods for all models.
 * Handles ID management for all derived classes.
 */
public abstract class BaseModel {
    private static Context context;
    private static String keyForLastId;
    private static int idCount;
    private int id;

    /**
     * Constructor that initializes the ID from shared preferences.
     * @param context The context used to access shared preferences.
     */
    public BaseModel(Context context) {
        this.context = context;
        keyForLastId = getCurrentSimpleClassName();
        if (idCount == 0) {
            String lastId = SharedPreferencesHelper.getInstance(context).getPreference(keyForLastId);
            if (lastId != null) {
                idCount = Integer.parseInt(lastId);
            }
        }

        idCount++;
        id = idCount;

        SharedPreferencesHelper.getInstance(context).SavePreference(keyForLastId, String.valueOf(id));
    }

    /**
     * Constructor that sets the ID to a given value.
     * @param id The ID to set for the model.
     */
    public BaseModel(int id) {
        this.id = id;
    }

    /**
     * Gets the ID of the model.
     * @return The ID of the model.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the model.
     * @param id The new ID for the model.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the Context of the model.
     * @return The Context of the model.
     */
    public static Context getContext() {
        return context;
    }

    /**
     * Gets the simple name of the current class.
     * @return The simple name of the current class.
     */
    private String getCurrentSimpleClassName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BaseModel baseModel = (BaseModel) o;

        return id == baseModel.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}