package com.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

// Represents an emergency shelter location, tracking its capacity, occupancy, status, medical staff, and accommodations.
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

    // Constructs a Shelter with basic details, defaulting name to location, medical staff to false, and empty lists.
    public Shelter(String location, UUID shelterId, int capacity, int occupancy, String status, boolean petFriendly) {
        this(location, location, shelterId, capacity, occupancy, status, petFriendly, false,
                new ArrayList<>(), new ArrayList<>());
    }

    // Constructs a Shelter with custom name, location, medical staff status, and default empty lists.
    public Shelter(String name, String location, UUID shelterId, int capacity, int occupancy,
            String status, boolean petFriendly, boolean hasMedicalStaff) {
        this(name, location, shelterId, capacity, occupancy, status, petFriendly, hasMedicalStaff,
                new ArrayList<>(), new ArrayList<>());
    }

    // Constructs a fully detailed Shelter with specified occupants and pets lists.
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

    // Constructs a basic Shelter without a pre-assigned UUID.
    public Shelter(String location, int capacity, int occupancy, String status, boolean petFriendly) {
        this(location, location, null, capacity, occupancy, status, petFriendly, false);
    }

    // Returns the current status string of the shelter (e.g., OPEN).
    public String shelterStatus() {
        return status;
    }

    // Gets the unique shelter ID.
    public UUID getShelterId() {
        return shelterId;
    }

    // Gets the maximum capacity of the shelter.
    public int getCapacity() {
        return capacity;
    }

    // Gets the current number of occupants.
    public int getOccupancy() {
        return occupancy;
    }

    // Checks if the shelter is pet-friendly.
    public boolean isPetFriendly() {
        return petFriendly;
    }

    // Gets the name of the shelter.
    public String getName() {
        return name;
    }

    // Gets the physical address or location of the shelter.
    public String getLocation() {
        return location;
    }

    // Checks if medical staff are present at the shelter.
    public boolean hasMedicalStaff() {
        return hasMedicalStaff;
    }

    // Sets whether medical staff are present.
    public void setHasMedicalStaff(boolean hasMedicalStaff) {
        this.hasMedicalStaff = hasMedicalStaff;
    }

    // Calculates available spaces remaining, ensuring it never returns a negative number.
    public int getAvailableSpaces() {
        return Math.max(0, capacity - occupancy);
    }

    // Returns an unmodifiable copy of the occupants list.
    public List<String> getOccupants() {
        return List.copyOf(occupants);
    }

    // Returns an unmodifiable copy of the checked-in pets list.
    public List<String> getPets() {
        return List.copyOf(pets);
    }

    // Checks in family members and an optional pet if capacity and shelter rules permit.
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

    // Returns a formatted string representation of the shelter.
    @Override 
    public String toString() {
        return name + " - " + location + " (" + occupancy + "/" + capacity + ")";
    }
}