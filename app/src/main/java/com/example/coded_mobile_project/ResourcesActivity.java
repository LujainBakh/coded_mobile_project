package com.example.coded_mobile_project;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class ResourcesActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resources); // Update layout for InstructorsActivity


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


        LinearLayout sisBox = findViewById(R.id.box1);
        LinearLayout libraryBox = findViewById(R.id.box2);
        LinearLayout bbBox = findViewById(R.id.box3);
        LinearLayout iauBox = findViewById(R.id.box4);

        // Add onClickListeners for each box
        sisBox.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://sis.iau.edu.sa/"));
            startActivity(intent);
        });

        libraryBox.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://login.library.iau.edu.sa/login"));
            startActivity(intent);
        });

        bbBox.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://vle.iau.edu.sa/"));
            startActivity(intent);
        });

        iauBox.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.iau.edu.sa/"));
            startActivity(intent);
        });
        // Set up BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);


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
