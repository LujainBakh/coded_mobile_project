package com.example.coded_mobile_project;

import android.content.Intent;
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
    DatabaseHelper dbHelp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelp=new DatabaseHelper(this);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);

//        loginButton.setOnClickListener(view -> {
//            String emailInput = email.getText().toString();
//            String passwordInput = password.getText().toString();
//
//            if(email.equals("")||password.equals(""))
//                Toast.makeText(LoginActivity.this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
//            else {
//                Boolean checkCredentials = dbHelp.checkEmailPassword(email, password);
//                if (checkCredentials == true) {
//                    Toast.makeText(LoginActivity.this, "Login Successfully!", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                    startActivity(intent);
//                } else {
//                    Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//        }
//        loginButton.setOnClickListener(view -> {
//            String emailInput = email.getText().toString().trim(); // Use trim to remove extra spaces
//            String passwordInput = password.getText().toString().trim();
//
//            if (emailInput.isEmpty() || passwordInput.isEmpty()) {
//                Toast.makeText(LoginActivity.this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
//            } else {
//                Boolean checkCredentials = dbHelp.checkEmailPassword(emailInput, passwordInput); // Pass trimmed strings
//                if (checkCredentials) {
//                    Toast.makeText(LoginActivity.this, "Login Successfully!", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                    startActivity(intent);
//                } else {
//                    Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
        loginButton.setOnClickListener(view -> {
            String emailInput = email.getText().toString().trim(); // Trim inputs
            String passwordInput = password.getText().toString().trim();

            if (emailInput.isEmpty() || passwordInput.isEmpty()) {
                Toast.makeText(LoginActivity.this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
            } else {
                Boolean checkCredentials = dbHelp.checkEmailPassword(emailInput, passwordInput);
                if (checkCredentials) {
                    Toast.makeText(LoginActivity.this, "Login Successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
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
