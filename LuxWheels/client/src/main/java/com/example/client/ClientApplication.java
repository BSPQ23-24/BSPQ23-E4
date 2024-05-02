package com.example.client;

import com.example.client.view.LoginView;
import com.example.client.view.MainFrame;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import javax.swing.*;

@SpringBootApplication
public class ClientApplication {

    public static void main(String[] args) {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(ClientApplication.class);
        builder.headless(false).run(args);
        SwingUtilities.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }

}