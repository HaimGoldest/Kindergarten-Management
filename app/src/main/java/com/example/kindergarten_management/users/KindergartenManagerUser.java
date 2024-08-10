package com.example.kindergarten_management.users;

import com.example.kindergarten_management.models.ClassModel;
import com.example.kindergarten_management.models.KindergartenModel;

import java.util.ArrayList;
import java.util.List;

/**
 * User class representing the kindergarten manager user, responsible for managing kindergartens and classes.
 */
public class KindergartenManagerUser extends BaseUser {
    private List<KindergartenModel> kinderGardens;
    private List<ClassModel> classes;

    public KindergartenManagerUser(String uid, String email, String name) {
        super(uid, email, name);
        kinderGardens = new ArrayList<>();
        classes = new ArrayList<>();
    }

}
