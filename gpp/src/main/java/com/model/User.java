package com.model;

import java.util.UUID;

public class User {

    private String firstName;
    private String lastName;
    private int age;
    private UUID id;
    private String password;
    private Languages language;
    private String currentLocation;
    private boolean admin;
    private String phoneNumber;
    private String email;


    /**
     * Creates a new user with a random ID.
     * @param firstName user's first name
     * @param lastName user's last name
     * @param age user's age
     * @param phoneNumber user's phone number
     * @param email user's email
     * @param password user's password
     * @param language user's language
     * @param currentLocation user's location
    */
    public User(String firstName, String lastName, int age, String phoneNumber, String email, String password, Languages language, String currentLocation) {
        this(UUID.randomUUID(), firstName, lastName, age, phoneNumber, email, password, language, currentLocation, false);
    }

    /**
     * Loads an existing user from JSON.
     * @param id user's ID
     * @param firstName user's first name
     * @param lastName user's last name
     * @param age user's age
     * @param phoneNumber user's phone number
     * @param email user's email
     * @param password user's password
     * @param language user's language
     * @param currentLocation user's location
     * @param admin admin status
    */
    public User(UUID id, String firstName, String lastName, int age, String phoneNumber, String email, String password, Languages language, String currentLocation, boolean admin) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.language = language;
        this.currentLocation = currentLocation;
        this.admin = admin;
    }

    /**
     * Checks if the password is correct.
     * @param password password to check
     * @return true if the password matches
    */
    public boolean authenticate(String password) {

        return this.password.equals(password);
    }

    /**
     * Displays an account creation message.
    */
    public void createAccount() {

        System.out.println("Account created for user: " + this.firstName + " " + this.lastName);
    }

    /**
     * Checks the user's login password.
     * @param password password to check
     * @return true if the password matches
    */
    public boolean login(String password) {

        return this.password.equals(password);
    }

    /**
     * Selects the user's preferred language.
     * @param language language to select
    */
    public void selectLanguage(Languages language) {

        this.language = language;
    }

    /**
     * Displays an accessibility settings message.
    */
    public void setAccessibility() {

        System.out.println("Accessibility settings have been configured for user: " + this.firstName + " " + this.lastName);
    }

    /**
     * Resets the user's password.
     * @param newPassword new password to set
    */
    public void resetPassword(String newPassword) {

        this.password = newPassword;
        System.out.println("Password has been reset for user: " + this.firstName + " " + this.lastName);
    }

    /**
     * Displays a logout message.
    */
    public void logout() {

        System.out.println("User " + this.firstName + " " + this.lastName + " has logged out.");
    }

    /**
     * Displays an account deletion message.
     */
    public void deleteAccount() {

        System.out.println("Account deleted for user: " + this.firstName + " " + this.lastName);
    }

    /**
     * Displays a phone verification message.
     * @return true
    */
    public boolean verifyPhone() {

        System.out.println("Phone number verified for user: " + this.firstName + " " + this.lastName);
        return true;
    }

    /**
     * Checks if the user is an admin.
     * @return true if the user is an admin
     */
    public boolean isAdmin() {

        return admin;
    }

    /**
     * Gets the user's first name.
     * @return first name
    */
    public String getFirstName() {

        return firstName;
    }

    /**
     * Gets the user's last name.
     * @return last name
     */
    public String getLastName() {

        return lastName;
    }

    /**
     * Gets the user's age.
     * @return age
     */
    public int getAge() {

        return age;
    }

    /**
     * Gets the user's ID.
     * @return user ID
    */
    public UUID getId() {

        return id;
    }

    /**
     * Gets the user's password.
     * @return password
    */
    public String getPassword() {

        return password;
    }

    /**
     * Gets the user's language.
     * @return language
    */
    public Languages getLanguage() {

        return language;
    }

    /**
     * Gets the user's current location.
     * @return current location
    */
    public String getCurrentLocation() {

        return currentLocation;
    }

    /**
     * Gets the user's admin status.
     * @return true if the user is an admin
    */
    public boolean getAdmin() {

        return admin;
    }

    /**
     * Gets the user's phone number.
     * @return phone number
    */
    public String getPhoneNumber() {

        return phoneNumber;
    }

    /**
     * Gets the user's email.
     * @return email
    */
    public String getEmail() {

        return email;
    }

    /**
     * Sets the user's first name.
     * @param firstName new first name
    */
    public void setFirstName(String firstName) {
        
        this.firstName = firstName;
    }

    /**
     * Sets the user's last name.
     * @param lastName new last name
     */
    public void setLastName(String lastName) {

        this.lastName = lastName;
    }

    /**
     * Sets the user's age.
     * @param age new age
     */
    public void setAge(int age) {

        this.age = age;
    }

    /**
     * Sets the user's password.
     * @param password new password
     */
    public void setPassword(String password) {

        this.password = password;
    }

    /**
     * Sets the user's language.
     * @param language new language
    */
    public void setLanguage(Languages language) {

        this.language = language;
    }

    /**
     * Sets the user's current location.
     * @param currentLocation new location
    */
    public void setCurrentLocation(String currentLocation) {

        this.currentLocation = currentLocation;
    }

    /**
     * Sets the user's admin status.
     * @param admin new admin status
    */
    public void setAdmin(boolean admin) {

        this.admin = admin;
    }

    /**
     * Sets the user's phone number.
     * @param phoneNumber new phone number
    */
    public void setPhoneNumber(String phoneNumber) {

        this.phoneNumber = phoneNumber;
    }

    /**
     * Sets the user's email.
     * @param email new email
    */
    public void setEmail(String email) {

        this.email = email;
    }
}