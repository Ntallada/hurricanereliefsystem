package com.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

public class VictimTest {

    private Victim victim;

    @Before
    public void setUp() {
        victim = new Victim(
            "John",
            "Doe",
            30,
            "123-456-7890",
            "john.doe@example.com",
            "password123",
            null,
            "Columbia"
        );
    }

    private Victim createExistingVictim(
            ArrayList<String> guests,
            ArrayList<String> members,
            boolean safe) {

        return new Victim(
            UUID.randomUUID(),
            "Jane",
            "Smith",
            25,
            "098-765-4321",
            "jane.smith@example.com",
            "123password",
            null,
            "Myrtle Beach, SC",
            false,
            guests,
            members,
            null,
            safe
        );
    }

    @Test
    public void newVictimStartsUnsafe() {
        assertFalse(victim.isSafe());
    }

    @Test
    public void markSafeUpdatesStatus() {
        victim.markSafe();

        assertTrue(victim.isSafe());
    }

    @Test
    public void markSafeTwiceKeepsVictimSafe() {
        victim.markSafe();
        victim.markSafe();

        assertTrue(victim.isSafe());
    }

    @Test
    public void existingSafeVictimKeepsStatus() {
        Victim existing = createExistingVictim(
            new ArrayList<>(),
            new ArrayList<>(),
            true
        );

        assertTrue(existing.isSafe());
    }

    @Test
    public void existingUnsafeVictimKeepsStatus() {
        Victim existing = createExistingVictim(
            new ArrayList<>(),
            new ArrayList<>(),
            false
        );

        assertFalse(existing.isSafe());
    }

    @Test
    public void newVictimHasNoGuests() {
        assertTrue(victim.getGuest().isEmpty());
    }

    @Test
    public void newVictimHasNoMembers() {
        assertTrue(victim.getMember().isEmpty());
    }

    @Test
    public void newVictimHasNoPet() {
        assertNull(victim.getPet());
    }

    @Test
    public void existingVictimKeepsGuests() {
        ArrayList<String> guests = new ArrayList<>();
        guests.add("Michael");

        Victim existing = createExistingVictim(
            guests,
            new ArrayList<>(),
            false
        );

        assertTrue(existing.getGuest().contains("Michael"));
    }

    @Test
    public void existingVictimKeepsMembers() {
        ArrayList<String> members = new ArrayList<>();
        members.add("Sarah");

        Victim existing = createExistingVictim(
            new ArrayList<>(),
            members,
            false
        );

        assertTrue(existing.getMember().contains("Sarah"));
    }

    @Test
    public void createReliefRequestReturnsRequest() {
        ReliefRequest request = victim.createReliefRequest(
            "John Doe",
            "Columbia",
            "Transportation",
            "Needs medical assistance",
            UrgencyLevel.values()[0],
            3,
            true,
            "Buddy",
            "Dog"
        );

        assertNotNull(request);
    }

    @Test
    public void nullGuestListShouldBeEmpty() {
        Victim existing = createExistingVictim(
            null,
            new ArrayList<>(),
            false
        );

        assertNotNull(existing.getGuest());
    }

    @Test
    public void nullMemberListShouldBeEmpty() {
        Victim existing = createExistingVictim(
            new ArrayList<>(),
            null,
            false
        );

        assertNotNull(existing.getMember());
    }
}
