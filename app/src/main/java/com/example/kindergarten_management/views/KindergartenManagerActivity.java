package com.example.kindergarten_management.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

}