package com.example.gpa_calculator;

public class Course {
    private String name;
    private String grade;
    private int credits;

    // Constructor
    public Course(String name, String grade, int credits) {
        this.name = name;
        this.grade = grade;
        this.credits = credits;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getGrade() {
        return grade;
    }

    public int getCredits() {
        return credits;
    }

    // Setters (optional)
    public void setName(String name) {
        this.name = name;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }
}
