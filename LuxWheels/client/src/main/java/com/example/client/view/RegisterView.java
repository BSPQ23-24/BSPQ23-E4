package com.example.client.view;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class RegisterView extends JFrame {

	private static RegisterView instance;
	
	private static final long serialVersionUID = 1L;
	//	Paneles
	protected JPanel pPrincipal = new JPanel(new GridLayout(2,1));
	protected JPanel pSignup = new JPanel(new GridLayout(7,1));
	protected JPanel pUser = new JPanel(new FlowLayout());
	protected JPanel psb = new JPanel(new FlowLayout());
	protected JPanel pPass = new JPanel(new FlowLayout());
	protected JPanel psPass = new JPanel(new FlowLayout());
	protected JPanel pNombre = new JPanel(new FlowLayout());
	protected JPanel pApellido = new JPanel(new FlowLayout());
	protected JPanel pDNI = new JPanel(new FlowLayout());
	
	//	Componentes signup
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
	protected JButton bSignup = new JButton("Sign up");
	protected JLabel lsError = new JLabel("");
	
	private RegisterView() {
		
		this.setTitle("Create an account - LuxWheels");
		this.setSize(450, 350);
		this.setLayout(new FlowLayout());
		

		pSignup.setBorder(BorderFactory.createTitledBorder("Create a new account"));
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
		psPass.add(lssPass);
		psPass.add(tssPass);
		pSignup.add(pUser);
		pSignup.add(pNombre);
		pSignup.add(pApellido);
		pSignup.add(pDNI);
		pSignup.add(pPass);
		pSignup.add(psPass);
		psb.add(bSignup);
		psb.add(lsError);
		pSignup.add(psb);
		pPrincipal.add(pSignup);
		this.add(pPrincipal);
		
		this.setVisible(true);
		
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				cerrarVentana();
			}
		});
		
	}
	
	public static RegisterView getInstance() {
		if(RegisterView.instance == null) {
			RegisterView.instance = new RegisterView();
		}
		return RegisterView.instance;
	}
		
	
		public static void cerrarVentana() {
			RegisterView.instance.dispose();
			RegisterView.instance = null;
		}
	}
