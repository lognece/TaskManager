package task_gamification.main;

import task_gamification.CSV.CSVReader;
import task_gamification.entity.User;
import task_gamification.helpers.GetFilePath;
import task_gamification.helpers.ButtonHelper;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class CreateUser extends JFrame{
    public static final int H_FRAME = 400;
    public static final int W_FRAME = 600;
    
    // size and position
    private static final int centerX = W_FRAME / 2;
    private static final int labelWidth = 100;
    private static final int textFieldWidth = 180;
    private static final int charWidth = 100;
    private static final int charHeight = 120;
    private static final int buttonWidth = 150;
    private static final int buttonHeight = 22;
    private static final int labelCharacterWidth = 170;
    private static final int labelErrorWidth = 260;
    
    private JPanel userPane;

    private JButton button_char1, button_char2, button_create, button_toLogin;

    private JLabel label_username, label_errorText, label_character;

    private JTextField textField_username;

    private Insets insets;

    private int characterNum; // 0 = Tiefling, 1 = Dragonborn
    private int score = 0, characterIndex;

    private String characterName;

    // path to csv files
    private GetFilePath FilePaths;
    private String userFilePath = FilePaths.USER_FILE_PATH;
    private String charImgPath_1 = FilePaths.CHAR_1_FILE_PATH;
    private String charImgPath_2 = FilePaths.CHAR_2_FILE_PATH;



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

        GUI();

    }

    private void GUI() {

        userPane = new JPanel();
        userPane.setLayout(null);
        userPane.setBounds(insets.left, insets.top, W_FRAME - insets.left - insets.right,
                H_FRAME - insets.bottom - insets.top);
               
        // Adjust label_username to be centered horizontally
        label_username = new JLabel("Username");
        label_username.setBounds(centerX - (labelWidth + textFieldWidth) / 2, 240, labelWidth, 20);
        userPane.add(label_username);

        // Adjust textField_username to be centered horizontally on the same row
        textField_username = new JTextField();
        textField_username.setBounds(centerX - (labelWidth + textFieldWidth) / 2 + labelWidth, 240, textFieldWidth, 20);
        userPane.add(textField_username);

        // Create button_char1 using ButtonHelper
        button_char1 = ButtonHelper.newButton("", "", e -> characterNum = 0,
                centerX - charWidth - 10, label_username.getY() - 145,
                charWidth, charHeight);

        // Set the icon for the button
        ImageIcon iconTiefling = new ImageIcon(charImgPath_1);
        Image newImageTiefling = iconTiefling.getImage().getScaledInstance(100, 120, Image.SCALE_DEFAULT);
        button_char1.setIcon(new ImageIcon(newImageTiefling));

        // Set additional properties
        button_char1.setFocusPainted(false);

        // Add the button to the panel
        userPane.add(button_char1);

        // Create button_char2 using ButtonHelper
        button_char2 = ButtonHelper.newButton("", "", e -> characterNum = 1,
                centerX + 10, label_username.getY() - 145,
                charWidth, charHeight);

        // Set the icon for the button
        ImageIcon iconDragonborn = new ImageIcon(charImgPath_2);
        Image newImageDragonborn = iconDragonborn.getImage().getScaledInstance(100, 120, Image.SCALE_DEFAULT);
        button_char2.setIcon(new ImageIcon(newImageDragonborn));

        // Set additional properties
        button_char2.setFocusPainted(false);

        // Add the button to the panel
        userPane.add(button_char2);

        // Create button_create using ButtonHelper
        button_create = ButtonHelper.newButton("Create User", "create", e -> {

            if(textField_username.getText().equals("")) {
                label_errorText.setText("Please enter a username");

            } else {
                label_errorText.setText("");
                User newUser = new User();
                boolean containsUsername = newUser.authenticate(textField_username.getText(),userFilePath);

                if (containsUsername) {
                    label_errorText.setText("Sorry, the user '" + textField_username.getText() + "' already exists");

                } else {
                    List<String> newUserContent = new ArrayList<>();

                    newUserContent.add(textField_username.getText());
                    newUserContent.add(String.valueOf(characterNum));
                    newUserContent.add(String.valueOf(score));

                    newUser.createNewUser(userFilePath, newUserContent);

                    EventQueue.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            CreateUser.this.dispose();
                            new MainFrame(textField_username.getText());
                        }
                    });
                }
            }
        }, centerX - buttonWidth - 10, label_username.getY() + 45,
                buttonWidth, buttonHeight);

        // Set additional properties
        button_create.setFocusPainted(false);

        // Add the button to the panel
        userPane.add(button_create);

        // Create button_create using ButtonHelper
        button_toLogin = ButtonHelper.newButton("Login", "login", e -> {
                    EventQueue.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            CreateUser.this.dispose();
                            new Login();
                        }
                    });
                }, centerX + 10, label_username.getY() + 45, buttonWidth, buttonHeight);

        // Set additional properties
        button_toLogin.setFocusPainted(false);

        // Add the button to the panel
        userPane.add(button_toLogin);

        // Adjust label_username to be centered horizontally
        label_character = new JLabel("Select your character");
        label_character.setBounds(centerX - labelCharacterWidth / 2, textField_username.getY() - 200, labelCharacterWidth, 30);
        userPane.add(label_character);

        // Adjust label_username to be centered horizontally
        label_errorText = new JLabel();
        label_errorText.setHorizontalAlignment(SwingConstants.CENTER);
        label_errorText.setVerticalAlignment(SwingConstants.CENTER);
        label_errorText.setForeground(Color.RED);
        label_errorText.setBounds(centerX - labelErrorWidth / 2, textField_username.getY() + 75, labelErrorWidth, 30);
        userPane.add(label_errorText);

        setContentPane(userPane);

    }
}
