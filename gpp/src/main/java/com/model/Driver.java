package com.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

public class Driver {
    private static final String FATHER_EMAIL = "mark.carter@example.com";
    private static final String VOLUNTEER_EMAIL = "rosa.delgado@example.com";
    private static final String MANAGER_EMAIL = "felix.manager@example.com";
    private static final String PASSWORD = "password123";
    private static final String HOME_ADDRESS = "12 Church St, Charleston, SC";
    private static final String PET_NAME = "Buddy";
    private static final String PET_TYPE = "Dog";
    private static final List<String> FAMILY = List.of(
            "Mark Carter (41)", "Emily Carter (39)", "Sophie Carter (9)");

    private HurricaneReliefFacade facade;
    private ReliefRequest request;
    private Shelter shelter;

    public Driver() {
        facade = HurricaneReliefFacade.getInstance();
    }

    public static void main(String[] args) {
        Driver driver = new Driver();
        driver.run();
    }

    public void run() {
        printSummary("before");
        createRequest();
        recommendRequests();
        findShelterAndAssign();
        exportTripInfo();
        volunteerCannotComplete();
        checkInAndComplete();
        verifyReload();
        printSummary("after");
    }

    // Scenario 1: Mark creates his account and a relief request
    private void createRequest() {
        System.out.println("\n--- Scenario: Create Carter account and relief request ---");
        ensureAccount("VICTIM", "Mark", "Carter", 41, "843-555-0142", FATHER_EMAIL, HOME_ADDRESS);
        facade.login(FATHER_EMAIL, PASSWORD);

        request = findPendingCarterRequest();
        if (request == null) {
            request = facade.submitRequest(HOME_ADDRESS, "Transportation",
                    "Sophie needs medical assistance for asthma. "
                            + "They need transportation for three people and their dog, Buddy.",
                    UrgencyLevel.HIGH, FAMILY.size(), true, PET_NAME, PET_TYPE);
        }

        User mark = facade.getCurrentUser();
        System.out.println("Account saved for " + mark.getFirstName() + " " + mark.getLastName()
                + "; contact: " + mark.getPhoneNumber() + ", " + mark.getEmail());
        System.out.println("Request " + request.getRequestId() + " at " + request.getLocation()
                + " for " + request.getNumberOfPeople() + " people; status: " + request.getStatus());
        facade.logout();
    }

    // Scenario 2: Rosa sees the recommended request
    private void recommendRequests() {
        System.out.println("\n--- Scenario: Rosa sees recommended requests ---");
        ensureAccount("VOLUNTEER", "Rosa", "Delgado", 35, "843-555-9988",
                VOLUNTEER_EMAIL, "5001 Coliseum Dr, North Charleston, SC");

        Volunteer rosa = (Volunteer) UserList.getInstance().getUserByEmail(VOLUNTEER_EMAIL);
        rosa.addSkill(Skill.TRANSPORTATION);
        rosa.setVehicle("Ford F-350");
        DataWriter.saveUsers(UserList.getInstance().getUsers());

        facade.login(VOLUNTEER_EMAIL, PASSWORD);
        addNeighborRequests();

        System.out.println(rosa.getFirstName() + " " + rosa.getLastName()
                + " | vehicle: " + rosa.getVehicle() + " | specialty: " + rosa.getAssignedSkills());
        System.out.println("RECOMMENDED: " + request.getVictimName() + " | "
                + request.getAidType() + " | " + request.getLocation());

        int count = 1;
        for (ReliefRequest other : facade.getAllRequests()) {
            if (count < 7 && other != request && !isCarterRequest(other)
                    && other.getStatus().equalsIgnoreCase("PENDING")) {
                System.out.println("OPEN: " + other.getVictimName() + " | "
                        + other.getAidType() + " | " + other.getLocation());
                count++;
            }
        }
        facade.logout();
    }

