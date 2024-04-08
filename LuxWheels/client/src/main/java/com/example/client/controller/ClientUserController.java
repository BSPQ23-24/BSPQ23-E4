package com.example.client.controller;

import com.example.client.model.UserModel;
import com.example.client.service.UserService;

public class ClientUserController {

    private static UserService userService = new UserService();

    public static void createUser(UserModel user) {
        String response = userService.createUser(user);
    }
}

