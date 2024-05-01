package com.example.client.view;

import com.example.client.controller.ClientCarController;
import com.example.client.controller.ClientUserController;
import com.example.client.model.CarModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.net.URL;
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

    private JTextField tslicensePlate;
    private JComboBox<CarModel.CarCondition> conditionComboBox;
    private JTextField tsBrand;
    private JTextField tsModel;
    private JTextField tsYear;
    private JTextField tsLocation;

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
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        Font labelFont = new Font("SansSerif", Font.BOLD, 12);

        // --------- Form section ---------

        lsLicensePlate = new JLabel("License Plate:");
        lsLicensePlate.setFont(labelFont);
        lsCarCondition = new JLabel("Car Condition:");
        lsCarCondition.setFont(labelFont);
        lsBrand = new JLabel("Brand:");
        lsBrand.setFont(labelFont);
        lsModel = new JLabel("Model:");
        lsModel.setFont(labelFont);
        lsYear = new JLabel("Year:");
        lsYear.setFont(labelFont);
        lsLocation = new JLabel("Location:");
        lsLocation.setFont(labelFont);

        tslicensePlate = new JTextField(15);
        conditionComboBox = new JComboBox<>(CarModel.CarCondition.values());
        conditionComboBox.setSelectedIndex(1);

        tsBrand = new JTextField(15);
        tsModel = new JTextField(15);
        tsYear = new JFormattedTextField(new SimpleDateFormat("yyyy"));
        tsYear.setColumns(15);
        tsLocation = new JTextField(15);

        formPanel.add(lsLicensePlate);
        formPanel.add(tslicensePlate);
        formPanel.add(lsCarCondition);
        formPanel.add(conditionComboBox);
        formPanel.add(lsBrand);
        formPanel.add(tsBrand);
        formPanel.add(lsModel);
        formPanel.add(tsModel);
        formPanel.add(lsYear);
        formPanel.add(tsYear);
        formPanel.add(lsLocation);
        formPanel.add(tsLocation);

        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        formPanel.setBackground(Color.lightGray);

        // --------- Buttons section ---------

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        registerButton = new JButton("Register Car");
        registerButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        registerButton.addActionListener(this::registerCarAction);

        buttonPanel.add(registerButton);
        buttonPanel.setBackground(Color.lightGray);

        // --------- Car picture section ---------

        JPanel imagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        URL carImageUrl = getClass().getResource("/bmw.jpg");
        ImageIcon icon = new ImageIcon(carImageUrl);
        Image image = icon.getImage();

        Image newImage = image.getScaledInstance(400, 250, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(newImage);
        JLabel imageLabel = new JLabel(scaledIcon);
        imagePanel.add(imageLabel);
        imagePanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        imagePanel.setBackground(Color.WHITE);

        // --------- Add panels to layout ---------

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        contentPanel.add(formPanel);
        contentPanel.add(buttonPanel);

        add(contentPanel, BorderLayout.NORTH);
        add(imagePanel, BorderLayout.SOUTH);

    }

    private void registerCarAction(ActionEvent e) {
        if (Arrays.asList(tslicensePlate, tsBrand, tsModel, tsYear, tsLocation)
                .stream().anyMatch(component -> component.getText().isEmpty())) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int option = JOptionPane.showConfirmDialog(this, "Are you sure you want to register this car?", "Confirm Car Registration", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            // Extract data from text fields
            Integer licensePlate = Integer.valueOf(tslicensePlate.getText());
            CarModel.CarCondition selectedCondition = (CarModel.CarCondition) conditionComboBox.getSelectedItem();
            String brand = tsBrand.getText();
            String model = tsModel.getText();
            String location = tsLocation.getText();
            String yearText = tsYear.getText();

            /*SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
            try {
                Date yearDate = sdf.parse(yearText);
            } catch (ParseException pe) {
                JOptionPane.showMessageDialog(this, "Invalid year format. Please use YYYY.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }*/

            ClientCarController.createCar(licensePlate, brand, location, model, yearText, selectedCondition);
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
