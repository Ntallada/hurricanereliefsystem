package com.model;

public class Shelter {
    private String location;
    private String shelterId;
    private int capacity;
    private int occupancy;
    private String status; 
    private boolean petFriendly;

    public Shelter(String location, String shelterId, int capacity, int occupancy, String status, boolean petFriendly) {
        this.location = location;
        this.shelterId = shelterId;
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

    public String Shelterstatus() {
        return this.status;
    }

    public String shelterStatus() {
        return this.status;
    }

    public int getAvailableSpace() {
        return Math.max(0, this.capacity - this.occupancy);

    }

    public String getMedInfo() {
        return "Medical information for shelter: " + this.location; 
    }

    public String getRelogiousServ() {
        return "Religious services for shelter: " + this.location;

    }

    public String getProtocal() {
        return "Standard shelter protocol";
    }

    // getters/setters i know she talk about these in class

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;

    }

    public String getShelterId() {
        return shelterId;
    }

    public void setShelterId(String shelterId) {
        this.shelterId = shelterId;
    }

    public int getCapacity() {
        return capacity;

    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getOccupancy() {
        return occupancy;

    }

    public void setOccupancy(int occupancy) {
        this.occupancy = occupancy;
    }

    public String getStatus() {
        return status;
        
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isPetFriendly() {
        return petFriendly;

    }

    public void setPetFriendly(boolean petFriendly) {
        this.petFriendly = petFriendly;
    }
}

