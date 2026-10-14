package com.model;

public class Shelter {
    private String location;
    private String shelterID;
    private int capacity;
    private int occupancy;
    private String status;
    private boolean petFriendly;

    public Shelter(String location, String shelterID, int capacity, int occupancy, String status, boolean petFriendly) {
        this.location = location;
        this.shelterID = shelterID;
        this.capacity = capacity;
        this.occupancy = occupancy;
        this.status = status;
        this.petFriendly = petFriendly;
    }

    public Shelter(String location, int capacity, int occupancy, String status, boolean petFriendly) {
        this.location = location;
        this.capacity = capacity;
        this.occupancy = occupancy;
        this.status = status;
        this.petFriendly = petFriendly;
    }

    public shelterStatus() {
        
    }
}
