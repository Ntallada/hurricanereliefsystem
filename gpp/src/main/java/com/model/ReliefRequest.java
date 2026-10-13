package com.model;

import java.util.UUID;

public class ReliefRequest {
    private UUID requestId;
    private User user;
    private String victimName;
    private String location; //changed enum to string for location
    private AidType aidType;
    private String specialRequests;
    private UrgencyLevel urgencyLevel;
    private String status;
    private boolean petNeeds;
    private boolean emsCalled; //added to run callEMS method

    public ReliefRequest(User user, String victimName, String location, String sepcialrequests, UrgencyLevel urgencyLevel, boolean petNeeds) {
        this.user = user;
        this.victimName = victimName;
        this.location = location;
        this.specialRequests = sepcialrequests;
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
    }

    public updateStatus(UrgencyLevel level, String status) {
        this.urgencyLevel = level;
        this.status = status;
    }

    public callEMS() {
        return null;
    }

    public attachPhoto(String photoPath) {
        this.photoPath = photoPath;
    }

    public viewETA() {
        return null;
    }
}