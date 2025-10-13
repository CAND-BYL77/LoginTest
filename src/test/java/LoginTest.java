import org.example.Login;
import org.junit.Test;
import static org.junit.Assert.*;

public class LoginTest {

    @Test
    public void testCheckUserName_CorrectlyFormatted() {
        Login login = new Login();
        assertTrue("Username 'kyl_1' should be correctly formatted",
                login.checkUserName("kyl_1"));
    }

    @Test
    public void testCheckUserName_IncorrectlyFormatted_NoUnderscore() {
        Login login = new Login();
        assertFalse("Username 'kyle1' should be incorrectly formatted (no underscore)",
                login.checkUserName("kyle1"));
    }

    @Test
    public void testCheckUserName_IncorrectlyFormatted_TooLong() {
        Login login = new Login();
        assertFalse("Username 'kyle_123' should be incorrectly formatted (too long)",
                login.checkUserName("kyle_123"));
    }

    @Test
    public void testCheckPasswordComplexity_MeetsRequirements() {
        Login login = new Login();
        assertTrue("Password 'Ch&&sec@ke99!' should meet complexity requirements",
                login.checkPasswordComplexity("Ch&&sec@ke99!"));
    }

    @Test
    public void testCheckPasswordComplexity_DoesNotMeetRequirements_TooShort() {
        Login login = new Login();
        assertFalse("Password 'Ab1!' should not meet complexity requirements (too short)",
                login.checkPasswordComplexity("Ab1!"));
    }

    @Test
    public void testCheckPasswordComplexity_DoesNotMeetRequirements_NoCapital() {
        Login login = new Login();
        assertFalse("Password 'abc123!@#' should not meet complexity requirements (no capital)",
                login.checkPasswordComplexity("abc123!@#"));
    }

    @Test
    public void testCheckPasswordComplexity_DoesNotMeetRequirements_NoNumber() {
        Login login = new Login();
        assertFalse("Password 'Abcdefg!' should not meet complexity requirements (no number)",
                login.checkPasswordComplexity("Abcdefg!"));
    }

    @Test
    public void testCheckPasswordComplexity_DoesNotMeetRequirements_NoSpecial() {
        Login login = new Login();
        assertFalse("Password 'Abcdefg1' should not meet complexity requirements (no special char)",
                login.checkPasswordComplexity("Abcdefg1"));
    }

    @Test
    public void testCheckCellPhoneNumber_CorrectlyFormatted() {
        Login login = new Login();
        assertTrue("Cellphone '+27838968976' should be correctly formatted",
                login.checkCellPhoneNumber("+27838968976"));
    }

    @Test
    public void testCheckCellPhoneNumber_IncorrectlyFormatted_NoPlus() {
        Login login = new Login();
        assertFalse("Cellphone '27838968976' should be incorrectly formatted (no +)",
                login.checkCellPhoneNumber("27838968976"));
    }

    @Test
    public void testCheckCellPhoneNumber_IncorrectlyFormatted_TooShort() {
        Login login = new Login();
        assertFalse("Cellphone '+2783' should be incorrectly formatted (too short)",
                login.checkCellPhoneNumber("+2783"));
    }

    @Test
    public void testCheckCellPhoneNumber_IncorrectlyFormatted_Letters() {
        Login login = new Login();
        assertFalse("Cellphone '+27abc8968976' should be incorrectly formatted (contains letters)",
                login.checkCellPhoneNumber("+27abc8968976"));
    }

    @Test
    public void testRegisterUser() {
        Login login = new Login();
        String result = login.registerUser("kyl_1", "Ch&&sec@ke99!", "+27838968976", "Kyle", "Smith");

        assertEquals("User registered successfully!", result);
        assertTrue(login.isRegistered());
        assertEquals("kyl_1", login.getUsername());
        assertEquals("Ch&&sec@ke99!", login.getPassword());
        assertEquals("+27838968976", login.getCellphoneNumber());
        assertEquals("Kyle", login.getFirstName());
        assertEquals("Smith", login.getLastName());
    }

    @Test
    public void testLoginUser_Successful() {
        Login login = new Login();
        login.registerUser("kyl_1", "Ch&&sec@ke99!", "+27838968976", "Kyle", "Smith");

        assertTrue("Login should be successful with correct credentials",
                login.loginUser("kyl_1", "Ch&&sec@ke99!"));
    }

    @Test
    public void testLoginUser_Failed_WrongUsername() {
        Login login = new Login();
        login.registerUser("kyl_1", "Ch&&sec@ke99!", "+27838968976", "Kyle", "Smith");

        assertFalse("Login should fail with wrong username",
                login.loginUser("wrong", "Ch&&sec@ke99!"));
    }

    @Test
    public void testLoginUser_Failed_WrongPassword() {
        Login login = new Login();
        login.registerUser("kyl_1", "Ch&&sec@ke99!", "+27838968976", "Kyle", "Smith");

        assertFalse("Login should fail with wrong password",
                login.loginUser("kyl_1", "wrong"));
    }

    @Test
    public void testReturnLoginStatus_Success() {
        Login login = new Login();
        login.registerUser("kyl_1", "Ch&&sec@ke99!", "+27838968976", "Kyle", "Smith");

        String status = login.returnLoginStatus(true);
        assertEquals("Welcome Kyle, Smith it is great to see you again.", status);
    }

    @Test
    public void testReturnLoginStatus_Failure() {
        Login login = new Login();

        String status = login.returnLoginStatus(false);
        assertEquals("Username or password incorrect, please try again.", status);
    }
}