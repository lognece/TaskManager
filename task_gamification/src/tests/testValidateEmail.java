package tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import task_gamification.main.CreateUser;
import static org.junit.jupiter.api.Assertions.*;

public class testValidateEmail {

    private CreateUser newUserTest;

    @BeforeEach
    void setUp() {
        newUserTest = new CreateUser();
    }

    @Test
    void testValidEmail_ValidContent_ValidSchema() {
        assertEquals(true, newUserTest.validateEmail("user@domain.com"));
        assertEquals(true, newUserTest.validateEmail("User12@domain.net"));
    }

    @Test
    void testValidEmail_InvalidContent_ValidSchema() {
        assertEquals(false, newUserTest.validateEmail("U$3R!@d0ma!n.com"));
    }

    @Test
    void testValidEmail_EmptyString() {
        assertEquals(false, newUserTest.validateEmail(""));
    }

    @Test
    void testValidEmail_ValidContent_InvalidSchema() {
        assertEquals(false, newUserTest.validateEmail("user@domain"));
    }

    @Test
    void testValidEmail_ValidContent_InvalidDot() {
        assertEquals(false, newUserTest.validateEmail("user@domain.com."));
    }





}
