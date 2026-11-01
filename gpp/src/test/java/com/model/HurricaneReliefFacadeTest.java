package com.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.lang.reflect.Field;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

public class HurricaneReliefFacadeTest {
    private HurricaneReliefFacade facade;

    @Before
    public void setUp() {
        resetSingletons();
        facade = HurricaneReliefFacade.getInstance();
    }

    @Test
    public void dataConstants_shouldUseProjectJsonPaths() {
        assertTrue(DataConstants.USERS_FILE_NAME.endsWith("gpp/json/users.json"));
        assertTrue(DataConstants.SHELTERS_FILE_NAME.endsWith("gpp/json/shelters.json"));
        assertTrue(DataConstants.REQUESTS_FILE_NAME.endsWith("gpp/json/reliefRequest.json"));
        assertTrue(DataConstants.isJunitTest());
    }

    @Test
    public void singletonFacade_shouldReturnTheSameInstance() {
        HurricaneReliefFacade secondReference = HurricaneReliefFacade.getInstance();
        assertSame("The facade should follow the singleton pattern", facade, secondReference);
    }

    @Test
    public void login_shouldReturnTrueForKnownUserCredentials() {
        assertTrue(facade.login("john.doe@example.com", "password123"));
        assertEquals("John", facade.getCurrentUser().getFirstName());
        assertEquals("Doe", facade.getCurrentUser().getLastName());
    }

    @Test
    public void login_shouldReturnFalseForIncorrectPassword() {
        assertFalse(facade.login("john.doe@example.com", "wrong-password"));
        assertNull(facade.getCurrentUser());
    }

    @Test
    public void logout_shouldClearTheCurrentUser() throws Exception {
        facade.login("john.doe@example.com", "password123");

        facade.logout();

        assertNull(facade.getCurrentUser());
    }

    @Test
    public void submitRequest_shouldCreateRequestForLoggedInVictim() throws Exception {
        Victim victim = new Victim("Maria", "Carter", 35, "555-1212", "maria@example.com", "secret", Languages.ENGLISH, "Charleston, SC");
        setCurrentUser(victim);

        ReliefRequest request = facade.submitRequest(
                "14 King St",
                "Food",
                "Gluten-free meals",
                UrgencyLevel.HIGH,
                2,
                true,
                "Buddy",
                "Dog");

        assertNotNull(request);
        assertEquals("Maria Carter", request.getVictimName());
        assertEquals("14 King St", request.getLocation());
        assertEquals("Food", request.getAidType());
        assertEquals(UrgencyLevel.HIGH, request.getUrgencyLevel());
        assertEquals("PENDING", request.getStatus());
    }

    @Test
    public void submitRequest_shouldReturnNullWhenUserIsNotVictim() {
        User volunteer = new Volunteer("Dana", "White", 28, "555-4444", "dana@example.com", "secret", Languages.ENGLISH, "Columbia, SC");
        setCurrentUser(volunteer);

        ReliefRequest request = facade.submitRequest(
                "22 Main St",
                "Shelter",
                "Wheelchair access",
                UrgencyLevel.MEDIUM,
                1,
                false,
                null,
                null);

        assertNull(request);
    }

    @Test
    public void reliefRequest_shouldUpdateStatusAndReturnVolunteerEta() {
        Victim victim = new Victim("Sam", "Lee", 41, "555-9999", "sam@example.com", "secret", Languages.ENGLISH, "Charleston, SC");
        ReliefRequest request = victim.createReliefRequest(
                "Sam Lee",
                "7 Harbor Rd",
                "Medical",
                "Need oxygen",
                UrgencyLevel.MEDIUM,
                3,
                true,
                "Luna",
                "Cat");

        request.assignVolunteer(UUID.fromString("641c442b-d765-4e54-9f10-41f540c60201"));
        request.updateStatus(UrgencyLevel.CRITICAL, "ASSIGNED");

        assertEquals(UrgencyLevel.CRITICAL, request.getUrgencyLevel());
        assertEquals("ASSIGNED", request.getStatus());
        assertTrue(request.viewETA().contains("volunteer"));
    }

    @Test
    public void reliefRequest_shouldEscalateToHighUrgencyWhenEmsIsCalled() {
        Victim victim = new Victim("Pat", "Jones", 50, "555-1111", "pat@example.com", "secret", Languages.ENGLISH, "North Charleston, SC");
        ReliefRequest request = victim.createReliefRequest(
                "Pat Jones",
                "8 Bay Street",
                "Medical",
                "Severe asthma",
                UrgencyLevel.MEDIUM,
                2,
                true,
                null,
                null);

        request.callEMS();

        assertTrue(request.isEmsCalled());
        assertEquals(UrgencyLevel.HIGH, request.getUrgencyLevel());
        assertTrue(request.viewETA().contains("EMS"));
    }

    private void setCurrentUser(User user) {
        try {
            Field field = HurricaneReliefFacade.class.getDeclaredField("currentUser");
            field.setAccessible(true);
            field.set(facade, user);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Unable to set current user in facade", e);
        }
    }

    private void resetSingletons() {
        try {
            Field facadeField = HurricaneReliefFacade.class.getDeclaredField("instance");
            facadeField.setAccessible(true);
            facadeField.set(null, null);

            Field userListField = UserList.class.getDeclaredField("userList");
            userListField.setAccessible(true);
            userListField.set(null, null);

            Field requestListField = ReliefRequestList.class.getDeclaredField("instance");
            requestListField.setAccessible(true);
            requestListField.set(null, null);
        } catch (Exception e) {
            throw new RuntimeException("Unable to reset singleton state for tests", e);
        }
    }
}