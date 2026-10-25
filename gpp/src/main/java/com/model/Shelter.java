package com.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Shelter {
    private String name;
    private String location;
    private UUID shelterId;
    private int capacity;
    private int occupancy;
    private String status;
    private boolean petFriendly;
    private boolean hasMedicalStaff;
    private ArrayList<String> occupants;
    private ArrayList<String> pets;

    public Shelter(String location, UUID shelterId, int capacity, int occupancy, String status, boolean petFriendly) {
        this(location, location, shelterId, capacity, occupancy, status, petFriendly, false,
                new ArrayList<>(), new ArrayList<>());
    }

    public Shelter(String name, String location, UUID shelterId, int capacity, int occupancy,
            String status, boolean petFriendly, boolean hasMedicalStaff) {
        this(name, location, shelterId, capacity, occupancy, status, petFriendly, hasMedicalStaff,
                new ArrayList<>(), new ArrayList<>());
    }

    public Shelter(String name, String location, UUID shelterId, int capacity, int occupancy,
            String status, boolean petFriendly, boolean hasMedicalStaff,
            ArrayList<String> occupants, ArrayList<String> pets) {
        this.name = name;
        this.location = location;
        this.shelterId = shelterId;
        this.capacity = capacity;
        this.occupancy = occupancy;
        this.status = status;
        this.petFriendly = petFriendly;
        this.hasMedicalStaff = hasMedicalStaff;
        this.occupants = occupants;
        this.pets = pets;
    }

    public Shelter(String location, int capacity, int occupancy, String status, boolean petFriendly) {
        this(location, location, null, capacity, occupancy, status, petFriendly, false);
    }

    public String shelterStatus() {
        return status;
    }

    public UUID getShelterId() {
        return shelterId;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getOccupancy() {
        return occupancy;
    }

    public boolean isPetFriendly() {
        return petFriendly;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public boolean hasMedicalStaff() {
        return hasMedicalStaff;
    }

    public void setHasMedicalStaff(boolean hasMedicalStaff) {
        this.hasMedicalStaff = hasMedicalStaff;
    }

    public int getAvailableSpaces() {
        return Math.max(0, capacity - occupancy);
    }

    public List<String> getOccupants() {
        return List.copyOf(occupants);
    }

    public List<String> getPets() {
        return List.copyOf(pets);
    }

    public boolean checkIn(List<String> familyMembers, String petName, String petType) {
        if (familyMembers == null || familyMembers.isEmpty()
                || getAvailableSpaces() < familyMembers.size()
                || (petName != null && (!petFriendly || petType == null || petType.isBlank()))) {
            return false;
        }
        occupants.addAll(familyMembers);
        occupancy += familyMembers.size();
        if (petName != null) {
            pets.add(petName + " (" + petType + ")");
        }
        return true;
    }

    @Override 
    public String toString() {
        return name + " - " + location + " (" + occupancy + "/" + capacity + ")";
    }
}
