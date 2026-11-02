package com.model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/*
 * +-----------------------------------+-------------------------------------------------------------+
 * | Test                              | Reasoning                                                   |
 * +-----------------------------------+-------------------------------------------------------------+
 * | testDefaultConstructorInitialization| Verifies that an empty Resources constructor initializes    |
 * |                                   | lists and water count to empty/zero baseline state.         |
 * +-----------------------------------+-------------------------------------------------------------+
 * | testUpdateSuppliesFailureState    | Confirms updateSupplies() returns false when supply         |
 * |                                   | inventories or water metrics are empty/zero.                |
 * +-----------------------------------+-------------------------------------------------------------+
 * | testUpdateSuppliesSuccessState    | Confirms updateSupplies() returns true once all categories  |
 * |                                   | contain active items and positive water numbers.            |
 * +-----------------------------------+-------------------------------------------------------------+
 * | testResourcesAlertGeneration      | Validates that resourcesAlert() constructs descriptive      |
 * |                                   | warning messages highlighting depleted categories.          |
 * +-----------------------------------+-------------------------------------------------------------+
 */
public class ResourcesTest {

    private Resources resources;

    @Before
    public void setUp() {
        resources = new Resources();
    }

    @Test
    public void testDefaultConstructorInitialization() {
        assertNotNull(resources.getFoodSupplies());
        assertTrue(resources.getFoodSupplies().isEmpty());
        assertEquals(0, resources.getWaterSupplies());
        assertNotNull(resources.medicalSupplies);
        assertTrue(resources.medicalSupplies.isEmpty());
        assertNotNull(resources.otherSupplies);
        assertTrue(resources.otherSupplies.isEmpty());
    }

    @Test
    public void testUpdateSuppliesFailureState() {
        // Completely empty lists and 0 water should evaluate to false
        assertFalse(resources.updateSupplies());
    }

    @Test
    public void testUpdateSuppliesSuccessState() {
        resources.foodSupplies.add("Canned Beans");
        resources.waterSupplies = 100;
        resources.medicalSupplies.add("Bandages");
        resources.otherSupplies.add("Flashlights");

        assertTrue(resources.updateSupplies());
    }

    @Test
    public void testResourcesAlertGeneration() {
        // All categories empty/zero initially
        String alert = resources.resourcesAlert();
        
        assertTrue(alert.contains("Food supplies are empty"));
        assertTrue(alert.contains("Water supplies are empty"));
        assertTrue(alert.contains("Medical supplies are empty"));
        assertTrue(alert.contains("Other supplies are empty"));

        // Stock one category and verify its warning drops off
        resources.waterSupplies = 50;
        String updatedAlert = resources.resourcesAlert();
        assertFalse(updatedAlert.contains("Water supplies are empty"));
        assertTrue(updatedAlert.contains("Food supplies are empty"));
    }
}