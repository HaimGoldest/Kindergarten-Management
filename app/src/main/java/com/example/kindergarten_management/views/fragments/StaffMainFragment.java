package com.example.kindergarten_management.views.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.kindergarten_management.R;
import com.example.kindergarten_management.helpers.SnackbarHelper;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

/**
 * Fragment for handling staff operations including uploading images to Firebase Storage.
 */
public class StaffMainFragment extends Fragment {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 100;
    private static final String IMAGES_DIRECTORY = "images/";

    private FirebaseStorage storage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_staff_main, container, false);

        storage = FirebaseStorage.getInstance();

        Button uploadImagesButton = view.findViewById(R.id.uploadImageButton);
        uploadImagesButton.setOnClickListener(v -> handleUploadImageButtonClick());

        return view;
    }

    /**
     * Handles the click event for the "Upload Images" button.
     */
    private void handleUploadImageButtonClick() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestCameraPermission();
        } else {
            openCamera();
        }
    }

    /**
     * Requests camera permission from the user.
     */
    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
    }

    /**
     * Opens the camera to capture an image.
     */
    private void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(requireActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } else {
            SnackbarHelper.sendErrorMessage(getView(), "No camera application found.");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                SnackbarHelper.sendErrorMessage(getView(), "Camera permission is required to take pictures.");
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK && data != null) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = extras != null ? (Bitmap) extras.get("data") : null;
            if (imageBitmap != null) {
                showUploadConfirmationDialog(imageBitmap);
            } else {
                SnackbarHelper.sendErrorMessage(getView(), "Failed to capture image.");
            }
        }
    }

    /**
     * Displays a confirmation dialog to upload the captured image.
     *
     * @param imageBitmap The captured image as a Bitmap.
     */
    private void showUploadConfirmationDialog(Bitmap imageBitmap) {
        new MaterialAlertDialogBuilder(requireContext())
                .setTitle("Upload Image")
                .setMessage("Do you want to upload this image to Firebase Storage?")
                .setPositiveButton("Yes", (dialog, which) -> uploadImageToFirebase(imageBitmap))
                .setNegativeButton("No", null)
                .show();
    }

    /**
     * Uploads the image to Firebase Storage.
     *
     * @param bitmap The image to upload.
     */
    private void uploadImageToFirebase(Bitmap bitmap) {
        StorageReference storageRef = storage.getReference();
        String fileName = IMAGES_DIRECTORY + UUID.randomUUID().toString() + ".jpg";
        StorageReference imageRef = storageRef.child(fileName);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = imageRef.putBytes(data);
        uploadTask.addOnSuccessListener(taskSnapshot -> {
            SnackbarHelper.sendSuccessMessage(getView(), "Image successfully uploaded");
        }).addOnFailureListener(e -> {
            SnackbarHelper.sendErrorMessage(getView(), "Failed to upload the image: " + e.getMessage());
        });
    }
}
