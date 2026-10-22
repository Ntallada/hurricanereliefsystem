package com.model;

import java.util.ArrayList;
import java.util.UUID;

public class Victim extends User {
    private ArrayList<String> guest;
    private ArrayList<String> member;
    private Pet pet;
    private boolean safe;

    public Victim(String firstName, String lastName, int age, String phoneNumber, String email, String password, Languages language, String currentLocation) {
        super(firstName, lastName, age, phoneNumber, email, password, language, currentLocation);
        this.guest = new ArrayList<>();
        this.member = new ArrayList<>();
        this.pet = null;
        this.safe = false;
    }

    public Victim(UUID userId, String firstName, String lastName, int age, String phoneNumber, String email, String password, Languages language, String currentLocation, boolean admin, ArrayList<String> guest, ArrayList<String> member, Pet pet, boolean safe) {
        super(userId, firstName, lastName, age, phoneNumber, email, password, language, currentLocation, admin);
        this.guest = guest;
        this.member = member;
        this.pet = pet;
        this.safe = safe;
    }

    public ReliefRequest createReliefRequest(String victimName, String location,
        String specialRequests, UrgencyLevel urgencyLevel,
        boolean petNeeds) {
return new ReliefRequest(this, victimName, location, specialRequests, urgencyLevel, petNeeds);
}
    
    public void markSafe() {
        this.safe = true;
    }

    public boolean isSafe() {
        return safe;
    }

    public ArrayList<String> getGuest(){
        return guest; 
    }

    public ArrayList<String> getMember() {
        return member; 
    }

    public Pet getPet() {
        return pet; 
    }
    public void setPet(Pet pet) {
        this.pet = pet; 
    }
    
}