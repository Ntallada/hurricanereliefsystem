package com.model;

import java.util.ArrayList;
import java.util.UUID;

public class UserList {
    private static UserList userList;
    private ArrayList<User> users;

    /**
     * Loads users from JSON into the user list.
    */
    private UserList() {
        users = DataLoader.loadUsers();
        if (users == null) {
            users = new ArrayList<>();
        }
    }

    /**
     * Gets the shared UserList instance.
     * @return the UserList instance
    */
    public static UserList getInstance() {
        if (userList == null) {
            userList = new UserList();
        }
        return userList;
    }

    /**
     * Gets all users.
     * @return the list of users
    */
    public ArrayList<User> getUsers() {
        return users;
    }

    /**
     * Adds a user and saves the updated list.
     * @param user the user to add
    */
    public void addUser(User user) {
        users.add(user);
        saveUsers();
    }

    /**
     * Finds a user by their email.
     * @param email the email to search for
     * @return the user or null if not found
    */
    public User getUserByEmail(String email) {
        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Creates and adds a new user.
     * @param type type of user
     * @param firstName user's first name
     * @param lastName user's last name
     * @param age user's age
     * @param phoneNumber user's phone number
     * @param email user's email
     * @param password user's password
     * @param language user's language
     * @param currentLocation user's location
     * @return true if the user was added successfully
    */
    public boolean addUser(String type, String firstName, String lastName, int age, String phoneNumber, String email, String password, Languages language, String currentLocation) {
        if (getUserByEmail(email) != null) {
            return false;
        }
        if ("VICTIM".equalsIgnoreCase(type)) {
            users.add(new Victim(firstName, lastName, age, phoneNumber, email, password, language, currentLocation));
        } else if ("VOLUNTEER".equalsIgnoreCase(type)) {
            users.add(new Volunteer(firstName, lastName, age, phoneNumber, email, password, language, currentLocation));
        } else if ("MANAGER".equalsIgnoreCase(type)) {
            users.add(new ShelterManager(firstName, lastName, age, phoneNumber, email, password, language, currentLocation));
        } else {
            return false;
        }
        saveUsers();
        return true;
    }

    /**
     * Removes a user by their ID.
     * @param userId the ID of the user
     * @return true if the user was removed
    */
    public boolean removeUser(UUID userId) {
        for (User user : users) {
            if (user.getId().equals(userId)) {
                users.remove(user);
                saveUsers();
                return true;
            }
        }
        return false;
    }

    /**
     * Finds a user by their ID.
     * @param userId the ID to search for
     * @return the user or null if not found
    */
    public User getUserById(UUID userId) {
        for (User user : users) {
            if (user.getId().equals(userId)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Finds a user using their first name and password.
     * @param firstName user's first name
     * @param password user's password
     * @return the user or null if not found
    */
    public User getUser(String firstName, String password) {
        for (User user : users) {
            if (user.getFirstName().equalsIgnoreCase(firstName) && user.login(password)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Gets all volunteers.
     * @return a list of volunteers
    */
    public ArrayList<Volunteer> getVolunteers() {
        ArrayList<Volunteer> volunteers = new ArrayList<>();
        for (User user : users) {
            if (user instanceof Volunteer) {
                volunteers.add((Volunteer) user);
            }
        }
        return volunteers;
    }

    /**
     * Gets all victims.
     * @return a list of victims
    */
    public ArrayList<Victim> getVictims() {
        ArrayList<Victim> victims = new ArrayList<>();
        for (User user : users) {
            if (user instanceof Victim) {
                victims.add((Victim) user);
            }
        }
        return victims;
    }

    /**
     * Saves all users to JSON.
    */
    public void saveUsers() {
        DataWriter.saveUsers(users);
    }

}