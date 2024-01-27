package task_gamification.main;

import task_gamification.entity.User;
import task_gamification.helpers.GetFilePath;
import task_gamification.helpers.ButtonHelper;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Represents the Frame to display the user interface to create a new user.
 * It provides functionalities to select a character, choose a username and login.
 */
public class CreateUser extends JFrame{

    // size and position
    public static final int H_FRAME = 400;
    public static final int W_FRAME = 600;
    private static final int centerX = W_FRAME / 2;
    private static final int labelWidth = 100;
    private static final int labelHight = 30;
    private static final int textFieldWidth = 180;
    private static final int charWidth = 100;
    private static final int charHeight = 120;
    private static final int buttonWidth = 150;
    private static final int buttonHeight = 25;
    private static final int labelCharacterWidth = 170;

    private JPanel userPane;
    private JButton char1Button, char2Button, button_create, button_toLogin;
    private JLabel usernameLabel, label_errorText, characterLabel, passwordLabel, emailLabel;
    private JTextField usernameTextField, emailTextField;
    private JPasswordField passwordField;
    private Insets insets;

    private int characterNum; // 0 = Tiefling, 1 = Dragonborn
    private int level = 0;
    private int score = 0, characterIndex;
    private boolean containsUsername, validEmail;
    private String characterName, regexPattern;
    private List<String> newUserContent;

    private User newUser;
    private ImageIcon iconTiefling, iconDragonborn;
    private Image newImageTiefling, newImageDragonborn;

    // path to csv files
    private GetFilePath FilePaths;
    private String userFilePath = FilePaths.USER_FILE_PATH,
            charImgPath_1 = FilePaths.CHAR_1_FILE_PATH,
            charImgPath_2 = FilePaths.CHAR_2_FILE_PATH;


    /**
     * Constructor for CreateUser frame.
     * Initializes the frame.
     */
    public CreateUser() {

        super("Create User");
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

        userPane = new JPanel();
        userPane.setLayout(null);
        userPane.setBounds(insets.left, insets.top, W_FRAME - insets.left - insets.right,
                H_FRAME - insets.bottom - insets.top);

        createLabels();
        createButtons();
        createTextFields();
        setContentPane(userPane);

    }

    /**
     * Creates all the labels in the panel.
     */
    private void createLabels() {

        // Add Label for character selection and determine its positioning
        characterLabel = new JLabel("Select your character");
        characterLabel.setHorizontalAlignment(SwingConstants.CENTER);
        characterLabel.setVerticalAlignment(SwingConstants.CENTER);
        characterLabel.setBounds(centerX - labelCharacterWidth / 2, 30,
                labelCharacterWidth, labelHight);
        userPane.add(characterLabel);

        // Add Label for username and determine its positioning
        usernameLabel = new JLabel("Username");
        usernameLabel.setBounds(centerX - (labelWidth + textFieldWidth) / 2,
                characterLabel.getY() + (labelHight/2) + charHeight + 45, labelWidth, labelHight);
        userPane.add(usernameLabel);

        // Add Label for password and determine its positioning
        passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(centerX - (labelWidth + textFieldWidth) / 2,
                usernameLabel.getY() + (labelHight/2) + 20, labelWidth, labelHight);
        userPane.add(passwordLabel);

        // Add Label for email and determine its positioning
        emailLabel = new JLabel("E-Mail");
        emailLabel.setBounds(centerX - (labelWidth + textFieldWidth) / 2,
                passwordLabel.getY() + (labelHight/2) + 20, labelWidth, labelHight);
        userPane.add(emailLabel);

    }

