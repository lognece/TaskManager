package task_gamification.main;

import task_gamification.entity.User;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private int score = 0;


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

        ImageIcon iconTiefling = new ImageIcon("src/icon/tiefling.png"); // replace with tiefling_portrait.png
        Image imageTiefling = iconTiefling.getImage();
        Image newImageTiefling = imageTiefling.getScaledInstance(100, 120, Image.SCALE_DEFAULT);
        iconTiefling = new ImageIcon(newImageTiefling);
        button_char1 = new JButton(iconTiefling);
        button_char1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                characterNum = 0;
            }
        });

        button_char1.setBounds(centerX - charWidth - 10, label_username.getY() - 145, charWidth, charHeight);
        button_char1.setFocusPainted(false);
        userPane.add(button_char1);

        ImageIcon iconDragonborn = new ImageIcon("src/icon/dragonborn.png"); // replace with dragonborn_portrait.png
        Image imageDragonborn = iconDragonborn.getImage();
        Image newImageDragonborn = imageDragonborn.getScaledInstance(100, 120, Image.SCALE_DEFAULT);
        iconDragonborn = new ImageIcon(newImageDragonborn);
        button_char2 = new JButton(iconDragonborn);
        button_char2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                characterNum = 1;
            }
        });
        button_char2.setBounds(centerX + 10, label_username.getY() - 145, charWidth, charHeight);
        button_char2.setFocusPainted(false);
        userPane.add(button_char2);

        // Adjust button_create to be centered horizontally on the same line as the label and text field
        button_create = new JButton("Create User");
        button_create.setBounds(centerX - buttonWidth - 10, label_username.getY() + 45, buttonWidth, buttonHeight);
        button_create.setFocusPainted(false);

        button_create.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(textField_username.getText().equals("")) {
                    label_errorText.setText("Please enter a username");

                } else {
                    label_errorText.setText("");
                    User newUser = new User();
                    boolean containsUsername = newUser.authenticate(textField_username.getText(),"src/users.csv");

                    if (containsUsername) {
                        label_errorText.setText("Sorry, the user '" + textField_username.getText() + "' already exists");

                    } else {
                        List<String> newUserContent = new ArrayList<>();

                        newUserContent.add(textField_username.getText());
                        newUserContent.add(String.valueOf(characterNum));
                        newUserContent.add(String.valueOf(score));
                        
                        newUser.createNewUser("src/users.csv", newUserContent);

                        EventQueue.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                CreateUser.this.dispose();
                                new MainFrame(textField_username.getText());
                            }
                        });
                    }
                }
            }
        });
        userPane.add(button_create);

        // Adjust button_toLogin to be centered horizontally on the same line as the label and text field
        button_toLogin = new JButton("Login");
        button_toLogin.setBounds(centerX + 10, label_username.getY() + 45, buttonWidth, buttonHeight);
        button_toLogin.setFocusPainted(false);
        button_toLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        CreateUser.this.dispose();
                        new Login();
                    }
                });

            }
        });
        userPane.add(button_toLogin);

        // Adjust label_username to be centered horizontally
        label_character = new JLabel("Select your character", SwingConstants.CENTER);
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
