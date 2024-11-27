package com.example.coded_mobile_project;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class UserDao {

    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    public UserDao(Context context) {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    // Method to add a user with additional attributes
    public long insertUser(User user) {
        return dbHelper.addUser(user);
    }

    // Method to retrieve a user by email
    public Cursor getUserByEmail(String email) {
        String[] columns = {
                DatabaseHelper.COLUMN_ID,
                DatabaseHelper.COLUMN_FIRST_NAME,
                DatabaseHelper.COLUMN_LAST_NAME,
                DatabaseHelper.COLUMN_EMAIL,
                DatabaseHelper.COLUMN_PASSWORD,
                DatabaseHelper.COLUMN_PHONE,
                DatabaseHelper.COLUMN_UNIVERSITY,
                DatabaseHelper.COLUMN_COLLEGE
        };
        String selection = DatabaseHelper.COLUMN_EMAIL + " = ?";
        String[] selectionArgs = { email };
        Log.d("UserDao", "Querying user by email: " + email);
        return database.query(DatabaseHelper.TABLE_NAME, columns, selection, selectionArgs, null, null, null);
    }
    public boolean verifyUser(String email, String password) {
        String[] columns = { DatabaseHelper.COLUMN_ID };
        String selection = DatabaseHelper.COLUMN_EMAIL + " = ? AND " + DatabaseHelper.COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = { email, password };
        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        boolean isUserValid = (cursor != null && cursor.getCount() > 0);
        if (cursor != null) {
            cursor.close();
        }
        return isUserValid;
    }


    public void close() {
        dbHelper.close();
    }
}
