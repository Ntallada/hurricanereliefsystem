package com.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;



public class ReliefRequestTest {

    private ReliefRequest request;

    @BeforeEach
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
    
}
