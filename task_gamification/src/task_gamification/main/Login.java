package task_gamification.main;

import task_gamification.entity.User;
import task_gamification.helpers.*;
import task_gamification.helpers.ComponentSizesSmallFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

/**
 * Represents the Frame to display the user interface to login as a user.
 * It provides functionalities to login or go back to the user creation frame.
 */
public class Login extends ComponentSizesSmallFrame {

    private JPanel loginPane;
    private JButton loginButton, toCreateUserButton;
    private JLabel usernameLabel, passwordLabel;
    private JTextField usernameTextField;
    private JPasswordField passwordField;
    private Insets insets;

    private User userLogin;
    private Userlog userlog;
    private GetFilePath FilePaths = new GetFilePath();
    private String userFilePath = FilePaths.USER_FILE_PATH;

    /**
     * Constructor for Login class.
     * Initializes the frame and GUI components of the login window.
     */
    public Login() {
        super("Login");
        setLayout(null);
        setSize(W_FRAME, H_FRAME);
        setLocationRelativeTo(null);
        setLocation(getX() - 80, getY() - 80);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);

        insets = this.getInsets();

        initializeGUI();
    }

    /**
     * Initializes the graphical user interface components.
     * Adds labels, buttons, and text fields to the login panel.
     */
    private void initializeGUI() {
        loginPane = new JPanel();
        loginPane.setLayout(null);
        loginPane.setBounds(insets.left, insets.top,
                W_FRAME - insets.left - insets.right, H_FRAME - insets.bottom - insets.top);

        addLabelsToPane();
        addButtonsToPane();
        addTextFieldsToPane();

        setContentPane(loginPane);
    }

    /**
     * Adds labels for username and password to the login panel.
     */
    private void addLabelsToPane() {
        usernameLabel = UIComponentHelper.createLabel("Username",
                CENTER_X - (LABEL_WIDTH + TEXT_FIELD_WIDTH) / 2, CENTER_Y - 70, LABEL_WIDTH, LABEL_HEIGHT);
        passwordLabel = UIComponentHelper.createLabel("Password",
                CENTER_X - (LABEL_WIDTH + TEXT_FIELD_WIDTH) / 2, usernameLabel.getY() + (LABEL_HEIGHT / 2) + 20, LABEL_WIDTH, LABEL_HEIGHT);
        loginPane.add(usernameLabel);
        loginPane.add(passwordLabel);
    }

    /**
     * Adds login and back buttons to the login panel.
     */
    private void addButtonsToPane() {
        // Coordinates for the Login button
        int loginButtonX = CENTER_X + 10;
        int loginButtonY = passwordLabel.getY() + 60;

        // Coordinates for the Back button
        int backButtonX = CENTER_X - BUTTON_WIDTH - 10;
        int backButtonY = passwordLabel.getY() + 60;

        // Create the Login button using the ButtonHelper
        loginButton = ButtonHelper.newButton("Login", "login", this::loginAction,
                loginButtonX, loginButtonY, BUTTON_WIDTH, BUTTON_HEIGHT);
        loginPane.add(loginButton);

        // Create the Back button using the ButtonHelper
        toCreateUserButton = ButtonHelper.newButton("Back", "back", this::backAction,
                backButtonX, backButtonY, BUTTON_WIDTH, BUTTON_HEIGHT);
        loginPane.add(toCreateUserButton);
    }

    /**
     * Defines the action to be taken when the login button is clicked.
     * Validates the user credentials and proceeds accordingly.
     *
     * @param e The action event.
     */
    private void loginAction(ActionEvent e) {
        if (usernameTextField.getText().isEmpty() && passwordField.getPassword().length == 0) {
            JOptionPane.showMessageDialog(this, "Please enter a valid username and password", "ERROR", JOptionPane.ERROR_MESSAGE);
        } else if (usernameTextField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a username", "ERROR", JOptionPane.ERROR_MESSAGE);
        } else if (passwordField.getPassword().length == 0) {
            JOptionPane.showMessageDialog(this, "Please enter a password", "ERROR", JOptionPane.ERROR_MESSAGE);
        } else {
            userLogin = new User();
            boolean containsUsername = userLogin.passwordAuthentification(usernameTextField.getText(), Arrays.toString(passwordField.getPassword()));

            if (containsUsername) {

                userlog = new Userlog();
                userlog.startUserlog(usernameTextField.getText());

                EventQueue.invokeLater(() -> {
                    Login.this.dispose();
                    new MainFrame(usernameTextField.getText());
                });
            } else {
                JOptionPane.showMessageDialog(this, "Sorry, the username and password don't seem to match. Please try again.", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Defines the action to be taken when the back button is clicked.
     * Disposes the current window and opens the user creation frame.
     *
     * @param e The action event.
     */
    private void backAction(ActionEvent e) {
        this.dispose();
        new CreateUser();
    }

    /**
     * Adds text fields for username and password to the login panel.
     */
    private void addTextFieldsToPane() {
        usernameTextField = UIComponentHelper.createTextField(CENTER_X - (LABEL_WIDTH + TEXT_FIELD_WIDTH) / 2 + LABEL_WIDTH, usernameLabel.getY(), TEXT_FIELD_WIDTH, LABEL_HEIGHT);
        passwordField = UIComponentHelper.createPasswordField(CENTER_X - (LABEL_WIDTH + TEXT_FIELD_WIDTH) / 2 + LABEL_WIDTH, passwordLabel.getY(), TEXT_FIELD_WIDTH, LABEL_HEIGHT);
        loginPane.add(usernameTextField);
        loginPane.add(passwordField);
    }
}
