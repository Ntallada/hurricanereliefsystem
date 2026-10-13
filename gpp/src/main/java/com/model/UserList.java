package com.model;

import java.util.ArrayList;
import java.util.UUID;

public class UserList {
    private static UserList userList;
    private ArrayList<User> users;

    private UserList() {
        users = DataLoader.getUsers();
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

    public void addUser(String firstName, String lastName, int age, String password, String languageStr, String currentLocation, String phoneNumber, String email) {
        Object langEnum = null;
        try {
            Class<?> innerEnum = Class.forName("com.model.User$Language");
            Object[] constants = innerEnum.getEnumConstants();
            
            for (Object constant : constants) {
                if (constant.toString().equalsIgnoreCase(languageStr)) {
                    langEnum = constant;
                    break;
                }
            }

            if (langEnum == null && constants.length > 0) {
                langEnum = constants[0];
            }

            java.lang.reflect.Constructor<?> constructor = User.class.getConstructor(
                String.class, String.class, int.class, String.class, innerEnum, String.class, String.class, String.class
            );

            User user = (User) constructor.newInstance(firstName, lastName, age, password, langEnum, currentLocation, phoneNumber, email);
            users.add(user);
            saveUsers();
        } catch (Exception e) {
            e.printStackTrace();
        }
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