    // Scenario 3: Rosa finds the closest suitable shelter and takes the request
    private void findShelterAndAssign() {
        System.out.println("\n--- Scenario: Find a suitable shelter and assign the request ---");
        facade.login(VOLUNTEER_EMAIL, PASSWORD);
        addShelters();

        double shortest = Double.MAX_VALUE;
        shelter = null;
        for (Shelter s : ShelterList.getInstance().getShelters()) {
            boolean suitable = s.shelterStatus().equalsIgnoreCase("OPEN")
                    && s.isPetFriendly()
                    && s.hasMedicalStaff()
                    && s.getAvailableSpaces() >= request.getNumberOfPeople();
            if (!suitable) {
                continue;
            }
            double distance = Gps.getDrivingDistance(request.getLocation(), s.getLocation());
            if (distance < 0) {
                throw new IllegalStateException("Could not get driving distance. "
                        + "Check gpp/ors_key.txt.");
            }
            if (distance < shortest) {
                shortest = distance;
                shelter = s;
            }
        }
        if (shelter == null) {
            throw new IllegalStateException("No shelter meets the requirements.");
        }

        Volunteer rosa = (Volunteer) facade.getCurrentUser();
        request.assignVolunteer(rosa.getId());
        request.assignShelter(shelter.getShelterId());
        DataWriter.saveRequests(facade.getAllRequests());

        System.out.println("Selected: " + shelter.getName() + " | " + shelter.getLocation()
                + " | spaces available: " + shelter.getAvailableSpaces());
        System.out.printf("Driving distance: %.1f miles%n", shortest);
        System.out.println("Assigned to Rosa; status: " + request.getStatus());
        facade.logout();
    }

    // Scenario 4: Write the printable trip file
    private void exportTripInfo() {
        System.out.println("\n--- Scenario: Export printable trip information ---");
        User rosa = UserList.getInstance().getUserByEmail(VOLUNTEER_EMAIL);
        User father = request.getUser();

        String trip = "HURRICANE SCARLETT - RELIEF TRIP\n"
                + "Family members: " + FAMILY + "\n"
                + "Pet: " + PET_NAME + " (" + PET_TYPE + ")\n"
                + "Contact: " + father.getFirstName() + " " + father.getLastName()
                + " | " + father.getPhoneNumber() + " | " + father.getEmail() + "\n"
                + "Pickup address: " + request.getLocation() + "\n"
                + "Medical note: Sophie needs medical assistance for asthma.\n\n"
                + "Rosa's location to pickup:\n"
                + getDirections(rosa.getCurrentLocation(), request.getLocation())
                + "Pickup to shelter:\n"
                + getDirections(request.getLocation(), shelter.getLocation())
                + "Shelter: " + shelter.getName() + "\n"
                + "Shelter address: " + shelter.getLocation() + "\n";

        Path path = Path.of("gpp", "exports", "trip_info_" + request.getRequestId() + ".txt");
        try {
            Files.createDirectories(path.getParent());
            Files.writeString(path, trip);
            System.out.println("Trip information exported to " + path.toAbsolutePath());
        } catch (IOException e) {
            System.out.println("Could not write trip file: " + e.getMessage());
        }
        System.out.println(trip);
    }

    // Scenario 5: A volunteer is not allowed to complete a request
    private void volunteerCannotComplete() {
        System.out.println("\n--- Scenario: Volunteer cannot complete a relief request ---");
        facade.login(VOLUNTEER_EMAIL, PASSWORD);
        if (!facade.completeRequest(request)) {
            System.out.println("Denied: volunteer role does not have permission to complete requests.");
        }
        System.out.println("Request remains " + request.getStatus());
        facade.logout();
    }

    // Scenario 6: Felix checks the family in and completes the request
    private void checkInAndComplete() {
        System.out.println("\n--- Scenario: Felix checks in the family and completes the request ---");
        ensureAccount("MANAGER", "Felix", "Shelton", 45, "843-555-7733",
                MANAGER_EMAIL, "Shelter Manager");
        facade.login(MANAGER_EMAIL, PASSWORD);

        if (!shelter.getOccupants().contains(FAMILY.get(0))) {
            shelter.checkIn(FAMILY, PET_NAME, PET_TYPE);
            DataWriter.saveShelters(ShelterList.getInstance().getShelters());
        }
        System.out.println("Checked in " + FAMILY.size() + " family members and "
                + PET_NAME + ". Occupancy: " + shelter.getOccupancy() + "/" + shelter.getCapacity());

        if (facade.completeRequest(request)) {
            System.out.println("Shelter manager permission accepted; status: " + request.getStatus());
        }
        facade.logout();
    }

