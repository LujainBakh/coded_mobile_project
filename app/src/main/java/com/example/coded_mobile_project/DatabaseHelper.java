package com.example.coded_mobile_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "user.db";
    private static final int DATABASE_VERSION = 1;

    // Table Name
    public static final String TABLE_NAME = "users";

    // Column Names
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_FIRST_NAME = "first_name";
    public static final String COLUMN_LAST_NAME = "last_name";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_UNIVERSITY = "university";
    public static final String COLUMN_COLLEGE = "college";

    // SQL statement to create the table
    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_FIRST_NAME + " TEXT, " +
                    COLUMN_LAST_NAME + " TEXT, " +
                    COLUMN_EMAIL + " TEXT, " +
                    COLUMN_PASSWORD + " TEXT, " +
                    COLUMN_PHONE + " TEXT, " +
                    COLUMN_UNIVERSITY + " TEXT, " +
                    COLUMN_COLLEGE + " TEXT);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            Log.d("DatabaseHelper", "Creating database and inserting dummy users");
            db.execSQL(TABLE_CREATE);
            insertDummyUsers(db); // Insert dummy users during database creation
        } catch (SQLException e) {
            Log.e("DatabaseHelper", "Error creating database", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            Log.d("DatabaseHelper", "Upgrading database");
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        } catch (SQLException e) {
            Log.e("DatabaseHelper", "Error upgrading database", e);
        }
    }



    private void insertDummyUsers(SQLiteDatabase db) {
        db.beginTransaction();
        try {
            // Add Jood Hussain
            insertUser(db, "Jood", "Hussain", "jood@gmail.com", "jood1234", "0501266130", "IAU", "CCSIT");

            // Add Lujain Bakhurji
            insertUser(db, "Lujain", "Bakhurji", "lujain@gmail.com", "lujain1234", "0501244789", "IAU", "CCSIT");

            // More dummy users can be added here

            db.setTransactionSuccessful();
            Log.d("DatabaseHelper", "Dummy users inserted successfully");
        } catch (SQLException e) {
            Log.e("DatabaseHelper", "Error inserting dummy users", e);
        } finally {
            db.endTransaction();
        }
    }

    private void insertUser(SQLiteDatabase db, String firstName, String lastName, String email, String password, String phone, String university, String college) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_FIRST_NAME, firstName);
        values.put(COLUMN_LAST_NAME, lastName);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_PHONE, phone);
        values.put(COLUMN_UNIVERSITY, university);
        values.put(COLUMN_COLLEGE, college);

        try {
            long result = db.insert(TABLE_NAME, null, values);
            if (result == -1) {
                Log.e("DatabaseHelper", "Error inserting user: " + firstName + " " + lastName);
            } else {
                Log.d("DatabaseHelper", "User inserted: " + firstName + " " + lastName + " " + email + " " + password);
            }
        } catch (SQLException e) {
            Log.e("DatabaseHelper", "Error inserting user: " + firstName + " " + lastName, e);
        }
    }


    // Method to add a user with additional attributes (can be called by UserDao)
    public long addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_FIRST_NAME, user.getFirstName());
        values.put(COLUMN_LAST_NAME, user.getLastName());
        values.put(COLUMN_EMAIL, user.getEmail());
        values.put(COLUMN_PASSWORD, user.getPassword());
        values.put(COLUMN_PHONE, user.getPhone());
        values.put(COLUMN_UNIVERSITY, user.getUniversity());
        values.put(COLUMN_COLLEGE, user.getCollege());

        long result = -1;
        try {
            result = db.insert(TABLE_NAME, null, values);
            if (result == -1) {
                Log.e("DatabaseHelper", "Error inserting user");
            } else {
                Log.d("DatabaseHelper", "User inserted: " + user.getFirstName() + " " + user.getLastName());
            }
        } catch (SQLException e) {
            Log.e("DatabaseHelper", "Error inserting user", e);
        } finally {
            db.close();
        }
        return result;
    }
}
