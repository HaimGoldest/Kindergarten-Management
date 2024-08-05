package com.example.kindergarten_management.helpers;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.kindergarten_management.controllers.DatabaseController;
import com.example.kindergarten_management.views.fragments.EmptyFragment;


public class FragmentHelper {

    public static void replaceFragment(FragmentManager fragmentManager, int fragment_container_id, Fragment fragment) {
        fragmentManager.beginTransaction()
                .replace(fragment_container_id, fragment)
                .commit();
    }


    public static void replaceFragment(FragmentManager fragmentManager, int fragment_container_id, Fragment fragment, Bundle args) {
        fragment.setArguments(args);
        fragmentManager.beginTransaction()
                .replace(fragment_container_id, fragment)
                .commit();
    }

    public static String getFragmentData(Fragment fragment, String argKey) {
        Bundle args = fragment.getArguments();
        if (args != null) {
            return args.getString(argKey);
        }

        return null;
    }

    public static void setEmptyFragment(FragmentManager fragmentManager, int fragment_container_id) {
        fragmentManager.beginTransaction()
                .replace(fragment_container_id, new EmptyFragment())
                .commit();
    }

}
