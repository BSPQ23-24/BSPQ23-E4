package com.example.client.service;

import com.example.client.model.CarModel;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.List;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CarService {
    private HttpClient client;
    private final String baseURL = "http://localhost:8080/api/cars";
    private ObjectMapper mapper;

    public CarService() {
        this.client = HttpClient.newHttpClient();
        this.mapper = new ObjectMapper();
    }

    public List<CarModel> getAllCars() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseURL))
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

    public CarModel getCarByLicensePlate(Integer licensePlate){

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseURL + "/" + licensePlate))
                .header("Content-Type", "application/json")
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                return mapper.readValue(response.body(), new TypeReference<CarModel>() {});
            } else {
                System.err.println("Error: " + response);
                return new CarModel();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new CarModel();
        }
    }

	public String deleteCar(int licensePlate) {

		System.out.println(URI.create(baseURL + licensePlate));
		System.out.println(URI.create(baseURL + "/" + licensePlate));

        HttpRequest request = HttpRequest.newBuilder()
        		.uri(URI.create(baseURL + "/" + licensePlate))
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

    public String updateCar(CarModel car) {
    	System.out.println(car.getLicensePlate());
        String carJson = convertCarToJson(car);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseURL + "/" + car.getLicensePlate()))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(carJson))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
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
            String json = mapper.writeValueAsString(car);
            System.out.println("Generated JSON: " + json);
            return json;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
