package com.example.kindergarten_management.helpers;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


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

}
