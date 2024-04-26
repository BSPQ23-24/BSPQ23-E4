package com.example.client.view;

import com.example.client.controller.ClientUserController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class RegisterCarView extends JFrame {

    public static RegisterCarView instance;

    private JLabel lsLicensePlate;
    private JLabel lsCarCondition;
    private JLabel lsBrand;
    private JLabel lsModel;
    private JLabel lsYear;
    private JLabel lsLocation;
    private JLabel lsUser;

    private JTextField tslicensePlate;
    private JTextField tsCarCondition;
    private JTextField tsBrand;
    private JTextField tsModel;
    private JTextField tsYear;
    private JTextField tsLocation;
    private JTextField tsUser;

    private JButton registerButton;

    public RegisterCarView() {
        setTitle("Car registration - LuxWheels");
        setSize(700, 550);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Ensure the application closes on window close

        initUI();
    }

    private void initUI() {
        JPanel formPanel = new JPanel(new GridLayout(7, 2, 10, 10)); // 7 rows, 2 columns, with gaps

        lsLicensePlate = new JLabel("License Plate:");
        lsCarCondition = new JLabel("Car Condition:");
        lsBrand = new JLabel("Brand:");
        lsModel = new JLabel("Model:");
        lsYear = new JLabel("Year:");
        lsLocation = new JLabel("Location:");
        lsUser = new JLabel("User:");

        tslicensePlate = new JTextField(15);
        tsCarCondition = new JTextField(15);
        tsBrand = new JTextField(15);
        tsModel = new JTextField(15);
        tsYear = new JFormattedTextField(new SimpleDateFormat("yyyy"));
        tsYear.setColumns(15);
        tsLocation = new JTextField(15);
        tsUser = new JTextField(15);

        formPanel.add(lsLicensePlate);
        formPanel.add(tslicensePlate);
        formPanel.add(lsCarCondition);
        formPanel.add(tsCarCondition);
        formPanel.add(lsBrand);
        formPanel.add(tsBrand);
        formPanel.add(lsModel);
        formPanel.add(tsModel);
        formPanel.add(lsYear);
        formPanel.add(tsYear);
        formPanel.add(lsLocation);
        formPanel.add(tsLocation);
        formPanel.add(lsUser);
        formPanel.add(tsUser);

        registerButton = new JButton("Register Car");
        registerButton.addActionListener(this::registerCarAction);
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(registerButton);

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    private void registerCarAction(ActionEvent e) {
        if (Arrays.asList(tslicensePlate, tsCarCondition, tsBrand, tsModel, tsYear, tsLocation, tsUser)
                .stream().anyMatch(component -> component.getText().isEmpty())) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int option = JOptionPane.showConfirmDialog(this, "Are you sure you want to register this car?", "Confirm Car Registration", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            // Extract data from text fields
            String license = tslicensePlate.getText();
            String condition = tsCarCondition.getText();
            String brandText = tsBrand.getText();
            String modelText = tsModel.getText();
            String locationText = tsLocation.getText();
            String userText = tsUser.getText();

            String yearText = tsYear.getText();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
            try {
                Date yearDate = sdf.parse(yearText);
            } catch (ParseException pe) {
                JOptionPane.showMessageDialog(this, "Invalid year format. Please use YYYY.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            // ClientUserController.createUser(name, surname, email, password, licenseNumber, DNI);
            System.out.println("Car Registration button clicked");
            closeWindow();
        }
    }

    public static RegisterCarView getInstance() {
        if (instance == null) {
            instance = new RegisterCarView();
        }
        return instance;
    }

    public void closeWindow() {
        this.dispose();
        RegisterCarView.instance = null;
    }

    /* MAIN
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RegisterCarView.getInstance().setVisible(true);
        });
    }
     */
}
