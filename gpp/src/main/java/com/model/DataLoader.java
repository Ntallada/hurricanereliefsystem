package com.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.UUID;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


public class DataLoader extends DataConstants {

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
                    Volunteer volunteer = new Volunteer(id, firstName, lastName, age, phoneNumber, email,
                            password, language, currentLocation, admin,
                            backgroundCheck, loadSkills((JSONArray) userJSON.get(USER_SKILLS)),
                            new ArrayList<String>(), true);
                    volunteer.setVehicle((String) userJSON.get(USER_VEHICLE));
                    users.add(volunteer);
                } else if ("MANAGER".equalsIgnoreCase(type)) {
                    users.add(new ShelterManager(id, firstName, lastName, age, phoneNumber,
                            email, password, language, currentLocation, admin));
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

                UUID id = null;
                if (shelterJSON.get(SHELTER_ID) != null) {
                    id = UUID.fromString((String) shelterJSON.get(SHELTER_ID));
                }
                String location = (String) shelterJSON.get(SHELTER_LOCATION);
                String name = (String) shelterJSON.get(SHELTER_NAME);
                if (name == null) {
                    name = location;
                }
                long capacity = (Long) shelterJSON.get(SHELTER_CAPACITY);
                long occupancy = (Long) shelterJSON.get(SHELTER_OCCUPANCY);
                String status = (String) shelterJSON.get(SHELTER_STATUS);
                boolean petFriendly = (Boolean) shelterJSON.get(SHELTER_PET_FRIENDLY);
                boolean hasMedicalStaff = Boolean.TRUE.equals(shelterJSON.get(SHELTER_MEDICAL_STAFF));
                shelters.add(new Shelter(name, location, id, (int) capacity, (int) occupancy,
                        status, petFriendly, hasMedicalStaff,
                        loadStringList((JSONArray) shelterJSON.get(SHELTER_OCCUPANTS)),
                        loadStringList((JSONArray) shelterJSON.get(SHELTER_PETS))));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return shelters;
    }

    private static ArrayList<Skill> loadSkills(JSONArray skillsJSON) {
        ArrayList<Skill> skills = new ArrayList<>();
        if (skillsJSON != null) {
            for (Object skill : skillsJSON) {
                skills.add(Skill.valueOf(skill.toString()));
            }
        }
        return skills;
    }

    private static ArrayList<String> loadStringList(JSONArray values) {
        ArrayList<String> result = new ArrayList<>();
        if (values != null) {
            for (Object value : values) {
                result.add(value.toString());
            }
        }
        return result;
    }

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

                ReliefRequest request = new ReliefRequest(requestId, user, victimName, location, aidType,
                        specialRequests, urgencyLevel, numberOfPeople, medicalNeeded, petName, petType,
                        status, volunteerId);
                if (requestJSON.get(REQUEST_SHELTER_ID) != null) {
                    request.assignShelter(UUID.fromString((String) requestJSON.get(REQUEST_SHELTER_ID)));
                }
                requests.add(request);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return requests;
        }
}
