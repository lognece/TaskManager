package task_gamification.main;

import task_gamification.views.ToDoPanel;
import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public static final String FRAME_NAME = "Task_Manager";
    public static final int W_FRAME = 1080;
    public static final int H_FRAME = (int) (W_FRAME / ((Math.sqrt(5) + 1) / 2));

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
        JMenuBar mainMenuBar = new JMenuBar();

        JMenu taskMenu = new JMenu("Task Overview");
        JMenuItem toDoItem = new JMenuItem("To-Do");
        JMenuItem doneItem = new JMenuItem("Done");

        taskMenu.add(toDoItem);
        taskMenu.add(doneItem);

        mainMenuBar.add(taskMenu);
        mainMenuBar.add(new JMenu("Character"));
        mainMenuBar.add(new JMenu("Highscore"));

        setJMenuBar(mainMenuBar);

        toDoItem.addActionListener(e -> showToDoPanel());
    }

    private void showToDoPanel() {
        ToDoPanel toDoPanel = new ToDoPanel("src/users.csv", loggedInUser); 
        contentPanel.removeAll();
        contentPanel.add(toDoPanel, "ToDo");
        CardLayout cl = (CardLayout)(contentPanel.getLayout());
        cl.show(contentPanel, "ToDo");
        revalidate();
        repaint();
    }

}

