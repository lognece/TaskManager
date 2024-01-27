package task_gamification.main;

import task_gamification.entity.User;
import task_gamification.helpers.ComponentSizesSmallFrame;
import task_gamification.helpers.GetFilePath;
import task_gamification.helpers.ButtonHelper;
import task_gamification.helpers.UIComponentHelper;

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
public class CreateUser extends ComponentSizesSmallFrame {

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

        characterLabel = UIComponentHelper.createLabel("Select your character",
                CENTER_X - LABEL_CHARACTER_WIDTH / 2, 30, LABEL_CHARACTER_WIDTH, LABEL_HEIGHT);
        userPane.add(characterLabel);

        usernameLabel = UIComponentHelper.createLabel("Username", CENTER_X - (LABEL_WIDTH + TEXT_FIELD_WIDTH) / 2,
                characterLabel.getY() + (LABEL_HEIGHT /2) + CHAR_HEIGHT + 45, LABEL_WIDTH, LABEL_HEIGHT);
        userPane.add(usernameLabel);

        passwordLabel = UIComponentHelper.createLabel("Password", CENTER_X - (LABEL_WIDTH + TEXT_FIELD_WIDTH) / 2,
                usernameLabel.getY() + (LABEL_HEIGHT /2) + 20, LABEL_WIDTH, LABEL_HEIGHT);
        userPane.add(passwordLabel);

        emailLabel = UIComponentHelper.createLabel("E-Mail", CENTER_X - (LABEL_WIDTH + TEXT_FIELD_WIDTH) / 2,
                passwordLabel.getY() + (LABEL_HEIGHT /2) + 20, LABEL_WIDTH, LABEL_HEIGHT);
        userPane.add(emailLabel);

    }

    /**
     * Creates all the buttons in the panel.
     */
    private void createButtons() {

        // Create button_char1 using ButtonHelper
        char1Button = ButtonHelper.newButton("", "", e -> characterNum = 0,
                CENTER_X - CHAR_WIDTH - 10, characterLabel.getY() + (LABEL_HEIGHT / 2) + 30,
                CHAR_WIDTH, CHAR_HEIGHT);

        // Set the icon for the button_char1
        iconTiefling = new ImageIcon(charImgPath_1);
        newImageTiefling = iconTiefling.getImage().getScaledInstance(100, 120, Image.SCALE_DEFAULT);
        char1Button.setIcon(new ImageIcon(newImageTiefling));

        // Create button_char2 using ButtonHelper
        char2Button = ButtonHelper.newButton("", "", e -> characterNum = 1,
                CENTER_X + 10, characterLabel.getY() + (LABEL_HEIGHT / 2) + 30,
                CHAR_WIDTH, CHAR_HEIGHT);

        // Set the icon for the button_char2
        iconDragonborn = new ImageIcon(charImgPath_2);
        newImageDragonborn = iconDragonborn.getImage().getScaledInstance(100, 120, Image.SCALE_DEFAULT);
        char2Button.setIcon(new ImageIcon(newImageDragonborn));

        // Create button_create using ButtonHelper
        button_create = ButtonHelper.newButton("Create User", "create", e -> {
                    checkInputData();

        }, CENTER_X - BUTTON_WIDTH - 10, emailLabel.getY() + 45, BUTTON_WIDTH, BUTTON_HEIGHT);


        // Create button_create using ButtonHelper
        button_toLogin = ButtonHelper.newButton("Login", "login", e -> {
            EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    CreateUser.this.dispose();
                    new Login();
                }
            });
        }, CENTER_X + 10, emailLabel.getY() + 45, BUTTON_WIDTH, BUTTON_HEIGHT);

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

        usernameTextField = UIComponentHelper.createTextField(CENTER_X - (LABEL_WIDTH + TEXT_FIELD_WIDTH) / 2 + LABEL_WIDTH,
                usernameLabel.getY(), TEXT_FIELD_WIDTH, LABEL_HEIGHT);
        userPane.add(usernameTextField);

        passwordField = UIComponentHelper.createPasswordField(CENTER_X - (LABEL_WIDTH + TEXT_FIELD_WIDTH) / 2 + LABEL_WIDTH,
                passwordLabel.getY(), TEXT_FIELD_WIDTH, LABEL_HEIGHT);
        userPane.add(passwordField);

        emailTextField = UIComponentHelper.createTextField(CENTER_X - (LABEL_WIDTH + TEXT_FIELD_WIDTH) / 2 + LABEL_WIDTH,
                emailLabel.getY(), TEXT_FIELD_WIDTH, LABEL_HEIGHT);
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