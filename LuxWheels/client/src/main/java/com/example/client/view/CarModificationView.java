package com.example.client.view;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CarModificationView extends JPanel {

    private JTextField nameField;
    private JTextField modelField;
    private JTextField cityField;
    private JFormattedTextField startDateField;
    private JFormattedTextField endDateField;
    private JButton submitButton;

    public CarModificationView() {
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240));

        // Create the date formatter for JFormattedTextField
        MaskFormatter dateFormatter = null;
        try {
            dateFormatter = new MaskFormatter("##/##/####");
            dateFormatter.setPlaceholderCharacter('_');
        } catch (Exception e) {
            e.printStackTrace();
        }

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        formPanel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("Car Modification");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.add(titleLabel);

        formPanel.add(createFieldPanel("Name:", nameField = new JTextField(15)));
        formPanel.add(createFieldPanel("Model:", modelField = new JTextField(15)));
        formPanel.add(createFieldPanel("City:", cityField = new JTextField(15)));

        formPanel.add(createFieldPanel("Start Date:", startDateField = new JFormattedTextField(dateFormatter)));
        formPanel.add(createFieldPanel("End Date:", endDateField = new JFormattedTextField(dateFormatter)));

        formPanel.add(Box.createRigidArea(new Dimension(0, 50)));

        submitButton = new JButton("Submit");
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
        String name = nameField.getText();
        String model = modelField.getText();
        String city = cityField.getText();
        String startDate = startDateField.getText();
        String endDate = endDateField.getText();

        // For now, I will just print out the results
        System.out.println("Car Modification Details:");
        System.out.println("Name: " + name);
        System.out.println("Model: " + model);
        System.out.println("City: " + city);
        System.out.println("Start Date: " + startDate);
        System.out.println("End Date: " + endDate);

        JOptionPane.showMessageDialog(this, "Car modification details submitted successfully!");

        // Should go back to the previous windows
        closeWindow(submitButton);
    }

    private void closeWindow(Component component) {
        Window window = SwingUtilities.getWindowAncestor(component);
        if (window != null) {
            window.dispose();
        }
    }

    /* MAIN
    public static void main(String[] args) {
        JFrame frame = new JFrame("Car Modification");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new CarModificationView());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
     */
}
