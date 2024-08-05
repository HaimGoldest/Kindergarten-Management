package com.example.kindergarten_management.users;

import com.example.kindergarten_management.models.ChildModel;

import java.util.List;

public class ParentUser extends BaseUser {
    private List<ChildModel> childes;

    public ParentUser(String uid, String email) {
        super(uid, email);
    }
}
