package com.example.kindergarten_management.views.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
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
import com.example.kindergarten_management.helpers.FragmentHelper;
import com.example.kindergarten_management.helpers.SnackbarHelper;
import com.example.kindergarten_management.models.ClassModel;
import com.example.kindergarten_management.models.KindergartenModel;
import com.example.kindergarten_management.models.StaffMemberModel;

import java.util.Calendar;
import java.util.List;

/**
 * Fragment for updating an existing staff member.
 */
public class UpdateStaffFragment extends Fragment {

    private boolean errorLoadingStaffMember;
    private EditText updateTextName;
    private TextView labelUpdateDate;
    private Spinner spinnerUpdateRule;
    private Spinner spinnerUpdateKindergarten;
    private Spinner spinnerUpdateClass;
    private Button buttonUpdateStaff;
    private Button buttonUpdateStartWorkingDate;
    private Calendar startDateCalendar;
    private StaffMemberModel currentStaffMember;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update_staff, container, false);

        updateTextName = view.findViewById(R.id.update_text_name);
        labelUpdateDate = view.findViewById(R.id.label_update_date);
        spinnerUpdateRule = view.findViewById(R.id.spinner_update_rule);
        spinnerUpdateKindergarten = view.findViewById(R.id.spinner_update_kindergarten);
        spinnerUpdateClass = view.findViewById(R.id.spinner_update_class);
        buttonUpdateStaff = view.findViewById(R.id.button_update_staff);
        buttonUpdateStartWorkingDate = view.findViewById(R.id.button_update_start_working_date);

        currentStaffMember = null;
        String idString = FragmentHelper.getFragmentData(this,"staffMemberId");
        if (idString != null) {
            int id = Integer.parseInt(idString);
            currentStaffMember = DatabaseController.getInstance(getContext()).getStaffMember(id);
        }

        if (currentStaffMember != null) {
            updateTextName.setText(currentStaffMember.getName());
            labelUpdateDate.setText("Current start date: " + currentStaffMember.getStartWorkingDate());
            ((TextView) view.findViewById(R.id.staff_member_name)).setText("Current name is: " + currentStaffMember.getName());

            setupRuleSpinner();
            setupKindergartenSpinner();
            setupClassSpinner(currentStaffMember.getAssignedKindergarten(), currentStaffMember.getAssignedClass());

            spinnerUpdateKindergarten.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    KindergartenModel selectedKindergarten = (KindergartenModel) parent.getSelectedItem();
                    setupClassSpinner(selectedKindergarten,null);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // Do nothing
                }
            });

            buttonUpdateStartWorkingDate.setOnClickListener(v -> showDatePickerDialog());

            buttonUpdateStaff.setOnClickListener(v -> updateStaffMember());

        } else {
            errorLoadingStaffMember = true;
        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(errorLoadingStaffMember) {
            SnackbarHelper.sendErrorMessage(getView(), "Failed to load staff member for updating!");
            FragmentHelper.replaceFragment(getParentFragmentManager(), R.id.kindergarten_manager_fragment, new StaffFragment());
        }
    }

    /**
     * Sets up the spinner for selecting a rule.
     */
    private void setupRuleSpinner() {
        ArrayAdapter<CharSequence> ruleAdapter = ArrayAdapter.createFromResource(getContext(), R.array.staff_rules_array, android.R.layout.simple_spinner_item);
        ruleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUpdateRule.setAdapter(ruleAdapter);
        spinnerUpdateRule.setSelection(ruleAdapter.getPosition(currentStaffMember.getRule()));
    }

    /**
     * Sets up the spinner for selecting a kindergarten.
     */
    private void setupKindergartenSpinner() {
        List<KindergartenModel> kindergartenList = DatabaseController.getInstance(getContext()).getAllKindergartens();
        if (kindergartenList != null) {
            ArrayAdapter<KindergartenModel> kindergartenAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, kindergartenList);
            kindergartenAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerUpdateKindergarten.setAdapter(kindergartenAdapter);
            spinnerUpdateKindergarten.setSelection(kindergartenAdapter.getPosition(currentStaffMember.getAssignedKindergarten()));
        }
    }

    /**
     * Sets up the spinner for selecting a class based on the selected kindergarten.
     *
     * @param kindergarten The selected kindergarten.
     */
    private void setupClassSpinner(KindergartenModel kindergarten, ClassModel defaultClass) {
        List<ClassModel> classList = DatabaseController.getInstance(getContext()).getClassesByKindergarten(kindergarten);
        if (classList != null) {
            ArrayAdapter<ClassModel> classAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, classList);
            classAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerUpdateClass.setAdapter(classAdapter);

            if (defaultClass != null) {
                spinnerUpdateClass.setSelection(classAdapter.getPosition(defaultClass));
            }
        }
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    startDateCalendar = Calendar.getInstance();
                    startDateCalendar.set(selectedYear, selectedMonth, selectedDay);
                    labelUpdateDate.setText(getString(R.string.selected_date) + selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear);
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void updateStaffMember() {
        if (!validateInputs())
            return;

        String newName = updateTextName.getText().toString();
        String newRule = spinnerUpdateRule.getSelectedItem().toString();
        String newKindergartenString = spinnerUpdateKindergarten.getSelectedItem().toString();
        String newClassString = spinnerUpdateClass.getSelectedItem().toString();
        String newStartDate = labelUpdateDate.getText().toString().split(" ")[2];
        currentStaffMember.setStartWorkingDate(newStartDate);
        currentStaffMember.setName(newName);
        currentStaffMember.setRule(newRule);

        String newKindergartenName = newKindergartenString.split("\\s+")[1];
        KindergartenModel kindergarten = DatabaseController.getInstance(getContext()).getKindergartenByName(newKindergartenName);
        currentStaffMember.setAssignedKindergarten(kindergarten.getId());

        int newClassId = Integer.parseInt(newClassString.split("\\s+")[1]);
        ClassModel classModel = DatabaseController.getInstance(getContext()).getClassModel(newClassId);
        currentStaffMember.setAssignedClass(classModel.getId());

        boolean wasUpdated = DatabaseController.getInstance(getContext()).updateStaffMember(currentStaffMember);
        if (wasUpdated) {
            SnackbarHelper.sendSuccessMessage(getView(), "Staff member updated successfully");
            FragmentHelper.replaceFragment(getParentFragmentManager(), R.id.kindergarten_manager_fragment, new StaffFragment());
        } else {
            SnackbarHelper.sendErrorMessage(getView(), "Failed to update staff member!");
        }
    }

    private boolean validateInputs() {
        if (currentStaffMember == null) {
            SnackbarHelper.sendErrorMessage(getView(), "Error: Staff Member not fount");
            FragmentHelper.replaceFragment(getParentFragmentManager(), R.id.kindergarten_manager_fragment, new ClassFragment());
            return false;
        }

        return true;
    }

}
