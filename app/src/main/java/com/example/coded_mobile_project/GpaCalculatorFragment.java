package com.example.coded_mobile_project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class GpaCalculatorFragment extends Fragment {

    private EditText courseName;
    private Spinner courseGradeSpinner;
    private EditText courseCredits;
    private Button addButton;
    private Button calculateButton;
    private ListView courseListView;
    private TextView gpaTextView;

    private List<com.example.gpa_calculator.Course> courses = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_gpa_calculator, container, false);

        // Initialize UI Components
        courseName = rootView.findViewById(R.id.courseName);
        courseGradeSpinner = rootView.findViewById(R.id.courseGradeSpinner);
        courseCredits = rootView.findViewById(R.id.courseCredits);
        addButton = rootView.findViewById(R.id.addButton);
        calculateButton = rootView.findViewById(R.id.calculateButton);
        courseListView = rootView.findViewById(R.id.courseListView);
        gpaTextView = rootView.findViewById(R.id.gpaTextView);

        // Populate the Spinner with grade options
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(
                getContext(),
                R.array.grades_array,
                android.R.layout.simple_spinner_item
        );
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        courseGradeSpinner.setAdapter(spinnerAdapter);

        // Set up ListView Adapter
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, new ArrayList<>());
        courseListView.setAdapter(adapter);

        // Add Button Click Listener
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCourse();
            }
        });

        // Calculate Button Click Listener
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateGPA();
            }
        });

        return rootView;
    }


    private void addCourse() {
        String name = courseName.getText().toString();
        String grade = courseGradeSpinner.getSelectedItem().toString();
        String creditsText = courseCredits.getText().toString();

        if (name.isEmpty() || creditsText.isEmpty()) {
            Toast.makeText(getContext(), "Please fill in all fields!", Toast.LENGTH_SHORT).show();
            return;
        }

        int credits;
        try {
            credits = Integer.parseInt(creditsText);
        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), "Invalid credit value!", Toast.LENGTH_SHORT).show();
            return;
        }

        com.example.gpa_calculator.Course course = new com.example.gpa_calculator.Course(name, grade, credits);
        courses.add(course);

        // Update ListView
        adapter.add(course.getName() + " - Grade: " + course.getGrade() + ", Credits: " + course.getCredits());
        adapter.notifyDataSetChanged();

        // Clear Input Fields
        courseName.setText("");
        courseCredits.setText("");
    }

    private void calculateGPA() {
        if (courses.isEmpty()) {
            Toast.makeText(getContext(), "No courses added!", Toast.LENGTH_SHORT).show();
            return;
        }

        double totalPoints = 0.0;
        int totalCredits = 0;

        for (com.example.gpa_calculator.Course course : courses) {
            double gradePoint = getGradePoint(course.getGrade());
            totalPoints += gradePoint * course.getCredits();
            totalCredits += course.getCredits();
        }

        double gpa = totalPoints / totalCredits;

        // Update GPA TextView
        gpaTextView.setText(String.format("Your GPA: %.2f ", gpa));
    }

    private double getGradePoint(String grade) {
        switch (grade) {
            case "A+":
                return 5.0;
            case "A":
                return 4.75;
            case "B+":
                return 4.5;
            case "B":
                return 4.0;
            case "C+":
                return 3.5;
            case "C":
                return 3.0;
            case "D+":
                return 2.5;
            case "D":
                return 2.0;
            case "F":
                return 0.0;
            default:
                return 0.0;
        }
    }
}
