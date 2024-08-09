package com.example.kindergarten_management.views.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.example.kindergarten_management.R;
import com.example.kindergarten_management.controllers.DatabaseController;
import com.example.kindergarten_management.helpers.FragmentHelper;
import com.example.kindergarten_management.helpers.SnackbarHelper;
import com.example.kindergarten_management.models.ChildModel;
import com.example.kindergarten_management.models.ClassModel;
import com.example.kindergarten_management.models.KindergartenModel;

import java.util.List;

/**
 * Fragment for Find KindergartenFragment.
 */
public class FindKindergartenFragment extends Fragment {

    private EditText editTextCity;
    private EditText editTextOrganizationalAffiliation;
    private EditText editTextAge;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_find_kindergarten, container, false);

        editTextCity = view.findViewById(R.id.edit_text_city);
        editTextOrganizationalAffiliation = view.findViewById(R.id.edit_organizational_affiliation);
        editTextAge = view.findViewById(R.id.edit_text_age);
        Button buttonFind = view.findViewById(R.id.button_find_kindergarten);

        buttonFind.setOnClickListener(v -> {
            if (validateInputs()) {
                findKindergartens();
            }
        });

        return view;
    }

    /**
     * Validates the input fields.
     *
     * @return True if inputs are valid, false otherwise.
     */
    private boolean validateInputs() {
        if (TextUtils.isEmpty(editTextCity.getText())) {
            editTextCity.setError("City Name is required");
            return false;
        }

        return true;
    }

    /**
     * Find relevant Kindergartens
     */
    private void findKindergartens() {
        String city = editTextCity.getText().toString();
        String organizationalAffiliation = editTextOrganizationalAffiliation.getText().toString();
        String age = editTextAge.getText().toString();

        Bundle args = new Bundle();
        args.putString("city", city);
        args.putString("organizationalAffiliation", organizationalAffiliation);
        args.putString("age", age);


        FragmentHelper.replaceFragment(getParentFragmentManager(), R.id.parent_fragment_container, new ChildRegistrationFragment(), args);
    }

}