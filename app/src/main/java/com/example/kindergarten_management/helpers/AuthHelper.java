package com.example.kindergarten_management.helpers;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.kindergarten_management.models.UserModel;
import com.example.kindergarten_management.views.KindergartenManagerActivity;
import com.google.firebase.auth.FirebaseUser;

public class AuthHelper {
    public static UserModel currentUser;

    public static void userLogin(Context context, View view, FirebaseUser firebaseUser) {
        currentUser = new UserModel(firebaseUser.getUid(), firebaseUser.getEmail());
        SnackbarHelper.sendSuccessMessage(view, "Login successful");
        navigateToUserHomepageAfterLogin(context);
    }

    public static void navigateToUserHomepageAfterLogin(Context context) {
        Intent intent = new Intent(context, KindergartenManagerActivity.class);
        context.startActivity(intent);
    }
}
