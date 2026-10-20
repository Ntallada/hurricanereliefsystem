package com.model;

import java.util.ArrayList;
import java.util.UUID;

public class Volunteer extends User {
    private boolean backgroundCheck;
    private ArrayList<Skill> assignedSkills; 
    private ArrayList<String> certifications;
    private boolean available;
    
    // New volunteer (sign up)
    public Volunteer(String firstName, String lastName, int age, String phoneNumber, String email, String password, Languages language, String currentLocation) {
        super(firstName, lastName, age, phoneNumber, email, password, language, currentLocation);
        this.backgroundCheck = false;
        this.assignedSkills = new ArrayList<>();
        this.certifications = new ArrayList<>();
        this.available = true;
    }

    // Loading from JSON
    public Volunteer(UUID id, String firstName, String lastName, int age, String phoneNumber, String email, String password, Languages language, String currentLocation, boolean admin,
        boolean backgroundCheck, ArrayList<Skill> assignedSkills, ArrayList<String> certifications, boolean available) {
        super(id, firstName, lastName, age, phoneNumber, email, password, language, currentLocation, admin);
        this.backgroundCheck = backgroundCheck;
        this.assignedSkills = assignedSkills;
        this.certifications = certifications;
        this.available = available;
    }
    
    /** 
    public boolean claimRequest(ReliefRequest r) {
        if (!available || !backgroundCheck || r.getVolunteerId() != null) {
            return false; 
        }
        r.assignVolunteer(getId());
        r.updateStatus("CLAIMED");
        return true;
    }
    */

    public String getVictimStatus(Victim v) {
        return v.isSafe() ? "Safe" : "Not marked safe";
    }

    public void markUnavailable() {
        available = false;
    }

    public void markAvailable() {
        available = true;
    }
    
    public boolean getBackgroundCheck() {
        return backgroundCheck; 
    }
}
