package com.model;

import java.util.ArrayList;

// Manages emergency resource inventories including food, water, medical, and miscellaneous supplies.
public class Resources {
    public ArrayList<String> foodSupplies;
    public int waterSupplies;
    public ArrayList<String> medicalSupplies;
    public ArrayList<String> otherSupplies;
    
    // Constructs a new Resources object, initializing empty supply lists and zero water supply.
    public Resources() {
        this.foodSupplies = new ArrayList<>();
        this.waterSupplies = 0;
        this.medicalSupplies = new ArrayList<>();
        this.otherSupplies = new ArrayList<>();
    }

    // Gets the list of food supplies.
    public ArrayList<String> getFoodSupplies() {
        return foodSupplies;
    }

    // Gets the amount of water supplies.
    public int getWaterSupplies() {
        return waterSupplies;
    }

    // Checks if all supply categories are stocked (returns false if any category is empty or zero).
    public boolean updateSupplies() {
        if (foodSupplies.isEmpty() || waterSupplies <= 0 || medicalSupplies.isEmpty() || otherSupplies.isEmpty()) {
            return false;
        }
        return true;
    }

    // Generates a warning message alert listing any supply categories that are currently empty or depleted.
    public String resourcesAlert() {
        String alert = " ";
        if (foodSupplies.isEmpty()) {
            alert += "Food supplies are empty. ";
        }
        if (waterSupplies <= 0) {
            alert += "Water supplies are empty. ";
        }
        if (medicalSupplies.isEmpty()) {
            alert += "Medical supplies are empty. ";
        }
        if (otherSupplies.isEmpty()) {
            alert += "Other supplies are empty. ";
        }
        return alert;
    }
}