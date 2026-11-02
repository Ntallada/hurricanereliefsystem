package com.model;

import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.UUID;
import static org.junit.Assert.*;

/*
 * +-----------------------------------+-------------------------------------------------------------+
 * | Test                              | Reasoning                                                   |
 * +-----------------------------------+-------------------------------------------------------------+
 * | testGetInstanceSingletonPattern   | Verifies that getInstance() returns a non-null singleton    |
 * |                                   | instance pointing to the exact same reference.              |
 * +-----------------------------------+-------------------------------------------------------------+
 * | testGetRequestByIdFound           | Confirms that looking up an active request by its unique    |
 * |                                   | UUID successfully returns the correct object.               |
 * +-----------------------------------+-------------------------------------------------------------+
 * | testGetRequestByIdNotFound        | Validates error/edge case when querying an unrecorded UUID  |
 * |                                   | returns null safely.                                        |
 * +-----------------------------------+-------------------------------------------------------------+
 * | testGetRequestsByUrgencyFilter    | Ensures requests are accurately filtered and retrieved by   |
 * |                                   | their specific UrgencyLevel enum.                           |
 * +-----------------------------------+-------------------------------------------------------------+
 * | testRemoveRequestHandling         | Tests removing an existing request ID returns true, while   |
 * |                                   | removing an invalid ID returns false.                       |
 * +-----------------------------------+-------------------------------------------------------------+
 */
public class ReliefRequestListTest {

    private ReliefRequestList requestList;
    private ReliefRequest sampleRequest;

    @Before
    public void setUp() {
        requestList = ReliefRequestList.getInstance();
        sampleRequest = new ReliefRequest(
            null, "Alice", "789 Pine Rd", "Shelter", 
            "None", UrgencyLevel.HIGH, 2, false, null, null
        );
    }

    @Test
    public void testGetInstanceSingletonPattern() {
        ReliefRequestList instance1 = ReliefRequestList.getInstance();
        ReliefRequestList instance2 = ReliefRequestList.getInstance();
        assertNotNull(instance1);
        assertSame(instance1, instance2);
    }

    @Test
    public void testGetRequestByIdFound() {
        requestList.addRequest(sampleRequest);
        UUID targetId = sampleRequest.getRequestId();
        
        ReliefRequest found = requestList.getRequestById(targetId);
        assertNotNull(found);
        assertEquals(targetId, found.getRequestId());
        
        // Clean up
        requestList.removeRequest(targetId);
    }

    @Test
    public void testGetRequestByIdNotFound() {
        UUID fakeId = UUID.randomUUID();
        ReliefRequest found = requestList.getRequestById(fakeId);
        assertNull(found);
    }

    @Test
    public void testGetRequestsByUrgencyFilter() {
        requestList.addRequest(sampleRequest); // HIGH urgency
        
        ArrayList<ReliefRequest> highRequests = requestList.getRequestsByUrgency(UrgencyLevel.HIGH);
        assertFalse(highRequests.isEmpty());
        assertEquals(UrgencyLevel.HIGH, highRequests.get(0).getUrgencyLevel());
        
        requestList.removeRequest(sampleRequest.getRequestId());
    }

    @Test
    public void testRemoveRequestHandling() {
        requestList.addRequest(sampleRequest);
        UUID id = sampleRequest.getRequestId();

        boolean removed = requestList.removeRequest(id);
        assertTrue(removed);

        boolean removedAgain = requestList.removeRequest(id);
        assertFalse(removedAgain);
    }
}