package com.model;

import java.util.ArrayList;
import java.util.UUID;

public class Volunteer extends User {
    private boolean backgroundCheck;
    private ArrayList<Skill> assignedSkills; 
    private ArrayList<String> certifications;
    private boolean available;
    private String vehicle;
    
    /** 
     * New volunteer (sign up)
     * @param firstName volunteer's first name
     * @param lastName volunteer's last name
     * @param age volunteer's age
     * @param phoneNumber volunteer's phone number
     * @param email volunteer's email
     * @param password volunteer's password
     * @param language volunteer's language
     * @param currentLocation volunteer's location
     */
    public Volunteer(String firstName, String lastName, int age, String phoneNumber, String email, String password, Languages language, String currentLocation) {
        super(firstName, lastName, age, phoneNumber, email, password, language, currentLocation);
        this.backgroundCheck = false;
        this.assignedSkills = new ArrayList<>();
        this.certifications = new ArrayList<>();
        this.available = true;
        this.vehicle = "";
    }

    /**
    * Loads an existing volunteer from JSON.
    * @param id volunteer's ID
    * @param firstName volunteer's first name
    * @param lastName volunteer's last name
    * @param age volunteer's age
    * @param phoneNumber volunteer's phone number
    * @param email volunteer's email
    * @param password volunteer's password
    * @param language volunteer's language
    * @param currentLocation volunteer's location
    * @param admin admin status
    * @param backgroundCheck background check status
    * @param assignedSkills volunteer's skills
    * @param certifications volunteer's certifications
    * @param available availability status
    */
    public Volunteer(UUID id, String firstName, String lastName, int age, String phoneNumber, String email, String password, Languages language, String currentLocation, boolean admin,
        boolean backgroundCheck, ArrayList<Skill> assignedSkills, ArrayList<String> certifications, boolean available) {
        super(id, firstName, lastName, age, phoneNumber, email, password, language, currentLocation, admin);
        this.backgroundCheck = backgroundCheck;
        this.assignedSkills = assignedSkills;
        this.certifications = certifications;
        this.available = available;
        this.vehicle = "";
    }

    /**
    * Gets the safety status of a victim.
    * @param v the victim
    * @return the victim's safety status
    */
    public String getVictimStatus(Victim v) {
        return v.isSafe() ? "Safe" : "Not marked safe";
    }

    /**
    * Marks the volunteer as unavailable.
    */
    public void markUnavailable() {
        available = false;
    }

    /**
    * Marks the volunteer as available.
    */
    public void markAvailable() {
        available = true;
    }
    
    /**
    * Gets the volunteer's background check status.
    * @return true if the background check is complete
    */
    public boolean getBackgroundCheck() {
        return backgroundCheck; 
    }

    /**
    * Gets the volunteer's assigned skills.
    * @return a list of assigned skills
    */
    public ArrayList<Skill> getAssignedSkills() {
        return new ArrayList<>(assignedSkills);
    }

    /**
    * Adds a skill to the volunteer.
    * @param skill the skill to add
    */
    public void addSkill(Skill skill) {
        if (skill != null && !assignedSkills.contains(skill)) {
            assignedSkills.add(skill);
        }
    }

    /**
    * Gets the volunteer's vehicle.
    * @return the vehicle
    */
    public String getVehicle() {
        return vehicle;
    }

    /**
    * Sets the volunteer's vehicle.
    * @param vehicle the vehicle to assign
    */
    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }
}
