package com.example.kindergarten_management.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;

import com.example.kindergarten_management.R;
import com.example.kindergarten_management.helpers.AuthHelper;
import com.example.kindergarten_management.helpers.FragmentHelper;
import com.example.kindergarten_management.users.AdminUser;
import com.example.kindergarten_management.views.fragments.ClassFragment;
import com.example.kindergarten_management.views.fragments.KindergartenFragment;
import com.example.kindergarten_management.views.fragments.StaffFragment;
import com.example.kindergarten_management.views.fragments.StaffMainFragment;

public class KindergartenManagerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kindergarten_manager);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button buttonStaff = findViewById(R.id.button_staff_registration);
        Button buttonClass = findViewById(R.id.button_class_registration);
        Button buttonKindergarten = findViewById(R.id.button_kindergarten_registration);

        buttonStaff.setOnClickListener(v ->
                FragmentHelper.replaceFragment(getSupportFragmentManager(), R.id.kindergarten_manager_fragment, new StaffFragment()));
        buttonClass.setOnClickListener(v ->
                FragmentHelper.replaceFragment(getSupportFragmentManager(), R.id.kindergarten_manager_fragment, new ClassFragment()));
        buttonKindergarten.setOnClickListener(v ->
                FragmentHelper.replaceFragment(getSupportFragmentManager(), R.id.kindergarten_manager_fragment, new KindergartenFragment()));
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
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        FragmentHelper.replaceFragment(getSupportFragmentManager(), R.id.main_fragment, new StaffMainFragment());
    }

}