package com.model;

import java.util.ArrayList;

/**
 * Represents a hurricane with its properties and behaviors.
 * Provides methods to track and update hurricane information.
 */

public class Hurricane {
    
    private String name;
    private int category;
    private double windSpeed;
    private ArrayList<String> affectedZipCodes;
    private String location;
    private ArrayList<String> affectedAreas;
    private String condition;
    private String terrain;

    public Hurricane(String name, int category, double windSpeed, ArrayList<String> affectedZipCodes, String location, ArrayList<String> affectedAreas, String condition, String terrain) {

        this.name = name;
        this.category = category;
        this.windSpeed = windSpeed;
        this.affectedZipCodes = affectedZipCodes;
        this.location = location;
        this.affectedAreas = affectedAreas;
        this.condition = condition;
        this.terrain = terrain;
    }

    //Gets a summary update string of the hurricane's status.
    public String getUpdates() {
        return name + " is currently a Category " + category + " hurricane with wind speeds of " + windSpeed + " mph.";
    }

    // Prints out a tracking message indicating the current position of the hurricane.
    public void trackPath() {
        System.out.println("Tracking " + name + " near " + location);
    }

    // Returns the current category of the hurricane.
    public int getCategory() {
        return category;
    }

    // Updates the wind speed of the hurricane.
    public void updateWindSpeed(double newSpeed) {
        this.windSpeed = newSpeed;
    }

    // Gets the list of affected areas.
    public ArrayList<String> getAffectedAreas() {
        return affectedAreas;
    }

    // Gets the current location of the hurricane.
    public String getCurrentLocation() {
        return location;
    }

    // Prints out the list of affected areas to the console.
    public void findAffectedAreas() {
        System.out.println("Affected areas: " + affectedAreas);
    }

    // Gets the name of the hurricane.
    public String getName() {
        return name;
    }

    // Gets the current wind speed.
    public double getWindSpeed() {
        return windSpeed;
    }

    // Gets the affected zip codes.
    public ArrayList<String> getAffectedZipCodes() {
        return affectedZipCodes;
    }

    // Gets the location of the hurricane.
    public String getLocation() {
        return location;
    }

    // Gets the weather condition description.
    public String getCondition() {
        return condition;
    }

    // Gets the terrain type.
    public String getTerrain() {
        return terrain;
    }

    // Sets the name of the hurricane.
    public void setName(String name) {
        this.name = name;
    }

    // Sets the category of the hurricane.
    public void setCategory(int category) {
        this.category = category;
    }

    // Sets the wind speed of the hurricane.
    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    // Sets the affected zip codes list.
    public void setAffectedZipCodes(ArrayList<String> affectedZipCodes) {
        this.affectedZipCodes = affectedZipCodes;
    }

    // Sets the current location of the hurricane.
    public void setLocation(String location) {
        this.location = location;
    }

    // Sets the affected areas list.
    public void setAffectedAreas(ArrayList<String> affectedAreas) {
        this.affectedAreas = affectedAreas;
    }

    // Sets the weather condition description.
    public void setCondition(String condition) {
        this.condition = condition;
    }

    // Sets the terrain type.
    public void setTerrain(String terrain) {
        this.terrain = terrain;
    }
}