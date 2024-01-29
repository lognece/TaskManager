package tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import task_gamification.CSV.CSVReader;
import task_gamification.CSV.CSVWriter;

import java.io.*;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class testCSVWriter {
	// Creates the temporary directory and deletes it after the tests
	@TempDir
	Path tempDir;

	private File outputFile;

	@BeforeEach
	void setUp() throws IOException {
		// Set up an output CSV file
		outputFile = tempDir.resolve("outputTest.csv").toFile();
	}

	@Test
	void testWriteCSV_ValidContent() throws IOException{
		List<List<String>> content = Arrays.asList(
				Arrays.asList("Tom", "0", "110", "2", "2024-01-26", "[T, o, m, T, o, m]", "tom@tom.com"),
				Arrays.asList("filia", "1", "60", "2", "2024-01-26", "[b, a, r, k]", "filia@barklin.shop")
		);

		CSVWriter.writeCSV(outputFile.getAbsolutePath(), content);

		// Verify file content
		BufferedReader reader = new BufferedReader(new FileReader(outputFile));
		String firstLine = reader.readLine();
		String secondLine = reader.readLine();
		reader.close();

		assertNotNull(reader, "The result should not be null");
		assertEquals("Tom;0;110;2;2024-01-26;[T, o, m, T, o, m];tom@tom.com", firstLine);
		assertEquals("filia;1;60;2;2024-01-26;[b, a, r, k];filia@barklin.shop", secondLine);
	}

	@Test
	void testWriteCSV_EmptyContent() throws IOException {
		List<List<String>> content = Arrays.asList();

		CSVWriter.writeCSV(outputFile.getAbsolutePath(), content);

		BufferedReader reader = new BufferedReader(new FileReader(outputFile));
		assertNull(reader.readLine(), "The file should be empty");
		reader.close();
	}
}
