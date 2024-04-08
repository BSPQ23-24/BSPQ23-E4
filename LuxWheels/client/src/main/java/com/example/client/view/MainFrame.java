package com.example.client.view;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private CardLayout cardLayout;

    public MainFrame() {
        setTitle("Application Main Frame");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        JPanel cardPanel = new JPanel(cardLayout);

        JPanel loginView = new LoginView();

        cardPanel.add(loginView, "LoginView");
        this.getContentPane().add(cardPanel);
    }
}
