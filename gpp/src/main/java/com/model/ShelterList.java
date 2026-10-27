package com.model;

import java.util.ArrayList;
import java.util.UUID;

public class ShelterList {
    private static ShelterList instance;
    private ArrayList<Shelter> shelters;

    /**
     * Loads shelters from JSON into the shelter list.
    */
    private ShelterList() {
        shelters = DataLoader.getShelters();
        if (shelters == null) {
            shelters = new ArrayList<>();
        }
    }

    /**
     * Gets the shared ShelterList instance.
     * @return the ShelterList instance
    */
    public static ShelterList getInstance() {
        if (instance == null) {
            instance = new ShelterList();
        }
        return instance;
    }

    /**
     * Gets all shelters.
     * @return the list of shelters
    */
    public ArrayList<Shelter> getShelters() {
        return shelters;
    }

    /**
     * Adds a shelter and saves the updated list.
     * @param shelter the shelter to add
    */
    public void addShelter(Shelter shelter) {
        shelters.add(shelter);
        DataWriter.saveShelters(shelters); // Saves to JSON
    }

    /**
     * Removes a shelter by its ID.
     * @param shelterId the ID of the shelter
     * @return true if the shelter was removed
    */
    public boolean removeShelter(UUID shelterId) {
        Shelter found = getShelterById(shelterId);
        if (found == null) {
            return false;
        }
        boolean removed = shelters.remove(found);
        if (removed) {
            DataWriter.saveShelters(shelters); // updates JSON on remove
        }
        return removed;
    }

    /**
     * Finds a shelter by its ID.
     * @param shelterId the ID to search for
     * @return the shelter or null if not found
    */
    public Shelter getShelterById(UUID shelterId) {
        if (shelterId == null) {
            return null;
        }
        String id = shelterId.toString();
        for (Shelter shelter : shelters) {
            if (shelter.getShelterId() != null && shelter.getShelterId().toString().equals(id)) {
                return shelter;
            }
        }
        return null;
    }
    
    /**
     * Gets all open shelters with available space.
     * @return a list of available shelters
    */
    public ArrayList<Shelter> getAvailableShelters() {
        ArrayList<Shelter> availableShelters = new ArrayList<>();
        for (Shelter shelter : shelters) {
            if (shelter.getOccupancy() < shelter.getCapacity() && "OPEN".equalsIgnoreCase(shelter.shelterStatus())) {
                availableShelters.add(shelter);
            }
        }
        return availableShelters;
    }

    /**
     * Gets all pet-friendly shelters.
     * @return a list of pet-friendly shelters
    */
    public ArrayList<Shelter> getPetFriendlyShelters() {
        ArrayList<Shelter> petFriendlyShelters = new ArrayList<>();
        for (Shelter shelter : shelters) {
            if (shelter.isPetFriendly()) {
                petFriendlyShelters.add(shelter);
            }
        }
        return petFriendlyShelters;
    }
}