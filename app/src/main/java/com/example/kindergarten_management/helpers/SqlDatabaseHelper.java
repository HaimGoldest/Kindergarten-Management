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
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "KindergartenManagementDb";
    private SQLiteDatabase db;

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

    public SqlDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        String CREATE_STAFF_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_STAFF_NAME + " ("
                + STAFF_COLUMN_ID + " INTEGER PRIMARY KEY, "
                + STAFF_COLUMN_NAME + " TEXT, "
                + STAFF_COLUMN_RULE + " TEXT, "
                + STAFF_COLUMN_START_WORKING_DATE + " TEXT, "
                + STAFF_COLUMN_ASSIGNED_KIND_ID + " INTEGER, "
                + STAFF_COLUMN_ASSIGNED_CLASS_ID + " INTEGER)";
        db.execSQL(CREATE_STAFF_TABLE);

        String CREATE_KINDERGARTEN_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_KINDERGARTEN_NAME + " ("
                + KINDERGARTEN_COLUMN_ID + " INTEGER PRIMARY KEY, "
                + KINDERGARTEN_COLUMN_NAME + " TEXT, "
                + KINDERGARTEN_COLUMN_ADDRESS + " TEXT, "
                + KINDERGARTEN_COLUMN_CITY + " TEXT, "
                + KINDERGARTEN_COLUMN_PHONE + " TEXT, "
                + KINDERGARTEN_COLUMN_OPENING_TIME + " TEXT, "
                + KINDERGARTEN_COLUMN_CLOSING_TIME + " TEXT, "
                + KINDERGARTEN_COLUMN_AFFILIATION + " TEXT)";
        db.execSQL(CREATE_KINDERGARTEN_TABLE);

        String CREATE_CLASS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_CLASS_NAME + " ("
                + CLASS_COLUMN_ID + " INTEGER PRIMARY KEY, "
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

    public boolean createStaffMember(StaffMemberModel staffMember) {
        ContentValues values = new ContentValues();
        values.put(STAFF_COLUMN_ID, staffMember.getId());
        values.put(STAFF_COLUMN_NAME, staffMember.getName());
        values.put(STAFF_COLUMN_RULE, staffMember.getRule());
        values.put(STAFF_COLUMN_START_WORKING_DATE, staffMember.getStartWorkingDate());
        values.put(STAFF_COLUMN_ASSIGNED_KIND_ID, staffMember.getAssignedKindergarten().getId());
        values.put(STAFF_COLUMN_ASSIGNED_CLASS_ID, staffMember.getAssignedClass().getId());

        long result = db.insert(TABLE_STAFF_NAME, null, values);
        return result != -1;
    }

    public StaffMemberModel readStaffMember(int staffMemberId) {
        String query = "SELECT * FROM " + TABLE_STAFF_NAME + " WHERE " + STAFF_COLUMN_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(staffMemberId)});

        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(STAFF_COLUMN_ID));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(STAFF_COLUMN_NAME));
            String rule = cursor.getString(cursor.getColumnIndexOrThrow(STAFF_COLUMN_RULE));
            String startWorkingDate = cursor.getString(cursor.getColumnIndexOrThrow(STAFF_COLUMN_START_WORKING_DATE));
            int assignedKindergartenId = cursor.getInt(cursor.getColumnIndexOrThrow(STAFF_COLUMN_ASSIGNED_KIND_ID));
            int assignedClassId = cursor.getInt(cursor.getColumnIndexOrThrow(STAFF_COLUMN_ASSIGNED_CLASS_ID));

            StaffMemberModel staffMember = new StaffMemberModel(id, name, rule, startWorkingDate, assignedKindergartenId, assignedClassId);

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
        values.put(STAFF_COLUMN_ASSIGNED_KIND_ID, staffMember.getAssignedKindergarten().getId());
        values.put(STAFF_COLUMN_ASSIGNED_CLASS_ID, staffMember.getAssignedClass().getId());

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
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(STAFF_COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(STAFF_COLUMN_NAME));
                String rule = cursor.getString(cursor.getColumnIndexOrThrow(STAFF_COLUMN_RULE));
                String startWorkingDate = cursor.getString(cursor.getColumnIndexOrThrow(STAFF_COLUMN_START_WORKING_DATE));
                int assignedKindergartenId = cursor.getInt(cursor.getColumnIndexOrThrow(STAFF_COLUMN_ASSIGNED_KIND_ID));
                int assignedClassId = cursor.getInt(cursor.getColumnIndexOrThrow(STAFF_COLUMN_ASSIGNED_CLASS_ID));

                StaffMemberModel staffMember = new StaffMemberModel(id, name, rule, startWorkingDate, assignedKindergartenId, assignedClassId);

                staffMembers.add(staffMember);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return staffMembers;
    }


    public boolean createClass(ClassModel classModel) {
        ContentValues values = new ContentValues();
        values.put(CLASS_COLUMN_ID, classModel.getId());
        values.put(CLASS_COLUMN_TYPE, classModel.getType());
        values.put(CLASS_COLUMN_MAX_CHILDREN, classModel.getMaxChildren());
        values.put(CLASS_COLUMN_MIN_CHILDREN, classModel.getMinChildren());
        values.put(CLASS_COLUMN_MAX_AGE, classModel.getMaxAge());
        values.put(CLASS_COLUMN_MIN_AGE, classModel.getMinAge());
        values.put(CLASS_COLUMN_KIND_ID, classModel.getKindergarten().getId());

        long result = db.insert(TABLE_CLASS_NAME, null, values);
        return result != -1;
    }
    //Todo use the other constructor with all the parameters
    public ClassModel readClass(int classId) {
        String query = "SELECT * FROM " + TABLE_CLASS_NAME + " WHERE " + CLASS_COLUMN_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(classId)});

        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(CLASS_COLUMN_ID));
            String type = cursor.getString(cursor.getColumnIndexOrThrow(CLASS_COLUMN_TYPE));
            int maxChildren = cursor.getInt(cursor.getColumnIndexOrThrow(CLASS_COLUMN_MAX_CHILDREN));
            int maxAge = cursor.getInt(cursor.getColumnIndexOrThrow(CLASS_COLUMN_MAX_AGE));
            int minAge = cursor.getInt(cursor.getColumnIndexOrThrow(CLASS_COLUMN_MIN_AGE));
            int kindergartenId = cursor.getInt(cursor.getColumnIndexOrThrow(CLASS_COLUMN_KIND_ID));

            ClassModel classModel = new ClassModel(id, type, maxChildren, maxAge, minAge, kindergartenId);

            cursor.close();
            return classModel;
        } else {
            return null;
        }
    }

    public boolean updateClass(ClassModel classModel) {
        ContentValues values = new ContentValues();
        values.put(CLASS_COLUMN_TYPE, classModel.getType());
        values.put(CLASS_COLUMN_MAX_CHILDREN, classModel.getMaxChildren());
        values.put(CLASS_COLUMN_MIN_CHILDREN, classModel.getMinChildren());
        values.put(CLASS_COLUMN_MAX_AGE, classModel.getMaxAge());
        values.put(CLASS_COLUMN_MIN_AGE, classModel.getMinAge());
        values.put(CLASS_COLUMN_KIND_ID, classModel.getKindergarten().getId());

        int result = db.update(TABLE_CLASS_NAME, values, CLASS_COLUMN_ID + " = ?",
                new String[]{String.valueOf(classModel.getId())});
        return result > 0;
    }

    public boolean deleteClass(int classId) {
        int result = db.delete(TABLE_CLASS_NAME, CLASS_COLUMN_ID + " = ?",
                new String[]{String.valueOf(classId)});
        return result > 0;
    }

    public List<ClassModel> getAllClasses() {
        List<ClassModel> classes = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_CLASS_NAME;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(CLASS_COLUMN_ID));
                String type = cursor.getString(cursor.getColumnIndexOrThrow(CLASS_COLUMN_TYPE));
                int maxChildren = cursor.getInt(cursor.getColumnIndexOrThrow(CLASS_COLUMN_MAX_CHILDREN));
                int maxAge = cursor.getInt(cursor.getColumnIndexOrThrow(CLASS_COLUMN_MAX_AGE));
                int minAge = cursor.getInt(cursor.getColumnIndexOrThrow(CLASS_COLUMN_MIN_AGE));
                int kindergartenId = cursor.getInt(cursor.getColumnIndexOrThrow(CLASS_COLUMN_KIND_ID));

                ClassModel classModel = new ClassModel(id, type, maxChildren, maxAge, minAge, kindergartenId);

                classes.add(classModel);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return classes;
    }


    public List<ClassModel> getClassesByKindergarten(int kindergartenId) {
        List<ClassModel> classList = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_CLASS_NAME + " WHERE " + CLASS_COLUMN_KIND_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(kindergartenId)});

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(CLASS_COLUMN_ID));
                String type = cursor.getString(cursor.getColumnIndexOrThrow(CLASS_COLUMN_TYPE));
                int maxChildren = cursor.getInt(cursor.getColumnIndexOrThrow(CLASS_COLUMN_MAX_CHILDREN));
                int maxAge = cursor.getInt(cursor.getColumnIndexOrThrow(CLASS_COLUMN_MAX_AGE));
                int minAge = cursor.getInt(cursor.getColumnIndexOrThrow(CLASS_COLUMN_MIN_AGE));

                ClassModel classModel = new ClassModel(id, type, maxChildren, maxAge, minAge, kindergartenId);

                classList.add(classModel);
            } while (cursor.moveToNext());
            cursor.close();
        }

        return classList;
    }


    public boolean createKindergarten(KindergartenModel kindergarten) {
        ContentValues values = new ContentValues();
        values.put(KINDERGARTEN_COLUMN_ID, kindergarten.getId());
        values.put(KINDERGARTEN_COLUMN_NAME, kindergarten.getName());
        values.put(KINDERGARTEN_COLUMN_ADDRESS, kindergarten.getAddress());
        values.put(KINDERGARTEN_COLUMN_CITY, kindergarten.getCityName());
        values.put(KINDERGARTEN_COLUMN_PHONE, kindergarten.getPhoneNumber());
        values.put(KINDERGARTEN_COLUMN_OPENING_TIME, kindergarten.getOpeningTime());
        values.put(KINDERGARTEN_COLUMN_CLOSING_TIME, kindergarten.getClosingTime());
        values.put(KINDERGARTEN_COLUMN_AFFILIATION, kindergarten.getOrganizationalAffiliation());

        long result = db.insert(TABLE_KINDERGARTEN_NAME, null, values);
        return result != -1;
    }

    public KindergartenModel readKindergarten(int kindergartenId) {
        String query = "SELECT * FROM " + TABLE_KINDERGARTEN_NAME + " WHERE " + KINDERGARTEN_COLUMN_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(kindergartenId)});
        if (cursor != null && cursor.moveToFirst()) {
            // todo - kfir to fix - dont use the getContext() Constructor
            KindergartenModel kindergarten = new KindergartenModel(getContext());
            kindergarten.setId(cursor.getInt(cursor.getColumnIndexOrThrow(KINDERGARTEN_COLUMN_ID)));
            kindergarten.setName(cursor.getString(cursor.getColumnIndexOrThrow(KINDERGARTEN_COLUMN_NAME)));
            kindergarten.setAddress(cursor.getString(cursor.getColumnIndexOrThrow(KINDERGARTEN_COLUMN_ADDRESS)));
            kindergarten.setCityName(cursor.getString(cursor.getColumnIndexOrThrow(KINDERGARTEN_COLUMN_CITY)));
            kindergarten.setPhoneNumber(cursor.getString(cursor.getColumnIndexOrThrow(KINDERGARTEN_COLUMN_PHONE)));
            kindergarten.setOpeningTime(cursor.getString(cursor.getColumnIndexOrThrow(KINDERGARTEN_COLUMN_OPENING_TIME)));
            kindergarten.setClosingTime(cursor.getString(cursor.getColumnIndexOrThrow(KINDERGARTEN_COLUMN_CLOSING_TIME)));
            kindergarten.setOrganizationalAffiliation(cursor.getString(cursor.getColumnIndexOrThrow(KINDERGARTEN_COLUMN_AFFILIATION)));
            cursor.close();
            return kindergarten;
        } else {
            return null;
        }
    }

    public boolean updateKindergarten(KindergartenModel kindergarten) {
        ContentValues values = new ContentValues();
        values.put(KINDERGARTEN_COLUMN_NAME, kindergarten.getName());
        values.put(KINDERGARTEN_COLUMN_ADDRESS, kindergarten.getAddress());
        values.put(KINDERGARTEN_COLUMN_CITY, kindergarten.getCityName());
        values.put(KINDERGARTEN_COLUMN_PHONE, kindergarten.getPhoneNumber());
        values.put(KINDERGARTEN_COLUMN_OPENING_TIME, kindergarten.getOpeningTime());
        values.put(KINDERGARTEN_COLUMN_CLOSING_TIME, kindergarten.getClosingTime());
        values.put(KINDERGARTEN_COLUMN_AFFILIATION, kindergarten.getOrganizationalAffiliation());

        int result = db.update(TABLE_KINDERGARTEN_NAME, values, KINDERGARTEN_COLUMN_ID + " = ?",
                new String[]{String.valueOf(kindergarten.getId())});
        return result > 0;
    }

    public boolean deleteKindergarten(int kindergartenId) {
        int result = db.delete(TABLE_KINDERGARTEN_NAME, KINDERGARTEN_COLUMN_ID + " = ?",
                new String[]{String.valueOf(kindergartenId)});
        return result > 0;
    }

    public List<KindergartenModel> getAllKindergartens() {
        List<KindergartenModel> kindergartens = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_KINDERGARTEN_NAME;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                // todo - kfir to fix - dont use the getContext() Constructor
                KindergartenModel kindergarten = new KindergartenModel(getContext());
                kindergarten.setId(cursor.getInt(cursor.getColumnIndexOrThrow(KINDERGARTEN_COLUMN_ID)));
                kindergarten.setName(cursor.getString(cursor.getColumnIndexOrThrow(KINDERGARTEN_COLUMN_NAME)));
                kindergarten.setAddress(cursor.getString(cursor.getColumnIndexOrThrow(KINDERGARTEN_COLUMN_ADDRESS)));
                kindergarten.setCityName(cursor.getString(cursor.getColumnIndexOrThrow(KINDERGARTEN_COLUMN_CITY)));
                kindergarten.setPhoneNumber(cursor.getString(cursor.getColumnIndexOrThrow(KINDERGARTEN_COLUMN_PHONE)));
                kindergarten.setOpeningTime(cursor.getString(cursor.getColumnIndexOrThrow(KINDERGARTEN_COLUMN_OPENING_TIME)));
                kindergarten.setClosingTime(cursor.getString(cursor.getColumnIndexOrThrow(KINDERGARTEN_COLUMN_CLOSING_TIME)));
                kindergarten.setOrganizationalAffiliation(cursor.getString(cursor.getColumnIndexOrThrow(KINDERGARTEN_COLUMN_AFFILIATION)));
                kindergartens.add(kindergarten);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return kindergartens;
    }

    public KindergartenModel getKindergartenByName(String name) {
        String query = "SELECT * FROM " + TABLE_KINDERGARTEN_NAME + " WHERE " + KINDERGARTEN_COLUMN_NAME + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{name});

        if (cursor != null && cursor.moveToFirst()) {
            // todo - kfir to fix - dont use the getContext() Constructor
            KindergartenModel kindergarten = new KindergartenModel(getContext());
            kindergarten.setId(cursor.getInt(cursor.getColumnIndexOrThrow(KINDERGARTEN_COLUMN_ID)));
            kindergarten.setName(cursor.getString(cursor.getColumnIndexOrThrow(KINDERGARTEN_COLUMN_NAME)));
            kindergarten.setAddress(cursor.getString(cursor.getColumnIndexOrThrow(KINDERGARTEN_COLUMN_ADDRESS)));
            kindergarten.setCityName(cursor.getString(cursor.getColumnIndexOrThrow(KINDERGARTEN_COLUMN_CITY)));
            kindergarten.setPhoneNumber(cursor.getString(cursor.getColumnIndexOrThrow(KINDERGARTEN_COLUMN_PHONE)));
            kindergarten.setOpeningTime(cursor.getString(cursor.getColumnIndexOrThrow(KINDERGARTEN_COLUMN_OPENING_TIME)));
            kindergarten.setClosingTime(cursor.getString(cursor.getColumnIndexOrThrow(KINDERGARTEN_COLUMN_CLOSING_TIME)));
            kindergarten.setOrganizationalAffiliation(cursor.getString(cursor.getColumnIndexOrThrow(KINDERGARTEN_COLUMN_AFFILIATION)));
            cursor.close();
            return kindergarten;
        } else {
            if (cursor != null) {
                cursor.close();
            }
            return null;
        }
    }

}