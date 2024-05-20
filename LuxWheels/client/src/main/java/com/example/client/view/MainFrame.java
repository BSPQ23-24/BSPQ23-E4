package com.example.client.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.example.client.controller.ClientUserController;

public class MainFrame extends JFrame {
    private static final Logger logger = LogManager.getLogger(LoginView.class);
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private JMenuBar menuBar;
    private Locale locale;
    private ResourceBundle messages;

    public MainFrame() {
        this.locale = new Locale("en");
        this.messages = ResourceBundle.getBundle("messages/messages", locale);
        LoginView loginView = new LoginView(this, messages);

        setTitle(messages.getString("title.application"));
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        CarListView carListView = new CarListView(locale, messages);
        ManageRentedCarsView manageRentedCarsView = new ManageRentedCarsView(locale, messages);

        cardPanel.add(loginView, "LoginView");
        cardPanel.add(carListView, "CarListView");
        cardPanel.add(manageRentedCarsView, "ManageRentedCarsView");

        menuBar = new JMenuBar();
        menuBar.setLayout(new BorderLayout());
        menuBar.setVisible(false); // Initially invisible

        setupMenuBar();
        cardLayout.show(cardPanel, "LoginView");
        getContentPane().add(cardPanel);
    }

    private void setupMenuBar() {
        JButton carListButton = new JButton(messages.getString("button.carsToRent"));
        carListButton.addActionListener(e -> {
            CarListView carListView = new CarListView(locale, messages);
            cardPanel.add(carListView, "CarListView");
            cardLayout.show(cardPanel, "CarListView");
        });

        JButton carRentingButton = new JButton(messages.getString("button.carsCurrentlyRenting"));
        carRentingButton.addActionListener(e -> cardLayout.show(cardPanel, "ManageRentedCarsView"));

        JButton carOfferingButton = new JButton(messages.getString("button.carsOfferingForRent"));
        carOfferingButton.addActionListener(e -> cardLayout.show(cardPanel, "HostedCarsView"));

        JButton btRegisterCar = new JButton(messages.getString("button.registerYourCar"));
        btRegisterCar.addActionListener(this::registerCar);

        JButton btUserData = new JButton(messages.getString("button.modifyYourAccount"));
        btUserData.addActionListener(e -> cardLayout.show(cardPanel, "UserDataModificationView"));

        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        navPanel.add(carListButton);
        navPanel.add(carRentingButton);
        navPanel.add(carOfferingButton);
        navPanel.add(btRegisterCar);
        navPanel.add(btUserData);

        menuBar.add(navPanel, BorderLayout.CENTER);

        JButton logoutButton = new JButton(messages.getString("button.logout"));
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

        HostedCarsView hostedCarsView = new HostedCarsView(ClientUserController.loggedUser, locale, messages);
        cardPanel.add(hostedCarsView, "HostedCarsView");

        UserDataModificationView userDataModificationView = new UserDataModificationView(ClientUserController.loggedUser, this, locale, messages);
        cardPanel.add(userDataModificationView, "UserDataModificationView");
    }

    private void logout() {
        JOptionPane.showMessageDialog(this, messages.getString("message.loggedOut"));
        cardLayout.show(cardPanel, "LoginView");
        menuBar.setVisible(false);
    }

    public void logoutForUserDeleting() {
        JOptionPane.showMessageDialog(this, messages.getString("message.accountDeleted"));
        cardLayout.show(cardPanel, "LoginView");
        menuBar.setVisible(false);
    }

    private void registerCar(ActionEvent actionEvent) {
        RegisterCarView registerCarView = new RegisterCarView(this, locale, messages);
        registerCarView.setVisible(true);
    }

    public CarListView getCarListView() {
        for (Component comp : cardPanel.getComponents()) {
            if (comp instanceof CarListView) {
                return (CarListView) comp;
            }
        }
        return null;
    }

    public HostedCarsView getHostedCarsView() {
        for (Component comp : cardPanel.getComponents()) {
            if (comp instanceof HostedCarsView) {
                return (HostedCarsView) comp;
            }
        }
        return null;
    }
}
