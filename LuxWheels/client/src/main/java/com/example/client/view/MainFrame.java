package com.example.client.view;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private JMenuBar menuBar;

    public MainFrame() {
        setTitle("Car Rental Application");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        LoginView loginView = new LoginView(this);
        JPanel carListView = new CarListView();
        cardPanel.add(loginView, "LoginView");
        cardPanel.add(carListView, "CarListView");

        menuBar = new JMenuBar();
        menuBar.setLayout(new BorderLayout());
        menuBar.setVisible(false); // Initially invisible

        setupMenuBar();
        cardLayout.show(cardPanel, "LoginView");
        getContentPane().add(cardPanel);
    }

    private void setupMenuBar() {
        JButton carListButton = new JButton("Cars to Rent");
        carListButton.addActionListener(e -> cardLayout.show(cardPanel, "CarListView"));
        JButton carRentingButton = new JButton("Cars Currently Renting");
        JButton carOfferingButton = new JButton("Cars Offering for Rent");

        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        navPanel.add(carListButton);
        navPanel.add(carRentingButton);
        navPanel.add(carOfferingButton);

        menuBar.add(navPanel, BorderLayout.CENTER);

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> logout());
        logoutButton.setBackground(Color.RED);
        logoutButton.setOpaque(true);
        logoutButton.setBorderPainted(false);

        JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        logoutPanel.add(logoutButton);

        menuBar.add(logoutPanel, BorderLayout.EAST);
    }

    public void onLoginSuccess() {
        cardLayout.show(cardPanel, "CarListView");
        menuBar.setVisible(true);
        setJMenuBar(menuBar);
    }

    private void logout() {
        JOptionPane.showMessageDialog(this, "You have been logged out.");
        cardLayout.show(cardPanel, "LoginView");
        menuBar.setVisible(false);
    }
}
