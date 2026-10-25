package com.model;

import java.util.UUID;

public class Shelter {
    private String location;
    private UUID shelterId;
    private int capacity;
    private int occupancy;
    private String status;
    private boolean petFriendly;

    public Shelter(String location, UUID shelterId, int capacity, int occupancy, String status, boolean petFriendly) {
        this.location = location;
        this.shelterId = shelterId;
        this.capacity = capacity;
        this.occupancy = occupancy;
        this.status = status;
        this.petFriendly = petFriendly;
    }

    public Shelter(String location, int capacity, int occupancy, String status, boolean petFriendly) {
        this.location = location;
        this.shelterId = null;
        this.capacity = capacity;
        this.occupancy = occupancy;
        this.status = status;
        this.petFriendly = petFriendly;
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
    public String getLocation() {
        return location;
    }

    @Override 
    public String toString() {
        return location + " (" + occupancy + "/" + capacity + ")";
    }
}
