package com.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.UUID;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * extends dataconstants to read users, shelters, and relief requests from json files
 * DataLoader
 */

public class DataLoader extends DataConstants {

    /**
     * reads users from json file and returns an arraylist of users
     * a users type determines what class is made, either victim or volunteer
     * if file could not be read, whatever was loaded is returned and error is printed
     * @return list of users in the file and left empty if file could not be read
     */
    public static ArrayList<User> loadUsers() {
        ArrayList<User> users = new ArrayList<>();

        try (FileReader reader = new FileReader(USERS_FILE_NAME)) {
            JSONParser parser = new JSONParser();
            JSONArray usersJSON = (JSONArray) parser.parse(reader);

            for (int i = 0; i < usersJSON.size(); i++) {
                JSONObject userJSON = (JSONObject) usersJSON.get(i);
                String idStr = (String) userJSON.get(USER_ID);
                UUID id = UUID.fromString(idStr);
                String firstName = (String) userJSON.get(USER_FIRST_NAME);
                String lastName = (String) userJSON.get(USER_LAST_NAME);
                String password = (String) userJSON.get(USER_PASSWORD);
                Languages language = Languages.valueOf((String) userJSON.get(USER_LANGUAGE));
                String currentLocation = (String) userJSON.get(USER_CURRENT_LOCATION);
                String type = (String) userJSON.get(USER_TYPE);
                String email = (String) userJSON.get(USER_EMAIL);
                int age = ((Long) userJSON.get(USER_AGE)).intValue();
                String phoneNumber = (String) userJSON.get(USER_PHONE_NUMBER);
                boolean admin = (Boolean) userJSON.get(USER_ADMIN);

                if ("VICTIM".equalsIgnoreCase(type)) {
                    users.add(new Victim(id, firstName, lastName, age, phoneNumber, email,
                            password, language, currentLocation, admin,
                            new ArrayList<String>(), new ArrayList<String>(), null, false));
                } else if ("VOLUNTEER".equalsIgnoreCase(type)) {
                    Boolean bc = (Boolean) userJSON.get(USER_BACKGROUND_CHECK);
                    boolean backgroundCheck = bc != null && bc;
                    users.add(new Volunteer(id, firstName, lastName, age, phoneNumber, email,
                            password, language, currentLocation, admin,
                            backgroundCheck, new ArrayList<Skill>(), new ArrayList<String>(), true));
                    }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return users;
    }

    /**
     * reads shelters from json file and returns an arraylist of shelters
     * if shelter has an id it is loaded with that id, otherwise a new id is generated
     * if file could not be read, whatever was loaded is returned and error is printed
     * @return list of shelters in the file and left empty if file could not be read
     */

    public static ArrayList<Shelter> getShelters() {
            ArrayList<Shelter> shelters = new ArrayList<>();

            try (FileReader reader = new FileReader(SHELTERS_FILE_NAME)) {
                JSONParser parser = new JSONParser();
                JSONArray sheltersJSON = (JSONArray) parser.parse(reader);

                for (int i = 0; i < sheltersJSON.size(); i++) {
                    JSONObject shelterJSON = (JSONObject) sheltersJSON.get(i);

                    UUID id = null;
                    if (shelterJSON.get(SHELTER_ID) != null) {
                        id = UUID.fromString((String) shelterJSON.get(SHELTER_ID));
                    }
                    String location = (String) shelterJSON.get(SHELTER_LOCATION);
                    long capacity = (Long) shelterJSON.get(SHELTER_CAPACITY);
                    long occupancy = (Long) shelterJSON.get(SHELTER_OCCUPANCY);
                    String status = (String) shelterJSON.get(SHELTER_STATUS);
                    boolean petFriendly = (Boolean) shelterJSON.get(SHELTER_PET_FRIENDLY);

                    if (id != null) {
                        shelters.add(new Shelter(location, id, (int) capacity, (int) occupancy, status, petFriendly));
                    } else {
                        shelters.add(new Shelter(location, (int) capacity, (int) occupancy, status, petFriendly));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        return shelters;
    }

    /**
     * reads relief requests from json file and returns an arraylist of requests
     * requests are linked to the userid if no id for user then request is null
     * if requests have no volunteerid then there is no volunteer for it
     * if file could not be read, whatever was loaded is returned and error is printed
     * @return list of every relief requests and left empty if file could not be read
     */
    public static ArrayList<ReliefRequest> getRequests() {
        ArrayList<ReliefRequest> requests = new ArrayList<>();

        try (FileReader reader = new FileReader(REQUESTS_FILE_NAME)) {
            JSONParser parser = new JSONParser();
            JSONArray requestsJSON = (JSONArray) parser.parse(reader);

        for (int i = 0; i < requestsJSON.size(); i++) {
            JSONObject requestJSON = (JSONObject) requestsJSON.get(i);

            UUID requestId = UUID.fromString((String) requestJSON.get(REQUEST_ID));
            UUID userId = UUID.fromString((String) requestJSON.get(REQUEST_USER_ID));
            User user = UserList.getInstance().getUserById(userId);
                String victimName = (String) requestJSON.get(REQUEST_VICTIM_NAME);
                String location = (String) requestJSON.get(REQUEST_LOCATION);
                String aidType = (String) requestJSON.get(REQUEST_AID_TYPE);
                String specialRequests = (String) requestJSON.get(REQUEST_SPECIAL_REQUESTS);
                UrgencyLevel urgencyLevel = UrgencyLevel.valueOf((String) requestJSON.get(REQUEST_URGENCY_LEVEL));
                String status = (String) requestJSON.get(REQUEST_STATUS);
                int numberOfPeople = ((Long) requestJSON.get(REQUEST_NUMBER_OF_PEOPLE)).intValue();
                boolean medicalNeeded = (Boolean) requestJSON.get(REQUEST_MEDICAL_NEEDED);
                String petName = (String) requestJSON.get(REQUEST_PET_NAME);
                String petType = (String) requestJSON.get(REQUEST_PET_TYPE);
                UUID volunteerId = null;
                if (requestJSON.get(REQUEST_VOLUNTEER_ID) != null) {
                    volunteerId = UUID.fromString((String) requestJSON.get(REQUEST_VOLUNTEER_ID));
                }

                requests.add(new ReliefRequest(requestId, user, victimName, location, aidType, specialRequests,
                    urgencyLevel, numberOfPeople, medicalNeeded, petName, petType, status, volunteerId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return requests;
        }
}
