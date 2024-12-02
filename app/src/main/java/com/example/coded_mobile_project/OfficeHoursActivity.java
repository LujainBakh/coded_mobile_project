package com.example.coded_mobile_project;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class OfficeHoursActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private Spinner instructorSearch;
    private DatePicker datePicker;
    private Spinner timeSpinner;
    private Button searchButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_office_hour); // Layout with appointment booking elements

        // Set up the Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialize DrawerLayout and NavigationView
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Add toggle button to handle the navigation drawer open/close
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Set up BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.Officehours); // Default to Officehours menu item

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
                return true; // Stay on OfficeHoursActivity
            } else if (item.getItemId() == R.id.calendar) {
                startActivity(new Intent(getApplicationContext(), CalendarActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            }
            return false;
        });

        // Initialize appointment booking views
        instructorSearch = findViewById(R.id.instructorSearch); // This is now a Spinner
        datePicker = findViewById(R.id.datePicker);
        timeSpinner = findViewById(R.id.timeSpinner);
        searchButton = findViewById(R.id.searchButton);

        // Setup time options for the Spinner
        String[] timeSlots = {"10:00 AM", "11:00 AM", "1:00 PM", "2:00 PM"};
        ArrayAdapter<String> timeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, timeSlots);
        timeSpinner.setAdapter(timeAdapter);

        // Setup instructor options for the Spinner (predefined list of instructors)
        String[] instructors = {"Dr. Smith", "Dr. Johnson", "Prof. Lee", "Dr. Brown", "Prof. Davis"};
        ArrayAdapter<String> instructorAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, instructors);
        instructorSearch.setAdapter(instructorAdapter); // Set the adapter for the Spinner

        // Handle search button click
        searchButton.setOnClickListener(v -> {
            // Get the selected instructor, date, and time
            String selectedInstructor = instructorSearch.getSelectedItem().toString(); // Correctly get selected item
            int day = datePicker.getDayOfMonth();
            int month = datePicker.getMonth() + 1; // Month is 0-indexed
            int year = datePicker.getYear();
            String selectedTime = timeSpinner.getSelectedItem().toString();

            // Check if the instructor is selected
            if (selectedInstructor.isEmpty()) {
                Toast.makeText(OfficeHoursActivity.this, "Please select an instructor", Toast.LENGTH_SHORT).show();
                return;
            }

            // Show an AlertDialog confirming the appointment details
            String bookingDetails = "Appointment booked with " + selectedInstructor + "\n" +
                    "Date: " + month + "/" + day + "/" + year + "\n" +
                    "Time: " + selectedTime;

            new AlertDialog.Builder(OfficeHoursActivity.this)
                    .setTitle("Confirm Appointment")
                    .setMessage(bookingDetails)
                    .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Handle booking confirmation, e.g., save to database or make network request
                            Toast.makeText(OfficeHoursActivity.this, "Appointment Booked!", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("Cancel", null) // Do nothing on cancel
                    .show();
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.nav_profile) {
            // Start ProfileActivity
            Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.nav_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.nav_calculate) {
            // Start GpaActivity
            Intent intent = new Intent(this, GpaCalculator.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.nav_map) {
            // Start MapActivity
            Intent intent = new Intent(this, MapActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.nav_logout) {
            // Handle Logout
            logout();
        }

        drawerLayout.closeDrawer(GravityCompat.START); // Close the navigation drawer
        return true;
    }

    private void logout() {
        // Clear the session using SessionManager
        SessionManager.clearSession(this);

        // Show a toast message for feedback
        Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show();

        // Redirect to LoginActivity
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Clear activity stack
        startActivity(intent);
        finish(); // Close current activity
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
