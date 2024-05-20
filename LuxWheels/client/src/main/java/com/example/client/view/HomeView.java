package com.example.client.view;

import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.Locale;
import java.util.ResourceBundle;

public class HomeView extends JFrame {

    private static HomeView instance;
    private static final long serialVersionUID = 1L;

    private JButton btRegisterCar;
    private JPanel buttonPanel;
    private Locale locale;
    private ResourceBundle messages;

    private HomeView(Locale locale, ResourceBundle messages) {
        this.locale = locale;
        this.messages = messages;
        setTitle(messages.getString("title.homepage"));
        setSize(700, 550);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        initUI();
    }

    private void initUI() {
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btRegisterCar = new JButton(messages.getString("button.registerYourCar"));
        btRegisterCar.addActionListener(this::registerCar);
        buttonPanel.add(btRegisterCar);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void registerCar(ActionEvent actionEvent) {
        RegisterCarView registerCarView = RegisterCarView.getInstance(/*MainFrame, */locale, messages);
        registerCarView.setVisible(true);
    }

    public static HomeView getInstance(Locale locale, ResourceBundle messages) {
        if (HomeView.instance == null) {
            HomeView.instance = new HomeView(locale, messages);
        }
        return HomeView.instance;
    }

    public void closeWindow() {
        this.dispose();
        HomeView.instance = null;
    }

}
