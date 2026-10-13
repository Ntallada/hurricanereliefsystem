package com.model;

import java.io.FileWriter;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class DataWriter extends DataConstants {

    @SuppressWarnings("unchecked")
    public static boolean saveUsers(ArrayList<User> users) {
        JSONArray usersJSON = new JSONArray();

        for (User user : users) {
            JSONObject userJSON = new JSONObject();

            userJSON.put(USER_ID, user.getId().toString());  
            userJSON.put(USER_FIRST_NAME, user.getfirstName().toString());
            userJSON.put(USER_LAST_NAME, user.getlastName().toString());
            userJSON.put(USER_PASSWORD, user.getPassword());
            userJSON.put(USER_LANGUAGE, user.getLanguage());
            userJSON.put(USER_CURRENT_LOCATION, user.getCurrentLocation());
            userJSON.put(USER_EMAIL, user.getEmail());

            if (user instanceof Victim) {
                userJSON.put(USER_TYPE, "Victim");
            } else if (user instanceof Volunteer) {
                userJSON.put(USER_TYPE, "Volunteer");
                userJSON.put(USER_BACKGROUND_CHECK, ((Volunteer) user).getBackgroundCheck());
            } else if (user instanceof Admin) {
                userJSON.put(USER_TYPE, "Admin");
                userJSON.put(USER_SHELTER_ACCESS, ((Admin) user).getShelterAccess());
            }

            usersJSON.add(userJSON);
        }

        return writeFile(USERS_FILE_NAME, usersJSON);
    }

    @SuppressWarnings("unchecked")
    public static boolean saveShelters(ArrayList<Shelter> shelters) {
        JSONArray sheltersJSON = new JSONArray();

        for (Shelter shelter : shelters) {
            JSONObject shelterJSON = new JSONObject();

            shelterJSON.put(SHELTER_LOCATION, shelter.getLocation());
            shelterJSON.put(SHELTER_CAPACITY, shelter.getCapacity());
            shelterJSON.put(SHELTER_OCCUPANCY, shelter.getOccupancy());
            shelterJSON.put(SHELTER_STATUS, shelter.getStatus());
            shelterJSON.put(SHELTER_PET_FRIENDLY, shelter.isPetFriendly());

            sheltersJSON.add(shelterJSON);
        }

        return writeFile(SHELTERS_FILE_NAME, sheltersJSON);
    }

    @SuppressWarnings("unchecked")
    public static boolean saveRequests(ArrayList<ReliefRequest> requests) {
        JSONArray requestsJSON = new JSONArray();

        for (ReliefRequest request : requests) {
            JSONObject requestJSON = new JSONObject();

            requestJSON.put(REQUEST_ID, request.getRequestID());
            requestJSON.put(REQUEST_VICTIM_NAME, request.getVictimName());
            requestJSON.put(REQUEST_LOCATION, request.getLocation());
            requestJSON.put(REQUEST_AID_TYPE, request.getAidType());
            requestJSON.put(REQUEST_SPECIAL_REQUESTS, request.getSpecialRequests());
            requestJSON.put(REQUEST_URGENCY_LEVEL, request.getUrgencyLevel());
            requestJSON.put(REQUEST_STATUS, request.getStatus());
            requestJSON.put(REQUEST_PET_NEEDS, request.hasPetNeeds());

            requestsJSON.add(requestJSON);
        }

        return writeFile(REQUESTS_FILE_NAME, requestsJSON);
    }

    private static boolean writeFile(String fileName, JSONArray array) {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(array.toJSONString());
            writer.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}