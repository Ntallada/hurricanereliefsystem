package com.model;

import java.util.ArrayList;

public class userList {
    private ArrayList<User> users;

    public void addUser(User user) {
        users.add(user);
    }

    public void addUser(String firstName, String lastName, int age, String phoneNumber, String email, String password, enum language, String currentLocation) {
        User user = new User(firstName, lastName, age, phoneNumber, email, password, language, currentLocation);
        users.add(user);
    }

    public boolean removeUser(UUID userld) {
        for (User user : users) {
            if (user.getUserId().equals(userld)) {
                users.remove(user);
                return true;
            }
        }
        return false;
    }
    //?? for getuserByld

    public ArrayList<Volunteer> getVolunteers() {
        return volunteers;
    }

    public ArrayList<Victim> getVictims() {
        return victims;
    }

}
