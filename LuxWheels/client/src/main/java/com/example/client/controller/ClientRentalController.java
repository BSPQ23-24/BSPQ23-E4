package com.example.client.controller;

import java.util.List;

import com.example.client.model.CarModel;
import com.example.client.model.RentalModel;
import com.example.client.service.RentalService;

public class ClientRentalController {
    private static RentalService rentalService = new RentalService();

    public static List<RentalModel> getAllRentals() {
        return rentalService.getAllRentals();
    }

    public static List<CarModel> getAllRentedCarsByUserEmail(String email) {
        return rentalService.getAllRentedCarsByUserEmail(email);
    }
}
