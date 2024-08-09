package com.example.kindergarten_management.views.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kindergarten_management.R;
import com.example.kindergarten_management.adapters.ClassAdapter;
import com.example.kindergarten_management.controllers.DatabaseController;
import com.example.kindergarten_management.helpers.AuthHelper;
import com.example.kindergarten_management.helpers.FragmentHelper;
import com.example.kindergarten_management.models.ClassModel;
import com.example.kindergarten_management.users.AdminUser;
import com.example.kindergarten_management.users.BaseUser;
import com.example.kindergarten_management.users.KindergartenManagerUser;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * ClassFragment Fragment
 */
public class ClassFragment extends Fragment {
    private boolean haveChangePermissions = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_class, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_class);
        FloatingActionButton fabAddStaff = view.findViewById(R.id.fab_add_class);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ArrayList<ClassModel> classList = (ArrayList<ClassModel>) DatabaseController.getInstance(getContext()).getAllClasses();
        ClassAdapter adapter = new ClassAdapter(classList, getContext(), getParentFragmentManager());
        recyclerView.setAdapter(adapter);

        BaseUser currentUser = AuthHelper.currentUser;
        if (currentUser instanceof AdminUser || currentUser instanceof KindergartenManagerUser) {
            haveChangePermissions = true;
        }

        if (haveChangePermissions) {
            fabAddStaff.setOnClickListener(v -> FragmentHelper.replaceFragment(getParentFragmentManager(), R.id.kindergarten_manager_fragment, new AddClassFragment()));
        } else {
            fabAddStaff.setVisibility(View.GONE);
        }

        return view;
    }
}