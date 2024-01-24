package task_gamification.main;

import task_gamification.entity.User;
import task_gamification.helpers.ButtonHelper;
import task_gamification.helpers.GetFilePath;

import javax.swing.*;
import java.awt.*;

/**
 * Represents the Frame to display the user interface to login as a user.
 * It provides functionalities to login or go back to the user creation frame.
 */
public class Login extends JFrame{

    // size and position
    public static final int H_FRAME = 400;
    public static final int W_FRAME = 600;
    private static final int buttonWidth = 150;
    private static final int buttonHeight = 22;
    private static final int labelWidth = 100;
    private static final int textFieldWidth = 180;
    private static final int centerX = W_FRAME / 2;
    private static final int centerY = H_FRAME / 2;
    private static final int labelErrorWidth = 260;

    private JPanel loginPane;
    private JButton button_login, button_toCreateUser;
    private JLabel label_username, label_errorText;
    private JTextField textField_username;
    private Insets insets;

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


        // Adjust label_username to be centered horizontally
        label_username = new JLabel("Username");
        label_username.setBounds(centerX - (labelWidth + textFieldWidth) / 2, centerY - 40, labelWidth, 20);
        loginPane.add(label_username);

        // Adjust textField_username to be centered horizontally on the same row
        textField_username = new JTextField();
        textField_username.setBounds(centerX - (labelWidth + textFieldWidth) / 2 + labelWidth, centerY - 40 , textFieldWidth, 20);
        loginPane.add(textField_username);

        // Create button_login using ButtonHelper
        button_login = ButtonHelper.newButton("Login", "login", e -> {
            if(textField_username.getText().equals("")) {
                label_errorText.setText("Please enter a username");

            } else {

                //label_errorText.setText("");
                User userLogin = new User();
                boolean containsUsername = userLogin.authenticate(textField_username.getText(),userFilePath);

                if ( containsUsername ) {
                    EventQueue.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            Login.this.dispose();
                            new MainFrame(textField_username.getText());
                        }
                    });

                } else {
                    label_errorText.setText("Sorry, the user: '" + textField_username.getText() + "' does not exist!");
                }


            }
        }, centerX + 10, label_username.getY() + 60, buttonWidth, buttonHeight);

        // Set additional properties
        button_login.setFocusPainted(false);

        // Add the button to the panel
        loginPane.add(button_login);


        // Create button_create using ButtonHelper
        button_toCreateUser = ButtonHelper.newButton("Back", "back", e -> {
            Login.this.dispose(); // Dispose current Login frame
            new CreateUser(); // Open CreateUser frame
        }, centerX - buttonWidth - 10, label_username.getY() + 60, buttonWidth, buttonHeight);

        // Set additional properties
        button_toCreateUser.setFocusPainted(false);

        // Add the button to the panel
        loginPane.add(button_toCreateUser);


        // Adjust label_username to be centered horizontally
        label_errorText = new JLabel();
        label_errorText.setHorizontalAlignment(SwingConstants.CENTER);
        label_errorText.setVerticalAlignment(SwingConstants.CENTER);
        label_errorText.setForeground(Color.RED);
        label_errorText.setBounds(centerX - labelErrorWidth / 2, textField_username.getY() + 95, labelErrorWidth, 30);
        loginPane.add(label_errorText);

        setContentPane(loginPane);


    }

}