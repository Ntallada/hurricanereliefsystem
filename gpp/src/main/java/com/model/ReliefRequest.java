package com.model;

import java.util.UUID;

public class ReliefRequest {
    private UUID requestId;
    private User user;
    private String victimName;
    private enum location;
    private enum aidType;
    private String specialRequests;
    private enum urgencyLevel;
    private String status;
    private boolean petNeeds;

    public ReliefRequest(User user, String victimName, enum location, enum aidType, String sepcialrequests, enum urgencyLevel, bool petNeeds) {
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

    public updateStatus(enum urgencyLevel, String level, String status) {
        this.urgencyLevel = urgencyLevel;
        this.status = status;
    }

    public callEMS() {
        return null;
    }

    public attachPhoto(String photoPath) {
        return null;
    }

    public viewETA() {
        return null;
    }
}