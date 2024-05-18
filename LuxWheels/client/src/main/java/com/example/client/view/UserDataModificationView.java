package com.example.client.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.text.MaskFormatter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.example.client.controller.ClientRentalController;
import com.example.client.controller.ClientUserController;
import com.example.client.model.CarModel;
import com.example.client.model.RentalModel;
import com.example.client.model.UserModel;
import com.example.client.model.UserSession;

public class UserDataModificationView extends JPanel{
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
	 protected JTextField tsPass = new JTextField(15);
	 protected JTextField tssPass = new JTextField(15);
	 protected JTextField tsNombre = new JTextField(15);
	 protected JTextField tsApellido = new JTextField(15);
	 protected JTextField tsDNI = new JTextField(15);
	 protected JButton bSignup = new JButton("Modify");
	 protected JLabel lsError = new JLabel("");
	 public UserModel user;
	 public MainFrame mainframe;


    public UserDataModificationView(UserModel user, MainFrame mainFrame) {
        setLayout(new BorderLayout());
        initializeUI();
        this.user = user;
        this.mainframe = mainFrame;
        
        tsMail.setText(user.getEmail());
   	    tsPass.setText(user.getPassword());
   	    tsNombre.setText(user.getName());
   	    tsApellido.setText(user.getSurname());
   	    tsDNI.setText(user.getLicensenumber());
   	    tssPass.setText(user.getBirthdate());

        
        lsMail.setFont(new Font("Arial", Font.BOLD, 20));
   	    lsPass.setFont(new Font("Arial", Font.BOLD, 20));
   	    lssPass.setFont(new Font("Arial", Font.BOLD, 20));
   	    lsNombre.setFont(new Font("Arial", Font.BOLD, 20));
   	    lsApellido.setFont(new Font("Arial", Font.BOLD, 20));
   	    lsDNI.setFont(new Font("Arial", Font.BOLD, 20));


   	    
   	  
    }

    private void initializeUI() {
    	JLabel headingLabel = new JLabel("Modify your user account");
    	headingLabel.setFont(new Font("Arial", Font.BOLD, 25));
    	headingLabel.setHorizontalAlignment(JLabel.CENTER);
    	add(headingLabel, BorderLayout.NORTH);

    	JPanel formPanel = new JPanel(new GridLayout(6, 1, 10, 10));
    	formPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

    	JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    	userPanel.add(lsMail);
    	userPanel.add(tsMail);

    	JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    	namePanel.add(lsNombre);
    	namePanel.add(tsNombre);
    	
    	JPanel surnamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    	surnamePanel.add(lsApellido);
    	surnamePanel.add(tsApellido);
    	
    	JPanel DNIPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    	DNIPanel.add(lsDNI);
    	DNIPanel.add(tsDNI);
    	
    	JPanel birthdatePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    	birthdatePanel.add(lssPass);
    	birthdatePanel.add(tssPass);
    	
    	JPanel passwordPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    	passwordPanel.add(lsPass);
    	passwordPanel.add(tsPass);

    	// Similarmente, crea paneles para los otros campos del usuario

    	JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    	buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

    	JButton modifyButton = new JButton("Modify");
    	modifyButton.setPreferredSize(new Dimension(120, 40));
    	modifyButton.setBackground(new Color(34, 177, 76));
    	modifyButton.setForeground(Color.WHITE);
    	modifyButton.setFocusPainted(false);
    	modifyButton.setBorderPainted(false);
    	modifyButton.addActionListener(e -> this.modifyUser());
    	
    	JButton deleteButton = new JButton("Delete");
    	deleteButton.setPreferredSize(new Dimension(120, 40));
    	deleteButton.setBackground(new Color(255, 0, 0));
    	deleteButton.setForeground(Color.WHITE);
    	deleteButton.setFocusPainted(false);
    	deleteButton.setBorderPainted(false);
    	deleteButton.addActionListener(e -> this.deleteUser());

    	buttonPanel.add(modifyButton);
    	buttonPanel.add(deleteButton);

    	formPanel.add(userPanel);
    	formPanel.add(namePanel);
    	formPanel.add(surnamePanel);
    	formPanel.add(DNIPanel);
    	formPanel.add(birthdatePanel);
    	formPanel.add(passwordPanel);
    	// Añade los otros paneles de campos de usuario aquí

    	JPanel mainPanel = new JPanel(new BorderLayout());
    	mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    	mainPanel.setBackground(Color.WHITE);

    	mainPanel.add(formPanel, BorderLayout.CENTER);
    	mainPanel.add(buttonPanel, BorderLayout.SOUTH);

    	add(mainPanel);
    	setVisible(true);


    }

    private void modifyUser() {
    	this.user.setEmail(tsMail.getText());
    	this.user.setName(tsNombre.getText());
    	this.user.setSurname(tsApellido.getText());
    	this.user.setLicensenumber(tsDNI.getText());
    	this.user.setPassword(tsPass.getText());
    	this.user.setBirthdate(tssPass.getText());
    	
    	ClientUserController.updateUser(this.user);
    }
    
    private void deleteUser() {
    	this.mainframe.logoutForUserDeleting();
    	System.out.println(this.user.getId());
    	ClientUserController.deleteUser(this.user.getId());
    }
}