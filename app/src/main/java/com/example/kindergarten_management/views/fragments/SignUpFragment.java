package com.example.kindergarten_management.views.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.kindergarten_management.R;
import com.example.kindergarten_management.helpers.AuthHelper;
import com.example.kindergarten_management.helpers.FragmentHelper;
import com.example.kindergarten_management.helpers.SnackbarHelper;
import com.example.kindergarten_management.models.ClassModel;
import com.example.kindergarten_management.users.AdminUser;
import com.example.kindergarten_management.users.BaseUser;
import com.example.kindergarten_management.users.KindergartenManagerUser;
import com.example.kindergarten_management.users.ParentUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class SignUpFragment extends Fragment {

    private EditText editTextEmail, editTextPassword;
    private ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;
    private Spinner spinnerUserRule;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        editTextEmail = view.findViewById(R.id.edit_text_email);
        editTextPassword = view.findViewById(R.id.edit_text_password);
        progressBar = view.findViewById(R.id.progress_bar);
        spinnerUserRule = view.findViewById(R.id.spinner_user_rule);

        Button buttonSignUp = view.findViewById(R.id.button_sign_up);
        Button buttonSwitchToLogin = view.findViewById(R.id.button_switch_to_login);

        buttonSignUp.setOnClickListener(v -> registerUser());
        buttonSwitchToLogin.setOnClickListener(v ->
                FragmentHelper.replaceFragment(getParentFragmentManager(), R.id.main_fragment, new LoginFragment()));

        return view;
    }

    private void registerUser() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String name = ""; //todo - fix here
        String rule = (String) spinnerUserRule.getSelectedItem();

        if (TextUtils.isEmpty(email)) {
            editTextEmail.setError("Email is required");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Password is required");
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            progressBar.setVisibility(View.GONE);
            if (task.isSuccessful()) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null) {
                    String uid = firebaseUser.getUid();
                    BaseUser newUser = AuthHelper.buildUser(uid, email, name, rule);
                    AuthHelper.registerNewUser(getContext(), getView(), newUser);
                }
            } else {
                String errorMsg = "Registration failed: " + Objects.requireNonNull(task.getException()).getMessage();
                SnackbarHelper.sendErrorMessage(getView(), errorMsg);
            }
        });
    }

}
