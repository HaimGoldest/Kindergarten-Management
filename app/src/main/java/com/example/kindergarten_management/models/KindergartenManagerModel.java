package com.example.kindergarten_management.models;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Model class representing the kindergarten manager, responsible for managing kindergartens and classes.
 */
public class KindergartenManagerModel extends BaseModel {
    private List<KindergartenModel> kinderGardens;
    private List<ClassModel> classes;

    /**
     * Constructor that initializes the kindergarten manager model with a context.
     * @param context The context used for initialization.
     */
    public KindergartenManagerModel(Context context) {
        super(context);
        initData();
    }

    /**
     * Constructor that initializes the kindergarten manager model with a given ID.
     * @param id The ID to set for the kindergarten manager model.
     */
    public KindergartenManagerModel(int id) {
        super(id);
        initData();
    }

    /**
     * Initializes the data for kindergartens and classes.
     */
    private void initData(){
        kinderGardens = new ArrayList<>();
        classes = new ArrayList<>();
    }
}
