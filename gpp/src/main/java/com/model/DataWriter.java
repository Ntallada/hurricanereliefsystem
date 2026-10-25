package com.model;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class DataWriter extends DataConstants {

    @SuppressWarnings("unchecked")
    public static boolean saveUsers(ArrayList<User> users) {
        JSONArray usersJSON = new JSONArray();

        for (User user : users) {
            JSONObject userJSON = new JSONObject();

            userJSON.put(USER_ID, user.getId().toString());  
            userJSON.put(USER_FIRST_NAME, user.getFirstName());
            userJSON.put(USER_LAST_NAME, user.getLastName());
            userJSON.put(USER_AGE, user.getAge());
            userJSON.put(USER_PHONE_NUMBER, user.getPhoneNumber());
            userJSON.put(USER_PASSWORD, user.getPassword());
            userJSON.put(USER_LANGUAGE, user.getLanguage().toString());
            userJSON.put(USER_CURRENT_LOCATION, user.getCurrentLocation());
            userJSON.put(USER_EMAIL, user.getEmail());
            userJSON.put(USER_ADMIN, user.isAdmin());

            if (user instanceof Volunteer) {
                userJSON.put(USER_TYPE, "VOLUNTEER");
                userJSON.put(USER_BACKGROUND_CHECK, ((Volunteer) user).getBackgroundCheck());
                Volunteer volunteer = (Volunteer) user;
                userJSON.put(USER_VEHICLE, volunteer.getVehicle());
                JSONArray skills = new JSONArray();
                for (Skill skill : volunteer.getAssignedSkills()) {
                    skills.add(skill.name());
                }
                userJSON.put(USER_SKILLS, skills);
            } else if (user instanceof ShelterManager) {
                userJSON.put(USER_TYPE, "MANAGER");
            } else {
                userJSON.put(USER_TYPE, "VICTIM");
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

            shelterJSON.put(SHELTER_ID, shelter.getShelterId() != null ? shelter.getShelterId().toString() : UUID.randomUUID().toString());
            shelterJSON.put(SHELTER_NAME, shelter.getName());
            shelterJSON.put(SHELTER_LOCATION, shelter.getLocation());
            shelterJSON.put(SHELTER_CAPACITY, shelter.getCapacity());
            shelterJSON.put(SHELTER_OCCUPANCY, shelter.getOccupancy());
            shelterJSON.put(SHELTER_STATUS, shelter.shelterStatus()); 
            shelterJSON.put(SHELTER_PET_FRIENDLY, shelter.isPetFriendly());
            shelterJSON.put(SHELTER_MEDICAL_STAFF, shelter.hasMedicalStaff());
            JSONArray occupants = new JSONArray();
            occupants.addAll(shelter.getOccupants());
            shelterJSON.put(SHELTER_OCCUPANTS, occupants);
            JSONArray pets = new JSONArray();
            pets.addAll(shelter.getPets());
            shelterJSON.put(SHELTER_PETS, pets);

            sheltersJSON.add(shelterJSON);
        }

        return writeFile(SHELTERS_FILE_NAME, sheltersJSON);
    }


    @SuppressWarnings("unchecked")
    public static boolean saveRequests(ArrayList<ReliefRequest> requests) {
        JSONArray requestsJSON = new JSONArray();

        for (ReliefRequest request : requests) {
            JSONObject requestJSON = new JSONObject();

            requestJSON.put(REQUEST_ID, request.getRequestId().toString());
            requestJSON.put(REQUEST_VICTIM_NAME, request.getVictimName());
            requestJSON.put(REQUEST_LOCATION, request.getLocation());
            requestJSON.put(REQUEST_AID_TYPE, request.getAidType());
            requestJSON.put(REQUEST_SPECIAL_REQUESTS, request.getSpecialRequests());
            requestJSON.put(REQUEST_URGENCY_LEVEL, request.getUrgencyLevel().toString());
            requestJSON.put(REQUEST_STATUS, request.getStatus());
            requestJSON.put(REQUEST_PET_NEEDS, request.isPetNeeds());
            requestJSON.put(REQUEST_USER_ID, request.getUser().getId().toString());
            requestJSON.put(REQUEST_NUMBER_OF_PEOPLE, request.getNumberOfPeople());
            requestJSON.put(REQUEST_MEDICAL_NEEDED, request.isMedicalNeeded());
            if (request.getPetName() != null) {
                requestJSON.put(REQUEST_PET_NAME, request.getPetName());
                requestJSON.put(REQUEST_PET_TYPE, request.getPetType());
            }
            if (request.getVolunteerId() != null) {
                requestJSON.put(REQUEST_VOLUNTEER_ID, request.getVolunteerId().toString());
            }
            if (request.getShelterId() != null) {
                requestJSON.put(REQUEST_SHELTER_ID, request.getShelterId().toString());
            }
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