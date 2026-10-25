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
        facade.login("john.doe@example.com", "password123");
        System.out.println("Logged in as: " + facade.getCurrentUser().getFirstName());
        facade.logout();
        System.out.println("Logging out: " + facade.getCurrentUser());

        // Testing Shelter List 
        System.out.println("\n Testing Shelter List ");
        ShelterList shelterList = ShelterList.getInstance();
        System.out.println("Loaded " + shelterList.getShelters().size() + " shelters from JSON.");

        for (Shelter shelter : shelterList.getShelters()) {
            System.out.println(shelter);
        }

        // Tests adding new shelter
        Shelter newShelter = new Shelter("Community Center", 150, 45, "OPEN", true);
        shelterList.addShelter(newShelter);
        System.out.println("Added new shelter. Total shelters now: " + shelterList.getShelters().size());
       
        // Execute core project scenarios
        testMap();
        scenarioCreateRequest();
        scenarioMatchAndAssignRequest();
        scenarioExportTripInfo();
        scenarioRolePermissions();
        scenarioShelterCheckInAndComplete();
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
        System.out.println("\n--- Scenario: Match Rosa with Request & Assign Shelter ---");
        HurricaneReliefFacade facade = HurricaneReliefFacade.getInstance();

        if (!facade.signUp("VOLUNTEER", "Rosa", "Delgado", 35, "843-555-9988",
                "rosa.delgado@example.com", "password123", Languages.ENGLISH,
                "5001 Coliseum Dr, North Charleston, SC")) {
            System.out.println("Rosa account exists, logging in.");
        }
        facade.login("rosa.delgado@example.com", "password123");
        System.out.println("Logged in as Volunteer: " + facade.getCurrentUser().getFirstName());
        
        System.out.println("Viewing open relief requests...");
        ReliefRequest targetRequest = null;
        for (ReliefRequest req : facade.getAllRequests()) {
            if (req.getPickupAddress().contains("Charleston") && req.getStatus().equals("Open")) {
                targetRequest = req;
                break;
            }
        }
        if (targetRequest != null) {
            System.out.println("Found recommended request ID: " + targetRequest.getRequestId() 
                + " for " + targetRequest.getAidType());
            
            Shelter bestShelter = null;
            double minDistance = Double.MAX_VALUE;

            for (Shelter shelter : ShelterList.getInstance().getShelters()) {
                if (shelter.isAcceptsPets() && shelter.isHasMedicalStaff() && shelter.getAvailableSpaces() >= 4) {
                    double dist = Gps.getDrivingDistance(targetRequest.getPickupAddress(), shelter.getAddress());
                    if (dist < minDistance) {
                        minDistance = dist;
                        bestShelter = shelter;
                    }
                }
            }

            if (bestShelter != null) {
                System.out.println("Selected closest matching shelter: " + bestShelter.getName() 
                    + " (Distance: " + String.format("%.1f", minDistance) + " miles)");
                
                facade.assignRequest(targetRequest, (Volunteer) facade.getCurrentUser());
                System.out.println("Request status updated to: " + targetRequest.getStatus());
            } else {
                System.out.println("No matching shelter found meeting all criteria.");
            }
        } else {
            System.out.println("No suitable open request found.");
        }
        facade.logout();
    }

    public static void scenarioExportTripInfo() {
        System.out.println("\n--- Scenario: Export Trip Information ---");
        HurricaneReliefFacade facade = HurricaneReliefFacade.getInstance();
        facade.login("rosa.delgado@example.com", "password123");

        ReliefRequest assignedReq = null;
        for (ReliefRequest req : facade.getAllRequests()) {
            if (req.getAssignedVolunteer() != null && 
                req.getAssignedVolunteer().getEmail().equals("rosa.delgado@example.com")) {
                assignedReq = req;
                break;
            }
        }

        if (assignedReq != null) {
            String exportFileName = "trip_info_" + assignedReq.getRequestId() + ".txt";
            boolean exported = facade.exportTripInformation(assignedReq, exportFileName);
            if (exported) {
                System.out.println("Trip information successfully exported to: " + exportFileName);
            } else {
                System.out.println("Failed to export trip information.");
            }
        } else {
            System.out.println("No assigned request found for export.");
        }
        facade.logout();
    }

    public static void scenarioRolePermissions() {
        System.out.println("\n--- Scenario: Demonstrate Role Permissions (Volunteer Restriction) ---");
        HurricaneReliefFacade facade = HurricaneReliefFacade.getInstance();
        facade.login("rosa.delgado@example.com", "password123");

        ReliefRequest assignedReq = null;
        for (ReliefRequest req : facade.getAllRequests()) {
            if (req.getAssignedVolunteer() != null && 
                req.getAssignedVolunteer().getEmail().equals("rosa.delgado@example.com")) {
                assignedReq = req;
                break;
            }
        }

        if (assignedReq != null) {
            System.out.println("Volunteer attempting to mark request as complete...");
            boolean success = facade.completeRequest(assignedReq);
            if (!success) {
                System.out.println("SUCCESS: System correctly prevented volunteer from completing the request. Role permissions enforced.");
            } else {
                System.out.println("ERROR: Volunteer should not have permission to complete requests.");
            }
        }
        facade.logout();
    }

    public static void scenarioShelterCheckInAndComplete() {
        System.out.println("\n--- Scenario: Check Family into Shelter & Complete Request ---");
        HurricaneReliefFacade facade = HurricaneReliefFacade.getInstance();

        if (!facade.signUp("MANAGER", "Felix", "Shelton", 45, "843-555-7733",
                "felix.manager@example.com", "password123", Languages.ENGLISH,
                "Shelter Location")) {
            System.out.println("Felix account exists, logging in.");
        }
        facade.login("felix.manager@example.com", "password123");
        System.out.println("Logged in as Shelter Manager: " + facade.getCurrentUser().getFirstName());

        ReliefRequest targetReq = null;
        for (ReliefRequest req : facade.getAllRequests()) {
            if (req.getPetName() != null && req.getPetName().equals("Buddy")) {
                targetReq = req;
                break;
            }
        }

        if (targetReq != null) {
            Shelter shelter = ShelterList.getInstance().getShelters().get(0);
            boolean checkedIn = facade.checkInFamilyToShelter(shelter, targetReq);
            
            if (checkedIn) {
                System.out.println("Family (3 members) and pet Buddy successfully checked into " + shelter.getName());
                System.out.println("New Shelter Occupancy: " + shelter.getCurrentOccupancy());
            }

            boolean completed = facade.completeRequest(targetReq);
            if (completed) {
                System.out.println("Shelter Manager successfully marked request " + targetReq.getRequestId() + " as 'Complete'.");
            }
        } else {
            System.out.println("Target request not found for check-in.");
        }
        facade.logout();
    }

    public static void testMap() {
        System.out.println("\n--- Test: map---");
        String from = "12 Church St, Charleston, SC";
        String to = "5001 Coliseum Dr, North Charleston, SC";
        System.out.printf("Driving distance: %.1f miles%n", Gps.getDrivingDistance(from, to));
        for (String step : Gps.getDirections(from, to)) {
            System.out.println(" - " + step);
        }
    }
}