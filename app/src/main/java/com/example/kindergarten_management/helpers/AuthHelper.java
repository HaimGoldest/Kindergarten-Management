package com.example.kindergarten_management.helpers;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.fragment.app.FragmentManager;

import com.example.kindergarten_management.R;
import com.example.kindergarten_management.users.AdminUser;
import com.example.kindergarten_management.users.BaseUser;
import com.example.kindergarten_management.users.KindergartenManagerUser;
import com.example.kindergarten_management.users.ParentUser;
import com.example.kindergarten_management.users.StaffMemberUser;
import com.example.kindergarten_management.views.KindergartenManagerActivity;
import com.example.kindergarten_management.views.MainActivity;
import com.example.kindergarten_management.views.ParentActivity;
import com.example.kindergarten_management.views.fragments.AdminFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class AuthHelper {
    public static BaseUser currentUser;

    /**
     * Method to check if there is a logged-in Firebase user.
     * If a user is found, it sets the currentUser to this user.
     */
    public static void checkLoggedInUser(Context context, View view, FragmentManager fragmentManager, OnUserFoundCallback callback) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        if (firebaseUser != null) {
            String userUid = firebaseUser.getUid();
            findUserInFirebase(view, userUid, success -> {
                if (success) {
                    callback.onUserFound(true);
                    SnackbarHelper.sendSuccessMessage(view, "User is already logged in");
                    navigateToUserHomepageAfterLogin(context, fragmentManager);
                } else {
                    callback.onUserFound(false);
                    SnackbarHelper.sendErrorMessage(view, "User data not found in Firestore");
                }
            });
        } else {
            callback.onUserFound(false);
        }
    }


    /**
     * Method to register a new user
     */
    public static void registerNewUser(Context context, View view, FragmentManager fragmentManager, BaseUser user) {
        addUserToFirebase(view, user, success -> {
            if (success) {
                currentUser = user;
                navigateToUserHomepageAfterLogin(context, fragmentManager);
            } else {
                SnackbarHelper.sendErrorMessage(view, "Registration failed. Please try again.");
            }
        });
    }

    /**
     * Method for user login
     */
    public static void userLogin(Context context, View view, FragmentManager fragmentManager, String userUid) {
        findUserInFirebase(view, userUid, success -> {
            if (success) {
                SnackbarHelper.sendSuccessMessage(view, "Login successful");
                navigateToUserHomepageAfterLogin(context, fragmentManager);
            } else {
                SnackbarHelper.sendErrorMessage(view, "User not found!");
            }
        });
    }

    /**
     * Method to sign out the current Firebase user.
     * It also clears the currentUser variable.
     */
    public static void signOutUser(Context context) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signOut();
        currentUser = null;

        if (!(context instanceof MainActivity)) {
            Intent intent = new Intent(context, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(intent);
        }
    }


    /**
     * Method to add a user to Firebase
     */
    private static void addUserToFirebase(View view, BaseUser user, OnUserAddCallback callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference usersCollection = db.collection("users");

        usersCollection.document(user.getUid()).set(user)
                .addOnSuccessListener(documentReference -> {
                    SnackbarHelper.sendSuccessMessage(view, "User added successfully");
                    callback.onUserAdd(true);
                })
                .addOnFailureListener(e -> {
                    String errorMsg = "Failed to add user: " + e.getMessage();
                    SnackbarHelper.sendErrorMessage(view, errorMsg);
                    callback.onUserAdd(false);
                });
    }

    /**
     * Method to find a user in Firebase by UID
     */
    private static void findUserInFirebase(View view, String userUid, OnUserFoundCallback callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference usersCollection = db.collection("users");

        usersCollection.document(userUid).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        currentUser = buildUser(documentSnapshot);
                        callback.onUserFound(true);
                    } else {
                        SnackbarHelper.sendErrorMessage(view, "User not found");
                        callback.onUserFound(false);
                    }
                })
                .addOnFailureListener(e -> {
                    String errorMsg = "Failed to find user: " + e.getMessage();
                    SnackbarHelper.sendErrorMessage(view, errorMsg);
                    callback.onUserFound(false);
                });
    }

    private static BaseUser buildUser(DocumentSnapshot documentSnapshot) {
        String uid = documentSnapshot.getString("uid");
        String email = documentSnapshot.getString("email");
        String name = documentSnapshot.getString("name");
        String rule = documentSnapshot.getString("rule");

        if (rule == null)
            return null;

        switch (rule) {
            case "AdminUser":
                return new AdminUser(uid,email,name);
            case "KindergartenManagerUser":
                return new KindergartenManagerUser(uid,email,name);
            case "ParentUser":
                return new ParentUser(uid,email,name);
            case "StaffMemberUser":
                return new StaffMemberUser(uid,email,name);
            default:
                return null;
        }
    }

    public static BaseUser buildUser(String uid, String email, String name, String rule) {
        switch (rule) {
            case "Admin":
                return new AdminUser(uid,email,name);
            case "Kindergarten Manager":
                return new KindergartenManagerUser(uid,email,name);
            case "Parent":
                return new ParentUser(uid,email,name);
            case "Staff Member":
                return new StaffMemberUser(uid,email,name);
            default:
                return null;
        }
    }

    /**
     * Method to navigate to the user's homepage after login
     */
    private static void navigateToUserHomepageAfterLogin(Context context, FragmentManager fragmentManager) {
        String rule = currentUser.getRule();
        Intent intent;

        switch (rule) {
            case "AdminUser":
                FragmentHelper.replaceFragment(fragmentManager, R.id.main_fragment, new AdminFragment());
                break;
            case "KindergartenManagerUser":
                intent = new Intent(context, KindergartenManagerActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                context.startActivity(intent);
                break;
            case "ParentUser":
                intent = new Intent(context, ParentActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                context.startActivity(intent);
                break;
            case "StaffMemberUser":
                break;
        }
    }

    /**
     * Callback interface for user addition
     */
    interface OnUserAddCallback {
        void onUserAdd(boolean success);
    }

    /**
     * Callback interface for user lookup
     */
    public interface OnUserFoundCallback {
        void onUserFound(boolean success);
    }
}