    // Scenario 7: Reload the JSON files and check the data was saved
    private void verifyReload() {
        System.out.println("\n--- Scenario: Verify saved data after reloading JSON ---");
        for (ReliefRequest r : DataLoader.getRequests()) {
            if (r.getRequestId().equals(request.getRequestId())) {
                System.out.println("Reloaded request " + r.getRequestId() + " (" + r.getStatus() + ")");
            }
        }
        for (Shelter s : DataLoader.getShelters()) {
            if (s.getShelterId().equals(shelter.getShelterId())) {
                System.out.println("Reloaded shelter " + s.getName() + " | occupants: "
                        + s.getOccupants() + " | pets: " + s.getPets());
            }
        }
    }

    // ---------- helpers ----------

    private void ensureAccount(String type, String first, String last, int age,
            String phone, String email, String location) {
        if (UserList.getInstance().getUserByEmail(email) == null) {
            facade.signUp(type, first, last, age, phone, email, PASSWORD, Languages.ENGLISH, location);
        }
    }

    private boolean isCarterRequest(ReliefRequest r) {
        return r.getVictimName().equals("Mark Carter") && r.getLocation().equals(HOME_ADDRESS);
    }

    private ReliefRequest findPendingCarterRequest() {
        for (ReliefRequest r : facade.getAllRequests()) {
            if (isCarterRequest(r) && r.getStatus().equalsIgnoreCase("PENDING")) {
                return r;
            }
        }
        return null;
    }

    // Makes sure there are neighbor requests so Rosa sees a full list
    private void addNeighborRequests() {
        for (int i = 1; i <= 6; i++) {
            String email = "scenario.request." + i + "@example.com";
            ensureAccount("VICTIM", "Scenario", "Neighbor" + i, 30 + i,
                    "843-555-010" + i, email, "Charleston, SC");
            User user = UserList.getInstance().getUserByEmail(email);

            boolean hasOpenRequest = false;
            for (ReliefRequest r : facade.getAllRequests()) {
                if (r.getUser() != null && r.getUser().getId().equals(user.getId())
                        && r.getStatus().equalsIgnoreCase("PENDING")) {
                    hasOpenRequest = true;
                }
            }
            if (!hasOpenRequest) {
                String aid = (i % 2 == 0) ? "Transportation" : "Supplies";
                facade.getAllRequests().add(new ReliefRequest(user,
                        user.getFirstName() + " " + user.getLastName(), "Charleston, SC", aid,
                        "Scenario queue request", UrgencyLevel.MEDIUM, 1, false, null, null));
            }
        }
        DataWriter.saveRequests(facade.getAllRequests());
    }

    // Makes sure the three Charleston shelters exist
    private void addShelters() {
        addShelter("Charleston Medical Pet Shelter", "100 King St, Charleston, SC",
                "00000000-0000-0000-0000-000000000101", 80, 20, true, true);
        addShelter("Charleston Community Shelter", "200 Meeting St, Charleston, SC",
                "00000000-0000-0000-0000-000000000102", 100, 30, true, false);
        addShelter("Charleston Medical Family Shelter", "300 Calhoun St, Charleston, SC",
                "00000000-0000-0000-0000-000000000103", 90, 25, false, true);
        DataWriter.saveShelters(ShelterList.getInstance().getShelters());
    }

    private void addShelter(String name, String address, String id, int capacity,
            int occupancy, boolean petFriendly, boolean medicalStaff) {
        for (Shelter s : ShelterList.getInstance().getShelters()) {
            if (s.getName().equals(name)) {
                s.setHasMedicalStaff(medicalStaff);
                return;
            }
        }
        ShelterList.getInstance().getShelters().add(new Shelter(name, address,
                UUID.fromString(id), capacity, occupancy, "OPEN", petFriendly, medicalStaff));
    }

    private String getDirections(String from, String to) {
        List<String> steps = Gps.getDirections(from, to);
        if (steps.isEmpty()) {
            throw new IllegalStateException("No directions found. Check gpp/ors_key.txt.");
        }
        String result = "";
        for (String step : steps) {
            result += "  - " + step + "\n";
        }
        return result;
    }

    private void printSummary(String phase) {
        System.out.println("--- Hurricane Scarlett scenario data " + phase + " ---");
        System.out.println("users: " + DataLoader.loadUsers().size());
        System.out.println("requests: " + DataLoader.getRequests().size());
        System.out.println("shelters: " + DataLoader.getShelters().size());
    }
}