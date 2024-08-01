package com.example.kindergarten_management.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.kindergarten_management.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent editModeIntent = new Intent(MainActivity.this, KindergartenManagerActivity.class);
        startActivity(editModeIntent);

        //temp();
    }

    private void temp(){
        Button aButton = findViewById(R.id.a_button);
        Button bButton = findViewById(R.id.b_button);

        aButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent normalModeIntent = new Intent(MainActivity.this, x.class);
//                startActivity(normalModeIntent);
            }
        });

        bButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent editModeIntent = new Intent(MainActivity.this, x.class);
//                startActivity(editModeIntent);
            }
        });
    }
}