    /**
     * Creates all the buttons in the panel.
     */
    private void createButtons() {

        // Create button_char1 using ButtonHelper
        char1Button = ButtonHelper.newButton("", "", e -> characterNum = 0,
                centerX - charWidth - 10, characterLabel.getY() + (labelHight / 2) + 30,
                charWidth, charHeight);

        // Set the icon for the button_char1
        iconTiefling = new ImageIcon(charImgPath_1);
        newImageTiefling = iconTiefling.getImage().getScaledInstance(100, 120, Image.SCALE_DEFAULT);
        char1Button.setIcon(new ImageIcon(newImageTiefling));

        // Create button_char2 using ButtonHelper
        char2Button = ButtonHelper.newButton("", "", e -> characterNum = 1,
                centerX + 10, characterLabel.getY() + (labelHight / 2) + 30,
                charWidth, charHeight);

        // Set the icon for the button_char2
        iconDragonborn = new ImageIcon(charImgPath_2);
        newImageDragonborn = iconDragonborn.getImage().getScaledInstance(100, 120, Image.SCALE_DEFAULT);
        char2Button.setIcon(new ImageIcon(newImageDragonborn));

        // Create button_create using ButtonHelper
        button_create = ButtonHelper.newButton("Create User", "create", e -> {
                    checkInputData();

        }, centerX - buttonWidth - 10, emailLabel.getY() + 45, buttonWidth, buttonHeight);


        // Create button_create using ButtonHelper
        button_toLogin = ButtonHelper.newButton("Login", "login", e -> {
            EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    CreateUser.this.dispose();
                    new Login();
                }
            });
        }, centerX + 10, emailLabel.getY() + 45, buttonWidth, buttonHeight);

        // Set additional properties
        char1Button.setFocusPainted(false);
        char2Button.setFocusPainted(false);
        button_create.setFocusPainted(false);
        button_toLogin.setFocusPainted(false);

        // Add the button to the panel
        userPane.add(char1Button);
        userPane.add(char2Button);
        userPane.add(button_create);
        userPane.add(button_toLogin);

    }

    /**
     * Creates all the text fields in the panel.
     */
    private void createTextFields() {

        // Add textfield for username and determine positioning
        usernameTextField = new JTextField();
        usernameTextField.setBounds(centerX - (labelWidth + textFieldWidth) / 2 + labelWidth,
                usernameLabel.getY(), textFieldWidth, labelHight);
        userPane.add(usernameTextField);

        // Add password field for password input
        passwordField = new JPasswordField();
        passwordField.setBounds(centerX - (labelWidth + textFieldWidth) / 2 + labelWidth,
                passwordLabel.getY(), textFieldWidth, labelHight);
        userPane.add(passwordField);

        // Add textfield for email address and determine positioning
        emailTextField = new JTextField();
        emailTextField.setBounds(centerX - (labelWidth + textFieldWidth) / 2 + labelWidth,
                emailLabel.getY(), textFieldWidth, labelHight);
        userPane.add(emailTextField);
    }

    /**
     * Checks validity of the input data.
     * Also shows error messages if necessary.
     */
    private void checkInputData() {
        if (usernameTextField.getText().equals("") && (passwordField.getPassword().length == 0)
                && emailTextField.getText().equals("")) {
            JOptionPane.showMessageDialog(this,
                    "Please enter a valid username, password and e-mail address",
                    "ERROR", JOptionPane.ERROR_MESSAGE);

        } else if (usernameTextField.getText().equals("")) {
            JOptionPane.showMessageDialog(this,
                    "Please enter a username",
                    "ERROR", JOptionPane.ERROR_MESSAGE);

        } else if (passwordField.getPassword().length == 0) {
            JOptionPane.showMessageDialog(this,
                    "Please enter a password",
                    "ERROR", JOptionPane.ERROR_MESSAGE);

        } else if (emailTextField.getText().equals("")) {
            JOptionPane.showMessageDialog(this,
                    "Please enter a e-mail address",
                    "ERROR", JOptionPane.ERROR_MESSAGE);

        } else {
            newUser = new User();
            containsUsername = newUser.authenticate(usernameTextField.getText(), userFilePath);

            if (containsUsername) {
                JOptionPane.showMessageDialog(this,
                        "Sorry, the user '" + usernameTextField.getText() + "' already exists",
                        "ERROR", JOptionPane.ERROR_MESSAGE);

            } else {

                validEmail = validateEmail(emailTextField.getText());

                if (validEmail) {

                    newUserContent = new ArrayList<>();

                    newUserContent.add(usernameTextField.getText()); // col 1 = username
                    newUserContent.add(String.valueOf(characterNum)); // col 2 = chosen character (number)
                    newUserContent.add(String.valueOf(score)); // col 3 = score
                    newUserContent.add(String.valueOf(1)); // col 4 = level
                    newUserContent.add(String.valueOf(LocalDate.now())); // col 5 = date of user creation
                    newUserContent.add(Arrays.toString(passwordField.getPassword())); // col 6 = password
                    newUserContent.add(emailTextField.getText()); // col 7 = e-mail address

                    newUser.createNewUser(userFilePath, newUserContent);

                    EventQueue.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            CreateUser.this.dispose();
                            new MainFrame(usernameTextField.getText());
                        }
                    });

                } else {
                    JOptionPane.showMessageDialog(this,
                            "Please enter a valid e-mail address",
                            "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    /**
     * Checks validity of the e-mail address using regular expression.
     */
    private boolean validateEmail(String emailAddress) {

        regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        validEmail = Pattern.compile(regexPattern)
                .matcher(emailAddress)
                .matches();

        return validEmail;
    }
}