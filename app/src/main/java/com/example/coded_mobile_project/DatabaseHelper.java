package com.example.coded_mobile_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database and Table Details
    private static final String DATABASE_NAME = "user.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "users";

    // Column Names
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_FIRST_NAME = "first_name";
    public static final String COLUMN_LAST_NAME = "last_name";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_UNIVERSITY = "university";
    public static final String COLUMN_COLLEGE = "college";

    // SQL Query to Create Table
    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_FIRST_NAME + " TEXT, " +
                    COLUMN_LAST_NAME + " TEXT, " +
                    COLUMN_EMAIL + " TEXT UNIQUE, " + // UNIQUE to ensure no duplicate emails
                    COLUMN_PASSWORD + " TEXT, " +
                    COLUMN_PHONE + " TEXT, " +
                    COLUMN_UNIVERSITY + " TEXT, " +
                    COLUMN_COLLEGE + " TEXT);";

    // Constructor
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Insert User Data
    public boolean insertData(String firstName, String lastName, String email, String password,
                              String phone, String university, String college) {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_FIRST_NAME, firstName);
            cv.put(COLUMN_LAST_NAME, lastName);
            cv.put(COLUMN_EMAIL, email);
            cv.put(COLUMN_PASSWORD, password);
            cv.put(COLUMN_PHONE, phone);
            cv.put(COLUMN_UNIVERSITY, university);
            cv.put(COLUMN_COLLEGE, college);

            long result = db.insert(TABLE_NAME, null, cv);
            return result != -1;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (db != null) db.close();
        }
    }

    // Check if Email Exists
    public boolean checkEmail(String email) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = this.getReadableDatabase();
            cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_EMAIL + " = ?", new String[]{email});
            return cursor.getCount() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (cursor != null) cursor.close();
            if (db != null) db.close();
        }
    }

    // Check Email and Password Combination
    public boolean checkEmailPassword(String email, String password) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = this.getReadableDatabase();
            cursor = db.rawQuery(
                    "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_EMAIL + " = ? AND " + COLUMN_PASSWORD + " = ?",
                    new String[]{email, password}
            );
            return cursor.getCount() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (cursor != null) cursor.close();
            if (db != null) db.close();
        }
    }

    // Fetch User Profile by Email
    public Cursor getUserProfile(String email) {
        SQLiteDatabase db = null;
        try {
            db = this.getReadableDatabase();
            return db.rawQuery(
                    "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_EMAIL + " = ?",
                    new String[]{email}
            );
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Update User Profile
    public boolean updateUserProfile(String email, String firstName, String lastName,
                                     String phone, String university, String college) {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_FIRST_NAME, firstName);
            cv.put(COLUMN_LAST_NAME, lastName);
            cv.put(COLUMN_PHONE, phone);
            cv.put(COLUMN_UNIVERSITY, university);
            cv.put(COLUMN_COLLEGE, college);

            int rowsAffected = db.update(TABLE_NAME, cv, COLUMN_EMAIL + " = ?", new String[]{email});
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (db != null) db.close();
        }
    }
}
