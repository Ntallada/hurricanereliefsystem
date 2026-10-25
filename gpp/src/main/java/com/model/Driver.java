package com.model;

public class Driver {
    
    public static void main(String[] args) {
        System.out.println(" Testing Data Loader ");
        UserList userList = UserList.getInstance();
        System.out.println("Loaded " + userList.getUsers().size() + " users from JSON.");

        /**
         * testing creating an account. use git restore gpp/json/users.json to reset test
         */
        System.out.println("\n Testing sign up and create user");
        boolean created = userList.addUser("VICTIM", "John", "Does", 21, "8035551234", "john.does@example.com", "password123", Languages.ENGLISH, "Columbia, SC");
        if (created) {
            System.out.println("User added and written to users.json successfully!");
        }
        
        System.out.println("\n Testing login");
        User loggedInUser = userList.getUser("John", "password123");

        if (loggedInUser != null) {
            System.out.println("User logged in successfully: " + loggedInUser.getFirstName() + " " + loggedInUser.getLastName());
            System.out.println("Current Location: " + loggedInUser.getCurrentLocation());
        } else {
            System.out.println("Login failed. Invalid username or password.");
        }

        System.out.println("\n Testing failed login");
        User failedUser = userList.getUser("john", "wrongPassword");
        if (failedUser == null) {
            System.out.println(" rejected bad password.");
        }

        /**
         * logout method
         */
        System.out.println("\n Testing logout");
        HurricaneReliefFacade facade = HurricaneReliefFacade.getInstance();
        if (facade.login("john.doe@example.com", "password123")) {
            System.out.println("Logged in as: " + facade.getCurrentUser().getFirstName());
            facade.logout();
        } else {
            System.out.println("Login failed; skipping logout test.");
        }
        System.out.println("Logging out: " + facade.getCurrentUser());

        // Testing Shelter List 
        System.out.println("\n Testing Shelter List ");
        ShelterList shelterList = ShelterList.getInstance();
        System.out.println("Loaded " + shelterList.getShelters().size() + " shelters from JSON.");

        for (Shelter shelter : shelterList.getShelters()) {
            System.out.println(shelter);
        }

        // Tests adding new shelter
        boolean shelterExists = shelterList.getShelters().stream()
                .anyMatch(shelter -> "Community Center".equalsIgnoreCase(shelter.getLocation()));
        if (!shelterExists) {
            Shelter newShelter = new Shelter("Community Center", 150, 45, "OPEN", true);
            shelterList.addShelter(newShelter);
            System.out.println("Added new shelter. Total shelters now: " + shelterList.getShelters().size());
        } else {
            System.out.println("Community Center already exists; not adding a duplicate.");
        }
       
        
        // Execute core project scenarios
        testMap();
        scenarioCreateRequest();
        scenarioMatchAndAssignRequest();
    } 
    public static void scenarioCreateRequest() {
        System.out.println("\n--- Scenario: Create the relief request ---");
        HurricaneReliefFacade facade = HurricaneReliefFacade.getInstance();

        if (!facade.signUp("VICTIM", "Mark", "Carter", 41, "843-555-0142",
                "mark.carter@example.com", "password123", Languages.ENGLISH,
                "12 Church St, Charleston, SC")) {
            System.out.println("Account already exists, logging in instead.");
        }
        if (!facade.login("mark.carter@example.com", "password123")) {
            System.out.println("Login failed.");
            return;
        }

        ReliefRequest request = facade.submitRequest(
                "12 Church St, Charleston, SC",
                "Transportation",
                "Daughter Sophie has asthma and needs medical assistance. Dog: Buddy.",
                UrgencyLevel.HIGH, 3, true, "Buddy", "Dog");

        if (request == null) {
            System.out.println("Request was not created.");
            facade.logout();
            return;
        }
        System.out.println("Request submitted: " + request.getRequestId());
        System.out.println("Type: " + request.getAidType() + ", status: " + request.getStatus());
        System.out.println("People: " + request.getNumberOfPeople() + ", medical: " + request.isMedicalNeeded());
        System.out.println("Pet: " + request.getPetName() + " (" + request.getPetType() + ")");
        System.out.println("Total requests saved: " + facade.getAllRequests().size());
        facade.logout();
    }

    public static void scenarioMatchAndAssignRequest() {
        System.out.println("\n Scenario: Match Rosa with Request & Recommend Shelter ");
        HurricaneReliefFacade facade = HurricaneReliefFacade.getInstance();

        if (!facade.signUp("VOLUNTEER", "Rosa", "Delgado", 35, "843-555-9988",
                "rosa.delgado@example.com", "password123", Languages.ENGLISH,
                "5001 Coliseum Dr, North Charleston, SC")) {
            System.out.println("Rosa account exists, logging in.");
        }
        if (!facade.login("rosa.delgado@example.com", "password123")
                || !(facade.getCurrentUser() instanceof Volunteer)) {
            System.out.println("Volunteer login failed; skipping assignment scenario.");
            facade.logout();
            return;
        }
        Volunteer volunteer = (Volunteer) facade.getCurrentUser();
        System.out.println("Logged in as Volunteer: " + volunteer.getFirstName());
        
        System.out.println("Viewing open relief requests...");
        ReliefRequest targetRequest = null;
        for (ReliefRequest req : facade.getAllRequests()) {
            if (req.getLocation() != null
                    && req.getLocation().contains("Charleston")
                    && "PENDING".equalsIgnoreCase(req.getStatus())) {
                targetRequest = req;
                break;
            }
        }
        if (targetRequest !=null) {
            System.out.println("Found recommended request ID: " + targetRequest.getRequestId() 
                + " for " + targetRequest.getAidType());
            
            Shelter bestShelter = null;
            double minDistance = Double.MAX_VALUE;

            for (Shelter shelter : ShelterList.getInstance().getShelters()) {
                int availableSpaces = shelter.getCapacity() - shelter.getOccupancy();
                if ((!targetRequest.isPetNeeds() || shelter.isPetFriendly())
                        && "OPEN".equalsIgnoreCase(shelter.shelterStatus())
                        && availableSpaces >= targetRequest.getNumberOfPeople()) {
                    double dist = Gps.getDrivingDistance(targetRequest.getLocation(), shelter.getLocation());
                    if (dist >= 0 && dist < minDistance) {
                        minDistance = dist;
                        bestShelter = shelter;
                    }
                }
            }

            if (bestShelter != null) {
                System.out.println("Selected closest matching shelter: " + bestShelter.getLocation()
                    + " (Distance: " + String.format("%.1f", minDistance) + " miles)");
                targetRequest.assignVolunteer(volunteer.getId());
                if (!DataWriter.saveRequests(facade.getAllRequests())) {
                    System.out.println("Could not save the request assignment.");
                    facade.logout();
                    return;
                }
                System.out.println("Request status updated to: " + targetRequest.getStatus());
            } else {
                System.out.println("No matching shelter with a valid route was found.");
            }
        } else {
            System.out.println("No suitable open request found.");
        }
        facade.logout();
    }


    public static void testMap() {
        System.out.println("\n--- Test: map---");
        String from ="12 Church St, Charleston,SC";
        String to = "5001 Coliseum Dr, North Charleston, SC";
        double distance = Gps.getDrivingDistance(from, to);
        if (distance < 0) {
            System.out.println("Driving distance is unavailable.");
        } else {
            System.out.printf("Driving distance: %.1f miles%n", distance);
            for (String step: Gps.getDirections(from, to)) {
                System.out.println(" - " + step);
            }
        }
    }

}