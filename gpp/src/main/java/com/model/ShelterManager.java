package com.model;

import java.util.UUID;

public class ShelterManager extends User {

    /**
     * Creates a new shelter manager.
     * @param firstName manager's first name
     * @param lastName manager's last name
     * @param age manager's age
     * @param phoneNumber manager's phone number
     * @param email manager's email
     * @param password manager's password
     * @param language manager's language
     * @param currentLocation manager's location
    */
    public ShelterManager(String firstName, String lastName, int age, String phoneNumber,
            String email, String password, Languages language, String currentLocation) {
        super(firstName, lastName, age, phoneNumber, email, password, language, currentLocation);
    }

    /**
     * Loads an existing shelter manager from JSON.
     * @param id manager's ID
     * @param firstName manager's first name
     * @param lastName manager's last name
     * @param age manager's age
     * @param phoneNumber manager's phone number
     * @param email manager's email
     * @param password manager's password
     * @param language manager's language
     * @param currentLocation manager's location
     * @param admin admin status
    */
    public ShelterManager(UUID id, String firstName, String lastName, int age, String phoneNumber,
            String email, String password, Languages language, String currentLocation, boolean admin) {
        super(id, firstName, lastName, age, phoneNumber, email, password, language, currentLocation, admin);
    }
}
