package task_gamification.views;

import task_gamification.helpers.GetLevelXP;
import task_gamification.entity.Character;
import task_gamification.entity.User;
import task_gamification.helpers.GetFilePath;
import task_gamification.task_manager.Level;
import task_gamification.task_manager.Story;

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

    private JLabel characterLabel, characterNameLabel, levelLabel, characterLevelLabel, progressLabel, storyLabel;
    private JProgressBar levelProgress;
    private JTextArea storyText;
    private JScrollPane storyScrollPane;
    private Insets insets;

    private int userXP, progressValue;
    private String loggedInUser, characterName;

    private User user;
    private Level levelManager;
    private Character character;

    private GetLevelXP getLevelXP;
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
        getLevelXP = new GetLevelXP();
        levelManager = new Level(levelFilePath);
        initializeGUI();
    }

    /**
     * Initializes the graphical user interface of the panel.
     * Sets up the layout, lables, progressbar and textfield.
     */
    private void initializeGUI() throws InterruptedException {
        setLayout(null);
        setBounds(insets.left, insets.top, W_FRAME - insets.left - insets.right, H_FRAME - insets.bottom - insets.top);

        // Add Label and output for character
        characterLabel = new JLabel("Character:", SwingConstants.LEFT);
        characterLabel.setBounds(centerX - (W_FRAME/2)+ 30, 30, labelWidth, 20);
        add(characterLabel);

        characterName = user.getCharacter(loggedInUser);

        characterNameLabel = new JLabel(characterName, SwingConstants.LEFT);
        characterNameLabel.setBounds(centerX - (W_FRAME/2) + labelWidth + 30, 30, labelWidth, 20);
        add(characterNameLabel);

        // Determine user's level based on current XP
        userXP = user.getXP(loggedInUser, userFilePath);
        int currentLevel = levelManager.determineLevel(userXP);
        characterLevelLabel = new JLabel("Level: " + currentLevel, SwingConstants.LEFT);
        characterLevelLabel.setBounds(centerX - (W_FRAME/2) + 30, 60, labelWidth, 20);
        add(characterLevelLabel);

        // Setup progress bar
        setupProgressBar(userXP, currentLevel);

        // Setup for story text
        character = new Character();
        storyText = new JTextArea(character.getStory(loggedInUser));
        storyText.setLineWrap(true);
        storyText.setEditable(false);
        storyScrollPane = new JScrollPane(storyText);
        storyScrollPane.setBounds(centerX - (W_FRAME/2) + 30, 180, W_FRAME - 60, H_FRAME - 250);
        add(storyScrollPane);

        //TODO to be tested: if user levels, does the story automatically update?

    }

    private void setupProgressBar(int userXP, int currentLevel) {
        progressLabel = new JLabel("Progress:", SwingConstants.LEFT);
        progressLabel.setBounds(centerX - (W_FRAME/2) + 30, 90, labelWidth, 20);
        add(progressLabel);

        levelProgress = new JProgressBar();
        levelProgress.setBounds(centerX - (W_FRAME/2) + 30 + labelWidth, 90, 200, 20);
        add(levelProgress);

        // Fetch XP thresholds for current and next level
        int lowerXP = getLevelXP.getLevelXP(String.valueOf(currentLevel), GetFilePath.LEVEL_FILE_PATH);
        int upperXP = getLevelXP.getLevelXP(String.valueOf(currentLevel + 1), GetFilePath.LEVEL_FILE_PATH);
        progressValue = (int) ((userXP - lowerXP) * 100.0 / (upperXP - lowerXP));
        levelProgress.setValue(progressValue);
        levelProgress.setStringPainted(true);
    }

    private void updateStoryText() {
        int currentLevel = levelManager.determineLevel(userXP);
        Story storyManager = new Story();
        String newStoryLine = storyManager.updateStory(currentLevel);

        storyText.setText(newStoryLine);
        storyText.setCaretPosition(0);
    }

    //TODO: on level 0 the textfield should be empty

}