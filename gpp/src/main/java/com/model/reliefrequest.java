package com.model;

import java.util.UUID;

public class ReliefRequest {
    private UUID requestId;
    private User user;
    private String victimName;
    private String location = [];
    private String aidType = [];
    private String specialRequests;
    private int urgencyLevel = [1,2,3,4,5];
    private String status;
    private boolean petNeeds;

    public ReliefRequest(User user, String victimName, String location, String aidType, String sepcialrequests, int urgencyLevel, bool petNeeds) {
        this.user = user;
        this.victimName = victimName;
        this.location = location;
        this.aidType = aidType;
        this.specialRequests = sepcialrequests;
        this.urgencyLevel = urgencyLevel;
        this.petNeeds = petNeeds;
    }

    public ReliefRequest(UUID requestId, User user, String victimName, enum location, enum aidType, String sepcialrequests, enum urgencyLevel, bool petNeeds, String status) {
        this.requestId = requestId;
        this.user = user;
        this.victimName = victimName;
        this.location = location;
        this.aidType = aidType;
        this.specialRequests = sepcialrequests;
        this.urgencyLevel = urgencyLevel;
        this.petNeeds = petNeeds;
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