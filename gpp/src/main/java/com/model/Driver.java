package com.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Driver {
    private static final String FATHER_EMAIL = "mark.carter@example.com";
    private static final String VOLUNTEER_EMAIL = "rosa.delgado@example.com";
    private static final String MANAGER_EMAIL = "felix.manager@example.com";
    private static final String SCENARIO_PASSWORD = "password123";
    private static final String HOME_ADDRESS = "12 Church St, Charleston, SC";
    private static final List<String> FAMILY = List.of(
            "Mark Carter (41)", "Emily Carter (39)", "Sophie Carter (9)");
    private static final String PET_NAME = "Buddy";
    private static final String PET_TYPE = "Dog";

    private static ReliefRequest carterRequest;
    private static Shelter selectedShelter;

    private Driver() {
    }

    private static ReliefRequest findPendingCarterRequest(List<ReliefRequest> requests) {
        return requests.stream()
                .filter(Driver::isCarterRequest)
                .filter(request -> "PENDING".equalsIgnoreCase(request.getStatus()))
                .findFirst()
                .orElse(null);
    }

    public static void main(String[] args) {
        printScenarioSummary("before");

        scenarioCreateRequest();
        scenarioMatchAndAssignRequest();
        scenarioFindShelterAndAssignRequest();
        scenarioExportTripInformation();
        scenarioVolunteerRolePermissions();
        scenarioShelterCheckInAndComplete();
        scenarioVerifySavedAfterReload();

        printScenarioSummary("after");
    }

    public static void scenarioCreateRequest() {
        System.out.println("\n--- Scenario: Create Carter account and relief request ---");
        HurricaneReliefFacade facade = HurricaneReliefFacade.getInstance();
        ensureAccount("VICTIM", "Mark", "Carter", 41, "843-555-0142",
                FATHER_EMAIL, HOME_ADDRESS);

        if (!facade.login(FATHER_EMAIL, SCENARIO_PASSWORD)) {
            throw new IllegalStateException("Could not log in as Mark Carter.");
        }

        User father = facade.getCurrentUser();
        carterRequest = findPendingCarterRequest(facade.getAllRequests());
        if (carterRequest == null) {
            carterRequest = facade.submitRequest(
                    HOME_ADDRESS,
                    "Transportation",
                    "Family: Mark Carter (41), Emily Carter (39), Sophie Carter (9). "
                            + "Sophie needs medical assistance for asthma. "
                            + "They need transportation for three people and their dog, Buddy.",
                    UrgencyLevel.HIGH, FAMILY.size(), true, PET_NAME, PET_TYPE);
        }

        if (carterRequest == null) {
            facade.logout();
            throw new IllegalStateException("Carter relief request was not created.");
        }
        System.out.println("Account saved for " + father.getFirstName() + " " + father.getLastName()
                + "; contact: " + father.getPhoneNumber() + ", " + father.getEmail());
        System.out.println("Request " + carterRequest.getRequestId() + " at " + carterRequest.getLocation()
                + " for " + carterRequest.getNumberOfPeople() + " people; medical assistance: "
                + carterRequest.isMedicalNeeded() + "; pet: " + carterRequest.getPetName()
                + " (" + carterRequest.getPetType() + "); status: " + carterRequest.getStatus());
        facade.logout();
    }

    public static void scenarioMatchAndAssignRequest() {
        System.out.println("\n--- Scenario: Rosa sees recommended requests ---");
        HurricaneReliefFacade facade = HurricaneReliefFacade.getInstance();
        ensureAccount("VOLUNTEER", "Rosa", "Delgado", 35, "843-555-9988",
                VOLUNTEER_EMAIL, "5001 Coliseum Dr, North Charleston, SC");

        User rosaUser = UserList.getInstance().getUserByEmail(VOLUNTEER_EMAIL);
        if (!(rosaUser instanceof Volunteer rosa)) {
            throw new IllegalStateException("Rosa's account is not a volunteer profile.");
        }
        rosa.addSkill(Skill.TRANSPORTATION);
        rosa.setVehicle("Ford F-350");
        requireSaved(DataWriter.saveUsers(UserList.getInstance().getUsers()), "volunteer profile");

        if (!facade.login(VOLUNTEER_EMAIL, SCENARIO_PASSWORD)) {
            throw new IllegalStateException("Could not log in as Rosa Delgado.");
        }
        carterRequest = requireCarterRequest(facade.getAllRequests());
        ensureSevenOpenRequests(facade);

        List<ReliefRequest> openRequests = facade.getAllRequests().stream()
                .filter(request -> "PENDING".equalsIgnoreCase(request.getStatus()))
                .filter(request -> request == carterRequest || !isCarterRequest(request))
                .sorted(Comparator.comparing((ReliefRequest request) -> request != carterRequest))
                .limit(7)
                .toList();
        System.out.println(rosa.getFirstName() + " " + rosa.getLastName() + " | vehicle: "
                + rosa.getVehicle() + " | specialty: " + rosa.getAssignedSkills());
        System.out.println("Open requests: " + openRequests.size());
        for (ReliefRequest request : openRequests) {
            if (request == carterRequest) {
                System.out.println("RECOMMENDED: " + request.getVictimName() + " | "
                        + request.getAidType() + " | " + request.getLocation());
                System.out.println("Why: nearby Charleston pickup and transportation matches "
                        + "Rosa's transportation specialty.");
            } else {
                System.out.println("OPEN: " + request.getVictimName() + " | "
                        + request.getAidType() + " | " + request.getLocation());
            }
        }
        System.out.println("Request details: 2 adults, 1 child, 1 dog; medical assistance needed: "
                + carterRequest.isMedicalNeeded() + ".");
        facade.logout();
    }

    public static void scenarioFindShelterAndAssignRequest() {
        System.out.println("\n--- Scenario: Find a suitable shelter and assign the request ---");
        HurricaneReliefFacade facade = HurricaneReliefFacade.getInstance();
        if (!facade.login(VOLUNTEER_EMAIL, SCENARIO_PASSWORD)) {
            throw new IllegalStateException("Could not log in as Rosa to assign the request.");
        }
        carterRequest = requireCarterRequest(facade.getAllRequests());
        ensureTenShelters();

        List<Shelter> matchingShelters = ShelterList.getInstance().getShelters().stream()
                .filter(shelter -> "OPEN".equalsIgnoreCase(shelter.shelterStatus()))
                .filter(Shelter::isPetFriendly)
                .filter(Shelter::hasMedicalStaff)
                .filter(shelter -> shelter.getAvailableSpaces() >= carterRequest.getNumberOfPeople())
                .toList();
        if (matchingShelters.isEmpty()) {
            facade.logout();
            throw new IllegalStateException("No open shelter meets the medical, pet, and capacity requirements.");
        }

        selectedShelter = null;
        double shortestDistance = Double.MAX_VALUE;
        for (Shelter shelter : matchingShelters) {
            double distance = Gps.getDrivingDistance(carterRequest.getLocation(), shelter.getLocation());
            if (distance < 0) {
                facade.logout();
                throw new IllegalStateException("Could not calculate the driving distance to "
                        + shelter.getName() + ". Closest-shelter selection cannot be confirmed.");
            }
            if (distance < shortestDistance) {
                selectedShelter = shelter;
                shortestDistance = distance;
            }
        }
        if (selectedShelter == null) {
            facade.logout();
            throw new IllegalStateException("Could not calculate driving distances for eligible shelters. "
                    + "Set ORS_API_KEY or configure gpp/ors_key.txt, then try again.");
        }

        Volunteer rosa = (Volunteer) facade.getCurrentUser();
        carterRequest.assignVolunteer(rosa.getId());
        carterRequest.assignShelter(selectedShelter.getShelterId());
        requireSaved(DataWriter.saveRequests(facade.getAllRequests()), "request assignment");
        System.out.println("Shelters searched: " + ShelterList.getInstance().getShelters().size());
        System.out.println("Selected: " + selectedShelter.getName() + " | " + selectedShelter.getLocation()
                + " | accepts dogs: " + selectedShelter.isPetFriendly() + " | medical staff: "
                + selectedShelter.hasMedicalStaff() + " | spaces available: "
                + selectedShelter.getAvailableSpaces());
        System.out.printf("Driving distance: %.1f miles%n", shortestDistance);
        System.out.println("Assigned to Rosa; status: " + carterRequest.getStatus()
                + "; other volunteers can see the assignment.");
        facade.logout();
    }

    public static void scenarioExportTripInformation() {
        System.out.println("\n--- Scenario: Export printable trip information ---");
        HurricaneReliefFacade facade = HurricaneReliefFacade.getInstance();
        carterRequest = requireCarterRequest(facade.getAllRequests());
        selectedShelter = findAssignedShelter(carterRequest);
        if (selectedShelter == null) {
            throw new IllegalStateException("The request does not have a persisted shelter assignment.");
        }
        User rosa = UserList.getInstance().getUserByEmail(VOLUNTEER_EMAIL);
        User father = carterRequest.getUser();
        List<String> volunteerToHome = directionsOrScenarioRoute(
                rosa.getCurrentLocation(), carterRequest.getLocation());
        List<String> homeToShelter = directionsOrScenarioRoute(
                carterRequest.getLocation(), selectedShelter.getLocation());

        String trip = "HURRICANE SCARLETT - RELIEF TRIP\n"
                + "=================================\n"
                + "Family members:\n"
                + String.join("\n", FAMILY.stream().map(name -> "  - " + name).toList()) + "\n"
                + "Accompanying pet: " + PET_NAME + " (Dog)\n\n"
                + "Contact: " + father.getFirstName() + " " + father.getLastName()
                + " | " + father.getPhoneNumber() + " | " + father.getEmail() + "\n"
                + "Pickup address: " + carterRequest.getLocation() + "\n"
                + "Medical note: Sophie needs medical assistance for asthma.\n\n"
                + "Rosa's location to pickup:\n" + formatDirections(volunteerToHome) + "\n"
                + "Pickup to shelter:\n" + formatDirections(homeToShelter) + "\n"
                + "Shelter: " + selectedShelter.getName() + "\n"
                + "Shelter address: " + selectedShelter.getLocation() + "\n";

        Path exportPath = Path.of("gpp", "exports",
                "trip_info_" + carterRequest.getRequestId() + ".txt");
        try {
            Files.createDirectories(exportPath.getParent());
            Files.writeString(exportPath, trip);
        } catch (IOException e) {
            throw new IllegalStateException("Could not write trip information to " + exportPath, e);
        }
        System.out.println("Trip information exported to " + exportPath.toAbsolutePath());
        System.out.println(trip);
    }

    public static void scenarioVolunteerRolePermissions() {
        System.out.println("\n--- Scenario: Volunteer cannot complete a relief request ---");
        HurricaneReliefFacade facade = HurricaneReliefFacade.getInstance();
        if (!facade.login(VOLUNTEER_EMAIL, SCENARIO_PASSWORD)) {
            throw new IllegalStateException("Could not log in as Rosa for the permission scenario.");
        }
        carterRequest = requireCarterRequest(facade.getAllRequests());
        String statusBefore = carterRequest.getStatus();
        if (!facade.completeRequest(carterRequest)) {
            System.out.println("Denied: volunteer role does not have permission to complete relief requests.");
        }
        System.out.println("Request remains " + carterRequest.getStatus() + " and assigned to Rosa.");
        if (!statusBefore.equals(carterRequest.getStatus())) {
            facade.logout();
            throw new IllegalStateException("Volunteer completion changed the request status.");
        }
        facade.logout();
    }

    public static void scenarioShelterCheckInAndComplete() {
        System.out.println("\n--- Scenario: Felix checks in the family and completes the request ---");
        HurricaneReliefFacade facade = HurricaneReliefFacade.getInstance();
        ensureAccount("MANAGER", "Felix", "Shelton", 45, "843-555-7733",
                MANAGER_EMAIL, "Shelter Manager");
        if (!facade.login(MANAGER_EMAIL, SCENARIO_PASSWORD)
                || !(facade.getCurrentUser() instanceof ShelterManager)) {
            throw new IllegalStateException("Could not log in as Felix with the shelter-manager role.");
        }

        carterRequest = requireCarterRequest(facade.getAllRequests());
        selectedShelter = findAssignedShelter(carterRequest);
        if (selectedShelter == null) {
            facade.logout();
            throw new IllegalStateException("Could not find the shelter assigned to the relief request.");
        }
        System.out.println("Shelter: " + selectedShelter.getName() + " | "
                + selectedShelter.getLocation() + " | capacity: " + selectedShelter.getCapacity()
                + " | occupancy: " + selectedShelter.getOccupancy() + " | available: "
                + selectedShelter.getAvailableSpaces() + " | occupants: "
                + selectedShelter.getOccupants());

        if (!selectedShelter.getOccupants().contains(FAMILY.get(0))) {
            if (!selectedShelter.checkIn(FAMILY, PET_NAME, PET_TYPE)) {
                facade.logout();
                throw new IllegalStateException("The family and pet could not be checked into the shelter.");
            }
            requireSaved(DataWriter.saveShelters(ShelterList.getInstance().getShelters()),
                    "shelter check-in");
        }
        System.out.println("Checked in " + FAMILY.size() + " family members and "
                + PET_NAME + " (" + PET_TYPE + "). Occupancy: "
                + selectedShelter.getOccupancy() + "/" + selectedShelter.getCapacity()
                + "; accompanying pets: " + selectedShelter.getPets());

        if (facade.completeRequest(carterRequest)) {
            System.out.println("Shelter manager permission accepted; request status: "
                    + carterRequest.getStatus());
        } else {
            facade.logout();
            throw new IllegalStateException("Only a shelter manager can complete this request.");
        }
        facade.logout();
    }

    public static void scenarioVerifySavedAfterReload() {
        System.out.println("\n--- Scenario: Verify saved data after reloading JSON ---");
        ArrayList<User> reloadedUsers = DataLoader.loadUsers();
        ArrayList<ReliefRequest> reloadedRequests = DataLoader.getRequests();
        ArrayList<Shelter> reloadedShelters = DataLoader.getShelters();
        ReliefRequest reloadedRequest = reloadedRequests.stream()
                .filter(request -> carterRequest.getRequestId().equals(request.getRequestId()))
                .findFirst()
                .orElse(null);
        if (reloadedRequest == null || !"COMPLETED".equals(reloadedRequest.getStatus())) {
            throw new IllegalStateException("Completed Carter request was not restored from JSON.");
        }
        Shelter reloadedShelter = reloadedShelters.stream()
                .filter(shelter -> selectedShelter != null
                        && selectedShelter.getShelterId().equals(shelter.getShelterId()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Selected shelter was not restored from JSON."));
        User reloadedManager = reloadedUsers.stream()
                .filter(user -> MANAGER_EMAIL.equalsIgnoreCase(user.getEmail()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Shelter manager account was not restored from JSON."));
        if (reloadedRequest.getVolunteerId() == null
                || reloadedRequest.getShelterId() == null
                || !reloadedShelter.getOccupants().contains(FAMILY.get(0))
                || !reloadedShelter.getPets().contains(PET_NAME + " (" + PET_TYPE + ")")) {
            throw new IllegalStateException("Assignment or shelter check-in data did not survive JSON reload.");
        }
        System.out.println("Reloaded " + reloadedManager.getFirstName() + "'s manager account, request "
                + reloadedRequest.getRequestId() + " (" + reloadedRequest.getStatus() + "), volunteer/shelter "
                + "assignment, " + reloadedShelter.getOccupancy() + " occupants, and pet "
                + reloadedShelter.getPets() + ".");
    }

    public static void testMap() {
        System.out.println("\n--- Test: Map directions ---");
        String from = "12 Church St, Charleston, SC";
        String to = "5001 Coliseum Dr, North Charleston, SC";
        System.out.printf("Driving distance: %.1f miles%n", Gps.getDrivingDistance(from, to));
        for (String step : Gps.getDirections(from, to)) {
            System.out.println(" - " + step);
        }
    }

    private static void ensureAccount(String type, String firstName, String lastName, int age,
            String phone, String email, String location) {
        HurricaneReliefFacade facade = HurricaneReliefFacade.getInstance();
        if (UserList.getInstance().getUserByEmail(email) == null
                && !facade.signUp(type, firstName, lastName, age, phone, email,
                        SCENARIO_PASSWORD, Languages.ENGLISH, location)) {
            throw new IllegalStateException("Could not create scenario account for " + email);
        }
    }

    private static ReliefRequest requireCarterRequest(List<ReliefRequest> requests) {
        if (carterRequest == null || !requests.contains(carterRequest)) {
            carterRequest = findActiveCarterRequest(requests);
        }
        if (carterRequest == null) {
            scenarioCreateRequest();
            carterRequest = findCarterRequest(HurricaneReliefFacade.getInstance().getAllRequests());
        }
        if (carterRequest == null) {
            throw new IllegalStateException("Carter relief request is unavailable.");
        }
        return carterRequest;
    }

    private static ReliefRequest findCarterRequest(List<ReliefRequest> requests) {
        return requests.stream()
                .filter(Driver::isCarterRequest)
                .findFirst()
                .orElse(null);
    }

    private static ReliefRequest findActiveCarterRequest(List<ReliefRequest> requests) {
        return requests.stream()
                .filter(Driver::isCarterRequest)
                .filter(request -> !"COMPLETED".equalsIgnoreCase(request.getStatus()))
                .findFirst()
                .orElse(null);
    }

    private static boolean isCarterRequest(ReliefRequest request) {
        return "Mark Carter".equals(request.getVictimName())
                && HOME_ADDRESS.equals(request.getLocation())
                && PET_NAME.equals(request.getPetName());
    }

    private static void ensureSevenOpenRequests(HurricaneReliefFacade facade) {
        for (int i = 1; i <= 6; i++) {
            String email = "scenario.request." + i + "@example.com";
            ensureAccount("VICTIM", "Scenario", "Neighbor" + i, 30 + i,
                    "843-555-01" + String.format("%02d", i), email,
                    "Charleston, SC");
            User user = UserList.getInstance().getUserByEmail(email);
            boolean alreadyOpen = facade.getAllRequests().stream()
                    .anyMatch(request -> request.getUser() != null
                            && request.getUser().getId().equals(user.getId())
                            && "PENDING".equalsIgnoreCase(request.getStatus()));
            if (alreadyOpen) {
                continue;
            }
            ReliefRequest request = new ReliefRequest(user,
                    user.getFirstName() + " " + user.getLastName(),
                    "Charleston, SC", i % 2 == 0 ? "Transportation" : "Supplies",
                    "Scenario queue request", UrgencyLevel.MEDIUM, 1, false, null, null);
            facade.getAllRequests().add(request);
            requireSaved(DataWriter.saveRequests(facade.getAllRequests()), "open request queue");
        }
    }

    private static void ensureTenShelters() {
        ShelterList shelterList = ShelterList.getInstance();
        addShelterIfMissing(shelterList, "Charleston Medical Pet Shelter",
                "100 King St, Charleston, SC", "00000000-0000-0000-0000-000000000101",
                80, 20, true, true);
        addShelterIfMissing(shelterList, "Charleston Community Shelter",
                "200 Meeting St, Charleston, SC", "00000000-0000-0000-0000-000000000102",
                100, 30, true, true);
        addShelterIfMissing(shelterList, "Charleston Medical Family Shelter",
                "300 Calhoun St, Charleston, SC", "00000000-0000-0000-0000-000000000103",
                90, 25, false, true);
        requireSaved(DataWriter.saveShelters(shelterList.getShelters()), "shelter directory");
    }

    private static void addShelterIfMissing(ShelterList shelters, String name, String address,
            String id, int capacity, int occupancy, boolean petFriendly, boolean medicalStaff) {
        Shelter existing = shelters.getShelters().stream()
                .filter(shelter -> name.equals(shelter.getName()))
                .findFirst()
                .orElse(null);
        if (existing == null) {
            shelters.getShelters().add(new Shelter(name, address, java.util.UUID.fromString(id),
                    capacity, occupancy, "OPEN", petFriendly, medicalStaff));
        } else {
            existing.setHasMedicalStaff(medicalStaff);
        }
    }

    private static Shelter findAssignedShelter(ReliefRequest request) {
        if (request.getShelterId() == null) {
            return null;
        }
        return ShelterList.getInstance().getShelterById(request.getShelterId());
    }

    private static List<String> directionsOrScenarioRoute(String from, String to) {
        List<String> directions = Gps.getDirections(from, to);
        if (directions.isEmpty()) {
            throw new IllegalStateException("Could not retrieve driving directions from " + from + " to "
                    + to + ". Set ORS_API_KEY or configure gpp/ors_key.txt, then try again.");
        }
        return directions;
    }

    private static String formatDirections(List<String> directions) {
        return String.join("\n", directions.stream().map(step -> "  - " + step).toList());
    }

    private static void printScenarioSummary(String phase) {
        Path requestsPath = Path.of("gpp", "json", "reliefRequest.json");
        Path sheltersPath = Path.of("gpp", "json", "shelters.json");
        try {
            int userCount = DataLoader.loadUsers().size();
            int requestCount = DataLoader.getRequests().size();
            int shelterCount = DataLoader.getShelters().size();
            System.out.println("--- Hurricane Scarlett scenario data " + phase + " ---");
            System.out.println("users.json: " + userCount + " accounts (credentials not printed)");
            System.out.println("reliefRequest.json: " + requestCount + " requests"
                    + (Files.exists(requestsPath) ? " [" + Files.size(requestsPath) + " bytes]" : ""));
            System.out.println("shelters.json: " + shelterCount + " shelters"
                    + (Files.exists(sheltersPath) ? " [" + Files.size(sheltersPath) + " bytes]" : ""));
        } catch (IOException e) {
            throw new IllegalStateException("Could not summarize scenario JSON data.", e);
        }
    }

    private static void requireSaved(boolean saved, String description) {
        if (!saved) {
            throw new IllegalStateException("Could not save " + description + " to JSON.");
        }
    }
}
