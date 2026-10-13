package com.model;

import java.util.ArrayList;
import java.util.UUID;

public class ShelterList {
    private ArrayList<Shelter> shelters;

    public ShelterList() {
        shelters = new ArrayList<>();
    }

    public void addShelter(Shelter shelter) {
        shelters.add(shelter);
    }

    public boolean removeShelter(UUID shelterId) {
        return false; // TODO
    }

    public Shelter getShelterById(UUID shelterId) {
        return null; // TODO
    }
    
    public ArrayList<Shelter> getAvailableShelters() {
        return new ArrayList<>(); // TODO
    }

    public ArrayList<Shelter> getPetFriendlyShelters() {
        return new ArrayList<>(); // TODO
    }
}