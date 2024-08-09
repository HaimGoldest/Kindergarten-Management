package com.example.kindergarten_management.views.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.kindergarten_management.R;
import com.example.kindergarten_management.controllers.DatabaseController;
import com.example.kindergarten_management.helpers.FragmentHelper;
import com.example.kindergarten_management.helpers.SnackbarHelper;
import com.example.kindergarten_management.models.ChildModel;
import com.example.kindergarten_management.models.ClassModel;
import com.example.kindergarten_management.models.KindergartenModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment for Child Registration.
 */
public class ChildRegistrationFragment extends Fragment {

    private EditText editTextChildId;
    private EditText editTextFullName;
    private Spinner spinnerKindergarten;
    private List<ClassModel> filteredClasses;
    private List<ClassModel> favoriteClassesList;

    String cityData, ageData, organizationalAffiliationData;
    private boolean isDialogFragmentOpen = false;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_child_registration, container, false);
        filteredClasses = new ArrayList<>();
        favoriteClassesList = new ArrayList<>();

        editTextChildId = view.findViewById(R.id.edit_text_child_id);
        editTextFullName = view.findViewById(R.id.edit_text_full_name);
        spinnerKindergarten = view.findViewById(R.id.spinner_kindergarten_child_registration);
        Button buttonSetClasses = view.findViewById(R.id.button_set_favorite_classes);
        Button buttonRegister = view.findViewById(R.id.button_child_registration);

        buttonSetClasses.setOnClickListener(v -> {
            if (filteredClasses != null && filteredClasses.size() > 0) {
                FavoriteClassesFragment dialogFragment = FavoriteClassesFragment.newInstance(filteredClasses);
                dialogFragment.setTargetFragment(this, 0);
                dialogFragment.show(getParentFragmentManager(), "SelectFavoritesDialogFragment");
                isDialogFragmentOpen = true;
            } else {
                SnackbarHelper.sendErrorMessage(getView(), "This Kindergarten not have any classes yet.");
            }

        });

        buttonRegister.setOnClickListener(v -> {
            if (validateInputs()) {
                registerChild();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initData();
        setupKindergartenSpinner();
        setFilteredClass();
    }

    /**
     * Sets up the spinner for selecting a kindergarten - filtered by "city" and "organizational AffiliationData".
     */
    private void setupKindergartenSpinner() {
        List<KindergartenModel> allList = DatabaseController.getInstance(getContext()).getAllKindergartens();
        List<KindergartenModel> filteredKindergartenList = new ArrayList<>();

        for(KindergartenModel k : allList) {
            if (!cityData.isEmpty() && !cityData.equalsIgnoreCase(k.getCityName())) {
                continue;
            }
            if (!organizationalAffiliationData.isEmpty() && !organizationalAffiliationData.equalsIgnoreCase(k.getOrganizationalAffiliation())) {
                continue;
            }

            filteredKindergartenList.add(k);
        }

        if (filteredKindergartenList.size() > 0) {
            ArrayAdapter<KindergartenModel> kindergartenAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, filteredKindergartenList);
            kindergartenAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerKindergarten.setAdapter(kindergartenAdapter);

            setFilteredClass();
        } else {
            SnackbarHelper.sendErrorMessage(getView(), "No kindergarten meets the conditions!");
            FragmentHelper.replaceFragment(getParentFragmentManager(), R.id.parent_fragment_container, new FindKindergartenFragment());
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

        if (spinnerKindergarten.getSelectedItem() == null) {
            SnackbarHelper.sendErrorMessage(getView(), "Kindergarten is required!");
            return false;
        }

        if (isDialogFragmentOpen || favoriteClassesList == null || favoriteClassesList.size() < 1) {
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
        KindergartenModel kindergarten = (KindergartenModel) spinnerKindergarten.getSelectedItem();

        ChildModel child = new ChildModel(id);
        child.setFullName(fullName);

        if (cityData != null) {
            child.setCity(cityData);
        }
        if (ageData != null && !ageData.isEmpty()) {
            try {
                child.setAge(Integer.parseInt(ageData));
            } catch (Exception e) {
                // do nothing
            }

        }

        child.setKindergarten(kindergarten.getId());
        child.setFavoriteClasses(favoriteClassesList);

        boolean wasAdded = DatabaseController.getInstance(getContext()).addChild(child);
        if (wasAdded) {
            SnackbarHelper.sendSuccessMessage(getView(), "Child registered successfully");
            FragmentHelper.setEmptyFragment(getParentFragmentManager(), R.id.parent_fragment_container);
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
        isDialogFragmentOpen = false;
    }

    private void initData() {
        Bundle args = this.getArguments();
        if (args != null) {
            cityData =  args.getString("city");
            organizationalAffiliationData =  args.getString("organizationalAffiliation");
            ageData =  args.getString("age");
        }
    }

    private void setFilteredClass() {
        spinnerKindergarten.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                KindergartenModel selectedKindergarten = (KindergartenModel) parent.getSelectedItem();
                filteredClasses = DatabaseController.getInstance(getContext()).getClassesByKindergarten(selectedKindergarten);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }
}