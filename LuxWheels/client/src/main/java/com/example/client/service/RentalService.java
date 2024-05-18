package com.example.client.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.List;

import com.example.client.model.CarModel;
import com.example.client.model.RentalModel;
import com.example.client.model.UserModel;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RentalService {
    private HttpClient client;
    private final String baseURL = "http://localhost:8080/api/rentals";
    private ObjectMapper mapper;

    public RentalService() {
        this.client = HttpClient.newHttpClient();
        this.mapper = new ObjectMapper();
    }

    public List<RentalModel> getAllRentals() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseURL))
                .header("Content-Type", "application/json")
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                return mapper.readValue(response.body(), new TypeReference<List<RentalModel>>() {});
            } else {
                System.err.println("Error: " + response);
                return Collections.emptyList();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public List<CarModel> getCarsRentedByUser(UserModel u) {
        // Assuming UserModel has a getUserId() method that returns a string or similar identifier.
        Integer userId = u.getId();
        String requestURI = baseURL + "/getCar?userId=" + Integer.valueOf(userId);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(requestURI))
                .header("Content-Type", "application/json")
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                return mapper.readValue(response.body(), new TypeReference<List<CarModel>>() {});
            } else {
                System.err.println("Error: " + response);
                return Collections.emptyList();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public String createRental(RentalModel rental) {
        String rentalJson = convertRentalToJson(rental);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseURL + "/create"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(rentalJson))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String convertRentalToJson(RentalModel rental) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String json = mapper.writeValueAsString(rental);
            System.out.println("Generated JSON: " + json);
            return json;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

