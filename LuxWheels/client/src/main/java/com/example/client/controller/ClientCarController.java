package com.example.client.controller;

import com.example.client.model.CarModel;
import com.example.client.service.CarService;

import java.util.List;

public class ClientCarController {
    private static CarService carService = new CarService();
    public static List<CarModel> getAllCars() {
        return carService.getAllCars();
    }
}
