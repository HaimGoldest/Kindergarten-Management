package com.example.kindergarten_management.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.kindergarten_management.models.ClassModel;
import com.example.kindergarten_management.models.KindergartenModel;
import com.example.kindergarten_management.models.StaffMemberModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for managing the SQLite database.
 */
public class SqlDatabaseHelper extends SQLiteOpenHelper {
    private static final String TABLE_STAFF_NAME = "Staff";
    private static final String STAFF_COLUMN_ID = "id";
    private static final String STAFF_COLUMN_NAME = "name";
    private static final String STAFF_COLUMN_RULE = "rule";
    private static final String STAFF_COLUMN_START_WORKING_DATE = "startWorkingDate";
    private static final String STAFF_COLUMN_ASSIGNED_KIND_ID = "assignedKindergartenId";
    private static final String STAFF_COLUMN_ASSIGNED_CLASS_ID = "assignedClassId";

    private static final String TABLE_KINDERGARTEN_NAME = "Kindergarten";
    private static final String KINDERGARTEN_COLUMN_ID = "id";
    private static final String KINDERGARTEN_COLUMN_NAME = "name";
    private static final String KINDERGARTEN_COLUMN_ADDRESS = "address";
    private static final String KINDERGARTEN_COLUMN_CITY = "cityName";
    private static final String KINDERGARTEN_COLUMN_PHONE = "phoneNumber";
    private static final String KINDERGARTEN_COLUMN_OPENING_TIME = "openingTime";
    private static final String KINDERGARTEN_COLUMN_CLOSING_TIME = "closingTime";
    private static final String KINDERGARTEN_COLUMN_AFFILIATION = "organizationalAffiliation";

    private static final String TABLE_CLASS_NAME = "Class";
    private static final String CLASS_COLUMN_ID = "id";
    private static final String CLASS_COLUMN_TYPE = "type";
    private static final String CLASS_COLUMN_MAX_CHILDREN = "maxChildren";
    private static final String CLASS_COLUMN_MIN_CHILDREN = "minChildren";
    private static final String CLASS_COLUMN_MAX_AGE = "maxAge";
    private static final String CLASS_COLUMN_MIN_AGE = "minAge";
    private static final String CLASS_COLUMN_KIND_ID = "kindergartenId";

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "KindergartenManagementDb";
    private SQLiteDatabase db;

    public SqlDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        // Create Staff table
        String CREATE_STAFF_TABLE = "CREATE TABLE " + TABLE_STAFF_NAME + " ("
                + STAFF_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + STAFF_COLUMN_NAME + " TEXT, "
                + STAFF_COLUMN_RULE + " TEXT, "
                + STAFF_COLUMN_START_WORKING_DATE + " TEXT, "
                + STAFF_COLUMN_ASSIGNED_KIND_ID + " INTEGER, "
                + STAFF_COLUMN_ASSIGNED_CLASS_ID + " INTEGER)";
        db.execSQL(CREATE_STAFF_TABLE);

        // Create Kindergarten table
        String CREATE_KINDERGARTEN_TABLE = "CREATE TABLE " + TABLE_KINDERGARTEN_NAME + " ("
                + KINDERGARTEN_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KINDERGARTEN_COLUMN_NAME + " TEXT, "
                + KINDERGARTEN_COLUMN_ADDRESS + " TEXT, "
                + KINDERGARTEN_COLUMN_CITY + " TEXT, "
                + KINDERGARTEN_COLUMN_PHONE + " TEXT, "
                + KINDERGARTEN_COLUMN_OPENING_TIME + " TEXT, "
                + KINDERGARTEN_COLUMN_CLOSING_TIME + " TEXT, "
                + KINDERGARTEN_COLUMN_AFFILIATION + " TEXT)";
        db.execSQL(CREATE_KINDERGARTEN_TABLE);

