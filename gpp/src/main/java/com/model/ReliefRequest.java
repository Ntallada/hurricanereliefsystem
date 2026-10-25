package com.model;

import java.util.UUID;

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
    private boolean emsCalled; 
    
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
        this.emsCalled = false;
    }

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
        this.emsCalled = false;
    }

    public ReliefRequest(UUID requestId, User user, String victimName, String location, String specialRequests, UrgencyLevel urgencyLevel, int numberOfPeople, boolean medicalNeeded, String petName, String petType, String status, UUID volunteerId) {
        this.requestId = requestId;
        this.user = user;
        this.victimName = victimName;
        this.location = location;
        this.aidType = "";
        this.numberOfPeople = numberOfPeople;
        this.medicalNeeded = medicalNeeded;
        this.petName = petName;
        this.petType = petType;
        this.petNeeds = petName != null;
        this.urgencyLevel = urgencyLevel;
        this.status = status;
        this.volunteerId = volunteerId;
        this.emsCalled = false;
    }

    public void updateStatus(UrgencyLevel level, String status) {
        this.urgencyLevel = level;
        this.status = status;
    }

    public void callEMS() {
        emsCalled = true;
        urgencyLevel = UrgencyLevel.HIGH;
        System.out.println("Emergency services have been called for this relief request. " + requestId);
    }

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


    public UUID getRequestId() { 
        return requestId; 
    }
    
    public User getUser() { 
        return user; 
    }

    public String getVictimName() { 
        return victimName; 
    }

    public String getLocation() { 
        return location; 
    }

    public String getAidType() {
        return aidType;
    }
    
    public String getSpecialRequests() { 
        return specialRequests; 
    }

    public UrgencyLevel getUrgencyLevel() { 
        return urgencyLevel; 
    }

    public String getStatus() { 
        return status; 
    }

    public boolean isPetNeeds() { 
        return petNeeds; 
    }
    
    public UUID getVolunteerId() { 
        return volunteerId; 
    }
    
    public boolean isEmsCalled() { 
        return emsCalled; 
    }

    public void assignVolunteer(UUID volunteerId) {
        this.volunteerId = volunteerId;
        this.status = "ASSIGNED";
    }
    public int getNumberOfPeople() { return numberOfPeople; }
    public boolean isMedicalNeeded() { return medicalNeeded; }
    public String getPetName() { return petName; }
    public String getPetType() { return petType; }

}
