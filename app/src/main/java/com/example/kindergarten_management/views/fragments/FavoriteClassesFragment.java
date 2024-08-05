package com.example.kindergarten_management.views.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.kindergarten_management.R;
import com.example.kindergarten_management.adapters.ClassAdapter;
import com.example.kindergarten_management.helpers.SnackbarHelper;
import com.example.kindergarten_management.models.ClassModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * A DialogFragment to allow children to select their favorite classes.
 */
public class FavoriteClassesFragment extends DialogFragment {

    private static final String ARG_FILTERED_CLASSES = "filteredClasses";
    private ArrayList<ClassModel> filteredClasses;
    private ClassAdapter classAdapter;


    public static FavoriteClassesFragment newInstance(List<ClassModel> filteredClasses) {
        FavoriteClassesFragment fragment = new FavoriteClassesFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_FILTERED_CLASSES, (Serializable) filteredClasses);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            filteredClasses = (ArrayList<ClassModel>) getArguments().getSerializable(ARG_FILTERED_CLASSES);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorite_classes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerViewFavorites = view.findViewById(R.id.recycler_view_favorites);
        Button buttonSaveFavorites = view.findViewById(R.id.button_save_favorites);
        Button buttonResetFavorites = view.findViewById(R.id.button_reset_favorites);

        classAdapter = new ClassAdapter(filteredClasses, getContext(), getParentFragmentManager());
        recyclerViewFavorites.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewFavorites.setAdapter(classAdapter);

        buttonSaveFavorites.setOnClickListener(v -> {
            List<ClassModel> favoriteList = classAdapter.getSelectedFavorites();
            if (favoriteList.size() > 3) {
                SnackbarHelper.sendErrorMessage(getView(), "You can select only up to 3 favorite classes!");
            } else {
                ChildRegistrationFragment targetFragment = (ChildRegistrationFragment) getTargetFragment();
                if (targetFragment != null) {
                    targetFragment.updateClassesAfterDialog(favoriteList);
                }
                dismiss();
            }
        });

        buttonResetFavorites.setOnClickListener(v -> {
            classAdapter.resetFavorites();
        });
    }
}
