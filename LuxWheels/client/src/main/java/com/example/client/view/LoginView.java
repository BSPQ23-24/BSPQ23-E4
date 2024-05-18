package com.example.client.view;

import com.example.client.controller.ClientUserController;
import com.example.client.model.UserSession;

import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;

@Getter
@Setter
public class LoginView extends JPanel {
	private static final Logger logger = LogManager.getLogger(LoginView.class);

    private JTextField nameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JLabel titleLabel;
    private JLabel nameLabel;
    private JLabel emailLabel;
    private JLabel passwordLabel;
    private JButton loginButton;
    private JLabel signupLabel;
    private JButton signupButton;
    private JComboBox<String> languageComboBox;
    private ResourceBundle messages;
    private MainFrame mainFrame;

    public LoginView(ResourceBundle messages) {
        this.messages = messages;
        this.initUI();
    }
    
    public LoginView(MainFrame mainFrame) {
        this.initUI();
        this.mainFrame = mainFrame;
    }
    private void initUI() {
        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240));

        // Language selection
        JPanel languagePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        String[] languages = {"en","es", "de", "eu"};
        languageComboBox = new JComboBox<>(languages);
        languageComboBox.setSelectedItem("en");
        languageComboBox.addActionListener(new LanguageChangeListener());
        languagePanel.add(new JLabel(messages.getString("select.language")));
        languagePanel.add(languageComboBox);
        add(languagePanel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(4, 1, 10, 10)); // Increased rows to accommodate the image
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Reduce space around the formPanel
        formPanel.setBackground(Color.WHITE);

        titleLabel = new JLabel(messages.getString("title.welcome"));
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        formPanel.add(titleLabel);

        nameField = new JTextField(15);
        emailField = new JTextField(15);
        passwordField = new JPasswordField(15);

        nameLabel = new JLabel(messages.getString("label.name"));
        emailLabel = new JLabel(messages.getString("label.email"));
        passwordLabel = new JLabel(messages.getString("label.password"));

        JPanel inputPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        inputPanel.setBackground(Color.WHITE);
        inputPanel.setBorder(BorderFactory.createEmptyBorder(1, 0, 1, 0)); // Reduce space between input fields

        inputPanel.add(createInputPanel(nameLabel, nameField));
        inputPanel.add(createInputPanel(emailLabel, emailField));
        inputPanel.add(createInputPanel(passwordLabel, passwordField));

        formPanel.add(inputPanel); 

        ImageIcon icon = new ImageIcon(getClass().getResource("/pics/audi.jpg"));
        Image image = icon.getImage();
        Image newImage = image.getScaledInstance(400, 125, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(newImage);
        JLabel photoLabel = new JLabel(scaledIcon);
        JPanel photoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        photoPanel.setBackground(Color.WHITE);
        photoPanel.add(photoLabel);
        formPanel.add(photoPanel); // Add image after the input fields

        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setBackground(Color.WHITE);

        loginButton = new JButton(messages.getString("button.login"));
        loginButton.addActionListener(this::submitAction);

        signupLabel = new JLabel(messages.getString("label.signup"));
        signupButton = new JButton(messages.getString("button.signup"));
        signupButton.addActionListener(this::registerAction);

        JPanel buttonContainerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonContainerPanel.setBackground(Color.WHITE);
        buttonContainerPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // Reduce space between buttons and bottom
        buttonContainerPanel.add(loginButton);
        buttonContainerPanel.add(signupLabel);
        buttonContainerPanel.add(signupButton);

        buttonPanel.add(buttonContainerPanel, BorderLayout.SOUTH);

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        
        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String selectedLanguage = (String) languageComboBox.getSelectedItem();
                Locale locale = new Locale(selectedLanguage);


                RegisterView registerView = new RegisterView(locale,messages);
                registerView.setVisible(true);

                closeWindow(signupButton);
            }
        });
    }
    
    private JPanel createInputPanel(JLabel label, JTextField field) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setBackground(Color.WHITE);
        panel.add(label);
        panel.add(field);
        return panel;
    }
    
    
    private void closeWindow(Component component) {
        Window window = SwingUtilities.getWindowAncestor(component);
        if (window != null) {
            window.dispose();
        }
    }

    private void submitAction(ActionEvent actionEvent) {
        String name = nameField.getText();
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());
        boolean success = ClientUserController.loginUser(name, email, password);

        if (success) {
            UserSession userSession = UserSession.getInstance();
            userSession.setEmail(email);
            userSession.setPassword(password);
            this.mainFrame.onLoginSuccess();      
        } else {
            JOptionPane.showMessageDialog(this, messages.getString("error.login"), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    

    private void registerAction(ActionEvent actionEvent) {
    	logger.info("Register button clicked");
    	
    }

    private class LanguageChangeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String selectedLanguage = (String) languageComboBox.getSelectedItem();
            Locale newLocale = new Locale(selectedLanguage);
            messages = ResourceBundle.getBundle("messages/messages", newLocale);
            updateTexts();
            
        }
    }

    private void updateTexts() {
    	
        titleLabel.setText(messages.getString("title.welcome"));
        nameLabel.setText(messages.getString("label.name"));
        emailLabel.setText(messages.getString("label.email"));
        passwordLabel.setText(messages.getString("label.password"));
        loginButton.setText(messages.getString("button.login"));
        signupLabel.setText(messages.getString("label.signup"));
        signupButton.setText(messages.getString("button.signup"));
    }

    public static void main(String[] args) {
        Locale locale = new Locale("en");
        ResourceBundle messages = ResourceBundle.getBundle("messages/messages", locale);
        LoginView loginView = new LoginView(messages);
        JFrame frame = new JFrame(messages.getString("title.welcome"));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(loginView);
        frame.pack(); 
        frame.setLocationRelativeTo(null); 
        frame.setVisible(true);
    }

}
