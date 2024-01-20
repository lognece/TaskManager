package task_gamification.main;

import task_gamification.views.DonePanel;
import task_gamification.views.ToDoPanel;
import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public static final String FRAME_NAME = "Task_Manager";
    public static final int W_FRAME = 1080;
    public static final int H_FRAME = (int) (W_FRAME / ((Math.sqrt(5) + 1) / 2));
    private JMenuBar mainMenuBar;
    private JMenu taskMenu, characterMenu, highscoreMenu, settingsMenu;
    private JMenuItem toDoItem, doneItem, generalSettings, userManual, credits;

    private JPanel contentPanel;
    private String loggedInUser;

    public MainFrame(String loggedInUser) {
    	this.loggedInUser = loggedInUser;
        initializeFrame();
        initializeGUI();
        setVisible(true);
    }

    private void initializeFrame() {
        setTitle(FRAME_NAME);
        setLayout(new BorderLayout());
        setSize(W_FRAME, H_FRAME);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void initializeGUI() {
        createMenus();
        contentPanel = new JPanel(new CardLayout());
        add(contentPanel, BorderLayout.CENTER);
    }

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

        // add menu items to menu
        taskMenu.add(toDoItem);
        taskMenu.add(doneItem);
        settingsMenu.add(generalSettings);
        settingsMenu.add(userManual);
        settingsMenu.add(credits);

        mainMenuBar.add(taskMenu);
        mainMenuBar.add(characterMenu);
        mainMenuBar.add(highscoreMenu);
        mainMenuBar.add(Box.createHorizontalGlue());
        mainMenuBar.add(settingsMenu);

        setJMenuBar(mainMenuBar);

        toDoItem.addActionListener(e -> showToDoPanel());
        doneItem.addActionListener(e -> showDonePanel());
    }

    private void showToDoPanel() {
        ToDoPanel toDoPanel = new ToDoPanel("src/tasks.csv", loggedInUser); 
        contentPanel.removeAll();
        contentPanel.add(toDoPanel, "ToDo");
        CardLayout cl = (CardLayout)(contentPanel.getLayout());
        cl.show(contentPanel, "ToDo");
        revalidate();
        repaint();
    }
    
    private void showDonePanel() {
        DonePanel DonePanel = new DonePanel("src/tasks.csv", loggedInUser); 
        contentPanel.removeAll();
        contentPanel.add(DonePanel, "Done");
        CardLayout cl = (CardLayout)(contentPanel.getLayout());
        cl.show(contentPanel, "Done");
        revalidate();
        repaint();
    }

}

