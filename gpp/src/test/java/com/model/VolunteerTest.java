package com.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

public class VolunteerTest {

    private Volunteer volunteer;

    @Before
    public void setUp() {
        volunteer = new Volunteer(
            "John",
            "Doe",
            25,
            "123-456-7890",
            "john@example.com",
            "password123",
            null,
            "Columbia, SC"
        );
    }

    @Test
    public void newVolunteerHasNoSkills() {
        assertTrue(volunteer.getAssignedSkills().isEmpty());
    }

    @Test
    public void newVolunteerHasNoBackgroundCheck() {
        assertFalse(volunteer.getBackgroundCheck());
    }


    @Test
    public void newVolunteerHasNoVehicle() {
        assertEquals("", volunteer.getVehicle());
    }

    @Test
    public void nullSkillIsNotAdded() {
        volunteer.addSkill(null);
        assertTrue(volunteer.getAssignedSkills().isEmpty());
    }

    @Test
    public void returnedSkillsListDoesNotChangeOriginal() {
        ArrayList<Skill> skills = volunteer.getAssignedSkills();
        skills.add(null);
        assertTrue(volunteer.getAssignedSkills().isEmpty());
    }
    
    @Test
    public void existingVolunteerKeepsBackgroundCheck() {
        Volunteer existingVolunteer = new Volunteer(
            UUID.randomUUID(),
            "Jane",
            "Smith",
            25,
            "098-765-4321",
            "jane.smith@example.com",
            "123password",
            null,
            "Myrtle Beach, SC",
            false,
            true,
            new ArrayList<Skill>(),
            new ArrayList<String>(),
            true
        );

        assertTrue(existingVolunteer.getBackgroundCheck());
    }

    @Test
    public void volunteerVehicleCanBeUpdated() {
        volunteer.setVehicle("Truck");

        assertEquals("Truck", volunteer.getVehicle());
    }
}