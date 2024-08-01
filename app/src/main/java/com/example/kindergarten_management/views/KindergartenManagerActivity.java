package com.example.kindergarten_management.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.example.kindergarten_management.R;
import com.example.kindergarten_management.helpers.FragmentHelper;
import com.example.kindergarten_management.views.fragments.StaffFragment;

public class KindergartenManagerActivity extends AppCompatActivity {

    private Button buttonSelectWhen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kindergarten_manager);

        buttonSelectWhen = findViewById(R.id.button_staff_registration);

        // replaceFragment(new DefaultFragment());

        buttonSelectWhen.setOnClickListener(v ->
                FragmentHelper.replaceFragment(getSupportFragmentManager(), R.id.kindergarten_manager_fragment, new StaffFragment()));
    }

}