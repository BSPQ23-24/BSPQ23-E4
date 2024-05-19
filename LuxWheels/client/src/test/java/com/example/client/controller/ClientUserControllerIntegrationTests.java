package com.example.client.controller;

import com.example.client.model.UserModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(OrderAnnotation.class)
public class ClientUserControllerIntegrationTests {

    private static HttpClient client;
    private ObjectMapper objectMapper = new ObjectMapper();
    private static UserModel user = new UserModel();

    @BeforeAll
    public static void setup() {
        client = HttpClient.newHttpClient();
        user.setName("John");
        user.setSurname("Doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("password123");
        user.setBirthdate("1990-01-01");
        user.setLicensenumber("AB123456");
    }

    @AfterAll
    public static void teardown() throws Exception {
        deleteUser(user);
    }

    //@Test
    @Order(1)
    public void testCreateUser() throws Exception {
        String userJson = new ObjectMapper().writeValueAsString(user);

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
        user.setId(createdUser.getId());
    }

    //@Test
    @Order(2)
    public void testLoginUser() throws Exception {
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
    }

    private static void deleteUser(UserModel user) throws Exception {
        if (user.getId() != null) {
            HttpRequest deleteRequest = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/api/users/" + user.getId()))
                    .header("Content-Type", "application/json")
                    .DELETE()
                    .build();

            HttpResponse<String> deleteResponse = client.send(deleteRequest, HttpResponse.BodyHandlers.ofString());
            assertEquals(204, deleteResponse.statusCode());
        }
    }
}
