package task_gamification.main;

import java.lang.management.ManagementFactory;

public class Main {

    //private static long currentTime, vmStartTime, startTime;
    private static CreateUser createUserFrame;

    public static void main(String[] args) {

        createUserFrame = new CreateUser();
        createUserFrame.setVisible(true);

    }

}