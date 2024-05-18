package com.example.client.controller;

import java.time.LocalDate;
import java.util.List;

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

    public static void createRental(CarModel car, LocalDate startDate, LocalDate endDate, Double price) {

        RentalModel rentalModel = new RentalModel();
        //rentalModel.setRentalID();
        rentalModel.setCar(car);
        rentalModel.setStartDate(startDate.toString());
        rentalModel.setEndDate(endDate.toString());
        rentalModel.setPrice(price);
        rentalModel.setCreationDate(LocalDate.now().toString());
        rentalModel.setUser(ClientUserController.loggedUser);

        rentalService.createRental(rentalModel);
    }
}
