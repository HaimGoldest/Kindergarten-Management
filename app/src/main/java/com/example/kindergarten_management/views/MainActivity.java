package com.example.kindergarten_management.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.kindergarten_management.R;
import com.example.kindergarten_management.helpers.AuthHelper;
import com.example.kindergarten_management.helpers.FragmentHelper;
import com.example.kindergarten_management.users.AdminUser;
import com.example.kindergarten_management.views.fragments.SignUpFragment;
import com.example.kindergarten_management.views.fragments.StaffMainFragment;
import com.google.firebase.FirebaseApp;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        AuthHelper.checkLoggedInUser(this, findViewById(android.R.id.content), getSupportFragmentManager(), success -> {
            if (!success) {
                FragmentHelper.replaceFragment(getSupportFragmentManager(), R.id.main_fragment, new SignUpFragment());
            }
            ProgressBar progressBar = findViewById(R.id.main_progress_bar);
            progressBar.setVisibility(View.GONE);
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem managerButton = menu.findItem(R.id.menu_manager_page);
        MenuItem parentButton = menu.findItem(R.id.menu_parent_page);
        MenuItem staffButton = menu.findItem(R.id.menu_staff_page);

        boolean haveFullPermissions = AuthHelper.currentUser instanceof AdminUser;
        managerButton.setVisible(haveFullPermissions);
        parentButton.setVisible(haveFullPermissions);
        staffButton.setVisible(haveFullPermissions);

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.menu_sign_out) {
            handleSignOutClick();
            return true;
        } else if (itemId == R.id.menu_manager_page) {
            handleManagerPageClick();
            return true;
        } else if (itemId == R.id.menu_parent_page) {
            handleParentPageClick();
            return true;
        } else if (itemId == R.id.menu_staff_page) {
            handleStaffPageClick();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void handleSignOutClick() {
        AuthHelper.signOutUser(getApplicationContext());
    }

    private void handleManagerPageClick() {
        Intent intent = new Intent(this, KindergartenManagerActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void handleParentPageClick() {
        Intent intent = new Intent(this, ParentActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void handleStaffPageClick() {
        FragmentHelper.replaceFragment(getSupportFragmentManager(), R.id.main_fragment, new StaffMainFragment());
    }
}
