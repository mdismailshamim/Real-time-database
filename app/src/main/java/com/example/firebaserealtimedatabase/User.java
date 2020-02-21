package com.example.firebaserealtimedatabase;

public class User {
    private String name;
    private String gender;
    private String phoneNumber;
    private String userId; //if need userId

    public User() {
    }

    public User(String name, String gender, String phoneNumber) {
        this.name = name;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
    }

    public User(String name, String gender, String phoneNumber, String userId) {
        this.name = name;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
