package com.model;

import java.util.UUID;

public class ShelterManager extends User {

    public ShelterManager(String firstName, String lastName, int age, String phoneNumber,
            String email, String password, Languages language, String currentLocation) {
        super(firstName, lastName, age, phoneNumber, email, password, language, currentLocation);
    }

    public ShelterManager(UUID id, String firstName, String lastName, int age, String phoneNumber,
            String email, String password, Languages language, String currentLocation, boolean admin) {
        super(id, firstName, lastName, age, phoneNumber, email, password, language, currentLocation, admin);
    }
}
