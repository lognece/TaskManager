package task_gamification.main;

import task_gamification.views.CharacterPanel;
import task_gamification.views.DonePanel;
import task_gamification.views.HighscorePanel;
import task_gamification.views.ToDoPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class MainFrame extends JFrame{

    public static final String FRAME_NAME = "Task_Manager";
    public static final int W_FRAME = 1080;
    public static final int H_FRAME = (int) (W_FRAME / ((Math.sqrt(5) + 1) / 2));
    public static Insets INSETS;
    private byte activePage = 0;
    private JMenuBar menuBar;
    private JMenu taskMenu, characterMenu, highscoreMenu;
    private JMenuItem toDoItem, doneItem;



    public MainFrame() {
        this(0);
    }

    public MainFrame(int component) {

        super(FRAME_NAME);
        super.setName("MainFrame");
        setLayout(null);
        setSize(W_FRAME, H_FRAME);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        activePage = (byte) component;

        INSETS = getInsets();

        GUI();

    }

    private void GUI(){
        createMenus();
        createComponents();
    }

    private void createMenus() {

        // create menubar
        JMenuBar mainMenuBar = new JMenuBar();

        // create menu
        taskMenu = new JMenu("Task Overview");
        characterMenu = new JMenu("Character");
        highscoreMenu = new JMenu("Highscore");

        // create menu items
        toDoItem = new JMenuItem("To-Do");
        doneItem = new JMenuItem("Done");

        // add menu items to menu
        taskMenu.add(toDoItem);
        taskMenu.add(doneItem);

        // add menu to menubar
        mainMenuBar.add(taskMenu);
        mainMenuBar.add(characterMenu);
        mainMenuBar.add(highscoreMenu);

        this.setJMenuBar(mainMenuBar);

    }

    private void createComponents() {

        ToDoPanel toDoPanel = new ToDoPanel();
        DonePanel donePanel = new DonePanel();
        CharacterPanel characterPanel = new CharacterPanel();
        HighscorePanel highscorePanel = new HighscorePanel();

    }

}
