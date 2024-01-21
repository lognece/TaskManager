package task_gamification.views;

import javax.swing.*;

public class HighscorePanel extends JPanel {

    // size and position
    public static final int W_FRAME = 1080;
    public static final int H_FRAME = (int) (W_FRAME / ((Math.sqrt(5) + 1) / 2));
    private static final int centerX = W_FRAME / 2;
    private static final int labelWidth = 100;

    private String loggedInUser; //  The user currently logged in
    private int score;

    public HighscorePanel(String loggedInUser) {
        this.loggedInUser = loggedInUser;
        // initializeGUI();
        // refreshData();
    }

}
