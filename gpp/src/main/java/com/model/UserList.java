package com.model;

import java.util.ArrayList;
import java.util.UUID;

public class UserList {
    private static UserList userList;
    private ArrayList<User> users;

    private UserList() {
        users = DataLoader.loadUsers();
        if (users == null) {
            users = new ArrayList<>();
        }
    }

    public static UserList getInstance() {
        if (userList == null) {
            userList = new UserList();
        }
        return userList;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void addUser(User user) {
        users.add(user);
        saveUsers();
    }

    public User getUserByEmail(String email) {
        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                return user;
            }
        }
        return null;
    }

    public boolean addUser(String type, String firstName, String lastName, int age,
                           String phoneNumber, String email, String password,
                           Languages language, String currentLocation) {
        if (getUserByEmail(email) != null) {
            return false;
        }
        if ("VICTIM".equalsIgnoreCase(type)) {
            users.add(new Victim(firstName, lastName, age, phoneNumber, email,
                    password, language, currentLocation));
        } else if ("VOLUNTEER".equalsIgnoreCase(type)) {
            users.add(new Volunteer(firstName, lastName, age, phoneNumber, email,
                    password, language, currentLocation));
        } else {
            return false;
        }
        saveUsers();
        return true;
    }

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

    public User getUserById(UUID userId) {
        for (User user : users) {
            if (user.getId().equals(userId)) {
                return user;
            }
        }
        return null;
    }

    public User getUser(String firstName, String password) {
        for (User user : users) {
            if (user.getFirstName().equalsIgnoreCase(firstName) && user.login(password)) {
                return user;
            }
        }
        return null;
    }

    public ArrayList<Volunteer> getVolunteers() {
        ArrayList<Volunteer> volunteers = new ArrayList<>();
        for (User user : users) {
            if (user instanceof Volunteer) {
                volunteers.add((Volunteer) user);
            }
        }
        return volunteers;
    }

    public ArrayList<Victim> getVictims() {
        ArrayList<Victim> victims = new ArrayList<>();
        for (User user : users) {
            if (user instanceof Victim) {
                victims.add((Victim) user);
            }
        }
        return victims;
    }

    public void saveUsers() {
        DataWriter.saveUsers(users);
    }


}