package com.example.client.controller;

import com.example.client.model.UserModel;
import com.example.client.service.UserService;

public class ClientCarController {

    private static UserService userService = new UserService();

    public static void createUser(String name, String surname, String email, String password, String birthdate, String licensenumber ) {
        UserModel user = new UserModel();
        user.setName(name);
        user.setSurname(surname);
        user.setEmail(email);
        user.setPassword(password);
        user.setBirthdate(birthdate);
        user.setLicensenumber(licensenumber);

        userService.createUser(user);
    }
    public static boolean loginUser(String name, String email, String password) {
        CarModel
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);

        String u = userService.loginUser(user);
        return !u.equals("{}");

    }
}
