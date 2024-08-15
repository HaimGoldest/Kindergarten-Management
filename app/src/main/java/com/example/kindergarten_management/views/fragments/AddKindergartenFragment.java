package com.example.kindergarten_management.views.fragments;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.kindergarten_management.R;
import com.example.kindergarten_management.controllers.DatabaseController;
import com.example.kindergarten_management.helpers.FragmentHelper;
import com.example.kindergarten_management.helpers.SnackbarHelper;
import com.example.kindergarten_management.models.KindergartenModel;

import java.util.Calendar;

/**
 * Fragment for adding a new class.
 */
public class AddKindergartenFragment extends Fragment {

    private EditText editTextName, editTextAddress, editTextCityName, editTextPhoneNumber, textViewOrganizationalAffiliation;
    private TextView textViewOpeningTime, textViewClosingTime;
    private Button buttonSetOpeningTime, buttonSetClosingTime, buttonAddKindergarten;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_kindergarten, container, false);

        editTextName = view.findViewById(R.id.edit_text_name);
        editTextAddress = view.findViewById(R.id.edit_text_address);
        editTextCityName = view.findViewById(R.id.edit_text_city_name);
        editTextPhoneNumber = view.findViewById(R.id.edit_text_phone_number);
        textViewOpeningTime = view.findViewById(R.id.text_view_opening_time);
        textViewClosingTime = view.findViewById(R.id.text_view_closing_time);
        textViewOrganizationalAffiliation = view.findViewById(R.id.edit_text_organizational_affiliation);
        buttonSetOpeningTime = view.findViewById(R.id.button_set_opening_time);
        buttonSetClosingTime = view.findViewById(R.id.button_set_closing_time);
        buttonAddKindergarten = view.findViewById(R.id.button_add_kindergarten);

        buttonSetOpeningTime.setOnClickListener(v -> showTimePickerDialog((view1, hourOfDay, minute) -> {
            textViewOpeningTime.setText(String.format("%02d:%02d", hourOfDay, minute));
        }));

        buttonSetClosingTime.setOnClickListener(v -> showTimePickerDialog((view1, hourOfDay, minute) -> {
            textViewClosingTime.setText(String.format("%02d:%02d", hourOfDay, minute));
        }));

        buttonAddKindergarten.setOnClickListener(v -> {
            if (validateInputs()) {
                addKindergarten();
            }
        });

        return view;
    }

    private void showTimePickerDialog(TimePickerDialog.OnTimeSetListener timeSetListener) {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), timeSetListener, hour, minute, true);
        timePickerDialog.show();
    }

    /**
     * Validates the input fields.
     *
     * @return True if inputs are valid, false otherwise.
     */
    private boolean validateInputs() {
        if (TextUtils.isEmpty(editTextName.getText())) {
            SnackbarHelper.sendErrorMessage(getView(), "Kindergarten name is required!");
            return false;
        }
        if (TextUtils.isEmpty(editTextAddress.getText())) {
            SnackbarHelper.sendErrorMessage(getView(), "Address is required!");
            return false;
        }
        if (TextUtils.isEmpty(editTextCityName.getText())) {
            SnackbarHelper.sendErrorMessage(getView(), "City name is required!");
            return false;
        }
        if (TextUtils.isEmpty(editTextPhoneNumber.getText())) {
            SnackbarHelper.sendErrorMessage(getView(), "Phone number is required!");
            return false;
        }
        if (TextUtils.isEmpty(textViewOpeningTime.getText())) {
            SnackbarHelper.sendErrorMessage(getView(), "Opening time is required!");
            return false;
        }
        if (TextUtils.isEmpty(textViewClosingTime.getText())) {
            SnackbarHelper.sendErrorMessage(getView(), "Closing time is required!");
            return false;
        }
        if (TextUtils.isEmpty(textViewOrganizationalAffiliation.getText())) {
            SnackbarHelper.sendErrorMessage(getView(), "Organizational affiliation is required!");
            return false;
        }
        return true;
    }

    private void addKindergarten() {
        String name = editTextName.getText().toString();
        String address = editTextAddress.getText().toString();
        String cityName = editTextCityName.getText().toString();
        String phoneNumber = editTextPhoneNumber.getText().toString();
        String openingTime = textViewOpeningTime.getText().toString();
        String closingTime = textViewClosingTime.getText().toString();
        String organizationalAffiliation = textViewOrganizationalAffiliation.getText().toString();

        KindergartenModel kindergarten = new KindergartenModel(getContext());
        kindergarten.setName(name);
        kindergarten.setAddress(address);
        kindergarten.setCityName(cityName);
        kindergarten.setPhoneNumber(phoneNumber);
        kindergarten.setOpeningTime(openingTime);
        kindergarten.setClosingTime(closingTime);
        kindergarten.setOrganizationalAffiliation(organizationalAffiliation);

        boolean wasAdded = DatabaseController.getInstance(getContext()).addKindergarten(kindergarten);
        if (wasAdded) {
            SnackbarHelper.sendSuccessMessage(getView(), "Kindergarten added successfully");
            FragmentHelper.replaceFragment(getParentFragmentManager(), R.id.kindergarten_manager_fragment, new KindergartenFragment());
        } else {
            SnackbarHelper.sendErrorMessage(getView(), "Failed to add kindergarten!");
        }
    }
}
