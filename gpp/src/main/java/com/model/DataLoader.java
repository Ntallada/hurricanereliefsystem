package com.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.UUID;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


public class DataLoader extends DataConstants {

   public static ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<>();

        try (FileReader reader = new FileReader(USERS_FILE_NAME)) {
            JSONParser parser = new JSONParser();
            JSONArray usersJSON = (JSONArray) parser.parse(reader);

            for (int i = 0; i < usersJSON.size(); i++) {
                JSONObject userJSON = (JSONObject) usersJSON.get(i);
                String idStr = (String) userJSON.get(USER_ID);
                UUID id = UUID.fromString(idStr);
                String FirstName = (String) firstName.get(firstName);
                String LastName = (String) lastName.get(lastName);
                String password = (String) userJSON.get(USER_PASSWORD);
                String language = (String) userJSON.get(USER_LANGUAGE);
                String currentLocation = (String) userJSON.get(USER_CURRENT_LOCATION);
                String type = (String) userJSON.get(USER_TYPE);
                String email = (String) userJSON.get(USER_EMAIL);

                if ("Victim".equalsIgnoreCase(type)) {
                    users.add(new Victim(id, password, language, currentLocation));
                } else if ("Volunteer".equalsIgnoreCase(type)) {
                    boolean backgroundCheck = (Boolean) userJSON.get(USER_BACKGROUND_CHECK);
                    users.add(new Volunteer(id, password, language, currentLocation, backgroundCheck));
                } else if ("Admin".equalsIgnoreCase(type)) {
                    boolean shelterAccess = (Boolean) userJSON.get(USER_SHELTER_ACCESS);
                    users.add(new Admin(id, password, language, currentLocation, shelterAccess));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return users;
    }

    public static ArrayList<Shelter> getShelters() {
        ArrayList<Shelter> shelters = new ArrayList<>();

        try (FileReader reader = new FileReader(SHELTERS_FILE_NAME)) {
            JSONParser parser = new JSONParser();
            JSONArray sheltersJSON = (JSONArray) parser.parse(reader);

            for (int i = 0; i < sheltersJSON.size(); i++) {
                JSONObject shelterJSON = (JSONObject) sheltersJSON.get(i);

                String location = (String) shelterJSON.get(SHELTER_LOCATION);
                long capacity = (Long) shelterJSON.get(SHELTER_CAPACITY);
                long occupancy = (Long) shelterJSON.get(SHELTER_OCCUPANCY);
                String status = (String) shelterJSON.get(SHELTER_STATUS);
                boolean petFriendly = (Boolean) shelterJSON.get(SHELTER_PET_FRIENDLY);

                shelters.add(new Shelter(location, (int) capacity, (int) occupancy, status, petFriendly));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return shelters;

        } 

        public static ArrayList<ReliefRequest> getRequests() {
        ArrayList<ReliefRequest> requests = new ArrayList<>();

        try (FileReader reader = new FileReader(REQUESTS_FILE_NAME)) {
            JSONParser parser = new JSONParser();
            JSONArray requestsJSON = (JSONArray) parser.parse(reader);

        for (int i = 0; i < requestJSON.size(); i++) {
            JSONObject requestJSON = (JSONObject) requestsJSON.get(i);

            String requestID = (String) requestJSON.get(REQUEST_ID);
                String victimName = (String) requestJSON.get(REQUEST_VICTIM_NAME);
                String location = (String) requestJSON.get(REQUEST_LOCATION);
                String aidType = (String) requestJSON.get(REQUEST_AID_TYPE);
                String specialRequests = (String) requestJSON.get(REQUEST_SPECIAL_REQUESTS);
                String urgencyLevel = (String) requestJSON.get(REQUEST_URGENCY_LEVEL);
                String status = (String) requestJSON.get(REQUEST_STATUS);
                boolean petNeeds = (Boolean) requestJSON.get(REQUEST_PET_NEEDS);

                requests.add(new ReliefRequest(requestID, victimName, location, aidType, specialRequests, urgencyLevel, status, petNeeds));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return requests;
        }
}
