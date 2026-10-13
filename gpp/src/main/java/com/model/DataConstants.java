
package com.model;
 
public abstract class DataConstants {
    //user stuff
    protected static final String USERS_FILE_NAME = "json/user.json";
    protected static final String USER_ID = "id";
    protected static final String USER_FIRST_NAME = "firstName";
    protected static final String USER_LAST_NAME = "lastName";
    protected static final String USER_PHONE_NUMBER = "phoneNumber";
    protected static final String USER_EMAIL = "email";
    protected static final String USER_AGE = "age";
    
    /* this is also in uml
    protected static final String USER_PASSWORD = "password";
    protected static final String USER_LANGUAGE = "language";
    protected static final String USER_CURRENT_LOCATION = "currentLocation";
    protected static final String USER_ADMIN = "admin";
    */

    //shelter
    protected static final String SHELTERS_FILE_NAME = "json/shelters.json";
    protected static final String SHELTER_LOCATION = "location";
    protected static final String SHELTER_CAPACITY = "capacity";
    protected static final String SHELTER_OCCUPANCY = "occupancy";
    protected static final String SHELTER_STATUS = "status";
    protected static final String SHELTER_PET_FRIENDLY = "petFriendly";
    //request
    protected static final String REQUESTS_FILE_NAME = "json/requests.json";
    protected static final String REQUEST_ID = "requestID";
    protected static final String REQUEST_VICTIM_NAME = "victimName";
    protected static final String REQUEST_LOCATION = "location";
    protected static final String REQUEST_AID_TYPE = "aidType";
    protected static final String REQUEST_SPECIAL_REQUESTS = "specialRequests";
    protected static final String REQUEST_URGENCY_LEVEL = "urgencyLevel";
    protected static final String REQUEST_STATUS = "status";
    protected static final String REQUEST_PET_NEEDS = "petNeeds";
}
 