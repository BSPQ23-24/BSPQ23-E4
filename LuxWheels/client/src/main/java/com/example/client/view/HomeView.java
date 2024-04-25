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


import javax.swing.*;
import java.awt.*;
import javax.swing.text.MaskFormatter;

import com.example.client.controller.ClientUserController;

import java.awt.event.ActionEvent;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import java.util.Arrays;

public class HomeView extends JFrame {

    private static HomeView instance;

    private static final long serialVersionUID = 1L;

    protected JCheckBox acceptTermsCheckbox = new JCheckBox("I accept the terms and conditions");

    private HomeView() {
        this.setTitle("Homepage - LuxWheels");
        this.setSize(700, 550);
        this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(null); 
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