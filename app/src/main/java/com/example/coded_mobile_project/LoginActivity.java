package com.example.coded_mobile_project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.coded_mobile_project.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    EditText email;
    EditText password;
    Button loginButton;
    DatabaseHelper dbHelp;
    ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelp = new DatabaseHelper(this);

        // Insert a test user into the database
        boolean isInserted = dbHelp.insertData(
                1, // User ID
                "John", // First Name
                "Doe", // Last Name
                "johndoe@example.com", // Email
                "password123", // Password
                "1234567890", // Phone
                "Default University", // University
                "Default College" // College
        );

        if (isInserted) {
            System.out.println("Test user added successfully");
        } else {
            System.out.println("Failed to add test user");
        }

        // Check if user is already logged in
        if (SessionManager.isLoggedIn(this)) {
            // User is logged in, redirect to MainActivity
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Close LoginActivity to prevent going back to it
        }

        EdgeToEdge.enable(this);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);

        binding.loginButton.setOnClickListener(view -> {
            String emailInput = binding.email.getText().toString().trim(); // Trim inputs
            String passwordInput = binding.password.getText().toString().trim();

            if (emailInput.isEmpty() || passwordInput.isEmpty()) {
                Toast.makeText(LoginActivity.this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
            } else {
                Boolean checkCredentials = dbHelp.checkEmailPassword(emailInput, passwordInput);
                if (checkCredentials) {
                    // Save login state using Shared Preferences
                    SessionManager.saveLoginState(LoginActivity.this, true, emailInput);

                    Toast.makeText(LoginActivity.this, "Login Successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish(); // Close LoginActivity to prevent going back
                } else {
                    Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
