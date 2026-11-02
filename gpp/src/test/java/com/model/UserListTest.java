package com.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class UserListTest {
    private Path usersFile;
    private String usersBackup;

    @BeforeClass
    public static void printTestCases() {
        TestCaseTable.print(new String[][] {
                {"empty email", "an email address is required"},
                {"duplicate email", "each email address has to be unique"},
                {"no \"@\"", "emails need to include an at sign"},
                {"no domain", "emails need a domain name"}
        });
    }

    @Before
    public void setUp() throws Exception {
        usersFile = Path.of(DataConstants.USERS_FILE_NAME);
        usersBackup = Files.readString(usersFile, StandardCharsets.UTF_8);
        resetUserList();
        UserList.getInstance().getUsers().clear();
    }

    @After
    public void tearDown() throws Exception {
        Files.writeString(usersFile, usersBackup, StandardCharsets.UTF_8);
        resetUserList();
    }

    @Test
    public void signUp_shouldRejectAnEmptyEmail() {
        assertFalse(addUser(""));
        assertFalse(addUser("   "));
    }

    @Test
    public void signUp_shouldRejectDuplicateEmails() {
        assertTrue(addUser("relief@example.org"));

        assertFalse(addUser("RELIEF@example.org"));
    }

    @Test
    public void signUp_shouldRejectAnEmailWithoutAtSign() {
        assertFalse(addUser("relief.example.org"));
    }

    @Test
    public void signUp_shouldRejectAnEmailWithoutDomain() {
        assertFalse(addUser("relief@"));
        assertFalse(addUser("relief@localhost"));
    }

    private boolean addUser(String email) {
        return UserList.getInstance().addUser(
                "VICTIM",
                "Test",
                "User",
                30,
                "555-0100",
                email,
                "password",
                Languages.ENGLISH,
                "Charleston, SC");
    }

    private void resetUserList() throws Exception {
        Field field = UserList.class.getDeclaredField("userList");
        field.setAccessible(true);
        field.set(null, null);
    }
}
