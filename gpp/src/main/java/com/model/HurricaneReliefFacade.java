package com.model; 

public class HurricaneReliefFacade {

    private UserList user; 
    private User currentUser; 

    public boolean login(String email, String password) {
        User user = user.getUserByEmail(email);
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
}
