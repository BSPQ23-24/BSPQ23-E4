package com.example.client.service;
import com.example.client.model.CarModel;
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
	
    private final String baseURL;
    private final HttpClient client;

    public CarService(String baseURL, HttpClient client) {
        this.baseURL = baseURL;
        this.client = client;
    }
	
	public String deleteCar(int licensePlate) {

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(baseURL + licensePlate)).header("Content-Type", "application/json").DELETE().build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}

