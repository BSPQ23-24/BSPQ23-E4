package com.example.client.view;

import com.example.client.controller.ClientUserController;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Getter
@Setter
public class LoginView extends JPanel {

    private JTextField nameField;
    private JTextField emailField;
    private JPasswordField passwordField;

    public LoginView() {
        this.initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240));

        JPanel formPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        formPanel.setBackground(Color.WHITE);

        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("Welcome to LuxWheels");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        formPanel.add(titleLabel);

        JPanel inputPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        inputPanel.setBackground(Color.WHITE);

        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField(15);
        namePanel.add(nameLabel);
        namePanel.add(nameField);
        inputPanel.add(namePanel);

        JPanel emailPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField(15);
        emailPanel.add(emailLabel);
        emailPanel.add(emailField);
        inputPanel.add(emailPanel);

        JPanel passwordPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(15);
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);
        inputPanel.add(passwordPanel);

        ImageIcon icon = new ImageIcon("src/main/java/pics/audi.jpg");
        Image image = icon.getImage();
        Image newImage = image.getScaledInstance(400, 125, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(newImage);
        JLabel photoLabel = new JLabel(scaledIcon);
        JPanel photoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        photoPanel.setBackground(Color.WHITE);
        photoPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        photoPanel.add(photoLabel);
        formPanel.add(photoPanel);

        buttonPanel.add(inputPanel, BorderLayout.NORTH);
        buttonPanel.add(photoPanel, BorderLayout.CENTER);

        JButton loginButton = new JButton("Login");
        loginButton.setPreferredSize(new Dimension(120, 40));
        loginButton.setBackground(new Color(0, 93, 232));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setBorderPainted(false);
        loginButton.addActionListener(this::submitAction);

        JLabel signupLabel = new JLabel("Don't have an account? ");
        JButton signupButton = new JButton("Sign up");
        signupButton.setPreferredSize(loginButton.getPreferredSize());
        signupButton.setBackground(new Color(0, 93, 232));
        signupButton.setForeground(Color.WHITE);
        signupButton.setBorderPainted(false);
        signupButton.setFocusPainted(false);
        signupButton.addActionListener(this::Register);

        JPanel buttonContainerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonContainerPanel.setBackground(Color.WHITE);
        buttonContainerPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        buttonContainerPanel.add(loginButton);
        buttonContainerPanel.add(signupLabel);
        buttonContainerPanel.add(signupButton);

        buttonPanel.add(buttonContainerPanel, BorderLayout.SOUTH);

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RegisterView registerView = RegisterView.getInstance();
                registerView.setVisible(true);
                closeWindow(signupButton);
            }
        });
    }

    private void closeWindow(Component component) {
        Window window = SwingUtilities.getWindowAncestor(component);
        if (window != null) {
            window.dispose();
        }
    }

    private void submitAction(ActionEvent actionEvent) {
        String name = nameField.getText();
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());
    }

    private void Register(ActionEvent actionEvent) {
        System.out.println("Sign up button clicked");
    }

    /*
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Welcome to LuxWheels");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(new LoginView());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
    */
    
}
