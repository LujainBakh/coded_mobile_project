package com.example.coded_mobile_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class UserDao {

    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    public UserDao(Context context) {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    // Method to add a user with additional attributes
    public long addUser(String firstName, String lastName, String email, String password, String phone, String university, String college) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_FIRST_NAME, firstName);
        values.put(DatabaseHelper.COLUMN_LAST_NAME, lastName);
        values.put(DatabaseHelper.COLUMN_EMAIL, email);
        values.put(DatabaseHelper.COLUMN_PASSWORD, password);
        values.put(DatabaseHelper.COLUMN_PHONE, phone);
        values.put(DatabaseHelper.COLUMN_UNIVERSITY, university);
        values.put(DatabaseHelper.COLUMN_COLLEGE, college);
        return database.insert(DatabaseHelper.TABLE_NAME, null, values);
    }

    // Method to retrieve a user by email
    public Cursor getUserByEmail(String email) {
        String[] columns = { DatabaseHelper.COLUMN_ID, DatabaseHelper.COLUMN_FIRST_NAME, DatabaseHelper.COLUMN_LAST_NAME, DatabaseHelper.COLUMN_EMAIL, DatabaseHelper.COLUMN_PASSWORD, DatabaseHelper.COLUMN_PHONE, DatabaseHelper.COLUMN_UNIVERSITY, DatabaseHelper.COLUMN_COLLEGE };
        String selection = DatabaseHelper.COLUMN_EMAIL + " = ?";
        String[] selectionArgs = { email };
        return database.query(DatabaseHelper.TABLE_NAME, columns, selection, selectionArgs, null, null, null);
    }

    public void close() {
        dbHelper.close();
    }
}
