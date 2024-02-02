package tests;

import org.junit.jupiter.api.Test;
import task_gamification.main.Main;

import java.lang.management.ManagementFactory;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test for the application start time.
 */
public class testStartTime {

	/**
	 * Tests if the application starts in an accaptable time frame.
	 * Here < 10 seconds.
	 */
	@Test
	public void testStartTimeWithin10Seconds() {

		long currentTime = System.currentTimeMillis();

		String[] args = null;
		Main.main(args);

		long vmStartTime = ManagementFactory.getRuntimeMXBean().getStartTime();

		long timeDifference = currentTime - vmStartTime;

		assertTrue(timeDifference <= 10000, "Start time is not within 10 seconds");
	}
}
