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
import com.example.kindergarten_management.views.fragments.FindKindergartenFragment;
import com.example.kindergarten_management.views.fragments.ImagesFragment;
import com.example.kindergarten_management.views.fragments.KindergartenFragment;
import com.example.kindergarten_management.views.fragments.StaffMainFragment;

public class ParentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button buttonShowKindergartens = findViewById(R.id.button_show_kindergartens);
        Button buttonShowClasses = findViewById(R.id.button_show_classes);
        Button buttonChildRegistration = findViewById(R.id.button_child_registration);
        Button buttonShowImages = findViewById(R.id.button_show_images);

        buttonShowKindergartens.setOnClickListener(v ->
                FragmentHelper.replaceFragment(getSupportFragmentManager(), R.id.parent_fragment_container, new KindergartenFragment()));

        buttonShowClasses.setOnClickListener(v ->
                FragmentHelper.replaceFragment(getSupportFragmentManager(), R.id.parent_fragment_container, new ClassFragment()));

        buttonChildRegistration.setOnClickListener(v ->
                FragmentHelper.replaceFragment(getSupportFragmentManager(), R.id.parent_fragment_container, new FindKindergartenFragment()));

        buttonShowImages.setOnClickListener(v ->
                FragmentHelper.replaceFragment(getSupportFragmentManager(), R.id.parent_fragment_container, new ImagesFragment()));
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
