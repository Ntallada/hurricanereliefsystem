package com.model; 
/**
 * 
 * The "front door" to the program. 
 * 
 */
public class HurricaneReliefFacade {

    private UserList users; // list of all users from user.json
    private User currentUser; // the person who is logged in

    private static HurricaneReliefFacade instance; // the only facade

    private HurricaneReliefFacade() {
        users = UserList.getInstance(); // gets shared the user list
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
}
