package com.example.coded_mobile_project;

public class Instructor {
    private String name;
    private String bio;
    private String location;
    private String hours;
    private String email;

    public Instructor(String name, String bio, String location, String hours, String email) {
        this.name = name;
        this.bio = bio;
        this.location = location;
        this.hours = hours;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getBio() {
        return bio;
    }

    public String getLocation() {
        return location;
    }

    public String getHours() {
        return hours;
    }

    public String getEmail() {
        return email;
    }
}
