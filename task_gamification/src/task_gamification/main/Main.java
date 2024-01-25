package task_gamification.main;

import java.lang.management.ManagementFactory;

public class Main {

    private static long currentTime, vmStartTime, startTime;
    private static CreateUser createUserFrame;

    public static void main(String[] args) {

        createUserFrame = new CreateUser();
        createUserFrame.setVisible(true);

        printStartTime();

    }

    /**
     * Calculates Time needed to start application.
     * Needed check if time < max. reaction time for Application
     */
    private static void printStartTime() {

        currentTime = System.currentTimeMillis();
        vmStartTime = ManagementFactory.getRuntimeMXBean().getStartTime();
        System.out.println(currentTime - vmStartTime);

    }
}