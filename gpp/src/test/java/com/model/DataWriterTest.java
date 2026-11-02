package com.model;
//Made by Nandasai Allada
//utilizes maven in order to test files
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.After;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class DataWriterTest {
    private Path usersFile;
    private Path sheltersFile;
    private Path requestsFile;
    private String usersBackup;
    private String sheltersBackup;
    private String requestsBackup;

    @BeforeClass
    public static void printTestCases() {
        TestCaseTable.print(new String[][] {
                {"save users", "user data is written as valid JSON"},
                {"save requests", "request data includes its linked user ID"},
                {"save shelters", "shelter data is written as valid JSON"}
        });
    }

    @Before
    public void setUp() throws Exception {
        usersFile = Path.of(DataConstants.USERS_FILE_NAME);
        sheltersFile = Path.of(DataConstants.SHELTERS_FILE_NAME);
        requestsFile = Path.of(DataConstants.REQUESTS_FILE_NAME);

        usersBackup = Files.readString(usersFile, StandardCharsets.UTF_8);
        sheltersBackup = Files.readString(sheltersFile, StandardCharsets.UTF_8);
        requestsBackup = Files.readString(requestsFile, StandardCharsets.UTF_8);
    }

    @After
    public void tearDown() throws Exception {
        Files.writeString(usersFile, usersBackup, StandardCharsets.UTF_8);
        Files.writeString(sheltersFile, sheltersBackup, StandardCharsets.UTF_8);
        Files.writeString(requestsFile, requestsBackup, StandardCharsets.UTF_8);
    }

    @Test
    public void saveUsers() throws Exception {
        ArrayList<User> usersToSave = new ArrayList<>();
        User ava = new Volunteer("Ava", "Miller", 26, "555-1000", "ava@example.com", "pw123", Languages.ENGLISH, "Charleston, SC");
        User noah = new Victim("Noah", "Brown", 33, "555-2000", "noah@example.com", "pw456", Languages.ENGLISH, "Charleston, SC");
        usersToSave.add(ava);
        usersToSave.add(noah);

        boolean wasSaved = DataWriter.saveUsers(usersToSave);

        assertTrue(wasSaved);

        JSONParser jsonParser = new JSONParser();
        JSONArray savedUsers = (JSONArray) jsonParser.parse(new FileReader(usersFile.toFile()));
        assertNotNull(savedUsers);
        assertTrue(savedUsers.size() >= 2);
    }

    @Test
    public void saveRequests() throws Exception {
        Victim ella = new Victim("Ella", "White", 40, "555-3000", "ella@example.com", "pw789", Languages.ENGLISH, "Charleston, SC");
        ReliefRequest foodRequest = ella.createReliefRequest(
                "Ella White",
                "10 Harbor Lane",
                "Food",
                "Meals and blankets",
                UrgencyLevel.MEDIUM,
                4,
                false,
                null,
                null);

        ArrayList<ReliefRequest> requestsToSave = new ArrayList<>();
        requestsToSave.add(foodRequest);

        boolean wasSaved = DataWriter.saveRequests(requestsToSave);

        assertTrue(wasSaved);

        JSONParser jsonParser = new JSONParser();
        JSONArray savedRequests = (JSONArray) jsonParser.parse(new FileReader(requestsFile.toFile()));
        assertNotNull(savedRequests);
        assertTrue(savedRequests.size() >= 1);

        JSONObject lastSavedRequest = (JSONObject) savedRequests.get(savedRequests.size() - 1);
        assertTrue(lastSavedRequest.containsKey("userId"));
        assertTrue(lastSavedRequest.get("userId") != null);
    }

    @Test
    public void saveShelter() throws Exception {
        ArrayList<Shelter> shelters = new ArrayList<>();
        shelters.add(new Shelter(
                "Test Shelter",
                "11 Market St",
                UUID.randomUUID(),
                25,
                5,
                "OPEN",
                true,
                true,
                new ArrayList<>(),
                new ArrayList<>()));

        boolean wasSaved = DataWriter.saveShelters(shelters);

        assertTrue(wasSaved);

        JSONParser jsonParser = new JSONParser();
        JSONArray savedShelters = (JSONArray) jsonParser.parse(new FileReader(sheltersFile.toFile()));
        assertNotNull(savedShelters);
        assertTrue(savedShelters.size() >= 1);
    }
}