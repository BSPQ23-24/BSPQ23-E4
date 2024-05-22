package com.example.client.controller;

import com.example.client.model.UserModel;
import com.example.client.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * The ClientUserController class provides methods to interact with user services.
 * It includes methods for creating, parsing, logging in, updating, and deleting users.
 */
public class ClientUserController {

    /** The UserService instance to handle user operations. */
    private static UserService userService = new UserService();

    /** The currently logged-in user. */
    public static UserModel loggedUser;

    /**
     * Creates a new user with the specified details.
     *
     * @param name the user's first name.
     * @param surname the user's last name.
     * @param email the user's email address.
     * @param password the user's password.
     * @param birthdate the user's birthdate.
     * @param licensenumber the user's driver's license number.
     */
    public static void createUser(String name, String surname, String email, String password, String birthdate, String licensenumber) {
        UserModel user = new UserModel();
        user.setName(name);
        user.setSurname(surname);
        user.setEmail(email);
        user.setPassword(password);
        user.setBirthdate(birthdate);
        user.setLicensenumber(licensenumber);

        userService.createUser(user);
    }

    /**
     * Parses a JSON string to create a UserModel object.
     *
     * @param json the JSON string representing the user.
     * @return a UserModel object created from the JSON string, or null if parsing fails.
     */
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

    /**
     * Attempts to log in a user with the specified details.
     *
     * @param name the user's name.
     * @param email the user's email address.
     * @param password the user's password.
     * @return true if the login is successful, false otherwise.
     */
    public static boolean loginUser(String name, String email, String password) {
        UserModel user = new UserModel();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);

        String u = userService.loginUser(user);
        System.out.println(u);
        if (u.equals("Invalid email or password")) {
            return false;
        } else {
            loggedUser = parseUserModel(u);
            return true;
        }
    }

    /**
     * Updates the details of an existing user.
     *
     * @param user the UserModel object containing the updated user details.
     */
    public static void updateUser(UserModel user) {
        userService.updateUser(user);
    }

    /**
     * Deletes a user with the specified user ID.
     *
     * @param userId the ID of the user to be deleted.
     */
    public static void deleteUser(int userId) {
        userService.deleteUser(userId);
    }
}