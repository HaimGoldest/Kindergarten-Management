package com.example.kindergarten_management.views;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import com.example.kindergarten_management.R;
import com.example.kindergarten_management.helpers.FragmentHelper;
import com.example.kindergarten_management.views.fragments.ClassFragment;
import com.example.kindergarten_management.views.fragments.FindKindergartenFragment;
import com.example.kindergarten_management.views.fragments.KindergartenFragment;
import com.example.kindergarten_management.views.fragments.ChildRegistrationFragment;

public class ParentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent);

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

        //todo - implement
//        buttonShowImages.setOnClickListener(v ->
//                FragmentHelper.replaceFragment(getSupportFragmentManager(), R.id.parent_fragment_container, new ImageFragment()));
    }
}
