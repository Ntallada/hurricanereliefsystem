package com.model;

import java.util.ArrayList;

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

    public String getUpdates() {
        return name + " is currently a Category " + category + " hurricane with wind speeds of " + windSpeed + " mph.";
    }

    public void trackPath() {
        System.out.println("Tracking " + name + " near " + location);
    }

    public int getCategory() {
        return category;
    }

    public void updateWindSpeed(double newSpeed) {
        this.windSpeed = newSpeed;
    }

    public ArrayList<String> getAffectedAreas() {
        return affectedAreas;
    }

    public String getCurrentLocation() {
        return location;
    }

    public void findAffectedAreas() {
        System.out.println("Affected areas: " + affectedAreas);
    }

    public String getName() {
        return name;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public ArrayList<String> getAffectedZipCodes() {
        return affectedZipCodes;
    }

    public String getLocation() {
        return location;
    }

    public String getCondition() {
        return condition;
    }

    public String getTerrain() {
        return terrain;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public void setAffectedZipCodes(ArrayList<String> affectedZipCodes) {
        this.affectedZipCodes = affectedZipCodes;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setAffectedAreas(ArrayList<String> affectedAreas) {
        this.affectedAreas = affectedAreas;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public void setTerrain(String terrain) {
        this.terrain = terrain;
    }
}