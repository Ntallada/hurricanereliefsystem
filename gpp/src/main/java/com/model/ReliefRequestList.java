package com.model;

import java.util.ArrayList;
import java.util.UUID;

public class ReliefRequestList {
    
    private ArrayList<ReliefRequest> requests;
    private static ReliefRequestList instance;

    private ReliefRequestList() {
        requests = DataLoader.getRequests();
    }

    public static ReliefRequestList getInstance() {
        if (instance == null) {
            instance = new ReliefRequestList();
        }
        return instance;
    }

    public ArrayList<ReliefRequest> getRequests() {
        return requests;
    }

    public void addRequest(ReliefRequest request) {
        requests.add(request);
        DataWriter.saveRequests(requests);
    }

    public boolean removeRequest(UUID requestId) {
        ReliefRequest found = getRequestById(requestId);
        if (found != null) {
            requests.remove(found);
            DataWriter.saveRequests(requests);
            return true;
        }
        return false;
    }

    public ReliefRequest getRequestById(UUID requestId) {
        for (ReliefRequest request : requests) {
            if (request.getRequestId().equals(requestId)) {
                return request;
            }
        }
        return null;
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