package com.model;

// made by Nandasai
//uses maven to run the const file and then reports bugs

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;

public class DataConstantsTest {
    //prints a summary of the test cases
    @BeforeClass
    public static void printTestCases() {
        TestCaseTable.print(new String[][] {
                {"users file path", "resolves to the existing users JSON file"},
                {"shelters file path", "resolves to the existing shelters JSON file"},
                {"requests file path", "resolves to the existing requests JSON file"},
                {"JSON property keys", "match the keys used by the data files"},
                {"JUnit detection", "identifies calls made from a JUnit test"}
        });
    }

    //marks methods that should be tested
    //test methods mark methods JUnit runs
    @Test
    public void filePaths_shouldResolveToExistingJsonFiles() {
        assertTrue(Files.isRegularFile(Path.of(DataConstants.USERS_FILE_NAME)));
        assertTrue(Files.isRegularFile(Path.of(DataConstants.SHELTERS_FILE_NAME)));
        assertTrue(Files.isRegularFile(Path.of(DataConstants.REQUESTS_FILE_NAME)));
    }

    @Test
    public void jsonKeys_shouldMatchTheDataFileSchema() {
        assertEquals("userId", DataConstants.USER_ID);
        assertEquals("email", DataConstants.USER_EMAIL);
        assertEquals("id", DataConstants.SHELTER_ID);
        assertEquals("requestId", DataConstants.REQUEST_ID);
        assertEquals("userId", DataConstants.REQUEST_USER_ID);
    }

    @Test
    public void isJunitTest_shouldDetectTheJUnitTestRunner() {
        assertTrue(DataConstants.isJunitTest());
    }
}
