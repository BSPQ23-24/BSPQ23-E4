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
/**
 * The RentalService class provides methods to interact with the rental-related backend services.
 * It includes methods to perform CRUD operations on RentalModel objects and related operations.
 */
public class RentalService {

    /** The HTTP client used to send requests. */
    private HttpClient client;

    /** The base URL for the rental-related API endpoints. */
    private final String baseURL = "http://localhost:8080/api/rentals";

    /** The ObjectMapper instance for JSON serialization and deserialization. */
    private ObjectMapper mapper;

    /**
     * Constructs a new RentalService instance.
     * Initializes the HTTP client and the ObjectMapper, registering JavaTimeModule.
     */
    public RentalService() {
        this.client = HttpClient.newHttpClient();
        this.mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
    }

    /**
     * Retrieves a list of all rentals.
     *
     * @return a list of RentalModel objects representing all rentals, or an empty list if an error occurs.
     */
    public List<RentalModel> getAllRentals() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseURL))
                .header("Content-Type", "application/json")
                .GET()
                .build();
        return sendRequest(request, new TypeReference<List<RentalModel>>() {});
    }
/**
 * Retrieves a list of all rented cars by the specified user's email.
 *
 * @param email the email of the user.
 * * @return a list of CarModel objects representing the cars rented by the user, or an empty list if an error occurs.
 **/
    public List<CarModel> getAllRentedCarsByUserEmail(String email) {
        String targetUrl = baseURL + "/user/" + email;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(targetUrl))
                .header("Content-Type", "application/json")
                .GET()
                .build();
        return sendRequest(request, new TypeReference<List<CarModel>>() {});
    }
    /**
     * Sends an HTTP request and processes the response.
     *
     * @param <T> the type of the response data.
     * @param request the HTTP request to send.
     * @param typeReference a TypeReference for the expected response data type.
     * @return a list of objects of the specified type, or an empty list if an error occurs.
     */
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
    /**
     * Retrieves a list of cars rented by the specified user.
     *
     * @param u the UserModel representing the user.
     * @return a list of CarModel objects representing the cars rented by the user, or an empty list if an error occurs.
     */
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
    /**
     * Retrieves a list of rentals by the specified car's license plate.
     *
     * @param licensePlate the license plate of the car.
     * @return a list of RentalModel objects representing the rentals of the car, or an empty list if an error occurs.
     */
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
    /**
     * Creates a new rental.
     *
     * @param rentalRequest the ClientRentalRequest containing the rental details.
     * @return the response body as a string, or null if an error occurs.
     */
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
    /**
     * Converts a RentalModel object to its JSON representation.
     *
     * @param rental the RentalModel object to convert.
     * @return the JSON representation of the rental, or null if an error occurs.
     */
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
    /**
     * Converts a ClientRentalRequest object to its JSON representation.
     *
     * @param rental the ClientRentalRequest object to convert.
     * @return the JSON representation of the rental request, or null if an error occurs.
     */
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
