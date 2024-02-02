package tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import task_gamification.main.CreateUser;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test for the validateEmail() Method.
 */
public class testValidateEmail {

    private CreateUser newUserTest;

    @BeforeEach
    void setUp() {
        newUserTest = new CreateUser();
    }

    /**
     * Tests if the method functions properly if a email is entered with
     * the valid content and valid schema.
     */
    @Test
    void testValidEmail_ValidContent_ValidSchema() {
        assertEquals(true, newUserTest.validateEmail("user@domain.com"));
        assertEquals(true, newUserTest.validateEmail("User12@domain.net"));
    }

    /**
     * Tests if the method functions properly if a email is entered with
     * the invalid content and valid schema.
     */
    @Test
    void testValidEmail_InvalidContent_ValidSchema() {
        assertEquals(false, newUserTest.validateEmail("U$3R!@d0ma!n.com"));
    }

    /**
     * Tests if the method functions properly if a email is entered with
     * an empty string.
     */
    @Test
    void testValidEmail_EmptyString() {
        assertEquals(false, newUserTest.validateEmail(""));
    }

    /**
     * Tests if the method functions properly if a email is entered with
     * the valid content and invalid schema.
     */
    @Test
    void testValidEmail_ValidContent_InvalidSchema() {
        assertEquals(false, newUserTest.validateEmail("user@domain"));
    }

    /**
     * Tests if the method functions properly if a email is entered with
     * the valid content and valid dot placement.
     */
    @Test
    void testValidEmail_ValidContent_InvalidDot() {
        assertEquals(false, newUserTest.validateEmail("user@domain.com."));
    }





}
