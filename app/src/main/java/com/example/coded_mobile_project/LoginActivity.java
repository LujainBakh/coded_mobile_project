package com.example.coded_mobile_project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.coded_mobile_project.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    private EditText email;
    private EditText password;
    private Button loginButton;
    private DatabaseHelper dbHelp;
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelp = new DatabaseHelper(this);

        // Insert a test user into the database (if not already exists)
        if (!dbHelp.checkEmail("22100001234@iau.edu.sa")) {
            boolean isInserted = dbHelp.insertData(
                    "Alaa", // First Name
                    "Hariri", // Last Name
                    "22100001234@iau.edu.sa", // Email
                    "password123", // Password
                    "0505800101", // Phone
                    "Imam Abdulrahman Bin Faisal University", // University
                    "College of Computer Science and Information Technology" // College
            );

            if (isInserted) {
                System.out.println("Test user added successfully");
            } else {
                System.out.println("Failed to add test user");
            }
        }

        // Check if user is already logged in
        if (SessionManager.isLoggedIn(this)) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        // Set up binding and UI elements
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);

        // Login Button Click Listener
        binding.loginButton.setOnClickListener(view -> {
            String emailInput = binding.email.getText().toString().trim();
            String passwordInput = binding.password.getText().toString().trim();

            if (emailInput.isEmpty() || passwordInput.isEmpty()) {
                Toast.makeText(LoginActivity.this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
            } else {
                boolean checkCredentials = dbHelp.checkEmailPassword(emailInput, passwordInput);
                if (checkCredentials) {
                    SessionManager.saveLoginState(LoginActivity.this, true, emailInput);
                    Toast.makeText(LoginActivity.this, "Login Successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dbHelp != null) {
            dbHelp.close();
        }
    }
}
