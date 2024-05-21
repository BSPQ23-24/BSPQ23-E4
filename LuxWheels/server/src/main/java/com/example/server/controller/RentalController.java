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

@RestController
@RequestMapping("/api/rentals")
public class RentalController {
	
	private static final Logger logger = LogManager.getLogger(RentalController.class);

    @Autowired
    private RentalService rentalService;

    @Autowired
    private CarService carService;

    @PostMapping("/create")
    public Rental create(@RequestBody RentalRequest rentalRequest) {
    	logger.info("Create a rental controller");
        return rentalService.createRental(rentalRequest);
    }

    @GetMapping("/{rentalId}")
    public Rental getRentalById(@PathVariable Integer rentalId) {
    	logger.info("rental by id request controller");
        return rentalService.getRentalById(rentalId);
    }

    @GetMapping
    public List<Rental> getAllRentals() {
    	List<Rental> rentals = rentalService.getAllRental();
    	logger.info("get all rentals request controller");
        return rentals;
    }

    @DeleteMapping("/{rentalId}")
    public void deleteRental(@PathVariable Integer rentalId) {
    	logger.info("delete rental request controller");
        rentalService.deleteRental(rentalId);;
    }

    @GetMapping("/user/{email}")
    public ResponseEntity<List<Car>> getAllRentedCarsByUserEmail(@PathVariable String email) {
    	logger.info("getAllRentedCars by user email controller");
        return ResponseEntity.ok(rentalService.getCarsRentedByUserEmail(email));
    }

    @GetMapping("/car/{licensePlate}")
    public List<Rental> getRentalsByLicensePlate(@PathVariable Integer licensePlate) {
    	logger.info("getRentalsByLicensePlate - ", licensePlate);
        Car car = carService.getCarByLicensePlate(licensePlate);
        return rentalService.getRentalsByLicensePlate(car);
    }
}
