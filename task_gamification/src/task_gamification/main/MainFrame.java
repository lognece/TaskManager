package task_gamification.main;

import task_gamification.helpers.Userlog;
import task_gamification.views.*;
import task_gamification.helpers.ShowPanel;
import task_gamification.helpers.GetFilePath;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.io.IOException;

/**
 * Represents the Frame to display the user interface for all main functions.
 * It provides functionalities in the task overview, the character overview,
 * the highscore overview and the settings.
 */
public class MainFrame extends JFrame {
    // Constants for frame name and dimensions
    public static final String FRAME_NAME = "Task Manager";
    public static final int W_FRAME = 1080;
    public static final int H_FRAME = (int) (W_FRAME / ((Math.sqrt(5) + 1) / 2));

    // Menu bar components
    private JMenuBar mainMenuBar;
    private JMenu taskMenu, characterMenu, highscoreMenu, settingsMenu;
    private JMenuItem toDoItem, doneItem, generalSettings, userManual, credits,
            characterOverview, highscoreOverview;
    public JPanel contentPanel; // Panel to hold the main content

    private String loggedInUser; // Stores the currently logged-in user's username

    private ShowPanel showPanel; // Panel for showing different views
    private ToDoPanel toDoPanel;
    private DonePanel donePanel;
    private CharacterPanel characterPanel;
    private HighscorePanel highscorePanel;
    private GeneralSettingsPanel generalSettingsPanel;
    private CreditsPanel creditsPanel;
    private Userlog userlog;
    private UserManualPanel userManualPanel;

    // path to csv files
    private GetFilePath FilePaths;
    private String taskFilePath = FilePaths.TASK_FILE_PATH;

    /**
     * Constructor for MainFrame frame.
     * Initializes the frame.
     */
    public MainFrame(String loggedInUser) {
        this.loggedInUser = loggedInUser;
        initializeFrame();
        initializeGUI();

        showPanel = new ShowPanel(contentPanel);
        toDoPanel = new ToDoPanel(taskFilePath, loggedInUser);
        showPanel.getShowPanel(toDoPanel, "ToDo");
        userManualPanel = new UserManualPanel();

        setVisible(true);
    }

    /**
     * Initializes the frame's properties
     */
    private void initializeFrame() {
        setTitle(FRAME_NAME);
        setLayout(new BorderLayout());
        setSize(W_FRAME, H_FRAME);
        setResizable(false);
        setLocationRelativeTo(null);

        // adding a actionlistener for userlog entry when frame is closed
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        actionWhenClosed();

    }

    /**
     * Adds window listener to save the loggedInUser data in the userlog.csv
     * when manually closing the window.
     */
    private void actionWhenClosed() {
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {

                userlog = new Userlog();
                try {
                    userlog.endUserlog();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                e.getWindow().dispose();
            }
        });
    }

    /**
     * Initializes the GUI components of the frame
     */
    private void initializeGUI() {
        createMenus();
        contentPanel = new JPanel(new CardLayout());
        add(contentPanel, BorderLayout.CENTER);
    }

    /**
     * Creates the menu bar and its components
     */
    private void createMenus() {
        // create menubar
        mainMenuBar = new JMenuBar();

        // create menu
        taskMenu = new JMenu("Task Overview");
        characterMenu = new JMenu("Character");
        highscoreMenu = new JMenu("Highscore");
        settingsMenu = new JMenu("Settings");

        // create menu items
        createMenuItems();

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
        addActionListeners();
    }

    /**
     * Creates menu items and adds them to the respective menus
     */
    private void createMenuItems() {
        toDoItem = new JMenuItem("To-Do");
        doneItem = new JMenuItem("Done");
        generalSettings = new JMenuItem("General Settings");
        userManual = new JMenuItem("User Manual");
        credits = new JMenuItem("Credits");
        characterOverview = new JMenuItem("Character Overview");
        highscoreOverview = new JMenuItem("Highscore Overview");
    }

    /**
     * Adds action listeners to menu items
     */
    private void addActionListeners() {
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
        generalSettings.addActionListener(e -> showGeneralSettingsPanel());
        credits.addActionListener(e -> showCreditsPanel());
        userManual.addActionListener(e -> userManualPanel.openManual());
    }

    /**
     * Shows the To-Do panel
     */
    private void showToDoPanel() {
        toDoPanel = new ToDoPanel(taskFilePath, loggedInUser);
        showPanel.getShowPanel(toDoPanel, "ToDo");
    }

    /**
     * Shows the Done panel
     */
    private void showDonePanel() {
        donePanel = new DonePanel(taskFilePath, loggedInUser);
        showPanel.getShowPanel(donePanel, "Done");
    }

    /**
     * Shows the Character panel
     */
    private void showCharacterPanel() throws InterruptedException {
        characterPanel = new CharacterPanel(loggedInUser);
        showPanel.getShowPanel(characterPanel, "Character Overview");
    }

    /**
     * Shows the Highscore panel
     */
    private void showHighscorePanel() {
        highscorePanel = new HighscorePanel();
        showPanel.getShowPanel(highscorePanel, "Highscore Overview");
    }

    /**
     * Shows the General Settings panel
     */
    private void showGeneralSettingsPanel() {
        generalSettingsPanel = new GeneralSettingsPanel(loggedInUser, this);
        showPanel.getShowPanel(generalSettingsPanel, "General Settings");
    }

    /**
     * Shows the Credits panel
     */
    private void showCreditsPanel() {
        creditsPanel = new CreditsPanel();
        showPanel.getShowPanel(creditsPanel, "Credits");
    }
}
