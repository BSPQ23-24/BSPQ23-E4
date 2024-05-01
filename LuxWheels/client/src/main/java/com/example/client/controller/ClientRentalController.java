package com.example.client.controller;

import java.util.List;

import com.example.client.model.RentalModel;
import com.example.client.service.RentalService;

public class ClientRentalController {
	private static RentalService carService = new RentalService();
    public static List<RentalModel> getAllCars() {
        return carService.getAllRentals();
    }

}
