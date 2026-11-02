package com.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.experimental.theories.suppliers.TestedOn;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test

public class ShelterTest {
    private Shelter shelter;

    @BeforeEach
    public void setUp() {
        shelter = new Shelter("Columbia's Shelter", "123 Main St", UUID.randomUUID(), 
        100, 0, "Open", true, false );
    }

    @Test
    public void testCheckInValid() {
        boolean isCheckIn = shelter.checkIn(List.of("John", "Jane"), null, null);
        assertTrue(isCheckIn);
    }

    @Test
    public void testCheckInIncreasesOccupancy() {
        List<String> family = List.of("John", "Jane");
        int initialOccupancy = shelter.getOccupancy();
        shelter.checkIn(family, null, null);
        int finalOccupancy = shelter.getOccupancy();
        assertEquals(initialOccupancy + family.size(), finalOccupancy);
    }
}
