package task_gamification.views;

import task_gamification.entity.Character;
import task_gamification.entity.User;
import task_gamification.helpers.GetFilePath;
import task_gamification.task_manager.Level;

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
    private static final int labelHight = 30;


    private JLabel characterLabel, characterNameLabel, levelLabel, characterLevelLabel, progressLabel, storyLabel;
    private JProgressBar levelProgress;
    private JTextArea storyText;
    private JScrollPane storyScrollPane;
    private Insets insets;

    private int userXP, currentLevelXP, nextLevelXP, progressValue;
    private String loggedInUser, characterName, currentLevel;

    private User user;
    private Level levelManager;
    private Character character;

    // path to csv files
    private GetFilePath FilePaths;
    private String userFilePath = FilePaths.USER_FILE_PATH,
            levelFilePath = FilePaths.LEVEL_FILE_PATH;

    /**
     * Constructor for CharacterPanel.
     * Initializes the panel with the character information linked to the currently logged-in user.
     *
     * @param loggedInUser The username of the currently logged-in user.
     */
    public CharacterPanel(String loggedInUser) throws InterruptedException {
        this.loggedInUser = loggedInUser;
        insets = this.getInsets();
        user = new User();
		storyText = new JTextArea(""); // Initialize storyText with an empty string
		storyText.setLineWrap(true);
		storyText.setEditable(false);
        levelManager = new Level();
        initializeGUI();
    }

    /**
     * Initializes the graphical user interface of the panel.
     * Sets up the layout, lables, progressbar and textfield.
     */
    private void initializeGUI() throws InterruptedException {
        setLayout(null);
        setBounds(insets.left, insets.top, W_FRAME - insets.left - insets.right,
                H_FRAME - insets.bottom - insets.top);

        createLabels();
        setupProgressBar();
        createTextFields();

    }

    /**
     * Creates all the labels in the panel.
     */
    private void createLabels() {

        // Add label and output for character
        characterLabel = new JLabel("Character:", SwingConstants.LEFT);
        characterLabel.setBounds(centerX - (W_FRAME/2) + 30, 30, labelWidth, labelHight);
        add(characterLabel);

        characterName = user.getCharacter(loggedInUser);

        characterNameLabel = new JLabel(characterName, SwingConstants.LEFT);
        characterNameLabel.setBounds(centerX - (W_FRAME/2) + labelWidth + 30, 30, labelWidth, labelHight);
        add(characterNameLabel);

        // Add label and output for level
        levelLabel = new JLabel("Level:", SwingConstants.LEFT);
        levelLabel.setBounds(centerX - (W_FRAME/2) + 30, 60, labelWidth, labelHight);
        add(levelLabel);

        currentLevel = levelManager.getLevel(loggedInUser);

        characterLevelLabel = new JLabel(currentLevel, SwingConstants.LEFT);
        characterLevelLabel.setBounds(centerX - (W_FRAME/2) + labelWidth + 30, 60, labelWidth, labelHight);
        add(characterLevelLabel);

        // Add label for progressbar
        progressLabel = new JLabel("Progress:", SwingConstants.LEFT);
        progressLabel.setBounds(centerX - (W_FRAME/2) + 30, 90, labelWidth, labelHight);
        add(progressLabel);

        // Add label for story line
        storyLabel = new JLabel("Story Line:", SwingConstants.LEFT);
        storyLabel.setBounds(centerX - (W_FRAME/2) + 30, 150, labelWidth, labelHight);
        add(storyLabel);

    }

    /**
     * Sets up progress bar to show users progress in the current level.
     */
    private void setupProgressBar() throws InterruptedException {
        userXP = user.getXP(loggedInUser);
        currentLevel = levelManager.getLevel(loggedInUser);
        currentLevelXP = levelManager.getLevelXP(String.valueOf(currentLevel));
        nextLevelXP = levelManager.getLevelXP(String.valueOf(currentLevel + 1));

        levelProgress = new JProgressBar();
        levelProgress.setBounds(centerX - (W_FRAME/2) + 30 + labelWidth, 90, 200, 20);
        levelProgress.setStringPainted(true);

        // Fetch XP thresholds for current and next level
        progressValue = levelManager.getProgressValue(Integer.parseInt(currentLevel), userXP);
        levelProgress.setValue(progressValue);
        Thread.sleep(nextLevelXP - currentLevelXP);
        add(levelProgress);
    }




    /**
     * Creates all the text fields in the panel.
     */
    private void createTextFields() {

        // Setup for story text
        character = new Character();
        storyText = new JTextArea(character.getStory(loggedInUser));
        storyText.setLineWrap(true);
        storyText.setEditable(false);
        storyScrollPane = new JScrollPane(storyText);
        storyScrollPane.setBounds(centerX - (W_FRAME/2) + 30, 180, W_FRAME - 60, H_FRAME - 260);
        add(storyScrollPane);

    }

}