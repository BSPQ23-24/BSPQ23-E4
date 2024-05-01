package com.example.client.service;

import com.example.client.model.CarModel;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;

import com.fasterxml.jackson.databind.ObjectMapper;


public class CarService {
    private HttpClient client;
    private final String baseURL = "http://localhost:8080/api/cars";
    public CarService() {
        this.client = HttpClient.newHttpClient();
    }

    public String createCar(CarModel car) {
        String carJson = convertCarToJson(car);

        System.out.println(carJson);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseURL + "/create"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(carJson))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    private String convertCarToJson(CarModel car) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(car);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
