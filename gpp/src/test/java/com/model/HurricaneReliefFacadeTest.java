package com.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import java.util.ArrayList;
import java.lang.reflect.Field;
import java.util.UUID;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.Test;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HurricaneReliefFacadeTest {
    private static HurricaneReliefFacade facade;
    
    @BeforeClass
    public static void setUpClass() {
        DataWriter.saveUsers(new ArrayList<User>());
        DataWriter.saveRequests(new ArrayList<ReliefRequest>());
        DataWriter.saveShelters(new ArrayList<Shelter>());
        facade = HurricaneReliefFacade.getInstance();
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
    public void loginFailsWithWrongPassword() {
        String email = uniqueEmail();                      // 1. set the user
        signUp("VICTIM", email);
        boolean result = facade.login(email, "wrongpassword");   // 2. do the thing
        assertFalse(result);                               // 3. check the result
        assertNull(facade.getCurrentUser());
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

        @Test
        public void signUpRejectsEmptyEmail() {
            assertFalse(signUp("VICTIM", ""));
        }
    
        @Test
        public void submitRequestRejectsNegativePeople() {
            String email = uniqueEmail();
            signUp("VICTIM", email);
            facade.login(email, "password123");
            assertNull(facade.submitRequest("12 Church St", "Transportation", "x",
                    UrgencyLevel.HIGH, -5, false, null, null));
        }
    
        @Test
        public void submitRequestRejectsZeroPeople() {
            String email = uniqueEmail();
            signUp("VICTIM", email);
            facade.login(email, "password123");
            assertNull(facade.submitRequest("12 Church St", "Transportation", "x",
                    UrgencyLevel.HIGH, 0, false, null, null));
        }
    
        @Test
        public void managerCannotCompleteUnassignedRequest() {
            String victimEmail = uniqueEmail();
            signUp("VICTIM", victimEmail);
            facade.login(victimEmail, "password123");
            ReliefRequest request = facade.submitRequest("12 Church St", "Transportation", "x",
                    UrgencyLevel.HIGH, 1, false, null, null);
            facade.logout();
    
            String managerEmail = uniqueEmail();
            signUp("MANAGER", managerEmail);
            facade.login(managerEmail, "password123");
            assertFalse(facade.completeRequest(request));
        }
    
        @Test
        public void requestListCannotBeEmptiedFromOutside() {
            facade.getAllRequests().clear();
            assertFalse(facade.getAllRequests().isEmpty());
        }
    
        // these two crash on purpose and run last because of the zz names
        @Test
        public void zzSignUpWithNullLanguage() {
            assertFalse(facade.signUp("VICTIM", "T", "U", 30, "843-555-0000",
                    uniqueEmail(), "password123", null, "Charleston, SC"));
        }
    
        @Test
        public void zzSubmitRequestWithNullUrgency() {
            String email = uniqueEmail();
            signUp("VICTIM", email);
            facade.login(email, "password123");
            assertNull(facade.submitRequest("12 Church St", "Transportation", "x",
                    null, 1, false, null, null));
        }
    
        @After
        public void tearDown() {
            facade.logout();
        }
    
        private String uniqueEmail() {
            return "test" + UUID.randomUUID().toString().substring(0, 8) + "@example.com";
        }
    
        private boolean signUp(String type, String email) {
            return facade.signUp(type, "T", "U", 30, "843-555-0000",
                    email, "password123", Languages.ENGLISH, "Charleston, SC");
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