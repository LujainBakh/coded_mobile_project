package com.example.coded_mobile_project;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;


public class MapActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {

    private DrawerLayout drawerLayout;
    private GoogleMap mMap;

    // this will let the app request for location permissions
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

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

        // Initialize the Google Maps fragment
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this); // Load the map asynchronously
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Enable location tracking
        enableMyLocation();

        // Adding Locations
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        LatLng IAU = new LatLng(26.391044, 50.187568);
        mMap.addMarker(new MarkerOptions().position(IAU).title("Imam Abdulrahman Bin Faisal University"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(IAU, 10));

        LatLng MsAmal = new LatLng(26.385577, 50.189821);
        LatLng IAUGirls = new LatLng(26.385035, 50.188979);
        LatLng A11 = new LatLng(26.394855, 50.195700);
        LatLng Library = new LatLng(26.393850, 50.190106);
        LatLng Library2 = new LatLng(26.390256, 50.183881);
        LatLng MsLayan = new LatLng(26.385394, 50.190006);
        LatLng MsLujain = new LatLng(26.385847740119683, 50.18911728811018);
        LatLng MsJood = new LatLng(26.386196, 50.187678);
        LatLng DrFaisal = new LatLng(26.394423, 50.195471);
        LatLng DrJood = new LatLng(26.385083, 50.189911);
        LatLng DrAmjad = new LatLng(26.394839, 50.196429);



        // Add markers for each location
        mMap.addMarker(new MarkerOptions().position(MsAmal).title("Ms. Amal AlHajri's Office: Building 650, Second floor no.205-H"));
        mMap.addMarker(new MarkerOptions().position(IAUGirls).title("College of Computer Science and Information Technology (Ladies Section)"));
        mMap.addMarker(new MarkerOptions().position(A11).title("College of Computer Science and Information Technology"));
        mMap.addMarker(new MarkerOptions().position(Library).title("Central Library IAU"));
        mMap.addMarker(new MarkerOptions().position(Library2).title("Central Library 2 IAU"));
        mMap.addMarker(new MarkerOptions().position(MsLayan).title("Ms. Layan AlNahdi's Office: Building 650, First floor no.104-B"));
        mMap.addMarker(new MarkerOptions().position(MsLujain).title("Ms. Lujain Bakhurji's Office: Building 750, Second floor no.112"));
        mMap.addMarker(new MarkerOptions().position(MsJood).title("Ms. Jood Shuwaikan's Office: Building A11, Second floor no.108"));
        mMap.addMarker(new MarkerOptions().position(DrFaisal).title("Dr. Faisal AlGhamdi's Office: Building A11, Second floor no.202"));
        mMap.addMarker(new MarkerOptions().position(DrJood).title("Dr. Jood AlGhamdi's Office: Building 650, Second floor no.210"));
        mMap.addMarker(new MarkerOptions().position(DrAmjad).title("Dr. Amjad AlKhalifa's Office: Building A11, First floor no.101"));


    }

    private void enableMyLocation() {
        // Check if the permission is granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            // Request location permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, enable location
                enableMyLocation();
            } else {
                // Permission denied, show a toast
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.nav_profile) {
            Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.nav_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.nav_calculate) {
            Intent intent = new Intent(this, GpaCalculator.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.nav_map) {
            return true; // Stay in the current activity
        } else if (item.getItemId() == R.id.nav_logout) {
            logout();
        }

        drawerLayout.closeDrawer(GravityCompat.START); // Close the navigation drawer
        return true;
    }

    private void logout() {
        SessionManager.clearSession(this);
        Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show();
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
}
