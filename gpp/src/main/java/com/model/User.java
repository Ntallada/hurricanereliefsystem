package com.model;

import java.util.UUID;

public class User {

    private String firstName;
    private String lastName;
    private int age;
    private UUID id;
    private String password;
    private String language;
    private String currentLocation;

    public User(String firstName, String lastName, int age, String password, String language, String currentLocation) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.id = UUID.randomUUID();
        this.password = password;
        this.language = language;
        this.currentLocation = currentLocation;
    }
    
    //Loading user from json
    public User(UUID id, String firstName, String lastName, String password, String language, String currentLocation) {

        this.id = id;
        this.password = password;
        this.language = language;
        this.currentLocation = currentLocation;
    }

    public boolean authenticate(String password) {

        return this.password.equals(password);
    }

    public void createAccount() {

        System.out.println("Account created for user: " + this.firstName + " " + this.lastName);
    }

    public boolean login(String password) {

        return this.password.equals(password);
    }

    public void selectLanguage(String language) {

        this.language = language;
    }

    public void setAccessibility() {

        System.out.println("Accessibility settings have been configured for user: " + this.firstName + " " + this.lastName);
    }

    public void resetPassword(String newPassword) {

        this.password = newPassword;
        System.out.println("Password has been reset for user: " + this.firstName + " " + this.lastName);
    }

    public void logout() {

        System.out.println("User " + this.firstName + " " + this.lastName + " has logged out.");
    }

    public void deleteAccount() {

        System.out.println("Account deleted for user: " + this.firstName + " " + this.lastName);
    }

    public boolean verifyPhone() {

        System.out.println("Phone number verified for user: " + this.firstName + " " + this.lastName);
        return true;
    }

    public String getFirstName() {

        return firstName;
    }

    public String getLastName() {

        return lastName;
    }

    public int getAge() {

        return age;
    }

    public UUID getId() {

        return id;
    }

    public String getPassword() {

        return password;
    }

    public String getLanguage() {

        return language;
    }

    public String getCurrentLocation() {

        return currentLocation;
    }

    public void setFirstName(String firstName) {
        
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {

        this.lastName = lastName;
    }

    public void setAge(int age) {

        this.age = age;
    }

    public void setPassword(String password) {

        this.password = password;
    }

    public void setLanguage(String language) {

        this.language = language;
    }

    public void setCurrentLocation(String currentLocation) {

        this.currentLocation = currentLocation;
    }
}