package com.example.client.controller;

import com.example.client.model.UserModel;
import com.example.client.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ClientUserController {

    private static UserService userService = new UserService();
    public static UserModel loggedUser;

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

    public static UserModel parseUserModel(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            UserModel userModel = objectMapper.readValue(json, UserModel.class);
            return userModel;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean loginUser(String name, String email, String password) {
        UserModel user = new UserModel();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);

        String u = userService.loginUser(user);
        loggedUser = parseUserModel(u);
        return !u.equals("{}");
        
    }
    
    public static void updateUser(UserModel user) {
    	userService.updateUser(user);
    }
    
    public static void deleteUser(int userId) {
    	userService.deleteUser(userId);
    }
}
