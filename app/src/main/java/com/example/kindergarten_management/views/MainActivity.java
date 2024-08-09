package com.example.kindergarten_management.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.kindergarten_management.R;
import com.example.kindergarten_management.helpers.FragmentHelper;
import com.example.kindergarten_management.views.fragments.SignUpFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        temp();

        FragmentHelper.replaceFragment(getSupportFragmentManager(), R.id.main_fragment, new SignUpFragment());
    }

    private void temp(){
        Button aButton = findViewById(R.id.a_button);

        aButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(MainActivity.this, KindergartenManagerActivity.class);
                Intent intent = new Intent(MainActivity.this, ParentActivity.class);
                startActivity(intent);
            }
        });


    }
}