package com.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

public class ShelterManagerTest {
    private Languages language;
    private UUID savedId;
    private ShelterManager newManager;
    private ShelterManager loadedManager;

    /**
     * makes a new manager and a manager loaded with an ID before each test
     */
    @Before
    public void setUp() {
        language = Languages.values()[0];
        savedId = UUID.randomUUID();
        newManager = new ShelterManager("Melissa", "Gilliard", 20, "803-555-1234",
        "mgilliard@email.com", "password123", language, "Columbia, SC");
        loadedManager = new ShelterManager(savedId, "John", "Smith", 45, "803-555-9876",
        "jsmith@email.com", "secret456", language, "Lexington, SC", true);
    }

    /**
     * tests that a shelter manager is a type of User
     */
    @Test
    public void testShelterManagerIsUser() {
        assertTrue(newManager instanceof User);
    }

    /**
     * tests that the new-manager constructor gives the manager an ID
     */
    @Test
    public void testNewManagerGetsId() {
        assertNotNull(newManager.getId());
    }

    /**
     * tests that two new managers get different IDs
     */
    @Test
    public void testNewManagersGetDifferentIds() {
        ShelterManager other = new ShelterManager("Ana", "Lopez", 30, "803-555-0000",
        "alopez@email.com", "pass789", language, "Irmo, SC");
        assertNotEquals(newManager.getId(), other.getId());
    }

    /**
     * tests that the new-manager constructor does not make the manager an admin
     */
    @Test
    public void testNewManagerIsNotAdmin() {
        assertFalse(newManager.isAdmin());
    }

    /**
     * tests that the new-manager constructor passes the name to User
     */
    @Test
    public void testNewManagerKeepsName() {
        assertEquals("Melissa", newManager.getFirstName());
    }

    /**
     * tests that the loading constructor keeps the ID it was given
     */
    @Test
    public void testLoadedManagerKeepsId() {
        assertEquals(savedId, loadedManager.getId());
    }

    /**
     * tests that the loading constructor keeps the admin status it was given
     */
    @Test
    public void testLoadedManagerKeepsAdmin() {
        assertTrue(loadedManager.isAdmin());
    }

    /**
     * tests that the loading constructor passes the name to User
     */
    @Test
    public void testLoadedManagerKeepsName() {
        assertEquals("John", loadedManager.getFirstName());
    }

    /**
     * tests that a manager cannot be made with a negative age
     */
    @Test(expected = IllegalArgumentException.class)
    public void testNegativeAge() {
        new ShelterManager("Melissa", "Gilliard", -5, "803-555-1234",
        "mgilliard@email.com", "password123", language, "Columbia, SC");
    }

    /**
     * tests that a manager cannot be made with an email missing the @ sign
     */
    @Test(expected = IllegalArgumentException.class)
    public void testEmailWithoutAtSign() {
        new ShelterManager("Melissa", "Gilliard", 20, "803-555-1234",
        "mgilliardemail.com", "password123", language, "Columbia, SC");
    }

    /**
     * tests that a manager cannot be made with a null first name
     */
    @Test(expected = IllegalArgumentException.class)
    public void testNullFirstName() {
        new ShelterManager(null, "Gilliard", 20, "803-555-1234", "mgilliard@email.com", "", language, "Columbia, SC");
    }
}