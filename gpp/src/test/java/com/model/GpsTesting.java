package com.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

public class GpsTesting {
    private static final String FROM = "12 Church St, Charleston, SC";
    private static final String TO = "5001 Coliseum Dr, North Charleston, SC"; 

    @Test
    public void drivingDistanceIsReasonable() {
        double miles = Gps.getDrivingDistance(FROM, TO);
        assertTrue("distance was " + miles, miles > 10 && miles < 20);
    }

    @Test
    public void overseasRouteFails() {
        assertEquals(-1.0, Gps.getDrivingDistance(FROM, "London, England"), 0.001);
    }
    
    @Test
    public void emptyAddressFails() {
        assertEquals(-1.0, Gps.getDrivingDistance("", TO), 0.001);
    }

    @Test 
    public void nullAddressFails() {
        assertEquals(-1.0, Gps.getDrivingDistance(null, TO), 0.001);
    }

    @Test 
    public void gibberishAddressGivesNoDirections() {
        assertTrue(Gps.getDirections("zzzzqqqqxxxx", TO).isEmpty());
    }

    @Test
    public void addressWithSpecialCharactersStillWorks() {
        double miles = Gps.getDrivingDistance("100 King St & Broad St #5, Charleston, SC", TO);
        assertTrue("distance was " + miles, miles > 0);
    }
}
