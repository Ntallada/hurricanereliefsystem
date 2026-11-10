package com.model;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UserTest {

    private User user;
    private ByteArrayOutputStream output;
    private PrintStream originalOutput;

    /**
     * Creates a user before each test.
     */
    @Before
    public void setUp() {
        user = new User(
            "John",
            "Doe",
            30,
            "123-456-7890",
            "john.doe@example.com",
            "password123",
            null,
            "Columbia, SC"
        );

        originalOutput = System.out;
        output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
    }

    /**
     * Restores the original output after each test.
     */
    @After
    public void tearDown() {
        System.setOut(originalOutput);
    }

    /**
     * Tests that a new user receives an ID.
     */
    @Test
    public void newUserReceivesId() {
        assertNotNull(user.getId());
    }

    /**
     * Tests that an existing user keeps their ID.
     */
    @Test
    public void existingUserKeepsId() {
        UUID id = UUID.randomUUID();

        User existingUser = new User(
            id,
            "John",
            "Doe",
            30,
            "123-456-7890",
            "john.doe@example.com",
            "password123",
            null,
            "Columbia, SC",
            true
        );

        assertEquals(id, existingUser.getId());
    }

    /**
     * Tests authentication with a correct password.
     */
    @Test
    public void authenticateWithCorrectPassword() {
        assertTrue(user.authenticate("password123"));
    }

    /**
     * Tests authentication with an incorrect password.
     */
    @Test
    public void authenticateWithIncorrectPassword() {
        assertFalse(user.authenticate("WrongPassword"));
    }

    /**
     * Tests authentication with a null password.
     */
    @Test
    public void authenticateWithNullPassword() {
        assertFalse(user.authenticate(null));
    }

    /**
     * Tests login with a correct password.
     */
    @Test
    public void loginWithCorrectPassword() {
        assertTrue(user.login("password123"));
    }

    /**
     * Tests login with an incorrect password.
     */
    @Test
    public void loginWithIncorrectPassword() {
        assertFalse(user.login("WrongPassword"));
    }

    /**
     * Tests login with a null password.
     */
    @Test
    public void loginWithNullPassword() {
        assertFalse(user.login(null));
    }

    /**
     * Tests that resetting the password updates it.
     */
    @Test
    public void resetPasswordUpdatesPassword() {
        user.resetPassword("NewPassword123");

        assertTrue(user.authenticate("NewPassword123"));
    }

    /**
     * Tests that the old password no longer works.
     */
    @Test
    public void oldPasswordFailsAfterReset() {
        user.resetPassword("NewPassword123");

        assertFalse(user.authenticate("password123"));
    }

    /**
     * Tests that selecting a language updates it.
     */
    @Test
    public void selectLanguageUpdatesPreference() {
        Languages selectedLanguage = Languages.values()[0];

        user.selectLanguage(selectedLanguage);

        assertEquals(selectedLanguage, user.getLanguage());
    }

    /**
     * Tests the account creation message.
     */
    @Test
    public void createAccountDisplaysMessage() {
        user.createAccount();

        assertEquals(
            "Account created for user: John Doe",
            output.toString().trim()
        );
    }

    /**
     * Tests the accessibility settings message.
     */
    @Test
    public void setAccessibilityDisplaysMessage() {
        user.setAccessibility();

        assertEquals(
            "Accessibility settings have been configured for user: John Doe",
            output.toString().trim()
        );
    }

    /**
     * Tests the password reset confirmation message.
     */
    @Test
    public void resetPasswordDisplaysMessage() {
        user.resetPassword("NewPassword123");

        assertEquals(
            "Password has been reset for user: John Doe",
            output.toString().trim()
        );
    }

    /**
     * Tests the logout confirmation message.
     */
    @Test
    public void logoutDisplaysMessage() {
        user.logout();

        assertEquals(
            "User John Doe has logged out.",
            output.toString().trim()
        );
    }

    /**
     * Tests the account deletion message.
     */
    @Test
    public void deleteAccountDisplaysMessage() {
        user.deleteAccount();

        assertEquals(
            "Account deleted for user: John Doe",
            output.toString().trim()
        );
    }

    /**
     * Tests that phone verification returns true.
     */
    @Test
    public void verifyPhoneReturnsTrue() {
        assertTrue(user.verifyPhone());
    }

    /**
     * Tests the phone verification message.
     */
    @Test
    public void verifyPhoneDisplaysMessage() {
        user.verifyPhone();

        assertEquals(
            "Phone number verified for user: John Doe",
            output.toString().trim()
        );
    }
}