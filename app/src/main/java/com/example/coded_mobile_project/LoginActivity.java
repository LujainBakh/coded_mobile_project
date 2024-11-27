package com.example.coded_mobile_project;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.util.Log;

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

        loginButton.setOnClickListener(view -> {
            Cursor cursor = userDao.getUserByEmail(email.getText().toString());
            if (cursor.moveToFirst()) {
                String storedPassword = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_PASSWORD));
                if (storedPassword.equals(password.getText().toString())) {
                    Toast.makeText(LoginActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                    // Log success
                    Log.d("LoginActivity", "Login successful for email: " + email.getText().toString());

                    // Navigate to MainActivity
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Login Failed! Incorrect Password.", Toast.LENGTH_SHORT).show();
                    // Log failure due to incorrect password
                    Log.d("LoginActivity", "Login failed for email: " + email.getText().toString() + " - Incorrect password");
                }
            } else {
                Toast.makeText(LoginActivity.this, "Login Failed! User not found.", Toast.LENGTH_SHORT).show();
                // Log failure due to user not found
                Log.d("LoginActivity", "Login failed for email: " + email.getText().toString() + " - User not found");
            }
            cursor.close();
        });
    }

    @Override
    protected void onDestroy() {
        userDao.close();
        super.onDestroy();
    }
}
