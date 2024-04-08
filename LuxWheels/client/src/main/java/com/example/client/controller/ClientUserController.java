package com.example.client.controller;

import com.example.client.model.UserModel;
import com.example.client.service.UserService;

public class ClientUserController {

    private static UserService userService = new UserService();

    public static void createUser(String name, String email, String password) {
        UserModel user = new UserModel();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);

        userService.createUser(user);
    }
}

