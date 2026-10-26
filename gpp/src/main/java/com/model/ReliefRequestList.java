package com.model;

import java.util.ArrayList;
import java.util.UUID;

// Manages a centralized collection of all relief requests, implementing the Singleton design pattern and persistent storage integration.
public class ReliefRequestList {
    
    private ArrayList<ReliefRequest> requests;
    private static ReliefRequestList instance;

    // Private constructor for Singleton pattern that loads existing requests from persistent storage.
    private ReliefRequestList() {
        requests = DataLoader.getRequests();
    }

    // Gets the singleton instance of ReliefRequestList, creating it if it does not already exist.
    public static ReliefRequestList getInstance() {
        if (instance == null) {
            instance = new ReliefRequestList();
        }
        return instance;
    }

    // Gets the full list of relief requests.
    public ArrayList<ReliefRequest> getRequests() {
        return requests;
    }

    // Adds a new relief request to the list and saves the updated list to persistent storage.
    public void addRequest(ReliefRequest request) {
        requests.add(request);
        DataWriter.saveRequests(requests);
    }

    // Removes a relief request by its unique UUID and saves the updated list to storage if successful.
    public boolean removeRequest(UUID requestId) {
        ReliefRequest found = getRequestById(requestId);
        if (found != null) {
            requests.remove(found);
            DataWriter.saveRequests(requests);
            return true;
        }
        return false;
    }

    // Finds and returns a specific relief request by its unique UUID.
    public ReliefRequest getRequestById(UUID requestId) {
        for (ReliefRequest request : requests) {
            if (request.getRequestId().equals(requestId)) {
                return request;
            }
        }
        return null;
    }

    // Filters and returns all relief requests matching a specified urgency level.
    public ArrayList<ReliefRequest> getRequestsByUrgency(UrgencyLevel urgencyLevel) {
        ArrayList<ReliefRequest> results = new ArrayList<>();
        for (ReliefRequest request : requests) {
            if (request.getUrgencyLevel() == urgencyLevel) {
                results.add(request);
            }
        }
        return results;
    }

    // Filters and returns all relief requests that currently have a "PENDING" status.
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