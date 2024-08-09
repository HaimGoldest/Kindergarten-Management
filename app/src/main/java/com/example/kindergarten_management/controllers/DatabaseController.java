package com.example.kindergarten_management.controllers;

import android.content.Context;

import com.example.kindergarten_management.helpers.FirebaseDatabaseHelper;
import com.example.kindergarten_management.helpers.SqlDatabaseHelper;
import com.example.kindergarten_management.helpers.TempDataHelper;
import com.example.kindergarten_management.models.ChildModel;
import com.example.kindergarten_management.models.ClassModel;
import com.example.kindergarten_management.models.KindergartenModel;
import com.example.kindergarten_management.models.StaffMemberModel;

import java.util.List;
import java.util.Random;

/**
 * Controller class to manage the database operations.
 * This class is a singleton to ensure only one instance handles database operations.
 */
public class DatabaseController {
    private static DatabaseController instance = null;
    private static FirebaseDatabaseHelper firebaseDb = null;
    private static SqlDatabaseHelper sqlDb = null;
    private static Context context = null;

    // Private constructor to prevent instantiation
    private DatabaseController() {
    }

    /**
     * Returns the singleton instance of the DatabaseController.
     * Closes the existing database connection if any, before returning the instance.
     *
     * @param context The context to use for database operations.
     * @return The singleton instance of DatabaseController.
     */
    public static synchronized DatabaseController getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseController();
        }
        closeSqlDatabase();
        openSqlDatabase(context);
        return instance;
    }

    /**
     * Deletes a staff member from the database.
     *
     * @param staffMember The staff member to delete.
     * @return True if the staff member was successfully deleted, false otherwise.
     */
    public boolean deleteStaffMember(StaffMemberModel staffMember) {
        // TODO: Integrate with Firebase

        boolean res = sqlDb.deleteStaffMember(staffMember.getId());
        if (res) {
            return true;
        }

        // Simulate random result for temporary behavior
        return new Random().nextBoolean();
    }

    /**
     * Adds a new staff member to the database.
     *
     * @param staffMember The staff member to add.
     * @return True if the staff member was successfully added, false otherwise.
     */
    public boolean addStaffMember(StaffMemberModel staffMember) {
        // TODO: Integrate with Firebase

        boolean res = sqlDb.createStaffMember(staffMember);
        if (res) {
            return true;
        }

        // Simulate random result for temporary behavior
        return new Random().nextBoolean();
    }

    /**
     * Updates the details of an existing staff member in the database.
     *
     * @param staffMember The staff member with updated details.
     * @return True if the staff member was successfully updated, false otherwise.
     */
    public boolean updateStaffMember(StaffMemberModel staffMember) {
        // TODO: Integrate with Firebase

        boolean res = sqlDb.updateStaffMember(staffMember);
        if (res) {
            return true;
        }

        // Simulate random result for temporary behavior
        return new Random().nextBoolean();
    }

    public StaffMemberModel getStaffMember(int staffMemberId) {
        // TODO: Integrate with Firebase

        StaffMemberModel staffMember = sqlDb.readStaffMember(staffMemberId);
        if (staffMember != null) {
            return staffMember;
        }

        // Simulate random data for temporary behavior
        return TempDataHelper.getStaffMember(staffMemberId);
    }

    /**
     * Retrieves all staff members from the database.
     *
     * @return A list of all staff members.
     */
    public List<StaffMemberModel> getAllStaffMembers() {
        // TODO: Integrate with Firebase

        List<StaffMemberModel> staffMembers = sqlDb.getAllStaffMembers();
        if (staffMembers != null) {
            return staffMembers;
        }

        // Simulate random data for temporary behavior
        return TempDataHelper.getStaffList();
    }

    /**
     * Deletes a class from the database.
     *
     * @param classModel The class to delete.
     * @return True if the class was successfully deleted, false otherwise.
     */
    public boolean deleteClass(ClassModel classModel) {
        // TODO: Integrate with Firebase

        boolean res = sqlDb.deleteClass(classModel.getId());
        if (res) {
            return true;
        }

        // Simulate random result for temporary behavior
        return new Random().nextBoolean();
    }

    /**
     * Adds a new class to the database.
     *
     * @param classModel The class to add.
     * @return True if the class was successfully added, false otherwise.
     */
    public boolean addClass(ClassModel classModel) {
        // TODO: Integrate with Firebase

        boolean res = sqlDb.createClass(classModel);
        if (res) {
            return true;
        }

        // Simulate random result for temporary behavior
        return new Random().nextBoolean();
    }

    /**
     * Updates the details of an existing class in the database.
     *
     * @param classModel The class with updated details.
     * @return True if the class was successfully updated, false otherwise.
     */
    public boolean updateClass(ClassModel classModel) {
        // TODO: Integrate with Firebase

        boolean res = sqlDb.updateClass(classModel);
        if (res) {
            return true;
        }

        // Simulate random result for temporary behavior
        return new Random().nextBoolean();
    }

    public ClassModel getClassModel(int classId) {
        // TODO: Integrate with Firebase

        ClassModel classModel = sqlDb.readClass(classId);
        if (classModel != null) {
            return classModel;
        }

        // Simulate random data for temporary behavior
        return TempDataHelper.getClassModel(classId);
    }

    /**
     * Retrieves all classes from the database.
     *
     * @return A list of all classes.
     */
    public List<ClassModel> getAllClasses() {
        // TODO: Integrate with Firebase

        List<ClassModel> classes = sqlDb.getAllClasses();
        if (classes != null) {
            return classes;
        }

        // Simulate random data for temporary behavior
        return TempDataHelper.getClassesList();
    }

    /**
     * Retrieves classes by the given kindergarten.
     *
     * @param kindergarten The kindergarten to get classes for.
     * @return A list of classes for the given kindergarten.
     */
    public List<ClassModel> getClassesByKindergarten(KindergartenModel kindergarten) {
        // TODO: Integrate with Firebase

        List<ClassModel> list = sqlDb.getClassesByKindergarten(kindergarten.getId());
        if (list != null){
            return list;
        }

        return TempDataHelper.getClassesByKindergarten(kindergarten);
    }

    /**
     * Deletes a kindergarten from the database.
     *
     * @param kindergartenModel The kindergarten to delete.
     * @return True if the kindergarten was successfully deleted, false otherwise.
     */
    public boolean deleteKindergarten(KindergartenModel kindergartenModel) {
        // TODO: Integrate with Firebase

        boolean res = sqlDb.deleteKindergarten(kindergartenModel.getId());
        if (res) {
            return true;
        }

        // Simulate random result for temporary behavior
        return new Random().nextBoolean();
    }

    /**
     * Adds a new kindergarten to the database.
     *
     * @param kindergartenModel The kindergarten to add.
     * @return True if the kindergarten was successfully added, false otherwise.
     */
    public boolean addKindergarten(KindergartenModel kindergartenModel) {
        // TODO: Integrate with Firebase

        boolean res = sqlDb.createKindergarten(kindergartenModel);
        if (res) {
            return true;
        }

        // Simulate random result for temporary behavior
        return new Random().nextBoolean();
    }

    /**
     * Updates the details of an existing kindergarten in the database.
     *
     * @param kindergartenModel The kindergarten with updated details.
     * @return True if the kindergarten was successfully updated, false otherwise.
     */
    public boolean updateKindergarten(KindergartenModel kindergartenModel) {
        // TODO: Integrate with Firebase

        boolean res = sqlDb.updateKindergarten(kindergartenModel);
        if (res) {
            return true;
        }

        // Simulate random result for temporary behavior
        return new Random().nextBoolean();
    }

    public KindergartenModel getKindergarten(int kindergartenId) {
        // TODO: Integrate with Firebase

        KindergartenModel kindergarten = sqlDb.readKindergarten(kindergartenId);
        if (kindergarten != null) {
            return kindergarten;
        }

        // Simulate random data for temporary behavior
        return TempDataHelper.getKindergarten(kindergartenId);
    }

    public KindergartenModel getKindergartenByName(String kindergartenName) {
        KindergartenModel kindergarten = sqlDb.getKindergartenByName(kindergartenName);
        if (kindergarten != null) {
            return kindergarten;
        }

        // Simulate random data for temporary behavior
        return TempDataHelper.getKindergartenByName(kindergartenName);
    }

    /**
     * Retrieves all kindergartens from the database.
     *
     * @return A list of all kindergartens.
     */
    public List<KindergartenModel> getAllKindergartens() {
        // TODO: Integrate with Firebase

        List<KindergartenModel> kindergartens = sqlDb.getAllKindergartens();
        if (kindergartens != null) {
            return kindergartens;
        }

        // Simulate random data for temporary behavior
        return TempDataHelper.getKindergartenList();
    }

    /**
     * Opens the SQL database connection.
     *
     * @param context The context to use for database operations.
     */
    private static void openSqlDatabase(Context context) {
        if (sqlDb == null) {
            sqlDb = new SqlDatabaseHelper(context);
        }
        sqlDb.open();
    }

    /**
     * Closes the SQL database connection if it is open.
     */
    private static void closeSqlDatabase() {
        if (sqlDb != null) {
            sqlDb.close();
        }
    }

    public boolean addChild(ChildModel child) {
        //todo - fix here

        // Simulate random result for temporary behavior
        return new Random().nextBoolean();
    }
}
