package com.example.kindergarten_management.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.kindergarten_management.R;
import com.example.kindergarten_management.helpers.AuthHelper;
import com.example.kindergarten_management.helpers.FragmentHelper;
import com.example.kindergarten_management.users.KindergartenManagerUser;
import com.example.kindergarten_management.users.ParentUser;
import com.example.kindergarten_management.views.fragments.SignUpFragment;
import com.google.firebase.FirebaseApp;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(this);

        buttonsSetup();

        AuthHelper.checkLoggedInUser(this, findViewById(android.R.id.content), success -> {
            if (!success) {
                FragmentHelper.replaceFragment(getSupportFragmentManager(), R.id.main_fragment, new SignUpFragment());
            }
        });
    }

    private void buttonsSetup(){
        Button manager_button = findViewById(R.id.manager_button);
        Button parent_button = findViewById(R.id.parent_button);
        Button out_button = findViewById(R.id.out_button);

        manager_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuthHelper.currentUser = new KindergartenManagerUser("uid", "mail", "name");
                Intent intent = new Intent(MainActivity.this, KindergartenManagerActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        parent_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuthHelper.currentUser = new ParentUser("uid", "mail", "name");
                Intent intent = new Intent(MainActivity.this, ParentActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        out_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              AuthHelper.signOutUser(getApplicationContext());
            }
        });


    }
}