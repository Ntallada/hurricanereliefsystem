package com.model;

import java.util.UUID;

// Represents a victim's request for hurricane relief assistance, including location, urgency, and assignment details.
public class ReliefRequest {
    private UUID requestId;
    private User user;
    private String victimName;
    private String location; 
    private String aidType;
    private String specialRequests;
    private UrgencyLevel urgencyLevel;
    private String status;
    private boolean petNeeds;
    private int numberOfPeople;
    private boolean medicalNeeded;
    private String petName; 
    private String petType;
    private UUID volunteerId;
    private UUID shelterId;
    private boolean emsCalled; 
    
    // Constructs a new ReliefRequest from user input with default pending status and unassigned IDs.
    public ReliefRequest(User user, String victimName, String location, String aidType, String specialRequests, UrgencyLevel urgencyLevel, int numberOfPeople, boolean medicalNeeded, String petName, String petType) {
        this.user = user;
        this.victimName = victimName;
        this.location = location;
        this.aidType = aidType;
        this.specialRequests = specialRequests;
        this.urgencyLevel = urgencyLevel;
        this.numberOfPeople = numberOfPeople;
        this.medicalNeeded = medicalNeeded;
        this.petName = petName; 
        this.petType = petType;
        this.petNeeds = petName != null; 
        this.requestId = UUID.randomUUID();
        this.status = "PENDING";
        this.volunteerId = null;
        this.shelterId = null;
        this.emsCalled = false;
    }

    // Constructs a ReliefRequest from string data (useful when loading from persistent storage or files).
    public ReliefRequest(String requestId, String victimName, String location,
            String aidType, String specialRequests, String urgencyLevel,
            String status, boolean petNeeds) {
        this.requestId = UUID.fromString(requestId);
        this.user = null;
        this.victimName = victimName;
        this.location = location;
        this.aidType = aidType;
        this.specialRequests = specialRequests;
        this.urgencyLevel = UrgencyLevel.valueOf(urgencyLevel.trim().toUpperCase());
        this.status = status;
        this.petNeeds = petNeeds;
        this.numberOfPeople = 0;
        this.medicalNeeded = false;
        this.petName = null;
        this.petType = null;
        this.volunteerId = null;
        this.shelterId = null;
        this.emsCalled = false;
    }

    // Constructs a ReliefRequest with fully specified fields including an existing UUID and volunteer assignment.
    public ReliefRequest(UUID requestId, User user, String victimName, String location, String aidType, String specialRequests, UrgencyLevel urgencyLevel, int numberOfPeople, boolean medicalNeeded, String petName, String petType, String status, UUID volunteerId) {
        this.requestId = requestId;
        this.user = user;
        this.victimName = victimName;
        this.location = location;
        this.aidType = aidType;
        this.specialRequests = specialRequests;
        this.numberOfPeople = numberOfPeople;
        this.medicalNeeded = medicalNeeded;
        this.petName = petName;
        this.petType = petType;
        this.petNeeds = petName != null;
        this.urgencyLevel = urgencyLevel;
        this.status = status;
        this.volunteerId = volunteerId;
        this.shelterId = null;
        this.emsCalled = false;
    }

    // Updates the urgency level and status of the relief request.
    public void updateStatus(UrgencyLevel level, String status) {
        this.urgencyLevel = level;
        this.status = status;
    }

    // Triggers emergency medical services for this request and escalates urgency to high.
    public void callEMS() {
        emsCalled = true;
        urgencyLevel = UrgencyLevel.HIGH;
        System.out.println("Emergency services have been called for this relief request. " + requestId);
    }

    // Returns an ETA status message based on whether EMS or a volunteer has been dispatched.
    public String viewETA() {
        if (emsCalled) {
            return "The EMS are on their way and will arrive shortly to " + location + ".";
        }
        if (this.volunteerId != null) {
            return "The volunteer with ID " + volunteerId + " is on their way and will arrive shortly to " + location + ".";
        } else {
            return "No volunteer has been assigned to this relief request yet.";
        }
    }

    // Gets the unique request ID.
    public UUID getRequestId() { 
        return requestId; 
    }
    
    // Gets the user associated with the request.
    public User getUser() { 
        return user; 
    }

    // Gets the name of the victim who made the request.
    public String getVictimName() { 
        return victimName; 
    }

    // Gets the location address of the request.
    public String getLocation() { 
        return location; 
    }

    // Gets the type of aid requested.
    public String getAidType() {
        return aidType;
    }
    
    // Gets any special instructions or requests.
    public String getSpecialRequests() { 
        return specialRequests; 
    }

    // Gets the current urgency level.
    public UrgencyLevel getUrgencyLevel() { 
        return urgencyLevel; 
    }

    // Gets the current status of the request.
    public String getStatus() { 
        return status; 
    }

    // Checks whether pet accommodations are needed.
    public boolean isPetNeeds() { 
        return petNeeds; 
    }
    
    // Gets the ID of the assigned volunteer.
    public UUID getVolunteerId() { 
        return volunteerId; 
    }

    // Gets the ID of the assigned shelter.
    public UUID getShelterId() {
        return shelterId;
    }
    
    // Checks if EMS has been called.
    public boolean isEmsCalled() { 
        return emsCalled; 
    }

    // Assigns a volunteer to this request and updates status to ASSIGNED.
    public void assignVolunteer(UUID volunteerId) {
        this.volunteerId = volunteerId;
        this.status = "ASSIGNED";
    }

    // Assigns a shelter to this request.
    public void assignShelter(UUID shelterId) {
        this.shelterId = shelterId;
    }

    // Gets the number of people included in the request.
    public int getNumberOfPeople() { 
        return numberOfPeople; 
    }

    // Checks if medical assistance is needed.
    public boolean isMedicalNeeded() { 
        return medicalNeeded; 
    }

    // Gets the pet's name if applicable.
    public String getPetName() { 
        return petName; 
    }

    // Gets the pet's type if applicable.
    public String getPetType() { 
        return petType; 
    }
}