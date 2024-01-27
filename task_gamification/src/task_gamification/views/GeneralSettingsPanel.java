package task_gamification.views;

import task_gamification.entity.User;
import task_gamification.helpers.ButtonHelper;
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

    // size and position
    public static final int W_FRAME = 1080;
    public static final int H_FRAME = (int) (W_FRAME / ((Math.sqrt(5) + 1) / 2));
    private static final int centerX = W_FRAME / 2;
    private static final int labelWidth = 100;
    private static final int labelHight = 30;
    private static final int buttonWidth = 150;
    private static final int buttonHeight = 25;

    private JLabel userLabel, usernameLabel, creationLabel, creationDateLabel, emailLabel, emailAddressLabel;
    private JButton logoutButton;
    private Insets insets;

    private String loggedInUser, creationDate;

    private MainFrame mainFrame;
    private User user;


    /**
     * Constructor for GeneralSettingsPanel.
     * Initializes the panel with the general settings information
     * inkl.: username of logged-in user, creation date of the user account.
     * there is also a button to logout.
     *
     * @param loggedInUser The username of the currently logged-in user.
     * @param mainFrame Currently open frame.
     */
    public GeneralSettingsPanel(String loggedInUser, MainFrame mainFrame) {
        this.loggedInUser = loggedInUser;
        this.mainFrame = mainFrame;
        insets = this.getInsets();
        initializeGUI();
    }

    /**
     * Initializes the graphical user interface of the panel.
     * Sets up the layout, lables and buttons.
     */
    private void initializeGUI() {
        setLayout(null);
        setBounds(insets.left, insets.top, W_FRAME - insets.left - insets.right,
                H_FRAME - insets.bottom - insets.top);

        createLabels();
        createButtons();

    }

    /**
     * Creates all the labels in the panel.
     */
    private void createLabels() {

        // Add Label and output for username
        userLabel = new JLabel("Username:", SwingConstants.LEFT);
        userLabel.setBounds(centerX - (W_FRAME/2)+ 30, 30, labelWidth, labelHight);
        add(userLabel);

        usernameLabel = new JLabel(loggedInUser, SwingConstants.LEFT);
        usernameLabel.setBounds(centerX - (W_FRAME/2) + labelWidth + 30, 30, labelWidth, labelHight);
        add(usernameLabel);

        // Add creation date label and output for username
        creationLabel = new JLabel("Created at:", SwingConstants.LEFT);
        creationLabel.setBounds(centerX - (W_FRAME/2)+ 30, userLabel.getY() + 30, labelWidth, labelHight);
        add(creationLabel);

        user = new User();
        creationDateLabel = new JLabel(user.getCreationDate(loggedInUser), SwingConstants.LEFT);
        creationDateLabel.setBounds(centerX - (W_FRAME/2) + labelWidth + 30, creationLabel.getY(),
                labelWidth, labelHight);
        add(creationDateLabel);

        // Add e-mail address label and output for username
        emailLabel = new JLabel("E-Mail:", SwingConstants.LEFT);
        emailLabel.setBounds(centerX - (W_FRAME/2)+ 30, creationLabel.getY() + 30, labelWidth, labelHight);
        add(emailLabel);

        emailAddressLabel = new JLabel(user.getEMail(loggedInUser), SwingConstants.LEFT);
        emailAddressLabel.setBounds(centerX - (W_FRAME/2) + labelWidth + 30, emailLabel.getY(),
                labelWidth, labelHight);
        add(emailAddressLabel);

    }

    /**
     * Creates all the buttons in the panel.
     */
    private void createButtons() {

        // Add logout button
        logoutButton = ButtonHelper.newButton("Logout", "logout", e -> {

            EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    mainFrame.dispose();
                    new CreateUser();
                }
            });

        }, centerX - (W_FRAME/2) + 20, emailLabel.getY() + (buttonHeight/2) + 30, buttonWidth, buttonHeight);
        add(logoutButton);

    }

}