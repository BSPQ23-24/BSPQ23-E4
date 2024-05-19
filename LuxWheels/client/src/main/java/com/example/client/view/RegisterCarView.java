package com.example.client.view;

import com.example.client.controller.ClientCarController;
import com.example.client.controller.ClientUserController;
import com.example.client.model.CarModel;
import com.sun.tools.javac.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Stream;
import java.util.Locale;
import java.util.ResourceBundle;

public class RegisterCarView extends JFrame {

    public static RegisterCarView instance;

    private JLabel lsLicensePlate;
    private JLabel lsCarCondition;
    private JLabel lsBrand;
    private JLabel lsModel;
    private JLabel lsYear;
    private JLabel lsLocation;
    private JLabel lsDescription;
    private JLabel lsPricePerDay;

    private JTextField tslicensePlate;
    private JComboBox<CarModel.CarCondition> conditionComboBox;
    private JTextField tsBrand;
    private JTextField tsModel;
    private JTextField tsYear;
    private JTextField tsLocation;
    private JTextField tsDescription;
    private JTextField tsPricePerDay;


    private JButton registerButton;
    private MainFrame mainFrame;
    private ResourceBundle messages;
    private Locale locale;

    public RegisterCarView(MainFrame mainFrame, Locale locale, ResourceBundle messages) {
        setTitle(messages.getString("title.carRegistration"));
        setSize(700, 550);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.mainFrame = mainFrame;
        this.messages = messages;
        this.locale = locale;
        initUI();
    }

    private void initUI() {
        JPanel formPanel = new JPanel(new GridLayout(8, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        Font labelFont = new Font("SansSerif", Font.BOLD, 12);

        // --------- Form section ---------

        lsLicensePlate = new JLabel(messages.getString("label.licensePlate"));
        lsCarCondition = new JLabel(messages.getString("label.carCondition"));
        lsBrand = new JLabel(messages.getString("label.brand"));
        lsModel = new JLabel(messages.getString("label.model"));
        lsYear = new JLabel(messages.getString("label.year"));
        lsLocation = new JLabel(messages.getString("label.location"));
        lsPricePerDay = new JLabel(messages.getString("label.pricePerDay"));
        lsDescription = new JLabel(messages.getString("label.description"));

        Stream.of(lsLicensePlate, lsCarCondition, lsBrand, lsModel, lsYear, lsLocation, lsPricePerDay, lsDescription)
                .forEach(label -> label.setFont(labelFont));

        tslicensePlate = new JTextField(15);
        conditionComboBox = new JComboBox<>(CarModel.CarCondition.values());
        conditionComboBox.setSelectedIndex(1);

        tsBrand = new JTextField(15);
        tsModel = new JTextField(15);
        tsYear = new JFormattedTextField(new SimpleDateFormat("yyyy"));
        tsYear.setColumns(15);
        tsLocation = new JTextField(15);
        tsPricePerDay = new JTextField(5);
        tsDescription = new JTextField(15);

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
        formPanel.add(lsPricePerDay);
        formPanel.add(tsPricePerDay);
        formPanel.add(lsDescription);
        formPanel.add(tsDescription);

        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        formPanel.setBackground(Color.lightGray);

        // --------- Buttons section ---------

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        registerButton = new JButton(messages.getString("button.registerCar"));
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
        imagePanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 5, 0));
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
        if (Arrays.asList(tsBrand, tsModel, tsYear, tsLocation, tsDescription, tsPricePerDay)
                .stream().anyMatch(component -> component.getText().isEmpty())) {
            JOptionPane.showMessageDialog(this, messages.getString("message.fillAllFields"), messages.getString("message.error"), JOptionPane.ERROR_MESSAGE);
            return;
        }

        int option = JOptionPane.showConfirmDialog(this, messages.getString("message.confirmRegistration"), messages.getString("title.confirmCarRegistration"), JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            CarModel.CarCondition selectedCondition = (CarModel.CarCondition) conditionComboBox.getSelectedItem();
            String brand = tsBrand.getText();
            String model = tsModel.getText();
            String location = tsLocation.getText();
            String yearText = tsYear.getText();
            String description = tsDescription.getText();
            Double pricePerDay;

            try {
                pricePerDay = Double.parseDouble(tsPricePerDay.getText());
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(this, messages.getString("message.priceFormatError"), messages.getString("message.error"), JOptionPane.ERROR_MESSAGE);
                return;
            }

            ClientCarController.createCar(brand, location, model, yearText, selectedCondition, description, pricePerDay);
            closeWindow();

            this.mainFrame.getCarListView().updateCarList();
            this.mainFrame.getHostedCarsView().loadUserCars();
        }
    }

    public static RegisterCarView getInstance() {
        if (instance == null) {
            //instance = new RegisterCarView();
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
