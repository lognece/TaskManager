package task_gamification.views;

import task_gamification.entity.User;
import task_gamification.helpers.ButtonHelper;
import task_gamification.main.CreateUser;
import task_gamification.main.MainFrame;

import javax.swing.*;
import java.awt.*;

/**
 * Class representing the General Settings panel in the app.
 * It allows users to see their general settings inkl. their username, date of creation
 * and an uption to logout.
 */

public class GeneralSettingsPanel extends JPanel{

    // size and position
    public static final int W_FRAME = 1080;
    public static final int H_FRAME = (int) (W_FRAME / ((Math.sqrt(5) + 1) / 2));
    private static final int centerX = W_FRAME / 2;
    private static final int labelWidth = 100;
    private Insets insets;

    private String loggedInUser, creationDate;
    private JLabel userLabel, usernameLabel, creationLabel, creationDateLabel;
    private JButton logoutButton;

    public GeneralSettingsPanel(String loggedInUser) {
        this.loggedInUser = loggedInUser;
        insets = this.getInsets();
        initializeGUI();
    }

    // Initializes the graphical user interface of the panel
    private void initializeGUI() {
        setLayout(null);
        setBounds(insets.left, insets.top, W_FRAME - insets.left - insets.right,
                H_FRAME - insets.bottom - insets.top);

        // Add Label and output for username
        userLabel = new JLabel("Username:", SwingConstants.LEFT);
        userLabel.setBounds(centerX - (W_FRAME/2)+ 30, 30, labelWidth, 20);
        add(userLabel);

        usernameLabel = new JLabel(loggedInUser, SwingConstants.LEFT);
        usernameLabel.setBounds(centerX - (W_FRAME/2) + labelWidth + 30, 30, labelWidth, 20);
        add(usernameLabel);

        // Add Label and output for username
        creationLabel = new JLabel("Created at:", SwingConstants.LEFT);
        creationLabel.setBounds(centerX - (W_FRAME/2)+ 30, 60, labelWidth, 20);
        add(creationLabel);

        User user = new User();
        creationDateLabel = new JLabel(user.getCreationDate(loggedInUser), SwingConstants.LEFT);
        creationDateLabel.setBounds(centerX - (W_FRAME/2) + labelWidth + 30, 60, labelWidth, 20);
        add(creationDateLabel);

        // Add logout button
        logoutButton = ButtonHelper.newButton("Logout", "logout", e -> {
            // go back to user creation
            MainFrame mainFrame = new MainFrame(loggedInUser);
            mainFrame.dispose();
            new CreateUser();

            //TODO prorerly close mainframe when clicked

        }, centerX - (W_FRAME/2) + 20, 100, 100, 25);
        add(logoutButton);

    }

}
