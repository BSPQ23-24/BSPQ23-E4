package com.example.server.controller;

import com.example.server.entity.Car;
import com.example.server.entity.Rental;
import com.example.server.helper.RentalRequest;
import com.example.server.service.CarService;
import com.example.server.service.RentalService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
        return rentalService.createRental(rentalRequest);
    }

    @GetMapping("/{rentalId}")
    public Rental getRentalById(@PathVariable Integer rentalId) {
    	logger.info("rental by id request");
        return rentalService.getRentalById(rentalId);
    }

    @GetMapping
    public List<Rental> getAllRentals() {
    	List<Rental> rentals = rentalService.getAllRental();
    	logger.info("get all rentals request");
        return rentals;
    }

    @DeleteMapping("/{rentalId}")
    public void deleteRental(@PathVariable Integer rentalId) {
    	logger.info("delete rental request");
        rentalService.deleteRental(rentalId);;
    }

    @GetMapping("/car/{licensePlate}")
    public List<Rental> getRentalsByLicensePlate(@PathVariable Integer licensePlate) {
        System.out.println("Getting rentals matching license plate: " + licensePlate);
        Car car = carService.getCarByLicensePlate(licensePlate);
        System.out.println("Car with license plate " + licensePlate + ": " + car);
        return rentalService.getRentalsByLicensePlate(car);
    }
}
