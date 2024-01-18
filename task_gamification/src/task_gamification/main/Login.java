package task_gamification.main;

import task_gamification.CSV.CSVReader;
import task_gamification.entity.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame{

    public static final int H_FRAME = 400;
    public static final int W_FRAME = 600;
    private JPanel contentPane;

    private JButton button_login;

    private JLabel label_username, label_icon, label_errorText;

    private JTextField textField_username;

    private Insets insets;

    String errorText = "ErrorText";

    public Login() {

        super("Login");
        //setIconImage(Toolkit.getDefaultToolkit().getImage("src/icon/fingerprint.png"));
        //setResizable(false);
        setLayout(null);
        setSize(W_FRAME, H_FRAME);
        setLocationRelativeTo(null);
        setLocation(getX() - 80, getY() - 80);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        insets = this.getInsets();

        GUI();

    }

    private void GUI() {

        JPanel loginPane = new JPanel();
        loginPane.setLayout(null);
        loginPane.setBounds(insets.left, insets.top, W_FRAME - insets.left - insets.right,
                H_FRAME - insets.bottom - insets.top);

        label_username = new JLabel("Username");
        label_username.setBounds(140, 160, 70, 20);
        loginPane.add(label_username);

        textField_username = new JTextField();
        textField_username.setBounds(label_username.getX() + label_username.getWidth() + 30,
                label_username.getY(), 180, label_username.getHeight());
        loginPane.add(textField_username);

        button_login = new JButton("Login");
        button_login.setBounds(textField_username.getX() + 20, label_username.getY() + 60, 80, 22);
        button_login.setFocusPainted(false);

        button_login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(textField_username.getText().equals("")) {

                    label_errorText.setText("Please enter a username");

                } else {

                    //label_errorText.setText("");
                    User userLogin = new User();
                    boolean containsUsername = userLogin.authenticate(textField_username.getText(),"src/users.csv");

                    if ( containsUsername ) {

                        EventQueue.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                Login.this.dispose();
                                new MainFrame();
                            }
                        });

                    }


                }

            }

        });
        loginPane.add(button_login);

        setContentPane(loginPane);
    }

}
