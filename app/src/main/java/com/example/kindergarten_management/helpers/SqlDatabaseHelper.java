package com.example.kindergarten_management.helpers;

import static com.example.kindergarten_management.models.BaseModel.getContext;

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
        return false;
    }

    public StaffMemberModel readStaffMember(int staffMemberId) {
        return null;
    }

    public boolean updateStaffMember(StaffMemberModel staffMember) {
        return false;
    }

    public boolean deleteStaffMember(int staffMemberId) {
        return false;
    }

    public List<StaffMemberModel> getAllStaffMembers() {
       return null;
    }

    // CRUD operations for ClassModel

    public boolean createClass(ClassModel classModel) {
        return false;
    }

    public ClassModel readClass(int classId) {
       return null;
    }

    public boolean updateClass(ClassModel classModel) {
        return false;
    }



    public boolean deleteClass(int classId) {
        return false;
    }

    public List<ClassModel> getAllClasses() {

        return null;
    }

    public List<ClassModel> getClassesByKindergarten(int kindergartenId) {

        return null;
    }

    public boolean createKindergarten(KindergartenModel kindergarten) {

        return false;
    }

    public KindergartenModel readKindergarten(int kindergartenId) {

            return null;

    }

    public boolean updateKindergarten(KindergartenModel kindergarten) {

        return false;
    }

    public boolean deleteKindergarten(int kindergartenId) {
        return false;
    }

    public List<KindergartenModel> getAllKindergartens() {
        return null;
    }

    public KindergartenModel getKindergartenByName(String name) {
        return null;
    }

}
