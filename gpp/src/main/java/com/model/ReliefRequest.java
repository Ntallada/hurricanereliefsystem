package com.model;

import java.util.UUID;

public class ReliefRequest {
    private UUID requestId;
    private User user;
    private String victimName;
    private String location; //changed enum to string for location
    private String specialRequests;
    private UrgencyLevel urgencyLevel;
    private String status;
    private boolean petNeeds;
    private UUID volunteerId;
    private boolean emsCalled; //added to run callEMS method
    
    public ReliefRequest(User user, String victimName, String location, String sepcialrequests, UrgencyLevel urgencyLevel, boolean petNeeds) {
        this.user = user;
        this.victimName = victimName;
        this.location = location;
        this.specialRequests = specialRequests;
        this.urgencyLevel = urgencyLevel;
        this.petNeeds = petNeeds;
    }

    public ReliefRequest(UUID requestId, User user, String victimName, String location, String sepcialrequests, UrgencyLevel urgencyLevel, boolean petNeeds, String status) {
        this.requestId = requestId;
        this.user = user;
        this.victimName = victimName;
        this.location = location;
        this.specialRequests = sepcialrequests;
        this.urgencyLevel = urgencyLevel;
        this.petNeeds = petNeeds;
        this.status = status;
        this.volunteerId = null;
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
    }
}
