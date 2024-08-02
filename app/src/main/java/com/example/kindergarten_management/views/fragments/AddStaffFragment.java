package com.example.kindergarten_management.views.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.kindergarten_management.R;
import com.example.kindergarten_management.controllers.DatabaseController;
import com.example.kindergarten_management.helpers.SnackbarHelper;
import com.example.kindergarten_management.models.ClassModel;
import com.example.kindergarten_management.models.KindergartenModel;
import com.example.kindergarten_management.models.StaffMemberModel;

import java.util.Calendar;
import java.util.List;

/**
 * Fragment for adding a new staff member.
 */
public class AddStaffFragment extends Fragment {

    private int selectedYear, selectedMonth, selectedDay;
    private TextView labelPickDate;
    private EditText editTextName;
    private Spinner spinnerRule;
    private Spinner spinnerAssignedKindergarten;
    private Spinner spinnerAssignedClass;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_staff, container, false);

        editTextName = view.findViewById(R.id.edit_text_name);
        labelPickDate = view.findViewById(R.id.label_pick_date);
        spinnerRule = view.findViewById(R.id.spinner_rule);
        spinnerAssignedKindergarten = view.findViewById(R.id.spinner_assigned_kindergarten);
        spinnerAssignedClass = view.findViewById(R.id.spinner_assigned_class);
        Button buttonAddStaff = view.findViewById(R.id.button_add_staff);
        Button buttonPickDate = view.findViewById(R.id.button_set_start_working_date);

        // Initialize current date and time
        Calendar calendar = Calendar.getInstance();
        selectedYear = calendar.get(Calendar.YEAR);
        selectedMonth = calendar.get(Calendar.MONTH);
        selectedDay = calendar.get(Calendar.DAY_OF_MONTH);

        buttonPickDate.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                    (view1, year, month, dayOfMonth) -> {
                        selectedYear = year;
                        selectedMonth = month;
                        selectedDay = dayOfMonth;
                        labelPickDate.setText(getString(R.string.selected_date) + selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear);
                    }, selectedYear, selectedMonth, selectedDay);
            datePickerDialog.show();
        });

        spinnerAssignedKindergarten.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                KindergartenModel selectedKindergarten = (KindergartenModel) parent.getSelectedItem();
                setupClassSpinner(selectedKindergarten);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        buttonAddStaff.setOnClickListener(v -> {
            if (validateInputs()) {
                addStaffMember();
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
            spinnerAssignedKindergarten.setAdapter(kindergartenAdapter);
        }
    }

    /**
     * Sets up the spinner for selecting a class based on the selected kindergarten.
     *
     * @param kindergarten The selected kindergarten.
     */
    private void setupClassSpinner(KindergartenModel kindergarten) {
        List<ClassModel> classList = DatabaseController.getInstance(getContext()).getClassesByKindergarten(kindergarten);
        if (classList != null) {
            ArrayAdapter<ClassModel> classAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, classList);
            classAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerAssignedClass.setAdapter(classAdapter);
        }
    }

    /**
     * Validates the input fields.
     *
     * @return True if inputs are valid, false otherwise.
     */
    private boolean validateInputs() {
        if (TextUtils.isEmpty(editTextName.getText())) {
            SnackbarHelper.sendErrorMessage(getView(), "Name is required!");
            return false;
        }

        if (TextUtils.isEmpty(labelPickDate.getText().toString())) {
            SnackbarHelper.sendErrorMessage(getView(), "Start working date is required!");
            return false;
        }

        if (spinnerRule.getSelectedItem() == null) {
            SnackbarHelper.sendErrorMessage(getView(), "Rule is required!");
            return false;
        }

        if (spinnerAssignedKindergarten.getSelectedItem() == null) {
            SnackbarHelper.sendErrorMessage(getView(), "Assigned kindergarten is required!");
            return false;
        }

        if (spinnerAssignedClass.getSelectedItem() == null) {
            SnackbarHelper.sendErrorMessage(getView(), "Assigned class is required!");
            return false;
        }

        return true;
    }

    /**
     * Adds a new staff member to the database.
     */
    private void addStaffMember() {
        String name = editTextName.getText().toString();
        String startWorkingDate = labelPickDate.getText().toString();
        String rule = (String) spinnerRule.getSelectedItem();
        KindergartenModel assignedKindergarten = (KindergartenModel) spinnerAssignedKindergarten.getSelectedItem();
        ClassModel assignedClass = (ClassModel) spinnerAssignedClass.getSelectedItem();

        StaffMemberModel staffMember = new StaffMemberModel(getContext());
        staffMember.setName(name);
        staffMember.setRule(rule);
        staffMember.setStartWorkingDate(startWorkingDate);
        staffMember.setAssignedKindergarten(assignedKindergarten.getId());
        staffMember.setAssignedClass(assignedClass.getId());

        boolean wasAdded = DatabaseController.getInstance(getContext()).addStaffMember(staffMember);
        if (wasAdded) {
            SnackbarHelper.sendSuccessMessage(getView(), "Staff member added successfully");
            getParentFragmentManager().popBackStack();
        } else {
            SnackbarHelper.sendErrorMessage(getView(), "Failed to add staff member!");
        }
    }
}
