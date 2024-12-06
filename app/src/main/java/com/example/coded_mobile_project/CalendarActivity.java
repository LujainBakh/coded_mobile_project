package com.example.coded_mobile_project;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.CalendarView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.Calendar;
import java.util.HashMap;

public class CalendarActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    CalendarView calendarView;
    Calendar calendar;

    // HashMap to store events
    HashMap<String, String> eventsMap;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar); // Update layout for CalendarActivity

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
        bottomNavigationView.setSelectedItemId(R.id.calendar); // Default to Calendar menu item

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
                return true; // Stay on CalendarActivity
            }
            return false;
        });
        calendarView = findViewById(R.id.calendarView);
        calendar = Calendar.getInstance();

        // Initialize events
        eventsMap = new HashMap<>();
        eventsMap.put("18/12/2024", "Start the final exam");
        eventsMap.put("02/01/2025", "End of semester one");
        eventsMap.put("12/01/2025", "Start of semester two");
        eventsMap.put("10/11/2024", "Start of spring vacation");

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                // Format the selected date to match the keys in the HashMap
                String selectedDate = String.format("%02d/%02d/%04d", day, month + 1, year);

                // Check if there's an event for the selected date
                if (eventsMap.containsKey(selectedDate)) {
                    // Show event in a dialog
                    showEventDialog(selectedDate, eventsMap.get(selectedDate));
                } else {
                    // No event for the selected date
                    Toast.makeText(CalendarActivity.this, "No events on this date.", Toast.LENGTH_SHORT).show();
                }
            }
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
    private void showEventDialog(String date, String event) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Event on " + date);
        builder.setMessage(event);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss(); // Close the dialog
            }
        });
        builder.show();
    }
}
