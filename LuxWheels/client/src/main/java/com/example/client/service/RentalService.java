package com.example.client.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.List;

import com.example.client.helper.ClientRentalRequest;
import com.example.client.model.CarModel;
import com.example.client.model.RentalModel;
import com.example.client.model.UserModel;
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

    public List<RentalModel> getRentalsByLicensePlate(Integer licensePlate) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseURL + "/car/" + licensePlate))
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

    public String createRental(ClientRentalRequest rentalRequest) {
        String rentalJson = convertRentalRequestToJson(rentalRequest);

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

    private String convertRentalRequestToJson(ClientRentalRequest rental) {
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
