package com.model; 

public class HurricaneReliefFacade {

    private UserList users; 
    private User currentUser; 

    private static HurricaneReliefFacade instance;

    private HurricaneReliefFacade() {
        users = UserList.getInstance();
    }
    public static HurricaneReliefFacade getInstance() {
        if (instance == null) { 
            instance = new HurricaneReliefFacade();
        }
        return instance;
    }

    public boolean login(String email, String password) {
        User user = users.getUserByEmail(email);
        if(user != null && user.login(password)) {
            currentUser = user;
            return true;
        }
        return false;
    }
    public void logout() {
        if(currentUser != null) {
            currentUser.logout();
            currentUser = null;
        }
    }
    public User getCurrentUser() {
        return currentUser;
    }
    
    public boolean signUp(String type, String firstName, String lastName, int age,
                          String phoneNumber, String email, String password,
                          Languages language, String currentLocation) {
        return users.addUser(type, firstName, lastName, age, phoneNumber,
                             email, password, language, currentLocation);
    }
}
