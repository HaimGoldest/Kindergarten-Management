package com.example.kindergarten_management.controllers;

import android.content.Context;

import com.example.kindergarten_management.helpers.FirebaseDatabaseHelper;
import com.example.kindergarten_management.helpers.SqlDatabaseHelper;
import com.example.kindergarten_management.helpers.TempDataHelper;
import com.example.kindergarten_management.models.ClassModel;
import com.example.kindergarten_management.models.KindergartenModel;
import com.example.kindergarten_management.models.StaffMemberModel;

import java.util.List;
import java.util.Random;

/**
 * Controller class to manage the database operations.
 */
public class DatabaseController {
    private static DatabaseController instance = null;
    private static FirebaseDatabaseHelper firebaseDb = null;
    private static SqlDatabaseHelper sqlDb = null;
    private static Context context = null;

    private DatabaseController() {
    }

    /**
     * Returns the singleton instance of the DatabaseController.
     * Closes the existing database connection if any, before returning the instance.
     *
     * @return The singleton instance of DatabaseController.
     */
    public static DatabaseController getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseController();
        }

        closeSqlDatabase();
        openSqlDatabase(context);
        return instance;
    }

    public boolean deleteStaffMember(StaffMemberModel staffMember) {
        // todo - Firebase

        boolean res = sqlDb.deleteStaffMember(staffMember.getId());
        if(res) {
            return true;
        }

        Random random = new Random();
        int num = random.nextBoolean() ? 1 : 0;
        if(num==1)
            return true;

        return false;



    }

    public boolean addStaffMember(StaffMemberModel staffMember) {
        // todo - Firebase

        boolean res = sqlDb.createStaffMember(staffMember);
        if(res) {
            return true;
        }

        Random random = new Random();
        int num = random.nextBoolean() ? 1 : 0;
        if(num==1)
            return true;

        return false;
    }

    public boolean updateStaffMember(StaffMemberModel staffMember) {
        // todo - Firebase

        boolean res = sqlDb.updateStaffMember(staffMember);
        if(res) {
            return true;
        }

        Random random = new Random();
        int num = random.nextBoolean() ? 1 : 0;
        if(num==1)
            return true;

        return false;
    }

    public List<ClassModel> getAllClasses() {
        // todo - Firebase

        List<ClassModel> list = sqlDb.getAllClasses();
        if (list != null){
            return list;
        }

        return TempDataHelper.getClassesList();
    }

    public List<ClassModel> getClassesByKindergarten(KindergartenModel kindergarten) {
        // todo - Firebase

        List<ClassModel> list = sqlDb.getClassesByKindergarten(kindergarten.getId());
        if (list != null){
            return list;
        }

        return TempDataHelper.getClassesByKindergarten(kindergarten);
    }

    public List<KindergartenModel> getAllKindergartens() {
        // todo - Firebase

        List<KindergartenModel> list = sqlDb.getAllKindergartens();
        if (list != null){
            return list;
        }

        return TempDataHelper.getKindergartenList();
    }

    public List<StaffMemberModel> getAllStaffMembers() {
        // todo - Firebase

        List<StaffMemberModel> list = sqlDb.getAllStaffMembers();
        if (list != null){
            return list;
        }

        return TempDataHelper.getStaffList();
    }

    /**
     * Opens a connection to the SQL database with the provided context.
     *
     * @param context The context to use for opening the database.
     */
    private static void openSqlDatabase(Context context) {
        if (context != null) {
            sqlDb = new SqlDatabaseHelper(context);
            sqlDb.open();
        }
    }

    /**
     * Closes the current SQL database connection if any.
     */
    private static void closeSqlDatabase() {
        if (sqlDb != null) {
            sqlDb.close();
            sqlDb = null;
        }
    }


}
