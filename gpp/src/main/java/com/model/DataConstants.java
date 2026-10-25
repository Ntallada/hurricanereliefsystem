
package com.model;
/**
 * constants that holds file paths and key named to be used for dataloader and datawriter
 * DataConstants
 */
public abstract class DataConstants {
    // user
    
    /** path to json file that holds user data */
    protected static final String USERS_FILE_NAME = "gpp/json/users.json";
    
    /** key for uuid */
    protected static final String USER_ID = "userId";
    
    /** key for user first name */
    protected static final String USER_FIRST_NAME = "firstName";
    
    /** key for user last name */
    protected static final String USER_LAST_NAME = "lastName";
    
    /** key for user phone number */
    protected static final String USER_PHONE_NUMBER = "phoneNumber";
    
    /** key for user email */
    protected static final String USER_EMAIL = "email";
    
    /** key for user age */
    protected static final String USER_AGE = "age";
    
    /** key for user password */
    protected static final String USER_PASSWORD = "password";
    
    /** key for user language */
    protected static final String USER_LANGUAGE = "language";
    
    /** key for user current location */
    protected static final String USER_CURRENT_LOCATION = "currentLocation";
    
    /** key for user admin status */
    protected static final String USER_ADMIN = "admin";
    
    /** key for user type */
    protected static final String USER_TYPE = "type";
    
    /** key for user background check */
    protected static final String USER_BACKGROUND_CHECK = "backgroundCheck";
    
    /** key for user assigned skills */
    protected static final String USER_ASSIGNED_SKILLS = "assignedSkills";
    
    //shelter

    /** path to json file that holds shelter data */
    protected static final String SHELTERS_FILE_NAME = "gpp/json/shelters.json";
   
    /** key for shelter id */
    protected static final String SHELTER_ID = "id";
    
    /** key for shelter location */
    protected static final String SHELTER_LOCATION = "location";
    
    /** key for shelter capacity */
    protected static final String SHELTER_CAPACITY = "capacity";
    
    /** key for shelter occupancy */
    protected static final String SHELTER_OCCUPANCY = "occupancy";
    
    /** key for shelter status */
    protected static final String SHELTER_STATUS = "status";
    
    /** key for shelter pet-friendly status */
    protected static final String SHELTER_PET_FRIENDLY = "petFriendly";
    
    // request

    /** path to json file that holds relief request data */
    protected static final String REQUESTS_FILE_NAME = "gpp/json/reliefRequest.json";
    
    /** key for request id */
    protected static final String REQUEST_ID = "requestId";
    
    /** key for victim name */
    protected static final String REQUEST_VICTIM_NAME = "victimName";
    
    /** key for request location */
    protected static final String REQUEST_LOCATION = "location";

    /** key for request aid type */
    protected static final String REQUEST_AID_TYPE = "aidType";

    /** key for request special requests */
    protected static final String REQUEST_SPECIAL_REQUESTS = "specialRequests";
    
    /** key for request urgency level */
    protected static final String REQUEST_URGENCY_LEVEL = "urgencyLevel";
    
    /** key for request status */
    protected static final String REQUEST_STATUS = "status";
    
    /** key for request pet needs */
    protected static final String REQUEST_PET_NEEDS = "petNeeds";
    
    /** key for request user id */
    protected static final String REQUEST_USER_ID = "userId";
    
    /** key for request volunteer id */
    protected static final String REQUEST_VOLUNTEER_ID = "volunteerId";
    
    /** key for request number of people */
    protected static final String REQUEST_NUMBER_OF_PEOPLE = "numberOfPeople";
    
    /** key for request medical needed */
    protected static final String REQUEST_MEDICAL_NEEDED = "medicalNeeded";
    
    /** key for request pet name */
    protected static final String REQUEST_PET_NAME = "petName";
    
    /** key for request pet type */
    protected static final String REQUEST_PET_TYPE = "petType";
}
 