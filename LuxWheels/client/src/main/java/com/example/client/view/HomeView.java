package com.example.client.view;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.*;

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

import java.util.Arrays;

public class HomeView extends JFrame {

    private static HomeView instance;

    private static final long serialVersionUID = 1L;

    private JButton btRegisterCar;
    private JPanel buttonPanel;

    private HomeView() {
        setTitle("Homepage - LuxWheels");
        setSize(700, 550);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        initUI();
    }

    public void initUI() {
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btRegisterCar = new JButton("Register your car!");
        btRegisterCar.addActionListener(this::registerCar);

        buttonPanel.add(btRegisterCar);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void registerCar(ActionEvent actionEvent) {
        RegisterCarView registerCarView = RegisterCarView.getInstance();
        registerCarView.setVisible(true);
    }

    public static HomeView getInstance() {
        if (HomeView.instance == null) {
            HomeView.instance = new HomeView();
        }
        return HomeView.instance;
    }

    public void closeWindow() {
        this.dispose();
        HomeView.instance = null;
    }

}