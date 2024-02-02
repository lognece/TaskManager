package tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import task_gamification.CSV.CSVReader;

import java.io.*;
import java.nio.Buffer;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test for the readCSV() Method.
 */
public class testCSVReader {

	// Creates the temporary directory and deletes it after the tests
	@TempDir
	Path tempDir;

	private File validCSVFile;
	private File emptyCSVFile;
	private File incorrectCSVFile;
	private String nonExistentFilePath = "no_existent_file.csv";

	/**
	 * Creates a temporary validTest.csv and emptyTest.csv with test data used for the test.
	 */
	@BeforeEach
	void setUp() throws IOException {
		// Set up a valid CSV file
		validCSVFile = tempDir.resolve("validTest.csv").toFile();
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(validCSVFile))) {
			writer.write("Tom;0;110;2;2024-01-26;[T, o, m, T, o, m];tom@tom.com\n");
			writer.write("filia;1;60;2;2024-01-26;[b, a, r, k];filia@barklin.shop\n");
		}

		// Set up an empty CSV file and ensure it's created
		emptyCSVFile = tempDir.resolve("emptyTest.csv").toFile();
		if (!emptyCSVFile.createNewFile()) {
			throw new IOException("Could not create empty CSV file");
		}
	}

	/**
	 * Tests if the method functions properly with a valid file input.
	 */
	@Test
	void testReadCSV_ValidFile() {
		List<List<String>> result = CSVReader.readCSV(validCSVFile.getAbsolutePath());
		assertNotNull(result, "The result should not be null");
		assertEquals(2, result.size(), "There should be two records in the result");

		// Check first data row
		assertEquals("filia", result.get(1).get(0), "First name should be filia");
		assertEquals("1", result.get(1).get(1), "filia's level should be 1");
		assertEquals("60", result.get(1).get(2), "filia's score should be 60");
		assertEquals("2", result.get(1).get(3), "filia's character is 2");
		assertEquals("2024-01-26", result.get(1).get(4), "date of creation should be 2024-01-26");
		assertEquals("[b, a, r, k]", result.get(1).get(5), "filia's password should be [b, a, r, k]");
		assertEquals("filia@barklin.shop", result.get(1).get(6), "the email should be filia@barklin.shop");
	}

	/**
	 * Tests if the method functions properly with a empty file input.
	 */
	@Test
	void testReadCSV_EmptyFile() {
		List<List<String>> result = CSVReader.readCSV(emptyCSVFile.getAbsolutePath());
		assertTrue(result.isEmpty(), "The result should be empty");
	}

	/**
	 * Tests if the method throws expected error with a non existing file input.
	 */
	@Test
	void testReadCSV_NonExistentFile() {
		Exception exception = assertThrows(RuntimeException.class, () -> {
			CSVReader.readCSV(nonExistentFilePath);
		});

		assertTrue(exception.getCause() instanceof IOException, "The cause should be an IOException");
	}
}
