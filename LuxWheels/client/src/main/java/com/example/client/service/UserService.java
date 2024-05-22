package com.example.client.service;

import com.example.client.model.UserModel;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * UserService class provides methods to interact with the user-related API endpoints.
 * It includes operations to create, login, update, and delete users.
 */
public class UserService {

    /** HttpClient for sending HTTP requests. */
    private HttpClient client;

    /** Base URL for the user-related API endpoints. */
    private final String baseURL = "http://localhost:8080/api/users";

    /**
     * Constructor for the UserService class.
     * Initializes the HttpClient.
     */
    public UserService() {
        this.client = HttpClient.newHttpClient();
    }

    /**
     * Creates a new user.
     *
     * @param user the UserModel object containing user details.
     * @return the response body from the API.
     */
    public String createUser(UserModel user) {
        String userJson = convertUserToJson(user);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseURL + "/register"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(userJson))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Logs in a user.
     *
     * @param user the UserModel object containing login details.
     * @return the response body from the API.
     */
    public String loginUser(UserModel user) {
        String userJson = convertUserToJson(user);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseURL + "/login"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(userJson))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
            return response.body();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Converts a UserModel object to a JSON string.
     *
     * @param user the UserModel object to convert.
     * @return the JSON string representation of the user.
     */
    private String convertUserToJson(UserModel user) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(user);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Updates user information.
     *
     * @param user the UserModel object containing updated user details.
     * @return the response body from the API.
     */
    public String updateUser(UserModel user) {
        System.out.println(user);
        String jsonUser = convertUserToJson(user);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseURL + "/" + user.getId()))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(jsonUser))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Deletes a user by their ID.
     *
     * @param userID the ID of the user to delete.
     * @return the response body from the API.
     */
    public String deleteUser(int userID) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseURL + "/" + userID))
                .header("Content-Type", "application/json")
                .DELETE()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}