package com.model;

import java.util.ArrayList;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class DataLoaderTest {

    @Test
    public void loadUsers_shouldReadUsersFromJsonDataFile() {
        ArrayList<User> loadedUsers = DataLoader.loadUsers();

        assertNotNull(loadedUsers);
        assertFalse(loadedUsers.isEmpty());
        assertTrue(loadedUsers.stream().anyMatch(currentUser -> "john.doe@example.com".equalsIgnoreCase(currentUser.getEmail())));
    }

    @Test
    public void getShelters_shouldReadSheltersFromJsonDataFile() {
        ArrayList<Shelter> loadedShelters = DataLoader.getShelters();

        assertNotNull(loadedShelters);
        assertFalse(loadedShelters.isEmpty());
        assertTrue(loadedShelters.stream().anyMatch(currentShelter -> "Charleston Medical Pet Shelter".equals(currentShelter.getName())));
    }

    @Test
    public void getRequests_shouldReadReliefRequestsFromJsonDataFile() {
        ArrayList<ReliefRequest> loadedRequests = DataLoader.getRequests();

        assertNotNull(loadedRequests);
        assertFalse(loadedRequests.isEmpty());
        assertTrue(loadedRequests.stream().anyMatch(currentRequest -> currentRequest.getLocation() != null && currentRequest.getLocation().contains("Charleston")));
    }
}