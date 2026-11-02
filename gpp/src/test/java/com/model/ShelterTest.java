package com.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

public class ShelterTest {
    private Shelter shelter;
    private Shelter noPetShelter;

    /**
     * creates new shelters before each test
     */
    @Before
    public void setUp() {
        shelter = new Shelter("Columbia Shelter", "123 Main St", UUID.randomUUID(), 
        100, 0, "OPEN", true, false );
        noPetShelter = new Shelter("Lexington Shelter", "456 Sunset Blvd", UUID.randomUUID(), 
        100, 0, "OPEN", false, false);
    }

    /**
     * tests the constructor using location as name
     */
    @Test
    public void testIfConstructorUsesLocationAsName() {
        Shelter regular = new Shelter("789 Elmwood Ave", UUID.randomUUID(), 
        100, 0, "OPEN", true);
        assertEquals("789 Elmwood Ave", regular.getName());
    }

    /**
     * tests the constructor setting medical staff false
     */
    @Test
    public void testIfConstructorDefaultsNoMedicalStaff() {
        Shelter regular = new Shelter("789 Elmwood Ave", UUID.randomUUID(), 
        100, 0, "OPEN", true);
        assertFalse(regular.hasMedicalStaff());
    }

    /**
     * tests the constructor starting with no occupants
     */
    @Test
    public void testIfConstructorStartsWithNoOccupants() {
        Shelter regular = new Shelter("789 Elmwood Ave", UUID.randomUUID(), 
        100, 0, "OPEN", true);
        assertTrue(regular.getOccupants().isEmpty());
    }

    /**
     * tests the constructor when no ID is provided
     */
    @Test
    public void testConstructorWithoutIdHasNullId() {
        Shelter noId = new Shelter("789 Pine St", 100, 
        0, "OPEN", true);
        assertNull(noId.getShelterId());
    }

    /**
     * tests available spaces = capacity - occupancy
     */
    @Test
    public void testAvailableSpacesNormal() {
        Shelter partFull = new Shelter("789 Elmwood Ave", UUID.randomUUID(), 
        100, 40, "OPEN", true);
        assertEquals(60, partFull.getAvailableSpaces());
    }

    /**
     * tests available spaces when the shelter is full
     */
    @Test
    public void testAvailableSpacesFull() {
        Shelter full = new Shelter("789 Elmwood Ave", UUID.randomUUID(), 
        100, 100, "OPEN", true);
        assertEquals(0, full.getAvailableSpaces());
    }

    /**
     * tests over capcity is full and not negative
     */
    @Test
    public void testAvailableSpacesOverCapacity() {
        Shelter overCapacity = new Shelter("789 Elmwood Ave", UUID.randomUUID(), 
        100, 150, "OPEN", true);
        assertEquals(0, overCapacity.getAvailableSpaces());
    }

    /**
     * tests that the occupants list cannot be modified
     */
    @Test(expected = UnsupportedOperationException.class)
    public void testOccupantsListCannotBeModified() {
        shelter.getOccupants().add("Stranger");
    }

    /**
     * tests that the pets list cannot be modified
     */
    @Test(expected = UnsupportedOperationException.class)
    public void testPetsListCannotBeModified() {
        shelter.getPets().add("Stray (cat)");
    }

    /**
     * tests the checkIn method with a valid family
     */
    @Test
    public void testCheckInValid() {
        boolean isCheckedIn = shelter.checkIn(List.of("John", "Jane"), null, null);
        assertTrue(isCheckedIn);
    }

    /**
     * tests the checkIn method increases occupancy
     */
    @Test
    public void testCheckInIncreasesOccupancy() {
        List<String> family = List.of("John", "Jane");
        int initialOccupancy = shelter.getOccupancy();
        shelter.checkIn(family, null, null);
        int finalOccupancy = shelter.getOccupancy();
        assertEquals(initialOccupancy + family.size(), finalOccupancy);
    }

    /**
     * tests the checkIn method adds family to occupancy list
     */
    @Test
    public void testCheckInAddsFamilyToOccupancyList() {
        List<String> family = List.of("John", "Jane");
        shelter.checkIn(family, null, null);
        assertTrue(shelter.getOccupants().containsAll(family));
    }

    /**
     * tests the checkIn method lowers availability
     */
    @Test
    public void testCheckInDecreasesAvailability(){
        List<String> family = List.of("John", "Jane");
        int initialAvailability = shelter.getAvailableSpaces();
        shelter.checkIn(family, null, null);
        int finalAvailability = shelter.getAvailableSpaces();
        assertEquals(initialAvailability - family.size(), finalAvailability);
    }

