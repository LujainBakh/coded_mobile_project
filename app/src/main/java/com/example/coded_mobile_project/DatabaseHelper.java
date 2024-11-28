package com.example.coded_mobile_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "user.db";
    private static final int DATABASE_VERSION = 1;

    // Table and Column Names
    public static final String TABLE_NAME = "users";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_FIRST_NAME = "first_name";
    public static final String COLUMN_LAST_NAME = "last_name";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_UNIVERSITY = "university";
    public static final String COLUMN_COLLEGE = "college";

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
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public Boolean insertData(int id,String firstName, String lastName, String email, String password,
                              String phone, String university, String college) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_ID, id);
        cv.put(COLUMN_FIRST_NAME, firstName);
        cv.put(COLUMN_LAST_NAME, lastName);
        cv.put(COLUMN_EMAIL, email);
        cv.put(COLUMN_PASSWORD, password);
        cv.put(COLUMN_PHONE, phone);
        cv.put(COLUMN_UNIVERSITY, university);
        cv.put(COLUMN_COLLEGE, college);

        long result = MyDatabase.insert(TABLE_NAME, null, cv);
        return result != -1;
    }

    public Boolean checkEmail(String email) {
        SQLiteDatabase MyDatabase = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = MyDatabase.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_EMAIL + " = ?", new String[]{email});
            return cursor.getCount() > 0;
        } finally {
            if (cursor != null) cursor.close();
        }
    }

    public Boolean checkEmailPassword(String email, String password) {
        SQLiteDatabase MyDatabase = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = MyDatabase.rawQuery(
                    "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_EMAIL + " = ? AND " + COLUMN_PASSWORD + " = ?",
                    new String[]{email, password}
            );
            return cursor.getCount() > 0;
        } finally {
            if (cursor != null) cursor.close();
        }
    }
}
