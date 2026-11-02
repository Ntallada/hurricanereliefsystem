package com.model;

//Made by Nandasai A.
//uses maven to run the file and then reports bugs

import java.util.ArrayList;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;

public class DataLoaderTest {

    @BeforeClass
    public static void printTestCases() {
        TestCaseTable.print(new String[][] {
                {"load users", "users JSON is converted into user objects"},
                {"load shelters", "shelters JSON is converted into shelter objects"},
                {"load requests", "request JSON is converted into relief request objects"}
        });
    }

    @Test
    public void loadUsers() {
        ArrayList<User> loadedUsers = DataLoader.loadUsers();

        assertNotNull(loadedUsers);
        assertFalse(loadedUsers.isEmpty());
        assertTrue(loadedUsers.stream().anyMatch(currentUser -> "john.doe@example.com".equalsIgnoreCase(currentUser.getEmail())));
    }

    @Test
    public void getShelters() {
        ArrayList<Shelter> loadedShelters = DataLoader.getShelters();

        assertNotNull(loadedShelters);
        assertFalse(loadedShelters.isEmpty());
        assertTrue(loadedShelters.stream().anyMatch(currentShelter -> "Charleston Medical Pet Shelter".equals(currentShelter.getName())));
    }

    @Test
    public void getRequests() {
        ArrayList<ReliefRequest> loadedRequests = DataLoader.getRequests();

        assertNotNull(loadedRequests);
        assertFalse(loadedRequests.isEmpty());
        assertTrue(loadedRequests.stream().anyMatch(currentRequest -> currentRequest.getLocation() != null && currentRequest.getLocation().contains("Charleston")));
    }
}