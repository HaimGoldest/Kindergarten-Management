package com.example.kindergarten_management.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kindergarten_management.R;
import com.example.kindergarten_management.adapters.StaffAdapter;
import com.example.kindergarten_management.controllers.DatabaseController;
import com.example.kindergarten_management.helpers.FragmentHelper;
import com.example.kindergarten_management.models.StaffMemberModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * StaffFragment Fragment
 */
public class StaffFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_staff, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_staff);
        FloatingActionButton fabAddStaff = view.findViewById(R.id.fab_add_staff);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ArrayList<StaffMemberModel> staffList = (ArrayList<StaffMemberModel>) DatabaseController.getInstance(getContext()).getAllStaffMembers();
        StaffAdapter adapter = new StaffAdapter(staffList, getContext(), getParentFragmentManager());
        recyclerView.setAdapter(adapter);

        fabAddStaff.setOnClickListener(v -> FragmentHelper.replaceFragment(getParentFragmentManager(), R.id.kindergarten_manager_fragment, new AddStaffFragment()));

        return view;
    }
}
