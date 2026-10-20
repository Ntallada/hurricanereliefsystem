package com.model;

import java.util.ArrayList;
import java.util.UUID;

public class ReliefRequestList {
    
    private ArrayList<ReliefRequest> requests;

    public ReliefRequestList() {
        requests = new ArrayList<>();
    }

    public void addRequest(ReliefRequest request) {
        requests.add(request);
    }

    public void addRequest(String victimName, String location, String specialRequests, UrgencyLevel urgencyLevel, boolean petNeeds) {
        requests.add(new ReliefRequest(victimName, location, specialRequests, urgencyLevel, petNeeds));
    }

    public boolean removeRequest(String requestId) {
        ReliefRequest found = getRequestById(requestId);
        if (found != null) {
            requests.remove(found);
            return true;
        }
        return false;
    }

    public ReliefRequest getRequestById(String requestId) {
        // ?? idk
    }

    public ArrayList<ReliefRequest> getRequestsByUrgency(UrgencyLevel urgencyLevel) {
        ArrayList<ReliefRequest> results = new ArrayList<>();
        for (ReliefRequest request : requests) {
            if (request.getUrgencyLevel() == urgencyLevel) {
                results.add(request);
            }
        }
        return results;
    }

    public ArrayList<ReliefRequest> getPendingRequests() {
        ArrayList<ReliefRequest> results = new ArrayList<>();
        for (ReliefRequest request : requests) {
            if (request.getStatus().equals("PENDING")) {
                results.add(request);
            }
        }
        return results;
    }
}