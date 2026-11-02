package com.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ShelterListTest {
    private ShelterList shelterList;
    private ArrayList<Shelter> savedShelters;
    private Shelter openShelter;
    private Shelter fullShelter;
    private Shelter closedShelter;

    /**
     * saves the real shelters, empties the list, and makes test shelters
     */
    @Before
    public void setUp() {
        shelterList = ShelterList.getInstance();
        savedShelters = new ArrayList<>(shelterList.getShelters());
        shelterList.getShelters().clear();

        openShelter = new Shelter("Columbia Shelter", "123 Main St", UUID.randomUUID(),
        100, 0, "OPEN", true, false);
        fullShelter = new Shelter("Lexington Shelter", "456 Sunset Blvd", UUID.randomUUID(),
        100, 100, "OPEN", false, false);
        closedShelter = new Shelter("Irmo Shelter", "789 Elmwood Ave", UUID.randomUUID(),
        100, 0, "CLOSED", false, false);
    }

    /**
     * puts the real shelters back and saves them to the JSON file
     */
    @After
    public void tearDown() {
        shelterList.getShelters().clear();
        shelterList.getShelters().addAll(savedShelters);
        DataWriter.saveShelters(shelterList.getShelters());
    }

    /**
     * tests that getInstance always returns the same ShelterList
     */
    @Test
    public void testGetInstanceReturnsSameList() {
        assertSame(ShelterList.getInstance(), ShelterList.getInstance());
    }

    /**
     * tests that addShelter puts the shelter in the list.
     */
    @Test
    public void testAddShelter() {
        shelterList.addShelter(openShelter);
        assertTrue(shelterList.getShelters().contains(openShelter));
    }

    /**
     * tests that addShelter increases the number of shelters by 1
     */
    @Test
    public void testAddShelterIncreasesSize() {
        int initialSize = shelterList.getShelters().size();
        shelterList.addShelter(openShelter);
        assertEquals(initialSize + 1, shelterList.getShelters().size());
    }

    /**
     * tests that an added shelter is saved to the JSON file
     */
    @Test
    public void testAddShelterIsSaved() {
        shelterList.addShelter(openShelter);
        boolean isSaved = false;
        for (Shelter saved : DataLoader.getShelters()) {
            if (openShelter.getShelterId().equals(saved.getShelterId())) {
                isSaved = true;
            }
        }
        assertTrue(isSaved);
    }

    /**
     * tests that getShelterById finds a shelter that was added
     */
    @Test
    public void testGetShelterByIdFound() {
        shelterList.addShelter(openShelter);
        assertSame(openShelter, shelterList.getShelterById(openShelter.getShelterId()));
    }

    /**
     * tests that getShelterById returns null for an ID that is not in the list
     */
    @Test
    public void testGetShelterByIdNotFound() {
        shelterList.addShelter(openShelter);
        assertNull(shelterList.getShelterById(UUID.randomUUID()));
    }

    /**
     * tests that getShelterById returns null when the ID is null
     */
    @Test
    public void testGetShelterByIdNull() {
        shelterList.addShelter(openShelter);
        assertNull(shelterList.getShelterById(null));
    }

    /**
     * tests that getShelterById skips shelters with no ID and still finds the right one
     */
    @Test
    public void testGetShelterByIdSkipsShelterWithoutId() {
        Shelter noId = new Shelter("999 Oak St", 100, 0, "OPEN", true);
        shelterList.addShelter(noId);
        shelterList.addShelter(openShelter);
        assertSame(openShelter, shelterList.getShelterById(openShelter.getShelterId()));
    }

    /**
     * tests that removeShelter returns true for a shelter in the list
     */
    @Test
    public void testRemoveShelterValid() {
        shelterList.addShelter(openShelter);
        boolean isRemoved = shelterList.removeShelter(openShelter.getShelterId());
        assertTrue(isRemoved);
    }

    /**
     * tests that a removed shelter can no longer be found
     */
    @Test
    public void testRemoveShelterTakesItOutOfList() {
        shelterList.addShelter(openShelter);
        shelterList.removeShelter(openShelter.getShelterId());
        assertNull(shelterList.getShelterById(openShelter.getShelterId()));
    }

    /**
     * tests that removing one shelter leaves the others
     */
    @Test
    public void testRemoveShelterKeepsOtherShelters() {
        shelterList.addShelter(openShelter);
        shelterList.addShelter(fullShelter);
        shelterList.removeShelter(openShelter.getShelterId());
        assertSame(fullShelter, shelterList.getShelterById(fullShelter.getShelterId()));
    }

    /**
     * tests that a removed shelter is also removed from the JSON file
     */
    @Test
    public void testRemoveShelterIsSaved() {
        shelterList.addShelter(openShelter);
        shelterList.removeShelter(openShelter.getShelterId());
        boolean isStillSaved = false;
        for (Shelter saved : DataLoader.getShelters()) {
            if (openShelter.getShelterId().equals(saved.getShelterId())) {
                isStillSaved = true;
            }
        }
        assertFalse(isStillSaved);
    }

    /**
     * tests that removeShelter returns false for an ID that is not in the list
     */
    @Test
    public void testRemoveShelterNotFound() {
        shelterList.addShelter(openShelter);
        boolean isRemoved = shelterList.removeShelter(UUID.randomUUID());
        assertFalse(isRemoved);
    }

    /**
     * tests that removeShelter returns false when the ID is null
     */
    @Test
    public void testRemoveShelterNullId() {
        shelterList.addShelter(openShelter);
        boolean isRemoved = shelterList.removeShelter(null);
        assertFalse(isRemoved);
    }

    /**
     * tests that an open shelter with space is in the available list
     */
    @Test
    public void testAvailableSheltersIncludesOpenWithSpace() {
        shelterList.addShelter(openShelter);
        assertTrue(shelterList.getAvailableShelters().contains(openShelter));
    }

    /**
     * tests that a full shelter is not in the available list
     */
    @Test
    public void testAvailableSheltersExcludesFull() {
        shelterList.addShelter(fullShelter);
        assertFalse(shelterList.getAvailableShelters().contains(fullShelter));
    }

    /**
     * tests that a closed shelter is not in the available list
     */
    @Test
    public void testAvailableSheltersExcludesClosed() {
        shelterList.addShelter(closedShelter);
        assertFalse(shelterList.getAvailableShelters().contains(closedShelter));
    }

    /**
     * tests that the status check ignores upper and lower case
     */
    @Test
    public void testAvailableSheltersIgnoresStatusCase() {
        Shelter lowerCase = new Shelter("Cayce Shelter", "321 River Rd", UUID.randomUUID(),
        100, 0, "open", false, false);
        shelterList.addShelter(lowerCase);
        assertTrue(shelterList.getAvailableShelters().contains(lowerCase));
    }

    /**
     * tests that the available list is empty when there are no shelters
     */
    @Test
    public void testAvailableSheltersEmptyList() {
        assertTrue(shelterList.getAvailableShelters().isEmpty());
    }

    /**
     * tests that a pet-friendly shelter is in the pet-friendly list
     */
    @Test
    public void testPetFriendlySheltersIncludesPetFriendly() {
        shelterList.addShelter(openShelter);
        assertTrue(shelterList.getPetFriendlyShelters().contains(openShelter));
    }

    /**
     * tests that a shelter without pets allowed is not in the pet-friendly list
     */
    @Test
    public void testPetFriendlySheltersExcludesNoPets() {
        shelterList.addShelter(fullShelter);
        assertFalse(shelterList.getPetFriendlyShelters().contains(fullShelter));
    }

    /**
     * tests that the pet-friendly list is empty when there are no shelters
     */
    @Test
    public void testPetFriendlySheltersEmptyList() {
        assertTrue(shelterList.getPetFriendlyShelters().isEmpty());
    }

    /**
     * tests that a null shelter is not added to the list
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAddNullShelter() {
        shelterList.addShelter(null);
    }

    /**
     * tests that the same shelter cannot be added twice
     */
    @Test
    public void testAddDuplicateShelter() {
        shelterList.addShelter(openShelter);
        shelterList.addShelter(openShelter);
        assertEquals(1, shelterList.getShelters().size());
    }

    /**
     * tests that a shelter with no ID is not added, since it could never be found or removed
     */
    @Test
    public void testAddShelterWithoutId() {
        Shelter noId = new Shelter("999 Oak St", 100, 0, "OPEN", true);
        shelterList.addShelter(noId);
        assertFalse(shelterList.getShelters().contains(noId));
    }
}
