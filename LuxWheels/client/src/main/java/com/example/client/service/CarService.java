package com.example.client.service;
import com.example.client.model.CarModel;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class CarService {
	
    private final String baseURL =  "http://localhost:8080/api/cars/"; 
    private HttpClient client;
	
	public String deleteCar(int licensePlate) {
		client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
        		.uri(URI.create(baseURL + licensePlate))
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
        String carJson = convertCarToJson(car);
        client = HttpClient.newHttpClient();
        
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseURL + car.getLicensePlate()))
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

