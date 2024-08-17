package com.example.kindergarten_management.helpers;

import android.util.Log;

import com.example.kindergarten_management.models.ChildModel;
import com.example.kindergarten_management.models.ClassModel;
import com.example.kindergarten_management.models.KindergartenModel;
import com.example.kindergarten_management.models.StaffMemberModel;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * Class for managing the Firebase FireStore database.
 */
public class FirestoreHelper {
    private static FirebaseFirestore db;

    /**
     * Initializes the FirebaseFirestore instance.
     */
    public FirestoreHelper() {
        if (db == null) {
            db = FirebaseFirestore.getInstance();
        }
    }

    /**
     * Gets the staff collection from Firestore.
     *
     * @return CollectionReference pointing to the "staff" collection.
     */
    private static CollectionReference getStaffCollection() {
        return db.collection("staff");
    }

    /**
     * Gets the class collection from Firestore.
     *
     * @return CollectionReference pointing to the "class" collection.
     */
    private static CollectionReference getClassCollection() {
        return db.collection("class");
    }

    /**
     * Gets the kindergarten collection from Firestore.
     *
     * @return CollectionReference pointing to the "kindergarten" collection.
     */
    private static CollectionReference getKindergartenCollection() {
        return db.collection("kindergarten");
    }

    /**
     * Gets the children collection from Firestore.
     *
     * @return CollectionReference pointing to the "children" collection.
     */
    private static CollectionReference getChildrenCollection() {
        return db.collection("children");
    }

    /**
     * Adds a new staff member to Firestore with a specific UID.
     *
     * @param staffMember The staff member to add.
     */
    public void addStaffMember(StaffMemberModel staffMember) {
        String uid = String.valueOf(staffMember.getId());
        getStaffCollection().document(uid)
                .set(staffMember)
                .addOnSuccessListener(aVoid -> {
                    Log.i("StaffAdd", "Staff member added successfully with UID: " + uid);
                    Log.d("StaffAdd", "Added staff member details: " + staffMember.toString());
                })
                .addOnFailureListener(e -> {
                    Log.e("StaffAdd", "Failed to add staff member", e);
                    Log.e("StaffAdd", "Error details: " + e.getMessage());
                });
    }

    /**
     * Updates an existing staff member in Firestore.
     *
     * @param staffMember The staff member to update.
     */
    public void updateStaffMember(StaffMemberModel staffMember) {
        String uid = String.valueOf(staffMember.getId());
        getStaffCollection().document(uid)
                .set(staffMember)
                .addOnSuccessListener(aVoid -> {
                    Log.i("StaffUpdate", "Staff member updated successfully: " + uid);
                    Log.d("StaffUpdate", "Updated staff member details: " + staffMember.toString());
                })
                .addOnFailureListener(e -> {
                    Log.e("StaffUpdate", "Failed to update staff member: " + uid, e);
                    Log.e("StaffUpdate", "Error details: " + e.getMessage());
                });
    }

    /**
     * Deletes a staff member from Firestore.
     *
     * @param staffMember The staff member to delete.
     */
    public void deleteStaffMember(StaffMemberModel staffMember) {
        String uid = String.valueOf(staffMember.getId());
        getStaffCollection().document(uid)
                .delete()
                .addOnSuccessListener(aVoid -> {
                    Log.i("StaffDelete", "Staff member deleted successfully: " + uid);
                })
                .addOnFailureListener(e -> {
                    Log.e("StaffDelete", "Failed to delete staff member: " + uid, e);
                    Log.e("StaffDelete", "Error details: " + e.getMessage());
                });
    }

    /**
     * Adds a new class to Firestore with a specific UID.
     *
     * @param classModel The class to add.
     */
    public void addClass(ClassModel classModel) {
        String uid = String.valueOf(classModel.getId());
        getClassCollection().document(uid)
                .set(classModel)
                .addOnSuccessListener(aVoid -> {
                    Log.i("ClassAdd", "Class added successfully with UID: " + uid);
                    Log.d("ClassAdd", "Added class details: " + classModel.toString());
                })
                .addOnFailureListener(e -> {
                    Log.e("ClassAdd", "Failed to add class", e);
                    Log.e("ClassAdd", "Error details: " + e.getMessage());
                });
    }

