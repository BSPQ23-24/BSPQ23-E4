package com.example.client.controller;

import com.example.client.model.UserModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;

import static org.junit.jupiter.api.Assertions.*;

public class ClientUserControllerIntegrationTests {

    private static HttpClient client;
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeAll
    public static void setup() {
        client = HttpClient.newHttpClient();
    }

    @AfterAll
    public static void teardown() {
        // Any cleanup if necessary
    }

    @Test
    public void testCreateUser() throws Exception {
        UserModel user = new UserModel();
        user.setName("John");
        user.setSurname("Doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("password123");
        user.setBirthdate("1990-01-01");
        user.setLicensenumber("AB123456");

        String userJson = objectMapper.writeValueAsString(user);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/users/register"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(userJson))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());

        UserModel createdUser = objectMapper.readValue(response.body(), UserModel.class);
        assertNotNull(createdUser);
        assertEquals("John", createdUser.getName());

        deleteUser(createdUser);
    }

    @Test
    public void testLoginUser() throws Exception {
        UserModel user = new UserModel();
        user.setName("John");
        user.setEmail("john.doe@example.com");
        user.setPassword("password123");

        String userJson = objectMapper.writeValueAsString(user);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/users/login"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(userJson))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());

        UserModel loggedInUser = objectMapper.readValue(response.body(), UserModel.class);
        assertNotNull(loggedInUser);
        assertEquals("John", loggedInUser.getName());

        deleteUser(loggedInUser);
    }

    private void deleteUser(UserModel user) throws Exception {
        HttpRequest deleteRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/users/" + user.getId()))
                .header("Content-Type", "application/json")
                .DELETE()
                .build();

        HttpResponse<String> deleteResponse = client.send(deleteRequest, HttpResponse.BodyHandlers.ofString());
        assertEquals(204, deleteResponse.statusCode());
    }
}

