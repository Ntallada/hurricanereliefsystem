package com.model;
 
import java.util.Random;

public class Driver {
    
    public static void main(String[] args) {
        System.out.println(" Testing Data Loader ");
        UserList userList = UserList.getInstance();
        System.out.println("Loaded " + userList.getUsers().size() + " users from JSON.");

/**
 * testing creating an account. use git restore gpp/json/users.json to reset test
 */
        System.out.println("\n Testing sign up and create user");
        boolean created = userList.addUser("VICTIM", "John", "Does", 21, "8035551234", "john.does@example.com", "password123", Languages.ENGLISH, "Columbia, SC");
        if (created) {
            System.out.println("User added and written to users.json successfully!");
        }
        System.out.println("\n Testing login");
        User loggedInUser = userList.getUser("John", "password123");

        if (loggedInUser != null) {
            System.out.println("User logged in successfully: " + loggedInUser.getFirstName() + " " + loggedInUser.getLastName());
            System.out.println("Current Location: " + loggedInUser.getCurrentLocation());
        } else {
            System.out.println("Login failed. Invalid username or password.");
        }

        System.out.println("\n Testing failed login");
        User failedUser = userList.getUser("john", "wrongPassword");
        if (failedUser == null) {
            System.out.println(" rejected bad password.");
        }
        /**
         * logout method
         */
        System.out.println("\n Testing logout");
        HurricaneReliefFacade facade = HurricaneReliefFacade.getInstance();
        facade.login("john.doe@example.com", "password123");
        System.out.println("Logged in as: " + facade.getCurrentUser().getFirstName());
        facade.logout();
        System.out.println("Logging out: " + facade.getCurrentUser());
    }

    /** 
    Random rand = new Random();
    User picked = userList.getUsers().get(rand.nextInt(userList.getUsers().size()));
    */
}

