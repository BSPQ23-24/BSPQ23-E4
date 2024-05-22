package com.example.server.controller;

import com.example.server.entity.Car;
import com.example.server.entity.Rental;
import com.example.server.helper.RentalRequest;
import com.example.server.service.CarService;
import com.example.server.service.RentalService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
/**
 * RentalController class handles HTTP requests related to rental operations.
 * It includes endpoints to create, retrieve, and delete rentals, as well as to get rentals by user email and car license plate.
 */
@RestController
@RequestMapping("/api/rentals")
public class RentalController {

    /** Logger instance for logging purposes. */
    private static final Logger logger = LogManager.getLogger(RentalController.class);

    /** RentalService instance for rental-related operations. */
    @Autowired
    private RentalService rentalService;

    /** CarService instance for car-related operations. */
    @Autowired
    private CarService carService;

    /**
     * Endpoint to create a new rental.
     *
     * @param rentalRequest the RentalRequest object containing rental details.
     * @return the created Rental object.
     */
    @PostMapping("/create")
    public Rental create(@RequestBody RentalRequest rentalRequest) {
        return rentalService.createRental(rentalRequest);
    }

    /**
     * Endpoint to retrieve a rental by its ID.
     *
     * @param rentalId the ID of the rental to retrieve.
     * @return the Rental object.
     */
    @GetMapping("/{rentalId}")
    public Rental getRentalById(@PathVariable Integer rentalId) {
        logger.info("Rental by ID request");
        return rentalService.getRentalById(rentalId);
    }

    /**
     * Endpoint to retrieve all rentals.
     *
     * @return a list of all Rental objects.
     */
    @GetMapping
    public List<Rental> getAllRentals() {
        List<Rental> rentals = rentalService.getAllRental();
        logger.info("Get all rentals request");
        return rentals;
    }

    /**
     * Endpoint to delete a rental by its ID.
     *
     * @param rentalId the ID of the rental to delete.
     */
    @DeleteMapping("/{rentalId}")
    public void deleteRental(@PathVariable Integer rentalId) {
        logger.info("Delete rental request");
        rentalService.deleteRental(rentalId);
    }

    /**
     * Endpoint to retrieve all rented cars by a user's email.
     *
     * @param email the email of the user.
     * @return a ResponseEntity containing a list of Car objects.
     */
    @GetMapping("/user/{email}")
    public ResponseEntity<List<Car>> getAllRentedCarsByUserEmail(@PathVariable String email) {
        return ResponseEntity.ok(rentalService.getCarsRentedByUserEmail(email));
    }

    /**
     * Endpoint to retrieve rentals by car license plate.
     *
     * @param licensePlate the license plate of the car.
     * @return a list of Rental objects.
     */
    @GetMapping("/car/{licensePlate}")
    public List<Rental> getRentalsByLicensePlate(@PathVariable Integer licensePlate) {
        System.out.println("Getting rentals matching license plate: " + licensePlate);
        Car car = carService.getCarByLicensePlate(licensePlate);
        System.out.println("Car with license plate " + licensePlate + ": " + car);
        return rentalService.getRentalsByLicensePlate(car);
    }
}