package com.example.client.view;

import java.awt.FlowLayout;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.*;
import java.awt.*;
import javax.swing.text.MaskFormatter;
import com.example.client.controller.ClientUserController;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Arrays;

public class RegisterView extends JFrame {
	
	private static final Logger logger = LogManager.getLogger(RegisterView.class);

    private static RegisterView instance;

    private static final long serialVersionUID = 1L;

    protected JPanel pPrincipal = new JPanel(new BorderLayout());
    protected JPanel pSignup = new JPanel(new GridLayout(7, 1));
    protected JPanel pUser = new JPanel(new FlowLayout());
    protected JPanel pPass = new JPanel(new FlowLayout());
    protected JPanel psPass = new JPanel(new FlowLayout());
    protected JPanel pNombre = new JPanel(new FlowLayout());
    protected JPanel pApellido = new JPanel(new FlowLayout());
    protected JPanel pDNI = new JPanel(new FlowLayout());
    protected JPanel pCheckbox = new JPanel(new FlowLayout(FlowLayout.LEFT)); 

    protected JLabel lsMail = new JLabel("Mail: ");
    protected JLabel lsPass = new JLabel("Password: ");
    protected JLabel lssPass = new JLabel("Birthdate: ");
    protected JLabel lsNombre = new JLabel("Name:");
    protected JLabel lsApellido = new JLabel("Surname:");
    protected JLabel lsDNI = new JLabel("License Number:");
    protected JTextField tsMail = new JTextField(15);
    protected JPasswordField tsPass = new JPasswordField(15);
    protected JFormattedTextField tssPass;
    protected JTextField tsNombre = new JTextField(15);
    protected JTextField tsApellido = new JTextField(15);
    protected JTextField tsDNI = new JTextField(15);
    protected JButton bSignup = new JButton("Sign up");
    protected JLabel lsError = new JLabel("");


    protected JCheckBox acceptTermsCheckbox = new JCheckBox("I accept the terms and conditions");

    private RegisterView() {
        this.setTitle("Create an account - LuxWheels");
        this.setSize(400, 550);
        this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(null); 

        JLabel titleLabel = new JLabel("Sign Up");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        pPrincipal.add(titleLabel, BorderLayout.NORTH);

        pSignup.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); 
        pSignup.setBackground(Color.WHITE); 

        pUser.add(lsMail);
        pUser.add(tsMail);
        pNombre.add(lsNombre);
        pNombre.add(tsNombre);
        pApellido.add(lsApellido);
        pApellido.add(tsApellido);
        pDNI.add(lsDNI);
        pDNI.add(tsDNI);
        pPass.add(lsPass);
        pPass.add(tsPass);

        try {
            MaskFormatter formatter = new MaskFormatter("##/##/####");
            formatter.setPlaceholderCharacter('_');
            tssPass = new JFormattedTextField(formatter);
        } catch (Exception e) {
            e.printStackTrace();
        }

        psPass.add(lssPass);
        psPass.add(tssPass);
        pSignup.add(pUser);
        pSignup.add(pNombre);
        pSignup.add(pApellido);
        pSignup.add(pDNI);
        pSignup.add(pPass);
        pSignup.add(psPass);

        ImageIcon icon = new ImageIcon("src/main/java/pics/aston.jpg"); 
        Image image = icon.getImage(); 
        Image newImage = image.getScaledInstance(300, 125, Image.SCALE_SMOOTH); 
        ImageIcon scaledIcon = new ImageIcon(newImage); 
        JLabel imageLabel = new JLabel(scaledIcon);
        JPanel imagePanel = new JPanel(new FlowLayout());
        imagePanel.setBackground(Color.WHITE);
        imagePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0)); 
        imagePanel.add(imageLabel);

        JPanel dataPanel = new JPanel(new BorderLayout());
        dataPanel.add(pSignup, BorderLayout.NORTH);
        dataPanel.add(imagePanel, BorderLayout.CENTER);

        pCheckbox.add(acceptTermsCheckbox);
        pSignup.add(pCheckbox);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.WHITE); 
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); 

        bSignup.setPreferredSize(new Dimension(120, 40)); 
        bSignup.setBackground(new Color(0, 93, 232)); 
        bSignup.setForeground(Color.WHITE); 
        bSignup.setFocusPainted(false); 
        bSignup.setBorderPainted(false); 
        bSignup.addActionListener(this::registerAction);

        bSignup.setEnabled(false);
        acceptTermsCheckbox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    bSignup.setEnabled(true);
                } else {
                    bSignup.setEnabled(false);
                }
            }
        });

        buttonPanel.add(bSignup);

        pPrincipal.add(dataPanel, BorderLayout.CENTER);
        pPrincipal.add(buttonPanel, BorderLayout.SOUTH);

        this.add(pPrincipal);
        this.setVisible(true);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                closeWindow();
            }
        });
    }

    public static RegisterView getInstance() {
        if (RegisterView.instance == null) {
            RegisterView.instance = new RegisterView();
        }
        return RegisterView.instance;
    }

    public void closeWindow() {
        this.dispose();
        RegisterView.instance = null;
    }

    private void registerAction(ActionEvent e) {
        if (Arrays.asList(tsMail, tsPass, tssPass, tsNombre, tsApellido, tsDNI)
                .stream().anyMatch(component -> component.getText().isEmpty())) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int option = JOptionPane.showConfirmDialog(this, "Are you sure you want to sign up?", "Confirm Sign Up", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            String name = tsNombre.getText();
            String surname = tsApellido.getText();
            String email = tsMail.getText();
            String password = new String(tsPass.getPassword());
            String licenseNumber = tssPass.getText();
            String DNI = tsDNI.getText();
            ClientUserController.createUser(name, surname, email, password, licenseNumber, DNI);
            logger.info("Register button clicked");
            closeWindow();
        }
    }
}