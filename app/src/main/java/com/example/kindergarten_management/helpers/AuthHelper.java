package com.example.kindergarten_management.helpers;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.kindergarten_management.users.BaseUser;
import com.example.kindergarten_management.users.KindergartenManagerUser;
import com.example.kindergarten_management.views.KindergartenManagerActivity;
import com.google.firebase.auth.FirebaseUser;

public class AuthHelper {
    public static BaseUser currentUser;

    public static void userLogin(Context context, View view, FirebaseUser firebaseUser) {
        // todo change KindergartenManagerUser to fit to any user
        currentUser = new KindergartenManagerUser(firebaseUser.getUid(), firebaseUser.getEmail());
        SnackbarHelper.sendSuccessMessage(view, "Login successful");
        navigateToUserHomepageAfterLogin(context);
    }

    public static void navigateToUserHomepageAfterLogin(Context context) {
        Intent intent = new Intent(context, KindergartenManagerActivity.class);
        context.startActivity(intent);
    }
}
