package task_gamification.main;

import task_gamification.entity.User;
import task_gamification.helpers.ButtonHelper;
import task_gamification.helpers.GetFilePath;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

/**
 * Represents the Frame to display the user interface to login as a user.
 * It provides functionalities to login or go back to the user creation frame.
 */
public class Login extends JFrame{

    // size and position
    public static final int H_FRAME = 400;
    public static final int W_FRAME = 600;
    private static final int buttonWidth = 150;
    private static final int buttonHeight = 25;
    private static final int labelWidth = 100;
    private static final int labelHight = 30;
    private static final int textFieldWidth = 180;
    private static final int centerX = W_FRAME / 2;
    private static final int centerY = H_FRAME / 2;

    private boolean containsUsername;

    private JPanel loginPane;
    private JButton loginButton, toCreateUserButton;
    private JLabel usernameLabel, errorTextLabel, passwordLabel;
    private JTextField usernameTextField;
    private JPasswordField passwordField;
    private Insets insets;

    private User userLogin;

    // path to csv files
    private GetFilePath FilePaths;
    private String userFilePath = FilePaths.USER_FILE_PATH;


    /**
     * Constructor for Login frame.
     * Initializes the frame.
     */
    public Login() {

        super("Login");
        setLayout(null);
        setSize(W_FRAME, H_FRAME);
        setLocationRelativeTo(null);
        setLocation(getX() - 80, getY() - 80);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);

        insets = this.getInsets();

        initializeGUI();

    }

    /**
     * Initializes the graphical user interface of the panel.
     * Sets up the layout, lables, textfields, buttons and error messages.
     */
    private void initializeGUI() {

        loginPane = new JPanel();
        loginPane.setLayout(null);
        loginPane.setBounds(insets.left, insets.top, W_FRAME - insets.left - insets.right,
                H_FRAME - insets.bottom - insets.top);

        createLabels();
        createButtons();
        createTextFields();
        setContentPane(loginPane);

    }

    /**
     * Creates all the labels in the panel.
     */
    private void createLabels() {

        // Add Label for username and determine its positioning
        usernameLabel = new JLabel("Username");
        usernameLabel.setBounds(centerX - (labelWidth + textFieldWidth) / 2, centerY - 70, labelWidth, labelHight);
        loginPane.add(usernameLabel);

        // Add Label for password and determine its positioning
        passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(centerX - (labelWidth + textFieldWidth) / 2,
                usernameLabel.getY() + (labelHight/2) + 20, labelWidth, labelHight);
        loginPane.add(passwordLabel);

    }

    /**
     * Creates all the buttons in the panel.
     */
    private void createButtons() {

        // Create button_login using ButtonHelper
        loginButton = ButtonHelper.newButton("Login", "login", e -> {
            if(usernameTextField.getText().equals("") && (passwordField.getPassword().length == 0)) {
                JOptionPane.showMessageDialog(this,
                        "Please enter a valid username and password",
                        "ERROR", JOptionPane.ERROR_MESSAGE);

            } else if (usernameTextField.getText().equals("")) {
                JOptionPane.showMessageDialog(this,
                        "Please enter a username",
                        "ERROR", JOptionPane.ERROR_MESSAGE);

            } else if (passwordField.getPassword().length == 0) {
                JOptionPane.showMessageDialog(this,
                        "Please enter a password",
                        "ERROR", JOptionPane.ERROR_MESSAGE);

            } else {
                userLogin = new User();
                containsUsername = userLogin.passwordAuthentification(usernameTextField.getText(),
                        Arrays.toString(passwordField.getPassword()));

                if ( containsUsername ) {
                    EventQueue.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            Login.this.dispose();
                            new MainFrame(usernameTextField.getText());
                        }
                    });

                } else {
                    JOptionPane.showMessageDialog(this,
                            "Sorry, the username and password don't seem to match. Plase try again.",
                            "ERROR", JOptionPane.ERROR_MESSAGE);
                }


            }
        }, centerX + 10, passwordLabel.getY() + 60, buttonWidth, buttonHeight);

        // Create button_create using ButtonHelper
        toCreateUserButton = ButtonHelper.newButton("Back", "back", e -> {
            Login.this.dispose(); // Dispose current Login frame
            new CreateUser(); // Open CreateUser frame
        }, centerX - buttonWidth - 10, passwordLabel.getY() + 60, buttonWidth, buttonHeight);

        // Set additional properties
        loginButton.setFocusPainted(false);
        toCreateUserButton.setFocusPainted(false);

        // Add the button to the panel
        loginPane.add(loginButton);
        loginPane.add(toCreateUserButton);

    }


    /**
     * Creates all the text fields in the panel.
     */
    private void createTextFields() {

        // Add textfield for username and determine positioning
        usernameTextField = new JTextField();
        usernameTextField.setBounds(centerX - (labelWidth + textFieldWidth) / 2 + labelWidth, usernameLabel.getY(), textFieldWidth, labelHight);
        loginPane.add(usernameTextField);

        // Add password field for password input
        passwordField = new JPasswordField();
        passwordField.setBounds(centerX - (labelWidth + textFieldWidth) / 2 + labelWidth,
                passwordLabel.getY(), textFieldWidth, labelHight);
        loginPane.add(passwordField);

    }

}