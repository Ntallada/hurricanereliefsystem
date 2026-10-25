package com.model;

import java.util.ArrayList;
import java.util.UUID;

public class ShelterList {
    private static ShelterList instance;
    private ArrayList<Shelter> shelters;

    private ShelterList() {
        shelters = DataLoader.getShelters();
        if (shelters == null) {
            shelters = new ArrayList<>();
        }
    }

    public static ShelterList getInstance() {
        if (instance == null) {
            instance = new ShelterList();
        }
        return instance;
    }

    public ArrayList<Shelter> getShelters() {
        return shelters;
    }

    public void addShelter(Shelter shelter) {
        shelters.add(shelter);
        DataWriter.saveShelters(shelters); // Saves to JSON
    }

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
    
    public ArrayList<Shelter> getAvailableShelters() {
        ArrayList<Shelter> availableShelters = new ArrayList<>();
        for (Shelter shelter : shelters) {
            if (shelter.getOccupancy() < shelter.getCapacity() && "OPEN".equalsIgnoreCase(shelter.shelterStatus())) {
                availableShelters.add(shelter);
            }
        }
        return availableShelters;
    }

    public ArrayList<Shelter> getPetFriendlyShelters() {
        ArrayList<Shelter> petFriendlyShelters = new ArrayList<>();
        for (Shelter shelter : shelters) {
            if (shelter.isPetFriendly()) {
                petFriendlyShelters.add(shelter);
            }
        }
        return petFriendlyShelters;
    }

    public
}