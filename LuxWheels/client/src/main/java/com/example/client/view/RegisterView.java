package com.example.client.view;

import javax.swing.*;
import java.awt.*;
import javax.swing.text.MaskFormatter;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.util.Arrays;

public class RegisterView extends JPanel {

    private static final long serialVersionUID = 1L;

    private JPanel pSignup = new JPanel(new GridLayout(7, 1));
    private JLabel lsMail = new JLabel("Email: ");
    private JLabel lsPass = new JLabel("Password: ");
    private JLabel lssPass = new JLabel("Birthdate: ");
    private JLabel lsNombre = new JLabel("Name:");
    private JLabel lsApellido = new JLabel("Surname:");
    private JLabel lsDNI = new JLabel("License Number:");
    private JTextField tsMail = new JTextField(15);
    private JPasswordField tsPass = new JPasswordField(15);
    private JFormattedTextField tssPass;
    private JTextField tsNombre = new JTextField(15);
    private JTextField tsApellido = new JTextField(15);
    private JTextField tsDNI = new JTextField(15);
    private JButton bSignup = new JButton("Sign up");
    private JCheckBox acceptTermsCheckbox = new JCheckBox("I accept the terms and conditions");

    public RegisterView() {
        setLayout(new BorderLayout());
        initializeUI();
    }

    private void initializeUI() {
        JLabel titleLabel = new JLabel("Sign Up", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        add(titleLabel, BorderLayout.NORTH);
        setupForm();
        setupTermsAndConditions();
        setupButtons();
    }

    private void setupForm() {
        pSignup.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        pSignup.setBackground(Color.WHITE);

        pSignup.add(new FlowLayoutPanel(lsMail, tsMail));
        pSignup.add(new FlowLayoutPanel(lsNombre, tsNombre));
        pSignup.add(new FlowLayoutPanel(lsApellido, tsApellido));
        pSignup.add(new FlowLayoutPanel(lsDNI, tsDNI));
        pSignup.add(new FlowLayoutPanel(lsPass, tsPass));
        setupBirthdateField();
        pSignup.add(new FlowLayoutPanel(new JLabel(""), acceptTermsCheckbox));

        add(pSignup, BorderLayout.CENTER);
    }

    private void setupBirthdateField() {
        try {
            MaskFormatter formatter = new MaskFormatter("##/##/####");
            formatter.setPlaceholderCharacter('_');
            tssPass = new JFormattedTextField(formatter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        pSignup.add(new FlowLayoutPanel(lssPass, tssPass));
    }

    private void setupTermsAndConditions() {
        acceptTermsCheckbox.addItemListener(e -> bSignup.setEnabled(e.getStateChange() == ItemEvent.SELECTED));
    }

    private void setupButtons() {
        bSignup.addActionListener(this::registerAction);
        bSignup.setEnabled(false);
        bSignup.setPreferredSize(new Dimension(120, 40));
        bSignup.setBackground(new Color(0, 93, 232));
        bSignup.setForeground(Color.WHITE);
        bSignup.setFocusPainted(false);
        bSignup.setBorderPainted(false);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        buttonPanel.add(bSignup);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private class FlowLayoutPanel extends JPanel {
        public FlowLayoutPanel(JComponent label, JComponent field) {
            setLayout(new FlowLayout(FlowLayout.LEFT));
            setBackground(Color.WHITE);
            add(label);
            add(field);
        }
    }

    private void registerAction(ActionEvent e) {
        if (Arrays.asList(tsMail, tsPass, tssPass, tsNombre, tsApellido, tsDNI).stream().anyMatch(component -> component.getText().isEmpty())) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            int option = JOptionPane.showConfirmDialog(this, "Are you sure you want to sign up?", "Confirm Sign Up", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                System.out.println("Register button clicked");
            }
        }
    }
}
