package com.model;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Represents a victim who needs help during a hurricane.
*/
public class Victim extends User {
    private ArrayList<String> guest;
    private ArrayList<String> member;
    private Pet pet;
    private boolean safe;

    /**
     * Creates a new victim.
     * @param firstName victim's first name
     * @param lastName victim's last name
     * @param age victim's age
     * @param phoneNumber victim's phone number
     * @param email victim's email
     * @param password victim's password
     * @param language victim's language
     * @param currentLocation victim's location
    */
    public Victim(String firstName, String lastName, int age, String phoneNumber, String email, String password, Languages language, String currentLocation) {
        super(firstName, lastName, age, phoneNumber, email, password, language, currentLocation);
        this.guest = new ArrayList<>();
        this.member = new ArrayList<>();
        this.pet = null;
        this.safe = false;
    }

    /**
     * Loads an existing victim from JSON.
     * @param userId victim's ID
     * @param firstName victim's first name
     * @param lastName victim's last name
     * @param age victim's age
     * @param phoneNumber victim's phone number
     * @param email victim's email
     * @param password victim's password
     * @param language victim's language
     * @param currentLocation victim's location
     * @param admin admin status
     * @param guest list of guests
     * @param member list of family members
     * @param pet victim's pet
     * @param safe victim's safety status
    */
    public Victim(UUID userId, String firstName, String lastName, int age, String phoneNumber, String email, String password, Languages language, String currentLocation, boolean admin, ArrayList<String> guest, ArrayList<String> member, Pet pet, boolean safe) {
        super(userId, firstName, lastName, age, phoneNumber, email, password, language, currentLocation, admin);
        this.guest = guest;
        this.member = member;
        this.pet = pet;
        this.safe = safe;
    }

    /**
     * Creates a relief request for a victim.
     * @param victimName name of the victim
     * @param location victim's location
     * @param aidType type of help needed
     * @param specialRequests additional requests
     * @param urgencyLevel urgency of the request
     * @param numberOfPeople number of people needing help
     * @param medicalNeeded whether medical assistance is needed
     * @param petName name of the pet
     * @param petType type of pet
     * @return the new relief request
    */
    public ReliefRequest createReliefRequest(String victimName, String location, String aidType, String specialRequests, UrgencyLevel urgencyLevel,
            int numberOfPeople, boolean medicalNeeded,
            String petName, String petType) {
        return new ReliefRequest(this, victimName, location, aidType, specialRequests,
                urgencyLevel, numberOfPeople, medicalNeeded, petName, petType);
    }

    /**
     * Marks the victim as safe.
    */
    public void markSafe() {
        this.safe = true;
    }

    /**
     * Checks if the victim is safe.
     * @return true if the victim is safe
    */
    public boolean isSafe() {
        return safe;
    }

    /**
     * Gets the victim's guests.
     * @return list of guests
    */
    public ArrayList<String> getGuest() {
        return guest;
    }

    /**
     * Gets the victim's family members.
     * @return list of family members
    */
    public ArrayList<String> getMember() {
        return member;
    }

    /**
     * Gets the victim's pet.
     * @return the victim's pet
    */
    public Pet getPet() {
        return pet;
    }

    /**
     * Sets the victim's pet.
     * @param pet the pet to assign
    */
    public void setPet(Pet pet) {
        this.pet = pet;
    }
}