    /**
     * Updates an existing class in Firestore.
     *
     * @param classModel The class to update.
     */
    public void updateClass(ClassModel classModel) {
        String uid = String.valueOf(classModel.getId());
        getClassCollection().document(uid)
                .set(classModel)
                .addOnSuccessListener(aVoid -> {
                    Log.i("ClassUpdate", "Class updated successfully: " + uid);
                    Log.d("ClassUpdate", "Updated class details: " + classModel.toString());
                })
                .addOnFailureListener(e -> {
                    Log.e("ClassUpdate", "Failed to update class: " + uid, e);
                    Log.e("ClassUpdate", "Error details: " + e.getMessage());
                });
    }

    /**
     * Deletes a class from Firestore.
     *
     * @param classModel The class to delete.
     */
    public void deleteClass(ClassModel classModel) {
        String uid = String.valueOf(classModel.getId());
        getClassCollection().document(uid)
                .delete()
                .addOnSuccessListener(aVoid -> {
                    Log.i("ClassDelete", "Class deleted successfully: " + uid);
                })
                .addOnFailureListener(e -> {
                    Log.e("ClassDelete", "Failed to delete class: " + uid, e);
                    Log.e("ClassDelete", "Error details: " + e.getMessage());
                });
    }

    /**
     * Adds a new kindergarten to Firestore with a specific UID.
     *
     * @param kindergartenModel The kindergarten to add.
     */
    public void addKindergarten(KindergartenModel kindergartenModel) {
        String uid = String.valueOf(kindergartenModel.getId());
        getKindergartenCollection().document(uid)
                .set(kindergartenModel)
                .addOnSuccessListener(aVoid -> {
                    Log.i("KindergartenAdd", "Kindergarten added successfully with UID: " + uid);
                    Log.d("KindergartenAdd", "Added kindergarten details: " + kindergartenModel.toString());
                })
                .addOnFailureListener(e -> {
                    Log.e("KindergartenAdd", "Failed to add kindergarten", e);
                    Log.e("KindergartenAdd", "Error details: " + e.getMessage());
                });
    }

    /**
     * Updates an existing kindergarten in Firestore.
     *
     * @param kindergartenModel The kindergarten to update.
     */
    public void updateKindergarten(KindergartenModel kindergartenModel) {
        String uid = String.valueOf(kindergartenModel.getId());
        getKindergartenCollection().document(uid)
                .set(kindergartenModel)
                .addOnSuccessListener(aVoid -> {
                    Log.i("KindergartenUpdate", "Kindergarten updated successfully: " + uid);
                    Log.d("KindergartenUpdate", "Updated kindergarten details: " + kindergartenModel.toString());
                })
                .addOnFailureListener(e -> {
                    Log.e("KindergartenUpdate", "Failed to update kindergarten: " + uid, e);
                    Log.e("KindergartenUpdate", "Error details: " + e.getMessage());
                });
    }

    /**
     * Deletes a kindergarten from Firestore.
     *
     * @param kindergartenModel The kindergarten to delete.
     */
    public void deleteKindergarten(KindergartenModel kindergartenModel) {
        String uid = String.valueOf(kindergartenModel.getId());
        getKindergartenCollection().document(uid)
                .delete()
                .addOnSuccessListener(aVoid -> {
                    Log.i("KindergartenDelete", "Kindergarten deleted successfully: " + uid);
                })
                .addOnFailureListener(e -> {
                    Log.e("KindergartenDelete", "Failed to delete kindergarten: " + uid, e);
                    Log.e("KindergartenDelete", "Error details: " + e.getMessage());
                });
    }

    /**
     * Adds a new child to Firestore with a specific UID.
     *
     * @param child The child to add.
     * @return true if the child was successfully added, false otherwise.
     */
    public boolean addChild(ChildModel child) {
        final boolean[] success = {false};
        String uid = String.valueOf(child.getId());
        getChildrenCollection().document(uid)
                .set(child)
                .addOnSuccessListener(aVoid -> {
                    Log.i("ChildAdd", "Child added successfully with UID: " + uid);
                    Log.d("ChildAdd", "Added child details: " + child.toString());
                    success[0] = true;
                })
                .addOnFailureListener(e -> {
                    Log.e("ChildAdd", "Failed to add child", e);
                    Log.e("ChildAdd", "Error details: " + e.getMessage());
                    success[0] = false;
                });
        return success[0];
    }
}
