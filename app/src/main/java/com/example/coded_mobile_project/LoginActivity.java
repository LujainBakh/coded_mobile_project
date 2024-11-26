package com.example.coded_mobile_project;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText email;
    EditText password;
    Button loginButton;
    UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        userDao = new UserDao(this);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);

        // Insert a dummy user for testing
        insertDummyUser();

        loginButton.setOnClickListener(view -> {
            Cursor cursor = userDao.getUserByEmail(email.getText().toString());
            if (cursor.moveToFirst()) {
                String storedPassword = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_PASSWORD));
                if (storedPassword.equals(password.getText().toString())) {
                    Toast.makeText(LoginActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();

                    // Navigate to MainActivity
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Login Failed! Incorrect Password.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(LoginActivity.this, "Login Failed! User not found.", Toast.LENGTH_SHORT).show();
            }
            cursor.close();
        });
    }

    // Method to insert a dummy user for testing
    private void insertDummyUser() {
        long result = userDao.addUser("John", "Doe", "john.doe@example.com", "password123", "1234567890", "Example University", "Example College");
        if (result != -1) {
            Toast.makeText(this, "Dummy user added!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to add dummy user!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        userDao.close();
        super.onDestroy();
    }
}
