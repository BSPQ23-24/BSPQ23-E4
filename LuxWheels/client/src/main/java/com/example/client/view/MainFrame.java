package com.example.client.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.example.client.controller.ClientUserController;
import com.example.client.model.UserSession;
/**
 * MainFrame is the basic structure off the whole application
 */
public class MainFrame extends JFrame {
	private static final Logger logger = LogManager.getLogger(LoginView.class);
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private JMenuBar menuBar;

    public MainFrame() {
        Locale locale = new Locale("en");
        ResourceBundle messages = ResourceBundle.getBundle("messages/messages", locale);
        LoginView loginView = new LoginView(this,messages);

        setTitle("Car Rental Application");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        //LoginView loginView = new LoginView(this);

        ManageRentedCarsView manageRentedCarsView = new ManageRentedCarsView();

        cardPanel.add(loginView, "LoginView");

        cardPanel.add(manageRentedCarsView, "ManageRentedCarsView");

        menuBar = new JMenuBar();
        menuBar.setLayout(new BorderLayout());
        menuBar.setVisible(false); // Initially invisible

        setupMenuBar();
        cardLayout.show(cardPanel, "LoginView");
        getContentPane().add(cardPanel);
    }

    private void setupMenuBar() {
        JButton carListButton = new JButton("Cars to Rent");

        carListButton.addActionListener(e -> {
        	CarListView carListView = new CarListView(this);
            cardPanel.add(carListView, "CarListView");
        	cardLayout.show(cardPanel, "CarListView");
        });
        JButton carRentingButton = new JButton("Cars Currently Renting");
        carRentingButton.addActionListener(e -> cardLayout.show(cardPanel, "ManageRentedCarsView"));

        JButton carOfferingButton = new JButton("Cars Offering for Rent");
        carOfferingButton.addActionListener(e -> cardLayout.show(cardPanel, "HostedCarsView"));
        JButton btRegisterCar = new JButton("Register your car!");
        btRegisterCar.addActionListener(this::registerCar);
        JButton btUserData = new JButton("Modify you account");
        btUserData.addActionListener(e -> cardLayout.show(cardPanel, "UserDataModificationView"));

        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        navPanel.add(carListButton);
        navPanel.add(carRentingButton);
        navPanel.add(carOfferingButton);
        navPanel.add(btRegisterCar);
        navPanel.add(btRegisterCar);
        navPanel.add(btUserData);

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
        CarListView carListView = new CarListView(this);
        cardPanel.add(carListView, "CarListView");
        
        cardLayout.show(cardPanel, "CarListView");
        menuBar.setVisible(true);
        setJMenuBar(menuBar);
        

        HostedCarsView hostedCarsView = new HostedCarsView(ClientUserController.loggedUser);
        cardPanel.add(hostedCarsView, "HostedCarsView");
        
        UserDataModificationView userDataModificationView = new UserDataModificationView(ClientUserController.loggedUser, this);
        cardPanel.add(userDataModificationView, "UserDataModificationView");
    }

    private void logout() {
        JOptionPane.showMessageDialog(this, "You have been logged out.");
        cardLayout.show(cardPanel, "LoginView");
        menuBar.setVisible(false);
    }
    
    public void logoutForUserDeleting() {
        JOptionPane.showMessageDialog(this, "You have deleted you account");
        cardLayout.show(cardPanel, "LoginView");
        menuBar.setVisible(false);
    }

    private void registerCar(ActionEvent actionEvent) {
        RegisterCarView registerCarView = new RegisterCarView(this);
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

    public ManageRentedCarsView getManageRentedCarsView() {
        for (Component comp : cardPanel.getComponents()) {
            if (comp instanceof ManageRentedCarsView) {
                return (ManageRentedCarsView) comp;
            }
        }
        return null;
    }
}
