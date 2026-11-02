package com.model;

import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.UUID;
import static org.junit.Assert.*;


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