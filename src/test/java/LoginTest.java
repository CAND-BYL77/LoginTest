
package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginTest {

    @Test
    void testValidUsername() {
        Login login = new Login("kyl_1", "Ch&&sec@ke99!", "Kyle", "Smith", "+27838968976");
        assertTrue(login.checkUserName());
    }

    @Test
    void testInvalidUsername() {
        Login login = new Login("kyle!!!!!!!", "Ch&&sec@ke99!", "Kyle", "Smith", "+27838968976");
        assertFalse(login.checkUserName());
    }

    @Test
    void testValidPassword() {
        Login login = new Login("kyl_1", "Ch&&sec@ke99!", "Kyle", "Smith", "+27838968976");
        assertTrue(login.checkPasswordComplexity());
    }

    @Test
    void testInvalidPassword() {
        Login login = new Login("kyl_1", "password", "Kyle", "Smith", "+27838968976");
        assertFalse(login.checkPasswordComplexity());
    }

    @Test
    void testValidCellPhone() {
        Login login = new Login("kyl_1", "Ch&&sec@ke99!", "Kyle", "Smith", "+27838968976");
        assertTrue(login.checkCellPhoneNumber());
    }

    @Test
    void testInvalidCellPhone() {
        Login login = new Login("kyl_1", "Ch&&sec@ke99!", "Kyle", "Smith", "08966553");
        assertFalse(login.checkCellPhoneNumber());
    }

    @Test
    void testRegistrationSuccess() {
        Login login = new Login("kyl_1", "Ch&&sec@ke99!", "Kyle", "Smith", "+27838968976");
        assertEquals("User registered successfully.", login.registerUser());
    }

    @Test
    void testLoginSuccess() {
        Login login = new Login("kyl_1", "Ch&&sec@ke99!", "Kyle", "Smith", "+27838968976");
        boolean success = login.loginUser("kyl_1", "Ch&&sec@ke99!");
        assertTrue(success);
        assertEquals("Welcome Kyle Smith it is great to see you again.", login.returnLoginStatus(success));
    }

    @Test
    void testLoginFail() {
        Login login = new Login("kyl_1", "Ch&&sec@ke99!", "Kyle", "Smith", "+27838968976");
        boolean success = login.loginUser("wrong", "wrong");
        assertFalse(success);
        assertEquals("Username or password incorrect, please try again.", login.returnLoginStatus(success));
    }
}
