package com.example.kindergarten_management.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.kindergarten_management.models.StaffMemberModel;

import java.util.List;

/**
 * Class for managing the Lite SQL database.
 */
public class SqlDatabaseHelper extends SQLiteOpenHelper {
    private SQLiteDatabase db = null;
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "KindergartenManagementDb";
    private static Context context;

    /**
     * Constructor.
     */
    public SqlDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * Opens the database for writing.
     */
    public void open() {
        try {
            db = getWritableDatabase();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    /**
     * Closes the database.
     */
    public void close() {
        try {
            db.close();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public boolean deleteStaffMember(int staffMemberId) {
        // todo - kfir
        return false;
    }

    public boolean createStaffMember(StaffMemberModel staffMember) {
        // todo - kfir
        return false;
    }

    public StaffMemberModel readStaffMember(int staffMemberId) {
        // todo - kfir
        return null;
    }

    public boolean updateStaffMember(StaffMemberModel staffMember) {
        // todo - kfir
        return false;
    }

    public List<StaffMemberModel> getAllStaffMembers() {
        // todo - kfir
        return null;
    }
}