        // Create Class table
        String CREATE_CLASS_TABLE = "CREATE TABLE " + TABLE_CLASS_NAME + " ("
                + CLASS_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + CLASS_COLUMN_TYPE + " TEXT, "
                + CLASS_COLUMN_MAX_CHILDREN + " INTEGER, "
                + CLASS_COLUMN_MIN_CHILDREN + " INTEGER, "
                + CLASS_COLUMN_MAX_AGE + " INTEGER, "
                + CLASS_COLUMN_MIN_AGE + " INTEGER, "
                + CLASS_COLUMN_KIND_ID + " INTEGER)";
        db.execSQL(CREATE_CLASS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STAFF_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_KINDERGARTEN_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLASS_NAME);
        onCreate(db);
    }

    public void open() {
        db = getWritableDatabase();
    }

    public void close() {
        if (db != null && db.isOpen()) {
            db.close();
        }
    }

    // CRUD operations for StaffMemberModel

    public boolean createStaffMember(StaffMemberModel staffMember) {
        ContentValues values = new ContentValues();
        values.put(STAFF_COLUMN_NAME, staffMember.getName());
        values.put(STAFF_COLUMN_RULE, staffMember.getRule());
        values.put(STAFF_COLUMN_START_WORKING_DATE, staffMember.getStartWorkingDate());
        values.put(STAFF_COLUMN_ASSIGNED_KIND_ID, staffMember.getAssignedKindergartenId());
        values.put(STAFF_COLUMN_ASSIGNED_CLASS_ID, staffMember.getAssignedClassId());

        long result = db.insert(TABLE_STAFF_NAME, null, values);
        return result != -1;
    }

    public StaffMemberModel readStaffMember(int staffMemberId) {
        String query = "SELECT * FROM " + TABLE_STAFF_NAME + " WHERE " + STAFF_COLUMN_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(staffMemberId)});
        if (cursor != null && cursor.moveToFirst()) {
            StaffMemberModel staffMember = new StaffMemberModel();
            staffMember.setId(cursor.getInt(cursor.getColumnIndexOrThrow(STAFF_COLUMN_ID)));
            staffMember.setName(cursor.getString(cursor.getColumnIndexOrThrow(STAFF_COLUMN_NAME)));
            staffMember.setRule(cursor.getString(cursor.getColumnIndexOrThrow(STAFF_COLUMN_RULE)));
            staffMember.setStartWorkingDate(cursor.getString(cursor.getColumnIndexOrThrow(STAFF_COLUMN_START_WORKING_DATE)));
            staffMember.setAssignedKindergartenId(cursor.getInt(cursor.getColumnIndexOrThrow(STAFF_COLUMN_ASSIGNED_KIND_ID)));
            staffMember.setAssignedClassId(cursor.getInt(cursor.getColumnIndexOrThrow(STAFF_COLUMN_ASSIGNED_CLASS_ID)));
            cursor.close();
            return staffMember;
        } else {
            return null;
        }
    }

    public boolean updateStaffMember(StaffMemberModel staffMember) {
        ContentValues values = new ContentValues();
        values.put(STAFF_COLUMN_NAME, staffMember.getName());
        values.put(STAFF_COLUMN_RULE, staffMember.getRule());
        values.put(STAFF_COLUMN_START_WORKING_DATE, staffMember.getStartWorkingDate());
        values.put(STAFF_COLUMN_ASSIGNED_KIND_ID, staffMember.getAssignedKindergartenId());
        values.put(STAFF_COLUMN_ASSIGNED_CLASS_ID, staffMember.getAssignedClassId());

        int result = db.update(TABLE_STAFF_NAME, values, STAFF_COLUMN_ID + " = ?",
                new String[]{String.valueOf(staffMember.getId())});
        return result > 0;
    }

    public boolean deleteStaffMember(int staffMemberId) {
        int result = db.delete(TABLE_STAFF_NAME, STAFF_COLUMN_ID + " = ?",
                new String[]{String.valueOf(staffMemberId)});
        return result > 0;
    }

    public List<StaffMemberModel> getAllStaffMembers() {
        List<StaffMemberModel> staffMembers = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_STAFF_NAME;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                StaffMemberModel staffMember = new StaffMemberModel();
                staffMember.setId(cursor.getInt(cursor.getColumnIndexOrThrow(STAFF_COLUMN_ID)));
                staffMember.setName(cursor.getString(cursor.getColumnIndexOrThrow(STAFF_COLUMN_NAME)));
                staffMember.setRule(cursor.getString(cursor.getColumnIndexOrThrow(STAFF_COLUMN_RULE)));
                staffMember.setStartWorkingDate(cursor.getString(cursor.getColumnIndexOrThrow(STAFF_COLUMN_START_WORKING_DATE)));
                staffMember.setAssignedKindergartenId(cursor.getInt(cursor.getColumnIndexOrThrow(STAFF_COLUMN_ASSIGNED_KIND_ID)));
                staffMember.setAssignedClassId(cursor.getInt(cursor.getColumnIndexOrThrow(STAFF_COLUMN_ASSIGNED_CLASS_ID)));
                staffMembers.add(staffMember);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return staffMembers;
    }

    // Similar CRUD operations for KindergartenModel and ClassModel

    // Implement getAllKindergartens(), getAllClasses(), getClassesByKindergarten(int kindergartenId), createClass(ClassModel classModel), etc.
}
