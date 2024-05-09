package com.example.server.controller;

import com.example.server.ServerApplication;
import com.example.server.entity.Car;
import com.example.server.entity.Rental;
import com.example.server.service.RentalService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api/rentals")
public class RentalController {
	
	private static final Logger logger = LogManager.getLogger(ServerApplication.class);

    private final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @PostMapping
    public Rental createRental(@RequestBody Rental rental) {
        Rental createdRental = rentalService.createRental(rental);
        logger.info("new rental request");
    	logger.info(createdRental);
        return createdRental;
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

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Car>> getAllRentedCarsByUserId(@PathVariable Integer userId) {
        return ResponseEntity.ok(rentalService.getCarsRentedByUser(userId));
    }
}
