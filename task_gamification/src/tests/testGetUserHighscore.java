package tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import task_gamification.entity.User;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test for the getUserHighscore() Method.
 */
public class testGetUserHighscore {

    // Creates the temporary directory and deletes it after the tests
    @TempDir
    Path tempDir;

    private File validCSVFile;
    private File emptyCSVFile;
    private User testUser;

    /**
     * Creates a temporary validTest.csv with test data used for the test.
     */
    @BeforeEach
    void setUp() throws IOException {
        testUser = new User();
        // Set up a valid CSV file
        validCSVFile = tempDir.resolve("validTest.csv").toFile();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(validCSVFile))) {
            writer.write("Tom;0;110;2;2024-01-26;[T, o, m, T, o, m];tom@tom.com\n");
            writer.write("filia;1;60;2;2024-01-26;[b, a, r, k];filia@barklin.shop\n");
            writer.write("Mel;0;0;2;2024-01-27;[p, a, s, s, w, o, r, d];mel@test.org\n");
        }

        // Set up an empty CSV file and ensure it's created
        emptyCSVFile = tempDir.resolve("emptyTest.csv").toFile();
        if (!emptyCSVFile.createNewFile()) {
            throw new IOException("Could not create empty CSV file");
        }
    }


    /**
     * Tests if the method functions properly if a user has no Highscore. (value - 0)
     */
    @Test
    void test_noHighscore() {
        assertEquals(0,
                testUser.getUserHighscore("Mel", validCSVFile.getAbsolutePath()));
    }

    /**
     * Tests if the method functions properly if a user has a Highscore.
     */
    @Test
    void test_newHighscore() {
        assertEquals(110, testUser.getUserHighscore("Tom", validCSVFile.getAbsolutePath()));
        assertEquals(60, testUser.getUserHighscore("filia", validCSVFile.getAbsolutePath()));
    }


}
