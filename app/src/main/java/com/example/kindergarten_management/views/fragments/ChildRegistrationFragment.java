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
import android.widget.Spinner;

import com.example.kindergarten_management.R;
import com.example.kindergarten_management.controllers.DatabaseController;
import com.example.kindergarten_management.helpers.SnackbarHelper;
import com.example.kindergarten_management.models.ChildModel;
import com.example.kindergarten_management.models.ClassModel;
import com.example.kindergarten_management.models.KindergartenModel;

import java.util.List;

/**
 * Fragment for Child Registration.
 */
public class ChildRegistrationFragment extends Fragment {

    private EditText editTextChildId;
    private EditText editTextFullName;
    private EditText editTextCity;
    private EditText editTextAge;
    private Spinner spinnerKindergarten;
    private List<ClassModel> favoriteClassesList;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_child_registration, container, false);

        editTextChildId = view.findViewById(R.id.edit_text_child_id);
        editTextFullName = view.findViewById(R.id.edit_text_full_name);
        editTextCity = view.findViewById(R.id.edit_text_city);
        editTextAge = view.findViewById(R.id.edit_text_age);
        spinnerKindergarten = view.findViewById(R.id.spinner_kindergarten_child_registration);
        Button buttonRegister = view.findViewById(R.id.button_child_registration);

        buttonRegister.setOnClickListener(v -> {
            if (validateInputs()) {
                registerChild();
            }
        });

        setupKindergartenSpinner();
        return view;
    }

    /**
     * Sets up the spinner for selecting a kindergarten.
     */
    private void setupKindergartenSpinner() {
        List<KindergartenModel> kindergartenList = DatabaseController.getInstance(getContext()).getAllKindergartens();
        if (kindergartenList != null) {
            ArrayAdapter<KindergartenModel> kindergartenAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, kindergartenList);
            kindergartenAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerKindergarten.setAdapter(kindergartenAdapter);
        }
    }

    /**
     * Validates the input fields.
     *
     * @return True if inputs are valid, false otherwise.
     */
    private boolean validateInputs() {
        if (TextUtils.isEmpty(editTextFullName.getText())) {
            editTextFullName.setError("Full Name is required");
            return false;
        }

        if (TextUtils.isEmpty(editTextCity.getText())) {
            editTextCity.setError("City Name is required");
            return false;
        }

        if (TextUtils.isEmpty(editTextAge.getText())) {
            editTextAge.setError("Age is required");
            return false;
        }

        if (spinnerKindergarten.getSelectedItem() == null) {
            SnackbarHelper.sendErrorMessage(getView(), "Kindergarten is required!");
            return false;
        }

        if (favoriteClassesList == null || favoriteClassesList.size() < 1) {
            SnackbarHelper.sendErrorMessage(getView(), "Select at least 1 class!");
            return false;
        }

        return true;
    }

    /**
     * register a new Child to the database.
     */
    private void registerChild() {
        String idString = editTextChildId.getText().toString();
        int id = Integer.parseInt(idString);
        String fullName = editTextFullName.getText().toString();
        String city = editTextCity.getText().toString();
        String ageString = editTextAge.getText().toString();
        int age = Integer.parseInt(ageString);
        KindergartenModel kindergarten = (KindergartenModel) spinnerKindergarten.getSelectedItem();

        ChildModel child = new ChildModel(id);
        child.setFullName(fullName);
        child.setCity(city);
        child.setAge(age);
        child.setKindergarten(kindergarten.getId());
        child.setFavoriteClasses(favoriteClassesList);

        //todo - fix here
        boolean wasAdded = false;
        //boolean wasAdded = DatabaseController.getInstance(getContext()).addClass(classModel);
        if (wasAdded) {
            SnackbarHelper.sendSuccessMessage(getView(), "Child registered successfully");
            getParentFragmentManager().popBackStack();
        } else {
            SnackbarHelper.sendErrorMessage(getView(), "Failed to register the child!");
        }
    }

    /**
     * Updates the classes after receiving the favorite classes from the dialog.
     * @param favoriteList List of favorite classes.
     */
    public void updateClassesAfterDialog(List<ClassModel> favoriteList) {
        favoriteClassesList = favoriteList;
    }
}