package com.example.coded_mobile_project;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.EditText;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "user.db";
    private static final int DATABASE_VERSION = 1;

    // Table Name
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
        {
            db.execSQL(TABLE_CREATE);
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_FIRST_NAME, "Default");
            cv.put(COLUMN_LAST_NAME, "User");
            cv.put(COLUMN_EMAIL, "d@e.com");
            cv.put(COLUMN_PASSWORD, "password123");
            cv.put(COLUMN_PHONE, "1234567890");
            cv.put(COLUMN_UNIVERSITY, "Default University");
            cv.put(COLUMN_COLLEGE, "Default College");
            db.insert(TABLE_NAME, null, cv);
        }
    }

//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        {
//            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
//            onCreate(db);
//        }
//    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    //    public Boolean insertData(int id,String Fname,String Lname,String email, String password,
//                              String phone, String uni,String college){
//        SQLiteDatabase MyDatabase = this.getWritableDatabase();
//        ContentValues cv = new ContentValues();
//        cv.put("id", id);
//        cv.put("fname", Fname);
//        cv.put("Lname", Lname);
//        cv.put("email", email);
//        cv.put("password", password);
//        cv.put("phone", phone);
//        cv.put("uni", uni);
//        cv.put("college", college);
//        long result = MyDatabase.insert("users", null, cv);
//        if (result == -1) {
//            return false;
//        } else {
//            return true;
//        }
//    }
public Boolean insertData(int id, String firstName, String lastName, String email, String password,
                          String phone, String university, String college) {
    SQLiteDatabase MyDatabase = this.getWritableDatabase();
    ContentValues cv = new ContentValues();
    cv.put(COLUMN_ID, id); // Fixed column names to match the schema
    cv.put(COLUMN_FIRST_NAME, firstName);
    cv.put(COLUMN_LAST_NAME, lastName);
    cv.put(COLUMN_EMAIL, email);
    cv.put(COLUMN_PASSWORD, password);
    cv.put(COLUMN_PHONE, phone);
    cv.put(COLUMN_UNIVERSITY, university);
    cv.put(COLUMN_COLLEGE, college);

    long result = MyDatabase.insert(TABLE_NAME, null, cv);
    return result != -1; // Return true if insertion was successful
}


    public Boolean checkEmail(String email){
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("Select * from users where email = ?", new String[]{email});
        if(cursor.getCount() > 0) {
            return true;
        }else {
            return false;
        }
    }

//    public Boolean checkEmailPassword(EditText email, EditText password){
//        SQLiteDatabase MyDatabase = this.getWritableDatabase();
//        Cursor cursor = MyDatabase.rawQuery("Select * from users where email = ? and password = ?", new String[]{String.valueOf(email), String.valueOf(password)});
//        if (cursor.getCount() > 0) {
//            return true;
//        }else {
//            return false;
//        }
//    }
//public Boolean checkEmailPassword(String email, String password) {
//    SQLiteDatabase MyDatabase = this.getReadableDatabase(); // Use readableDatabase
//    Cursor cursor = MyDatabase.rawQuery(
//            "SELECT * FROM users WHERE email = ? AND password = ?",
//            new String[]{email, password}
//    );
//    boolean exists = cursor.getCount() > 0; // Check if any row exists
//    cursor.close(); // Close cursor to avoid memory leaks
//    return exists;
//}
public Boolean checkEmailPassword(String email, String password) {
    SQLiteDatabase MyDatabase = this.getReadableDatabase(); // Use readable database for queries
    Cursor cursor = MyDatabase.rawQuery(
            "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_EMAIL + " = ? AND " + COLUMN_PASSWORD + " = ?",
            new String[]{email, password}
    );
    boolean exists = cursor.getCount() > 0;
    cursor.close(); // Avoid memory leaks
    return exists;
}


}