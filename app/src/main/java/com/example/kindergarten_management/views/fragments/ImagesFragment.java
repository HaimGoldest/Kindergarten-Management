package com.example.kindergarten_management.views.fragments;

import android.net.Uri;
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
import com.example.kindergarten_management.adapters.ImageAdapter;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class ImagesFragment extends Fragment {

    private RecyclerView recyclerView;
    private ImageAdapter imageAdapter;
    private List<Uri> imageUriList;
    private FirebaseStorage storage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_images, container, false);

        recyclerView = view.findViewById(R.id.recycler_view_images);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        imageUriList = new ArrayList<>();
        imageAdapter = new ImageAdapter(imageUriList);
        recyclerView.setAdapter(imageAdapter);

        storage = FirebaseStorage.getInstance();
        loadImagesFromFirebase();

        return view;
    }

    private void loadImagesFromFirebase() {
        StorageReference storageRef = storage.getReference().child("images/");
        storageRef.listAll().addOnSuccessListener(listResult -> {
            for (StorageReference item : listResult.getItems()) {
                item.getDownloadUrl().addOnSuccessListener(uri -> {
                    imageUriList.add(uri);
                    imageAdapter.notifyDataSetChanged();
                });
            }
        }).addOnFailureListener(e -> {
            // Handle any errors
        });
    }
}
