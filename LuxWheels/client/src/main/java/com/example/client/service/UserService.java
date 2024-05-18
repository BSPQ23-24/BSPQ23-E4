package com.example.client.service;

import com.example.client.model.UserModel;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;

import com.fasterxml.jackson.databind.ObjectMapper;


public class UserService {
    private HttpClient client;
    private final String baseURL = "http://localhost:8080/api/users";
    public UserService() {
        this.client = HttpClient.newHttpClient();
    }

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

    private String convertUserToJson(UserModel user) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(user);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public String updateUser(UserModel user) {
    	System.out.println(user);
        String jsonUser = convertUserToJson(user);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseURL + "/" + user.getId())) // Suponiendo que user.getId() devuelve el ID del usuario
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
