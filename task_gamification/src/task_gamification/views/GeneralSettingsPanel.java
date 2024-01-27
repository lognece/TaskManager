package task_gamification.views;

import task_gamification.entity.User;
import task_gamification.helpers.ButtonHelper;
import task_gamification.helpers.UIComponentHelper;
import task_gamification.main.CreateUser;
import task_gamification.main.Login;
import task_gamification.main.MainFrame;

import javax.swing.*;
import java.awt.*;

/**
 * Class representing the General Settings panel in the app.
 * It allows users to see their general settings inkl. their username, date of creation
 * and an uption to logout.
 */
public class GeneralSettingsPanel extends JPanel{

    // size and position constants
    public static final int W_FRAME = 1080;
    public static final int H_FRAME = (int) (W_FRAME / ((Math.sqrt(5) + 1) / 2));
    private static final int CENTER_X = W_FRAME / 2;
    private static final int LABEL_WIDTH = 100;
    private static final int LABEL_HEIGHT = 30;
    private static final int BUTTON_WIDTH = 150;
    private static final int BUTTON_HEIGHT = 25;

    private MainFrame mainFrame;
    private User user;

    /**
     * Constructor for GeneralSettingsPanel.
     * @param loggedInUser The username of the currently logged-in user.
     * @param mainFrame Currently open frame.
     */
    public GeneralSettingsPanel(String loggedInUser, MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        user = new User();

        setLayout(null);
        Insets insets = this.getInsets();
        setBounds(insets.left, insets.top, W_FRAME - insets.left - insets.right,
                H_FRAME - insets.bottom - insets.top);

        addUsernameLabel(loggedInUser);
        addCreationDateLabel(loggedInUser);
        addEmailLabel(loggedInUser);
        addLogoutButton();
    }

    private void addUsernameLabel(String loggedInUser) {
        JLabel userLabel = UIComponentHelper.createLabel("Username:", CENTER_X - (W_FRAME / 2) + 30,
                30, LABEL_WIDTH, LABEL_HEIGHT);
        add(userLabel);

        JLabel usernameLabel = UIComponentHelper.createLabel(loggedInUser, CENTER_X - (W_FRAME / 2) + 130,
                30, LABEL_WIDTH, LABEL_HEIGHT);
        add(usernameLabel);
    }

    private void addCreationDateLabel(String loggedInUser) {
        JLabel creationLabel = UIComponentHelper.createLabel("Created at:", CENTER_X - (W_FRAME / 2) + 30,
                60, LABEL_WIDTH, LABEL_HEIGHT);
        add(creationLabel);

        JLabel creationDateLabel = UIComponentHelper.createLabel(user.getCreationDate(loggedInUser),
                CENTER_X - (W_FRAME / 2) + 130, 60, LABEL_WIDTH, LABEL_HEIGHT);
        add(creationDateLabel);
    }

    private void addEmailLabel(String loggedInUser) {
        JLabel emailLabel = UIComponentHelper.createLabel("E-Mail:", CENTER_X - (W_FRAME / 2) + 30, 90,
                LABEL_WIDTH, LABEL_HEIGHT);
        add(emailLabel);

        JLabel emailAddressLabel = UIComponentHelper.createLabel(user.getEMail(loggedInUser),
                CENTER_X - (W_FRAME / 2) + 130, 90, LABEL_WIDTH + 100, LABEL_HEIGHT);
        add(emailAddressLabel);
    }

    private void addLogoutButton() {
        JButton logoutButton = ButtonHelper.newButton("Logout", "logout", e -> {
            EventQueue.invokeLater(() -> {
                mainFrame.dispose();
                // Assuming 'CreateUser' is a class to show the user creation screen
                new CreateUser();
            });
        }, CENTER_X - (W_FRAME / 2) + 20, 150, BUTTON_WIDTH, BUTTON_HEIGHT);
        add(logoutButton);
    }
}