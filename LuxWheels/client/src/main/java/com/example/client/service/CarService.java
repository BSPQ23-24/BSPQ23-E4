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
/**
 * The CarService class provides methods to interact with the car-related backend services.
 * It includes methods to perform CRUD operations on CarModel objects.
 */
public class CarService {

    /** The HTTP client used to send requests. */
    private HttpClient client;

    /** The base URL for the car-related API endpoints. */
    private final String baseURL = "http://localhost:8080/api/cars";

    /** The ObjectMapper instance for JSON serialization and deserialization. */
    private ObjectMapper mapper;

    /**
     * Constructs a new CarService instance.
     * Initializes the HTTP client and the ObjectMapper.
     */
    public CarService() {
        this.client = HttpClient.newHttpClient();
        this.mapper = new ObjectMapper();
    }

    /**
     * Retrieves a list of all cars.
     *
     * @return a list of CarModel objects representing all cars, or an empty list if an error occurs.
     */
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

    /**
     * Retrieves a car by its license plate number.
     *
     * @param licensePlate the license plate number of the car to retrieve.
     * @return the CarModel object corresponding to the provided license plate number, or a new CarModel if an error occurs.
     */
    public CarModel getCarByLicensePlate(Integer licensePlate) {
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

    /**
     * Deletes a car by its license plate number.
     *
     * @param licensePlate the license plate number of the car to delete.
     * @return a string response from the server, or null if an error occurs.
     */
    public String deleteCar(int licensePlate) {
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

    /**
     * Updates the details of an existing car.
     *
     * @param car the CarModel object containing updated details of the car.
     * @return a string response from the server, or null if an error occurs.
     */
    public String updateCar(CarModel car) {
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

    /**
     * Creates a new car with the provided details.
     *
     * @param car the CarModel object containing details of the new car.
     * @return a string response from the server, or null if an error occurs.
     */
    public String createCar(CarModel car) {
        String carJson = convertCarToJson(car);

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

    /**
     * Converts a CarModel object to its JSON representation.
     *
     * @param car the CarModel object to convert.
     * @return a JSON string representation of the car, or null if an error occurs.
     */
    private String convertCarToJson(CarModel car) {
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