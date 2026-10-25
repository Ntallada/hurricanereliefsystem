package com.model; 
/**
 * 
 * The "front door" to the program. 
 * 
 */

public class HurricaneReliefFacade {

    private UserList users; // list of all users from user.json
    private User currentUser; // the person who is logged in
    private ReliefRequestList requests;

    private static HurricaneReliefFacade instance; // the only facade

    private HurricaneReliefFacade() {
        users = UserList.getInstance(); // gets shared the user list
        requests = ReliefRequestList.getInstance();
    }
    public static HurricaneReliefFacade getInstance() { // public making it the only way to get the facade from other classes
        if (instance == null) { 
            instance = new HurricaneReliefFacade();
        }
        return instance;
    }
    // logs someone in, returns true if it worked, false if it did not. 
    public boolean login(String email, String password) {
        User user = users.getUserByEmail(email);
        if(user != null && user.login(password)) {
            currentUser = user;
            return true;
        }
        return false;
    }
    public void logout() {
        //only works when someone is logged in
        if(currentUser != null) {
            currentUser.logout();
            currentUser = null; // says no one is logged in anymore
        }
    }
    public User getCurrentUser() {
        return currentUser;
    }
    // creates a new account 
    public boolean signUp(String type, String firstName, String lastName, int age, String phoneNumber, String email, String password, Languages language, String currentLocation) {
        return users.addUser(type, firstName, lastName, age, phoneNumber, email, password, language, currentLocation);
    }

    // a victim submits a relief request; returns null if nobody or a non-victim is logged in
    public ReliefRequest submitRequest(String location, String aidType, String specialRequests,
        UrgencyLevel urgencyLevel, int numberOfPeople,
        boolean medicalNeeded, String petName, String petType) {

        if (!(currentUser instanceof Victim)) {
            return null;
        }

        Victim victim = (Victim) currentUser;
        String victimName = victim.getFirstName() + " " + victim.getLastName();
        ReliefRequest request = victim.createReliefRequest(victimName, location, aidType, specialRequests,
        urgencyLevel, numberOfPeople, medicalNeeded, petName, petType);
        requests.addRequest(request);
        return request;
    }

    public java.util.ArrayList<ReliefRequest> getAllRequests() {
        return requests.getRequests();
    }
}
