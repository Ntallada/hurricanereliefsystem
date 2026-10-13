package com.model;

import java.util.ArrayList;
import java.util.UUID;

public class Victim extends User {
    private ArrayList<String> guest;
    private ArrayList<String> member;
    private Pet pet;
    private boolean safe;
    private ArrayList<ReliefRequest> requests; //not in uml but added to use for viewrequesthistory and deleterequest
    private int rating; //not in uml but added to use for rateexperience

    public Victim(String firstName, String lastName, int age, String phoneNumber, String email, String password, Languages language, String currentLocation) {
        super(firstName, lastName, age, phoneNumber, email, password, language, currentLocation);
        this.guest = new ArrayList<>();
        this.member = new ArrayList<>();
        this.requests = new ArrayList<>();
        this.pet = null;
        this.safe = false;
        this.rating = 0;
    }

    public Victim(UUID userId, String firstName, String lastName, int age, String phoneNumber, String email, String password, Languages language, String currentLocation, boolean admin, ArrayList<String> guest, ArrayList<String> member, Pet pet, boolean safe) {
        super(userId, firstName, lastName, age, phoneNumber, email, password, language, currentLocation, admin);
        this.guest = guest;
        this.member = member;
        this.requests = new ArrayList<>();
        this.pet = pet;
        this.safe = safe;
        this.requests = new ArrayList<>();
        this.rating = 0;
    }

    public createReliefRequest(String victimName, String location, String aidType, String urgencyLevel) {
        return null;
    }

    public editReliefRequest(ReliefRequest r) {
        return null;
    }

    public deleteReliefRequest(ReliefRequest r) {
        return requests.remove(r);
    }

    public viewReliefRequest(ReliefRequest r) {
        return requests.contains(r) ? r : null; 
    }

    public viewReliefRequestStatus(ReliefRequest r) {
        return null;
    }

    public requestVolunteer() {
        return null;
    }

    public viewRequestHistory() {
        return null;
    }

    public rateExperience() {
        return null;
    }

    public void markSafe() {
        this.safe = true;
    }
    public boolean isSafe() {
        return this.safe;
    }
}