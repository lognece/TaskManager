package tests;

import org.junit.jupiter.api.Test;

import java.lang.management.ManagementFactory;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class testStartTime {
	@Test
	public void testStartTimeWithin10Seconds() {
		long currentTime = System.currentTimeMillis();
		long vmStartTime = ManagementFactory.getRuntimeMXBean().getStartTime();

		long timeDifference = currentTime - vmStartTime;

		assertTrue(timeDifference <= 10000, "Start time is not within 10 seconds");
	}
}
