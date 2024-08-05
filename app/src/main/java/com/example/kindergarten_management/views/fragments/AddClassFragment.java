package com.example.kindergarten_management.views.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.kindergarten_management.R;
import com.example.kindergarten_management.controllers.DatabaseController;
import com.example.kindergarten_management.helpers.SnackbarHelper;
import com.example.kindergarten_management.models.ClassModel;
import com.example.kindergarten_management.models.KindergartenModel;

import java.util.List;

/**
 * Fragment for adding a new class.
 */
public class AddClassFragment extends Fragment {

    private EditText editTextType;
    private EditText editTextMaxChildren;
    private EditText editTextMinAge;
    private EditText editTextMaxAge;
    private Spinner spinnerKindergarten;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_class, container, false);

        editTextType = view.findViewById(R.id.edit_text_type);
        editTextMaxChildren = view.findViewById(R.id.edit_text_max_children);
        editTextMinAge = view.findViewById(R.id.edit_text_min_age);
        editTextMaxAge = view.findViewById(R.id.edit_text_max_age);
        spinnerKindergarten = view.findViewById(R.id.label_select_kindergarten);
        Button buttonAddClass = view.findViewById(R.id.button_add_class);

        buttonAddClass.setOnClickListener(v -> {
            if (validateInputs()) {
                addClass();
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
        if (TextUtils.isEmpty(editTextType.getText())) {
            SnackbarHelper.sendErrorMessage(getView(), "Class type is required!");
            return false;
        }

        if (TextUtils.isEmpty(editTextMaxChildren.getText())) {
            SnackbarHelper.sendErrorMessage(getView(), "Max children count is required!");
            return false;
        }

        if (TextUtils.isEmpty(editTextMinAge.getText())) {
            SnackbarHelper.sendErrorMessage(getView(), "Min age is required!");
            return false;
        }

        if (TextUtils.isEmpty(editTextMaxAge.getText())) {
            SnackbarHelper.sendErrorMessage(getView(), "Max age is required!");
            return false;
        }

        if (spinnerKindergarten.getSelectedItem() == null) {
            SnackbarHelper.sendErrorMessage(getView(), "Kindergarten is required!");
            return false;
        }

        return true;
    }

    /**
     * Adds a new class to the database.
     */
    private void addClass() {
        String type = editTextType.getText().toString();
        int maxChildren = Integer.parseInt(editTextMaxChildren.getText().toString());
        int minAge = Integer.parseInt(editTextMinAge.getText().toString());
        int maxAge = Integer.parseInt(editTextMaxAge.getText().toString());
        KindergartenModel assignedKindergarten = (KindergartenModel) spinnerKindergarten.getSelectedItem();

        ClassModel classModel = new ClassModel(getContext());
        classModel.setType(type);
        classModel.setMaxChildren(maxChildren);
        classModel.setMinAge(minAge);
        classModel.setMaxAge(maxAge);
        classModel.setKindergarten(assignedKindergarten.getId());

        boolean wasAdded = DatabaseController.getInstance(getContext()).addClass(classModel);
        if (wasAdded) {
            SnackbarHelper.sendSuccessMessage(getView(), "Class added successfully");
            getParentFragmentManager().popBackStack();
        } else {
            SnackbarHelper.sendErrorMessage(getView(), "Failed to add class!");
        }
    }
}
