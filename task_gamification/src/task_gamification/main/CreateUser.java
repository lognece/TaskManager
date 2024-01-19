package task_gamification.main;

import task_gamification.CSV.CSVReader;
import task_gamification.CSV.CSVWriter;
import task_gamification.entity.User;
import task_gamification.task_manager.Task;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class CreateUser extends JFrame{
    public static final int H_FRAME = 400;
    public static final int W_FRAME = 600;
    private JPanel contentPane;

    private JButton button_login;

    private JLabel label_username, label_icon, label_errorText, label_character;

    private JTextField textField_username;

    private Insets insets;

    String errorText = "ErrorText";
    private int characterNum; // 0 = Tiefling, 1 = Dragonborn
    private int score = 0;


    public CreateUser() {

        super("Create User");
        //setResizable(false);
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

        JPanel userPane = new JPanel();
        userPane.setLayout(null);
        userPane.setBounds(insets.left, insets.top, W_FRAME - insets.left - insets.right,
                H_FRAME - insets.bottom - insets.top);

        label_username = new JLabel("Username");
        label_username.setBounds(150, 240, 70, 20);
        userPane.add(label_username);

        textField_username = new JTextField();
        textField_username.setBounds(label_username.getX() + label_username.getWidth() + 30,
                label_username.getY(), 180, label_username.getHeight());
        userPane.add(textField_username);

        ImageIcon iconTiefling = new ImageIcon("src/icon/tiefling.png"); // replace with tiefling_portrait.png
        Image imageTiefling = iconTiefling.getImage();
        Image newImageTiefling = imageTiefling.getScaledInstance(100, 120, Image.SCALE_DEFAULT);
        iconTiefling = new ImageIcon(newImageTiefling);
        JButton button_char1 = new JButton(iconTiefling);
        button_char1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                characterNum = 0;
            }
        });
        button_char1.setBounds(textField_username.getX() - 60, label_username.getY() - 145, 100, 120);
        button_char1.setFocusPainted(false);
        userPane.add(button_char1);

        ImageIcon iconDragonborn = new ImageIcon("src/icon/dragonborn.png"); // replace with dragonborn_portrait.png
        Image imageDragonborn = iconDragonborn.getImage();
        Image newImageDragonborn = imageDragonborn.getScaledInstance(100, 120, Image.SCALE_DEFAULT);
        iconDragonborn = new ImageIcon(newImageDragonborn);
        JButton button_char2 = new JButton(iconDragonborn);
        button_char2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                characterNum = 1;
            }
        });
        button_char2.setBounds(textField_username.getX() + 80, label_username.getY() - 145, 100, 120);
        button_char2.setFocusPainted(false);
        userPane.add(button_char2);

        JButton button_create = new JButton("Create User");
        button_create.setBounds(textField_username.getX() - 40, label_username.getY() + 45, 80, 22);
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
                        label_errorText.setText("User already exists");
                    } else {
                        List<String> newUserContent = new ArrayList<>();

                        newUserContent.add(textField_username.getText());
                        newUserContent.add(String.valueOf(characterNum));
                        newUserContent.add(String.valueOf(score));
                        // Add empty strings for string fields
                        newUserContent.add(""); // Placeholder for taskID (assuming it's a string)
                        newUserContent.add(""); // Placeholder for taskTitle
                        newUserContent.add(""); // Placeholder for taskDescription
                        newUserContent.add(""); // Placeholder for taskPriority

                        // Add default integers for integer fields
                        newUserContent.add("0"); // Placeholder for taskXP (assuming it's an integer)

                        // Add an empty string or a default value for the completion status
                        newUserContent.add(""); // Placeholder for completeStatus
                        
                        System.out.println("New User Content: " + newUserContent); //Debugging

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

        JButton button_toLogin = new JButton("Login");
        button_toLogin.setBounds(textField_username.getX() + 80, label_username.getY() + 45, 80, 22);
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

        label_character = new JLabel("Select your character");
        label_character.setBounds(textField_username.getX() - 10, textField_username.getY() - 200,
                170, 30);
        userPane.add(label_character);

        label_errorText = new JLabel();
        label_errorText.setForeground(Color.RED);
        label_errorText.setBounds(textField_username.getX() - 5, textField_username.getY() + 75,
                170, 30);
        userPane.add(label_errorText);

        setContentPane(userPane);

    }
}
