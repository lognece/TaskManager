package task_gamification.main;

import task_gamification.views.CharacterPanel;
import task_gamification.views.DonePanel;
import task_gamification.views.HighscorePanel;
import task_gamification.helpers.ShowPanel;
import task_gamification.views.ToDoPanel;
import task_gamification.helpers.GetFilePath;
import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
	// Constants for frame name and dimensions
    public static final String FRAME_NAME = "Task_Manager";
    public static final int W_FRAME = 1080;
    public static final int H_FRAME = (int) (W_FRAME / ((Math.sqrt(5) + 1) / 2));
    
    // Menu bar components
    private JMenuBar mainMenuBar;
    private JMenu taskMenu, characterMenu, highscoreMenu, settingsMenu;
    private JMenuItem toDoItem, doneItem, generalSettings, userManual, credits, characterOverview, highscoreOverview;
    
    private ShowPanel showPanel; // Panel for showing different views
    private JPanel contentPanel; // Panel to hold the main content
    private String loggedInUser; // Stores the currently logged-in user's username

    // path to csv files
    private GetFilePath FilePaths;
    private String taskFilePath = FilePaths.TASK_FILE_PATH;

    // Constructor for MainFrame, initializes the frame and GUI components
    public MainFrame(String loggedInUser) {
    	this.loggedInUser = loggedInUser;
        initializeFrame();
        initializeGUI();
        showPanel = new ShowPanel(contentPanel);
        setVisible(true);
    }

    // Initializes the frame's properties
    private void initializeFrame() {
        setTitle(FRAME_NAME);
        setLayout(new BorderLayout());
        setSize(W_FRAME, H_FRAME);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
    
    // Initializes the GUI components of the frame
    private void initializeGUI() {
        createMenus();
        contentPanel = new JPanel(new CardLayout());
        add(contentPanel, BorderLayout.CENTER);
    }

    // Creates the menu bar and its components
    private void createMenus() {
        // create menubar
        mainMenuBar = new JMenuBar();

        // create menu
        taskMenu = new JMenu("Task Overview");
        characterMenu = new JMenu("Character");
        highscoreMenu = new JMenu("Highscore");
        settingsMenu = new JMenu("Settings");

        // create menu items
        toDoItem = new JMenuItem("To-Do");
        doneItem = new JMenuItem("Done");
        generalSettings = new JMenuItem("General Settings");
        userManual = new JMenuItem("User Manual");
        credits = new JMenuItem("Credits");
        characterOverview = new JMenuItem("Character Overview");
        highscoreOverview = new JMenuItem("Highscore Overview");

        // add menu items to menu
        taskMenu.add(toDoItem);
        taskMenu.add(doneItem);
        settingsMenu.add(generalSettings);
        settingsMenu.add(userManual);
        settingsMenu.add(credits);
        characterMenu.add(characterOverview);
        highscoreMenu.add(highscoreOverview);

        mainMenuBar.add(taskMenu);
        mainMenuBar.add(characterMenu);
        mainMenuBar.add(highscoreMenu);
        mainMenuBar.add(Box.createHorizontalGlue());
        mainMenuBar.add(settingsMenu);

        setJMenuBar(mainMenuBar);

        // Adding action listeners to menu items
        toDoItem.addActionListener(e -> showToDoPanel());
        doneItem.addActionListener(e -> showDonePanel());
        characterOverview.addActionListener(e -> {
            try {
                showCharacterPanel();
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        });
        highscoreOverview.addActionListener(e -> showHighscorePanel());
    }

    // Shows the To-Do panel
    private void showToDoPanel() {
        ToDoPanel toDoPanel = new ToDoPanel(taskFilePath, loggedInUser);
        showPanel.getShowPanel(toDoPanel, "ToDo");
    }
    
    // Shows the Done panel
    private void showDonePanel() {
        DonePanel donePanel = new DonePanel(taskFilePath, loggedInUser);
        showPanel.getShowPanel(donePanel, "Done");
    }

    // Shows the Character panel
    private void showCharacterPanel() throws InterruptedException {
        CharacterPanel characterPanel = new CharacterPanel(loggedInUser);
        showPanel.getShowPanel(characterPanel, "Character Overview");
    }

    // Shows the Character panel
    private void showHighscorePanel() {
        HighscorePanel highscorePanel = new HighscorePanel(loggedInUser);
        showPanel.getShowPanel(highscorePanel, "Highscore Overview");
    }

}

