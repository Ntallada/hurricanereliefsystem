package com.model;

import java.util.ArrayList;

public class Resources {
    public ArrayList<String> foodSupplies;
    public int waterSupplies;
    public ArrayList<String> medicalSupplies;
    public ArrayList<String> otherSupplies;
    
    public Resources() {
        this.foodSupplies = new ArrayList<>();
        this.waterSupplies = 0;
        this.medicalSupplies = new ArrayList<>();
        this.otherSupplies = new ArrayList<>();
    }
    
    public ArrayList<String> getFoodSupplies() {
        return foodSupplies;
    }

    public int getWaterSupplies() {
        return waterSupplies;
    }

    public boolean updateSupplies() {
        if (foodSupplies.isEmpty() || waterSupplies <= 0 || medicalSupplies.isEmpty() || otherSupplies.isEmpty()) {
            return false;
        }
        return true;
    }

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
