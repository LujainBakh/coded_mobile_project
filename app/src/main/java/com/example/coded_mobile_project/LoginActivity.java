package com.example.coded_mobile_project;

import android.content.Intent;
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
            String emailInput = email.getText().toString();
            String passwordInput = password.getText().toString();
            Log.d("LoginActivity", "Attempting login with email: " + emailInput + " and password: " + passwordInput);

            if (emailInput.isEmpty() || passwordInput.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Please enter both email and password.", Toast.LENGTH_SHORT).show();
                Log.d("LoginActivity", "Email or password field is empty.");
                return;
            }

            boolean isUserValid = userDao.verifyUser(emailInput, passwordInput);
            if (isUserValid) {
                Toast.makeText(LoginActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                Log.d("LoginActivity", "Login successful for email: " + emailInput);

                // Navigate to MainActivity
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(LoginActivity.this, "Login Failed! Incorrect email or password.", Toast.LENGTH_SHORT).show();
                Log.d("LoginActivity", "Login failed for email: " + emailInput + " - Invalid credentials");
            }
        });
    }

    @Override
    protected void onDestroy() {
        userDao.close();
    }
}
