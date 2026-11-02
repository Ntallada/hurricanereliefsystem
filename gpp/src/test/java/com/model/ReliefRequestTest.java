package com.model;

import org.junit.Before;
import org.junit.Test;
import java.util.UUID;
import static org.junit.Assert.*;


public class ReliefRequestTest {

    private ReliefRequest request;

    @Before
    public void setUp() {
        request = new ReliefRequest(
            null, "Jane Doe", "123 Main St", "Food/Water", 
            "None", UrgencyLevel.MEDIUM, 3, true, "Max", "Dog"
        );
    }

    @Test
    public void testPrimaryConstructorCreation() {
        assertNotNull(request.getRequestId());
        assertEquals("Jane Doe", request.getVictimName());
        assertEquals("123 Main St", request.getLocation());
        assertEquals(UrgencyLevel.MEDIUM, request.getUrgencyLevel());
        assertEquals("PENDING", request.getStatus());
        assertTrue(request.isPetNeeds());
        assertEquals(3, request.getNumberOfPeople());
        assertTrue(request.isMedicalNeeded());
        assertEquals("Max", request.getPetName());
        assertEquals("Dog", request.getPetType());
        assertNull(request.getVolunteerId());
        assertNull(request.getShelterId());
        assertFalse(request.isEmsCalled());
    }

    @Test
    public void testStringConstructorMapping() {
        UUID testUUID = UUID.randomUUID();
        ReliefRequest stringReq = new ReliefRequest(
            testUUID.toString(), "John Smith", "456 Oak Rd", 
            "Medical", "Allergies", "HIGH", "APPROVED", false
        );

        assertEquals(testUUID, stringReq.getRequestId());
        assertEquals("John Smith", stringReq.getVictimName());
        assertEquals(UrgencyLevel.HIGH, stringReq.getUrgencyLevel());
        assertEquals("APPROVED", stringReq.getStatus());
        assertFalse(stringReq.isPetNeeds());
    }

    @Test
    public void testCallEMSAndUrgencyEscalation() {
        assertFalse(request.isEmsCalled());
        request.callEMS();
        assertTrue(request.isEmsCalled());
        assertEquals(UrgencyLevel.HIGH, request.getUrgencyLevel());
    }

    @Test
    public void testViewETAMessages() {
        // 1. Unassigned case
        String etaUnassigned = request.viewETA();
        assertTrue(etaUnassigned.contains("No volunteer has been assigned"));

        // 2. Volunteer assigned case
        UUID volId = UUID.randomUUID();
        request.assignVolunteer(volId);
        String etaVolunteer = request.viewETA();
        assertTrue(etaVolunteer.contains(volId.toString()));

        // 3. EMS called case (overrides volunteer message)
        request.callEMS();
        String etaEms = request.viewETA();
        assertTrue(etaEms.contains("EMS are on their way"));
    }

    @Test
    public void testAssignVolunteerAndUpdateStatus() {
        UUID volId = UUID.randomUUID();
        request.assignVolunteer(volId);
        assertEquals(volId, request.getVolunteerId());
        assertEquals("ASSIGNED", request.getStatus());
    }
}