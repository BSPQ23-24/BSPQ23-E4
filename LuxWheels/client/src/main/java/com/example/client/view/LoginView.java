package com.example.client.view;

import com.example.client.controller.ClientUserController;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

@Getter
@Setter
public class LoginView extends JPanel {
    private JTextField nameField, emailField;
    private JPasswordField passwordField;
    private JButton submitButton;

    public LoginView() {
        this.initUI();
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
        ClientUserController.createUser(name, email, password);
    }
}
