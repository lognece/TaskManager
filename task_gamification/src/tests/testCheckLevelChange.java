package tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import task_gamification.entity.User;
import task_gamification.task_manager.Score;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test for testion the checkLevelChange() Method.
 */
public class testCheckLevelChange {

	private Score score;
	private User loggedInUser;

	// Creates the temporary directory and deletes it after the tests
	@TempDir
	Path tempDir;

	/**
	 * Creates a temporary Test.csv with test data used for the test.
	 */
	@BeforeEach
	void setUp() throws IOException {
		score = new Score();
		loggedInUser = new User();

		File testFile = tempDir.resolve("Test.csv").toFile();
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(testFile))) {
			writer.write("Tom;0;110;2;2024-01-26;[T, o, m, T, o, m];tom@tom.com\n");
		}
	}

	/**
	 * checkLevelChange returns false when newScore is less than nextLevelXP
	 */
	@Test
	void levelChangeNoUpdate() {
		int newScore = 10;
		boolean result = score.checkLevelChange(String.valueOf(loggedInUser), newScore);
		assertFalse(result);

	}

	/**
	 *  if newScore is equal or greater than nextLevelXP
	 *  checkLevelChange returns true and userLevel is updated
	 */
	@Test
	void levelChangeWithUpdate() {
		int newScore = 150;
		boolean result = score.checkLevelChange(String.valueOf(loggedInUser), newScore);
		assertTrue(result);

	}
}
