package com.example.client.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.List;

import com.example.client.model.CarModel;
import com.example.client.model.RentalModel;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class RentalService {
    private HttpClient client;
    private final String baseURL = "http://localhost:8080/api/rentals";
    private ObjectMapper mapper;

    public RentalService() {
        this.client = HttpClient.newHttpClient();
        this.mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
    }

    public List<RentalModel> getAllRentals() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseURL))
                .header("Content-Type", "application/json")
                .GET()
                .build();
        return sendRequest(request, new TypeReference<List<RentalModel>>() {});
    }

    public List<CarModel> getAllRentedCarsByUserEmail(String email) {
        String targetUrl = baseURL + "/user/" + email;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(targetUrl))
                .header("Content-Type", "application/json")
                .GET()
                .build();
        return sendRequest(request, new TypeReference<List<CarModel>>() {});
    }

    private <T> List<T> sendRequest(HttpRequest request, TypeReference<List<T>> typeReference) {
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                return mapper.readValue(response.body(), typeReference);
            } else {
                System.err.println("HTTP Error: " + response.statusCode() + " - " + response.body());
                return Collections.emptyList();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
