package com.example.client.controller;

import java.time.LocalDate;
import java.util.List;

import com.example.client.helper.ClientRentalRequest;
import com.example.client.model.CarModel;
import com.example.client.model.RentalModel;
import com.example.client.model.UserModel;
import com.example.client.service.RentalService;
/**
 * The ClientRentalController class provides methods to interact with rental services.
 * It includes methods to perform operations related to rentals and rented cars.
 */
public class ClientRentalController {

    /** The RentalService instance to handle rental operations. */
    private static RentalService rentalService = new RentalService();

    /**
     * Retrieves a list of all rentals.
     *
     * @return a list of RentalModel objects representing all rentals.
     */
    public static List<RentalModel> getAllRentals() {
        return rentalService.getAllRentals();
    }

    /**
     * Retrieves a list of rentals for a specific car by its license plate.
     *
     * @param licensePlate the license plate of the car.
     * @return a list of RentalModel objects representing the rentals of the car.
     */
    public static List<RentalModel> getRentalsByLicensePlate(Integer licensePlate) {
        return rentalService.getRentalsByLicensePlate(licensePlate);
    }

    /**
     * Retrieves a list of cars rented by the currently logged-in user.
     *
     * @return a list of CarModel objects representing the cars rented by the logged-in user.
     */
    public static List<CarModel> getRentedCarsByUser() {
        return rentalService.getCarsRentedByUser(ClientUserController.loggedUser);
    }

    /**
     * Retrieves a list of cars rented by a user specified by their email.
     *
     * @param email the email of the user.
     * @return a list of CarModel objects representing the cars rented by the user.
     */
    public static List<CarModel> getAllRentedCarsByUserEmail(String email) {
        return rentalService.getAllRentedCarsByUserEmail(email);
    }

    /**
     * Creates a new rental for a car.
     *
     * @param car the CarModel object representing the car to be rented.
     * @param startDate the start date of the rental.
     * @param endDate the end date of the rental.
     * @param price the price of the rental.
     */
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