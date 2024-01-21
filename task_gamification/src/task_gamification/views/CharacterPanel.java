package task_gamification.views;

import helpers.GetLevelXP;
import task_gamification.entity.User;

import javax.swing.*;
import java.awt.*;

/**
 * Class representing the Character panel in the app.
 * It allows users to see their character overview inkl. which character was chosen, a XP progress bar,
 * a level indicator and the unlocked story line.
 */

public class CharacterPanel extends JPanel {

    // size and position
    public static final int W_FRAME = 1080;
    public static final int H_FRAME = (int) (W_FRAME / ((Math.sqrt(5) + 1) / 2));
    private static final int centerX = W_FRAME / 2;
    private static final int labelWidth = 100;
    private Insets insets;

    private String loggedInUser; //  The user currently logged in
    private int score, level, characterNum, userIndex, userXP, upperXP, lowerXP, progressValue;
    private String characterName, characterLevel, nextLevel;
    private JLabel characterLabel, characterNameLabel, levelLabel, characterLevelLabel, progressLabel, storyLabel;
    private JProgressBar levelProgress;
    private JTextArea storyText;
    private JScrollPane storyScrollPane;



    public CharacterPanel(String loggedInUser) throws InterruptedException {
        this.loggedInUser = loggedInUser;
        insets = this.getInsets();
        initializeGUI();
        //refreshData();
    }

    // Initializes the graphical user interface of the panel
    private void initializeGUI() throws InterruptedException {
        setLayout(null);
        setBounds(insets.left, insets.top, W_FRAME - insets.left - insets.right,
                H_FRAME - insets.bottom - insets.top);

        // Add Label and output for character
        characterLabel = new JLabel("Character:", SwingConstants.LEFT);
        characterLabel.setBounds(centerX - (W_FRAME/2)+ 30, 30, labelWidth, 20);
        add(characterLabel);

        User user = new User();
        characterName = user.getCharacter(loggedInUser);

        characterNameLabel = new JLabel(characterName, SwingConstants.LEFT);
        characterNameLabel.setBounds(centerX - (W_FRAME/2) + labelWidth + 30, 30, labelWidth, 20);
        add(characterNameLabel);

        // Add Label and output for Level
        levelLabel = new JLabel("Level:", SwingConstants.LEFT);
        levelLabel.setBounds(centerX - (W_FRAME/2) + 30, 60, labelWidth, 20);
        add(levelLabel);

        characterLevel = user.getLevel(loggedInUser);

        characterLevelLabel = new JLabel(characterLevel, SwingConstants.LEFT);
        characterLevelLabel.setBounds(centerX - (W_FRAME/2) + labelWidth + 30, 60, labelWidth, 20);
        add(characterLevelLabel);

        // Add progress bar
        progressLabel = new JLabel("Progress:", SwingConstants.LEFT);
        progressLabel.setBounds(centerX - (W_FRAME/2) + 30, 90, labelWidth, 20);
        add(progressLabel);

        levelProgress = new JProgressBar();
        levelProgress.setBounds(centerX - (W_FRAME/2) + labelWidth + 30, 90, labelWidth, 20);

        userXP = user.getXP(loggedInUser, "src/users.csv");

        GetLevelXP getLevelXP = new GetLevelXP();
        lowerXP = getLevelXP.getLevelXP(characterLevel, "src/level.csv");
        nextLevel = String.valueOf(Integer.parseInt(characterLevel) + 1);
        upperXP = getLevelXP.getLevelXP(nextLevel, "src/level.csv");

        progressValue = (userXP - lowerXP) * 100 / (upperXP - lowerXP);
        levelProgress.setValue(progressValue);
        levelProgress.setStringPainted(true);
        Thread.sleep(upperXP - lowerXP);
        add(levelProgress);

        // Add label and text field for story line
        storyLabel = new JLabel("Story Line:", SwingConstants.LEFT);
        storyLabel.setBounds(centerX - (W_FRAME/2) + 30, 150, labelWidth, 20);
        add(storyLabel);


        storyText = new JTextArea(13,20);
        storyText.setLineWrap(true);
        storyScrollPane = new JScrollPane(storyText);
        storyScrollPane.setBounds(centerX - (W_FRAME/2) + 30, 180, W_FRAME - 60, H_FRAME - 250);
        storyText.setText(user.getStory(loggedInUser));
        add(storyScrollPane);
        // to be tested: if user levels, does the story automatically update?








    }





}
