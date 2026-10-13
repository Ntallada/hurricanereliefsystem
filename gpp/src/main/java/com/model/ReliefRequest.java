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
}