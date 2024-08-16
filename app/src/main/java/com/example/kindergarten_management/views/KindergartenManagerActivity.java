package com.example.kindergarten_management.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import com.example.kindergarten_management.R;
import com.example.kindergarten_management.helpers.FragmentHelper;
import com.example.kindergarten_management.views.fragments.ClassFragment;
import com.example.kindergarten_management.views.fragments.KindergartenFragment;
import com.example.kindergarten_management.views.fragments.StaffFragment;

public class KindergartenManagerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kindergarten_manager);

        // Set up the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialize buttons and set click listeners
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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            // Handle the settings action
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
