package com.model;

import java.util.ArrayList;
import java.util.UUID;

public class UserList {
    private static UserList userList;
    private ArrayList<User> users;

    private UserList() {
        users = Dataloader.getUsers();
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

    public void addUser(String firstName, String lastName, int age, String password, Languages language, String currentLocation) {
        User user = new User(firstName, lastName, age, password, language.toString(), currentLocation);
        users.add(user);
        saveUsers();
    }

    public boolean removeUser(UUID userld) {
        for (User user : users) {
            if (user.getId().equals(userld)) {
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

    public User getUser(String firstName, String lastName) {
        for (User user : users) {
            if (user.getFirstName().equals(firstName) && user.getLastName().equals(lastName)) {
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
        for (User user:users) {
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
