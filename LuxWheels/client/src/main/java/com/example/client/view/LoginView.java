package com.example.client.view;
import com.example.client.controller.ClientUserController;
import com.example.client.model.UserModel;
import com.example.client.service.UserService;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

@Getter
@Setter
public class LoginView extends JFrame {
    private JTextField nameField, emailField;
    private JPasswordField passwordField;
    private JButton submitButton;
    private UserService userService;


    public LoginView() {
        super("User Form");
        userService = new UserService();
        initUI();
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void initUI() {
        setLayout(new GridLayout(4, 2));

        add(new JLabel("Name:"));
        nameField = new JTextField();
        add(nameField);

        add(new JLabel("Email:"));
        emailField = new JTextField();
        add(emailField);

        add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        add(passwordField);

        submitButton = new JButton("Submit");
        add(submitButton);

        submitButton.addActionListener(this::submitAction);
    }

    private void submitAction(ActionEvent actionEvent) {
        String name = nameField.getText();
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());
        UserModel user = new UserModel();
        user.setName(name);
        user.setPassword(password);
        user.setEmail(email);
        ClientUserController.createUser(user);
    }

}

