package com.example.kindergarten_management.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
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

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        buttonsSetup();

        AuthHelper.checkLoggedInUser(this, findViewById(android.R.id.content), success -> {
            if (!success) {
                FragmentHelper.replaceFragment(getSupportFragmentManager(), R.id.main_fragment, new SignUpFragment());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_option_1) {
            // Handle option 1 click
            Toast.makeText(this, "Option 1 selected", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.menu_option_2) {
            // Handle option 2 click
            Toast.makeText(this, "Option 2 selected", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.menu_option_3) {
            // Handle option 3 click
            Toast.makeText(this, "Option 3 selected", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void buttonsSetup() {
        // existing buttons setup code
    }
}
