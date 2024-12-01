package com.example.coded_mobile_project;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class BookingFragment extends Fragment {

    private EditText instructorSearch;
    private DatePicker datePicker;
    private Spinner timeSpinner;
    private Button searchButton;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the fragment layout
        View view = inflater.inflate(R.layout.fragment_booking, container, false);

        // Initialize the views
        instructorSearch = view.findViewById(R.id.instructorSearch);
        datePicker = view.findViewById(R.id.datePicker);
        timeSpinner = view.findViewById(R.id.timeSpinner);
        searchButton = view.findViewById(R.id.searchButton);

        // Setup time options for the Spinner
        String[] timeSlots = {"10:00 AM", "11:00 AM", "1:00 PM", "2:00 PM"};
        ArrayAdapter<String> timeAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, timeSlots);
        timeSpinner.setAdapter(timeAdapter);

        // Handle search button click
        searchButton.setOnClickListener(v -> {
            // Get the entered instructor, date, and time
            String instructor = instructorSearch.getText().toString();
            int day = datePicker.getDayOfMonth();
            int month = datePicker.getMonth() + 1; // Month is 0-indexed
            int year = datePicker.getYear();
            String selectedTime = timeSpinner.getSelectedItem().toString();

            // Check if the instructor field is empty
            if (instructor.isEmpty()) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Error")
                        .setMessage("Please enter an instructor name")
                        .setPositiveButton("OK", null)
                        .show();
                return;
            }

            // Show a confirmation popup for appointment booking
            String bookingDetails = "Appointment booked with " + instructor + " on " + month + "/" + day + "/" + year + " at " + selectedTime;

            new AlertDialog.Builder(getActivity())
                    .setTitle("Appointment Confirmed")
                    .setMessage("A confirmation email will be sent with the appointment details.")
                    .setPositiveButton("OK", (dialog, which) -> {
                        // Optionally, handle button click action
                    })
                    .show();
        });

        return view;
    }
}
