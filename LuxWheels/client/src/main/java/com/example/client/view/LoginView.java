package com.example.client.view;

import com.example.client.controller.ClientUserController;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

@Getter
@Setter
public class LoginView extends JPanel {

    public LoginView() {
    	this.setSize(200,100);
        this.initUI();
    }
    
	private static LoginView instanceLogin;
	private static RegisterView instanceSignup;
    
    protected JPanel pLogin = new JPanel(new GridLayout(5,1));
	protected JPanel pPrincipal = new JPanel(new BorderLayout());
	protected JPanel plUser = new JPanel(new FlowLayout());
	protected JPanel plName = new JPanel(new FlowLayout());
	protected JPanel plb = new JPanel(new FlowLayout());
	protected JPanel plPass = new JPanel(new FlowLayout());
	protected JPanel pSignup = new JPanel(new FlowLayout());
	protected JPanel pLogo = new JPanel(new FlowLayout());
	
	protected JLabel lName = new JLabel("Name: ");
	protected JLabel lMail = new JLabel("Mail: ");
	protected JLabel lPass = new JLabel("Password: ");
	protected JTextField tName = new JTextField(15);
	protected JTextField tMail = new JTextField(15);
	protected JPasswordField tPass = new JPasswordField(15);
	protected JButton submitButton = new JButton("Login");
	protected JLabel lError = new JLabel("");
	protected JLabel lSignup = new JLabel("Don't have an account? Register here.");
	protected JButton bSignup = new JButton("Create account");

    private void initUI() {
		this.setSize(200, 100);
		this.setLayout(new FlowLayout());
		

		plName.add(lName);
		plName.add(tName);
		plUser.add(lMail);
		plUser.add(tMail);
		plPass.add(lPass);
		plPass.add(tPass);
		pLogin.add(plName);
		pLogin.add(plUser);
		pLogin.add(plPass);
		plb.add(submitButton);
		plb.add(lError);
		pLogin.add(plb);
		lSignup.setForeground(new Color(0, 93, 232));
		pSignup.add(lSignup);
		bSignup.setForeground(new Color(0, 93, 232));
		pSignup.add(bSignup);
		pLogin.add(pSignup);
		pPrincipal.add(pLogin, BorderLayout.CENTER);
		
		this.add(pPrincipal);
		
		this.setVisible(true);
		
		bSignup.addActionListener(this::Register);
        submitButton.addActionListener(this::submitAction);
    }

    private void submitAction(ActionEvent actionEvent) {
        String name = tName.getText();
        String email = tMail.getText();
        String password = new String(tPass.getPassword());
        ClientUserController.createUser(name, email, password);
    }
    private void Register(ActionEvent actionEvent) {
    	LoginView.instanceSignup = RegisterView.getInstance();
		LoginView.instanceSignup.setVisible(true);
    	
    }
}
