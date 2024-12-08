package com.example.coded_mobile_project;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class InstructorsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private LinearLayout container;
    private EditText searchBox;
    private List<Instructor> instructors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructors);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.instrcutors);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.home) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            } else if (item.getItemId() == R.id.instrcutors) {
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

        // Initialize the container and search box
        container = findViewById(R.id.instructor_container);
        searchBox = findViewById(R.id.search_box);


        instructors = new ArrayList<>();
        instructors.add(new Instructor("Ms. Amal AlHajri", "PhD in AI, Expert in Data Science and Machine Learning.", "Building 650, Second floor no.205-H", "Mon & Wed, 10:00 AM - 12:00 PM", "amal.hajri@example.com"));
        instructors.add(new Instructor("Ms. Layan AlNahdi", "MSc in Software Engineering, Researcher in Cloud Computing.", "Building 650, First floor no.104-B", "Tue & Thu, 1:00 PM - 3:00 PM", "layan.nahdi@example.com"));
        instructors.add(new Instructor("Ms. Jood Shuwaikan", "BSc in Computer Science, Specialist in Web Development.", "Building A11, Second floor no.108", "Mon & Wed, 9:00 AM - 11:00 AM", "jood.shuwaikan@example.com"));
        instructors.add(new Instructor("Dr. Faisal AlGhamdi", "PhD in Computer Vision, Faculty Lead for Graduate Research.", "Building A11, Second floor no.202", "Sun & Tue, 11:00 AM - 1:00 PM", "faisal.alghamdi@example.com"));
        instructors.add(new Instructor("Dr. Jood Alghamdi", "PhD in Computer Science, Expert in Artificial Intelligence and Parallel Computing.", "Building 650, Second floor no.210", "Sun & Wen, 12:00 AM - 1:00 PM", "jood.alghamdi@example.com"));
        instructors.add(new Instructor("Dr. Amjad AlKhalifa", "PhD in Computer Engineering, Specialist in Embedded Systems and IoT Security.", "Building A11, First floor no.101", "Tue & Thu, 10:00 AM - 12:00 PM", "amjad.alkhalifa@example.com"));
        instructors.add(new Instructor("Ms. Lujain Bakhurji", "MSc in Computer Science, Focused on Human-Computer Interaction and Software Development.", "Building 750, Second floor no.112", "Mon & Tue, 11:00 AM - 1:00 PM", "lujain.bakhurji@example.com"));


        displayInstructors(instructors);

        // Add search functionality
        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                filterInstructors(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });
    }

    private void displayInstructors(List<Instructor> instructorsToDisplay) {
        container.removeAllViews(); // Clear previous content
        for (Instructor instructor : instructorsToDisplay) {
            addInstructor(instructor);
        }
    }

    private void filterInstructors(String query) {
        List<Instructor> filteredList = new ArrayList<>();
        for (Instructor instructor : instructors) {
            if (instructor.getName() != null && instructor.getName().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(instructor);
            }
        }
        displayInstructors(filteredList);
    }

    private void addInstructor(Instructor instructor) {
        LinearLayout instructorLayout = (LinearLayout) getLayoutInflater()
                .inflate(R.layout.instructor_item, container, false);

        TextView nameView = instructorLayout.findViewById(R.id.instructor_name);
        TextView bioView = instructorLayout.findViewById(R.id.instructor_bio);
        TextView locationView = instructorLayout.findViewById(R.id.instructor_location);
        TextView hoursView = instructorLayout.findViewById(R.id.instructor_hours);
        TextView emailView = instructorLayout.findViewById(R.id.instructor_email);

        nameView.setText(instructor.getName());
        bioView.setText(instructor.getBio());
        locationView.setText(instructor.getLocation());
        hoursView.setText(instructor.getHours());
        emailView.setText(instructor.getEmail());

        emailView.setOnClickListener(v -> {
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + instructor.getEmail()));
            startActivity(Intent.createChooser(emailIntent, "Send Email"));
        });

        container.addView(instructorLayout);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.nav_profile) {
            startActivity(new Intent(this, ProfileActivity.class));
        } else if (item.getItemId() == R.id.nav_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
        } else if (item.getItemId() == R.id.nav_calculate) {
            startActivity(new Intent(this, GpaCalculator.class));
        } else if (item.getItemId() == R.id.nav_map) {
            startActivity(new Intent(this, MapActivity.class));
        } else if (item.getItemId() == R.id.nav_logout) {
            logout();
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logout() {
        SessionManager.clearSession(this);
        startActivity(new Intent(this, LoginActivity.class));
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
}
