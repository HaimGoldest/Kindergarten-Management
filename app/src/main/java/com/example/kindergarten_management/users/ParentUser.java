package com.example.kindergarten_management.users;

import com.example.kindergarten_management.models.ChildModel;

import java.util.ArrayList;
import java.util.List;

public class ParentUser extends BaseUser {
    private List<Integer> childes;

    public ParentUser(String uid, String email, String name) {
        super(uid, email, name);
        childes = new ArrayList<>();
    }
}
