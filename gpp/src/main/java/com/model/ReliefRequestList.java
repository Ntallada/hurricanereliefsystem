package com.model;

import java.util.ArrayList;
import java.util.UUID;

public class ReliefRequestList {
    private ArrayList<ReliefRequest> requests;
    
}

public void addRequest(ReliefRequest request) {
    requests.add(request);
}

public void addRequest(String victimName, String location, String specialRequests, UrgencyLevel urgencyLevel, boolean petNeeds) {
    ReliefRequest request = new ReliefRequest(victimName, location, specialRequests, urgencyLevel, petNeeds);
    requests.add(request); //?
}

public void removeRequest(boolean String requestId) { // void?
    for (ReliefRequest request : requests) {
        if (request.getRequestId().toString().equals(requestId)) {
            requests.remove(request);
            break;
        } // ?
    }
    
}

public getRequestById(String requestId) {
    for (ReliefRequest request : requests) {
        if (request.getRequestId().toString().equals(requestId)) {
            return request;
        }
    }
    return null;
}

public getRequestsByUrgency(UrgencyLevel urgencyLevel) {
    ArrayList<ReliefRequest> filteredRequests = new ArrayList<>();
    for (ReliefRequest request : requests) {
        if (request.getUrgencyLevel() == urgencyLevel) {
            filteredRequests.add(request);
        }
    }
    return filteredRequests;
}

public getPendingRequests() {
    ArrayList<ReliefRequest> pendingRequests = new ArrayList<>();
    for (ReliefRequest request : requests) {
        if (request.getStatus().equals("PENDING")) {
            pendingRequests.add(request);
        }
    }
    return pendingRequests;
}