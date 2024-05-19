package com.example.client.view;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.example.client.model.CarModel;
import com.example.client.model.CarModel.CarCondition;
import com.example.client.model.UserModel;
import com.example.client.service.CarService;
import java.util.Locale;
import java.util.ResourceBundle;

public class CarModificationView extends JPanel {

    private JTextField licensePlateField;
    private JTextField brandField;
    private JTextField modelField;
    private JTextField yearField;
    private JComboBox<CarCondition> conditionField;
    private JTextField locationField;
    //private JTextField userIdField;
    private JButton submitButton;
    private UserModel currentUser;
    private Locale locale;
    private ResourceBundle messages;

    public CarModificationView(UserModel user, Locale locale, ResourceBundle messages) {
        this.currentUser = user;
        this.locale = locale;
        this.messages = messages;
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240));

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        formPanel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel(messages.getString("title.carModification"));
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.add(titleLabel);

        formPanel.add(createFieldPanel(messages.getString("label.licensePlate"), licensePlateField = new JTextField(10)));
        formPanel.add(createFieldPanel(messages.getString("label.brand"), brandField = new JTextField(15)));
        formPanel.add(createFieldPanel(messages.getString("label.model"), modelField = new JTextField(15)));
        formPanel.add(createFieldPanel(messages.getString("label.year"), yearField = new JTextField(4)));

        conditionField = new JComboBox<>(CarCondition.values());
        formPanel.add(createFieldPanel(messages.getString("label.condition"), conditionField));

        formPanel.add(createFieldPanel(messages.getString("label.location"), locationField = new JTextField(15)));
        //formPanel.add(createFieldPanel("User ID:", userIdField = new JTextField(10)));

        formPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        submitButton = new JButton(messages.getString("button.submit"));
        submitButton.setPreferredSize(new Dimension(120, 40));
        submitButton.setBackground(new Color(0, 93, 232));
        submitButton.setForeground(Color.WHITE);
        submitButton.setBorderPainted(false);
        submitButton.setFocusPainted(false);
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        submitButton.addActionListener(this::submitAction);

        formPanel.add(submitButton);

        add(formPanel, BorderLayout.CENTER);
    }

    private JPanel createFieldPanel(String labelText, JComponent fieldComponent) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBackground(Color.WHITE);

        JLabel label = new JLabel(labelText);
        panel.add(label);
        panel.add(fieldComponent);

        return panel;
    }

    private void submitAction(ActionEvent e) {
        CarModel car = new CarModel();
        CarService carService = new CarService();

        car.setLicensePlate(Integer.parseInt(licensePlateField.getText()));
        car.setBrand(brandField.getText());
        car.setModel(modelField.getText());
        car.setYear(yearField.getText());
        car.setCarCondition((CarCondition) conditionField.getSelectedItem());
        car.setLocation(locationField.getText());
        //car.setUser(Integer.parseInt(userIdField.getText()));
        car.setUser(currentUser);

        carService.updateCar(car);

        JOptionPane.showMessageDialog(this, messages.getString("message.carModificationSuccess"));

        closeWindow(submitButton);
    }

    private void closeWindow(Component component) {
        Window window = SwingUtilities.getWindowAncestor(component);
        if (window != null) {
            window.dispose();
        }
    }
}
