package com.example.client.controller;

import java.time.LocalDate;
import java.util.List;

import com.example.client.helper.ClientRentalRequest;
import com.example.client.model.CarModel;
import com.example.client.model.RentalModel;
import com.example.client.model.UserModel;
import com.example.client.service.RentalService;

public class ClientRentalController {

    private static RentalService rentalService = new RentalService();

    public static List<RentalModel> getAllRentals() {
        return rentalService.getAllRentals();
    }
    public static List<RentalModel> getRentalsByLicensePlate(Integer licensePlate) { return rentalService.getRentalsByLicensePlate(licensePlate); }
    public static List<CarModel> getRentedCarsByUser() { return rentalService.getCarsRentedByUser( ClientUserController.loggedUser ); }

    public static List<CarModel> getAllRentedCarsByUserEmail(String email) {
        return rentalService.getAllRentedCarsByUserEmail(email);
    }

    public static void createRental(CarModel car, LocalDate startDate, LocalDate endDate, Double price) {
        ClientRentalRequest request = new ClientRentalRequest();
        request.setCarId(car.getLicensePlate());
        request.setStartDate(startDate.toString());
        request.setEndDate(endDate.toString());
        request.setPrice(price);
        request.setCreationDate(LocalDate.now().toString());
        request.setUserId(ClientUserController.loggedUser.getId());

        rentalService.createRental(request);

    }
}
