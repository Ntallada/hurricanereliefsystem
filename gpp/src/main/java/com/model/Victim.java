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

    public createReliefRequest(String victmName, String location, String aidType, String urgencyLevel) {
        return null;
    }

    public editReliefRequest(ReliefRequest r) {
        return null;
    }

    public deleteReliefRequest(ReliefRequest r) {
        return null;
    }

    public viewReliefRequest(ReliefRequest r) {
        return null;
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

    public boolean markSafe() {
        this.safe = true;
    }
}