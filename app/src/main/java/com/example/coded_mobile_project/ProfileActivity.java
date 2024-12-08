package com.example.coded_mobile_project;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class ProfileActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private DatabaseHelper dbHelper;
    private EditText email, university, firstName, lastName, phone;
    private TextView college;
    private Button updateButton;
    private String loggedInEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Set up Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialize DrawerLayout and NavigationView
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Add toggle button for navigation drawer
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Set up BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.home) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            } else if (item.getItemId() == R.id.instrcutors) {
                startActivity(new Intent(getApplicationContext(), InstructorsActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            } else if (item.getItemId() == R.id.Officehours) {
                startActivity(new Intent(getApplicationContext(), OfficeHoursActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            } else if (item.getItemId() == R.id.calendar) {
                startActivity(new Intent(getApplicationContext(), CalendarActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            }
            return false;
        });

        // Initialize UI components
        email = findViewById(R.id.email);
        university = findViewById(R.id.university);
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        phone = findViewById(R.id.phone);
        college = findViewById(R.id.college);
        updateButton = findViewById(R.id.updateButton);

        // Initialize DatabaseHelper and retrieve logged-in user's email
        dbHelper = new DatabaseHelper(this);
        loggedInEmail = SessionManager.getLoggedInUserEmail(this);

        // Load user profile data
        loadUserProfile();

        // Update Button functionality
        updateButton.setOnClickListener(view -> {
            String updatedFirstName = firstName.getText().toString().trim();
            String updatedLastName = lastName.getText().toString().trim();
            String updatedPhone = phone.getText().toString().trim();

            if (!isValidPhoneNumber(updatedPhone)) {
                Toast.makeText(ProfileActivity.this, "Phone number must start with 05 and be 10 digits long", Toast.LENGTH_SHORT).show();
            } else if (updatedFirstName.isEmpty() || updatedLastName.isEmpty()) {
                Toast.makeText(ProfileActivity.this, "Please fill all mandatory fields", Toast.LENGTH_SHORT).show();
            } else {
                boolean isUpdated = dbHelper.updateUserProfile(loggedInEmail, updatedFirstName, updatedLastName,
                        updatedPhone, university.getText().toString().trim(), college.getText().toString());

                if (isUpdated) {
                    Toast.makeText(ProfileActivity.this, "Profile Updated Successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ProfileActivity.this, "Failed to Update Profile", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void loadUserProfile() {
        Cursor cursor = null;
        try {
            cursor = dbHelper.getUserProfile(loggedInEmail);
            if (cursor != null && cursor.moveToFirst()) {
                email.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_EMAIL)));
                university.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_UNIVERSITY)));
                firstName.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_FIRST_NAME)));
                lastName.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_LAST_NAME)));
                phone.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_PHONE)));
                college.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_COLLEGE)));

                // Disable editing for email, university, and college
                email.setEnabled(false);
                university.setEnabled(false);
                college.setEnabled(false);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    private boolean isValidPhoneNumber(String phone) {
        return phone.startsWith("05") && phone.length() == 10 && phone.matches("\\d+");
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.nav_profile) {
            return true; // Stay on this activity
        } else if (item.getItemId() == R.id.nav_settings) {
            // Start SettingsActivity
            startActivity(new Intent(this, SettingsActivity.class));
        } else if (item.getItemId() == R.id.nav_calculate) {
            // Start GpaActivity
            startActivity(new Intent(this, GpaCalculator.class));
        } else if (item.getItemId() == R.id.nav_map) {
            // Start MapActivity
            startActivity(new Intent(this, MapActivity.class));
        } else if (item.getItemId() == R.id.nav_logout) {
            // Handle logout
            logout();
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logout() {
        // Clear the session using SessionManager
        SessionManager.clearSession(this);

        // Show a toast message for feedback
        Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show();

        // Redirect to LoginActivity
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dbHelper != null) {
            dbHelper.close();
        }
    }
}