    /**
     * tests a fmaily fills exactly fills the shelter is accepted
     */
    @Test
    public void testCheckInExactlyFillsShelter() {
        Shelter almostFull = new Shelter("789 Elmwood Ave", UUID.randomUUID(), 
        100, 98, "OPEN", true);
        List<String> family = List.of("John", "Jane");
        boolean isCheckedIn = almostFull.checkIn(family, null, null);
        assertTrue(isCheckedIn);
    }

    /**
     * tests checking in with no pets does nothing to pets list
     */
    @Test
    public void testCheckInWithNoPets() {
        List<String> family = List.of("John", "Jane");
        int initialPetsCount = shelter.getPets().size();
        shelter.checkIn(family, null, null);
        int finalPetsCount = shelter.getPets().size();
        assertEquals(initialPetsCount, finalPetsCount);
    }

    /**
     * tests checkIn method with a pet at petfriendly shelter
     */
    @Test
    public void testCheckInWithPetValid() {
        boolean isCheckedIn = shelter.checkIn(List.of("John"), "Max", "dog");
        assertTrue(isCheckedIn);
    }

    /**
     * tests a checked in pet is saved as name type
     */
    @Test 
    public void testCheckInRecordsPet() {
        shelter.checkIn(List.of("John"), "Max", "dog");
        assertEquals(List.of("Max (dog)"), shelter.getPets());
    }

    /**
     * tests checkIn method with empty family
     */
    @Test 
    public void testCheckInNullFamily() {
        boolean isCheckedIn = shelter.checkIn(null, null, null);
        assertFalse(isCheckedIn);
    }

    /**
     * tests checkIn method with empty family
     */
    @Test
    public void testCheckInEmptyFamily() {
        boolean isCheckedIn = shelter.checkIn(new ArrayList<>(), null, null);
        assertFalse(isCheckedIn);
    }

    /**
     * tests checkIn method when fmaily is bigger than availability
     */
    @Test 
    public void testCheckInNotEnoughAvailability() {
        Shelter almostFull = new Shelter("123 Main St", UUID.randomUUID(), 100, 98, "OPEN", false);
        boolean isCheckedIn = almostFull.checkIn(List.of("John", "Jane", "Jack"), null, null);
        assertFalse(isCheckedIn);
    }

    /**
     * tests checkIn method with a pet at a shelter that is nto petfriendly
     */
    @Test
    public void testCheckInPetatNonPetFriendlyShelter() {
        boolean isCheckedIn = noPetShelter.checkIn(List.of("John"), "Max", "dog");
        assertFalse(isCheckedIn);
    }

    /**
     * tests the checkIn method with a pet type that is only spaces
     */
    @Test
    public void testCheckInPetBlankType() {
        boolean isCheckedIn = shelter.checkIn(List.of("John"), "Max", "   ");
        assertFalse(isCheckedIn);
    }
    
    /**
     * tests toString shows name, location, and occupancy/capacity
     */
    @Test
    public void testToString () {
        assertEquals("Columbia Shelter - 123 Main St (0/100)", shelter.toString());
    }

    /**
     * tests closed shelter does not take checkins
     */
    @Test
    public void testCheckInClosedShelter() {
        Shelter closed = new Shelter ("123 Main St", UUID.randomUUID(), 
        100, 0, "CLOSED", false);
        boolean isCheckedIn = closed.checkIn(List.of("John"), null, null);
        assertFalse(isCheckedIn);
    }

    /**
     * tests checkin method with null name in family
     */
    @Test
    public void testCheckInNullMemberName() {
        boolean isCheckedIn = shelter.checkIn(Arrays.asList((String) null), "Max", "dog");
        assertFalse(isCheckedIn);
    }

    /**
     * tests constructor rejects negative capacity
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNegativeCapacity() {
        new Shelter("789 Elmwood Ave", UUID.randomUUID(), -5, 0, "OPEN", false);
    }

    /**     
     * tests that the constructor rejects more occupants than capacity
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorOccupancyOverCapacity() {
        new Shelter("789 Elmwood Ave", UUID.randomUUID(), 100, 120, "OPEN", false);
    }

    /**
     * tests that changing the original occupants list does not change the shelter
     */
    @Test
    public void testConstructorCopiesOccupantsList() {
        ArrayList<String> people = new ArrayList<>(List.of("John"));
        Shelter withList = new Shelter("Columbia Shelter", "123 Main St", UUID.randomUUID(),
                100, 1, "OPEN", false, false, people, new ArrayList<>());
        people.add("Sneaky");
        assertEquals(List.of("John"), withList.getOccupants());
    }
}