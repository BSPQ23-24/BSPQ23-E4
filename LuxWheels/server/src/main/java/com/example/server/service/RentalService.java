package com.example.server.service;
import java.util.*;

import com.example.server.helper.RentalRequest;
import com.example.server.entity.User;
import com.example.server.repository.CarRepository;
import com.example.server.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.server.entity.Rental;
import com.example.server.ServerApplication;
import com.example.server.entity.Car;

import com.example.server.repository.RentalRepository;

@Service
public class RentalService {
	
    private static final Logger logger = LogManager.getLogger(RentalService.class);

    /** The rental repository for performing database operations on rentals. */
    @Autowired
    private RentalRepository rentalRepository;

    /** The user repository for performing database operations on users. */
    @Autowired
    private UserRepository userRepository;

    /** The car repository for performing database operations on cars. */
    @Autowired
    private CarRepository carRepository;

    /**
     * Creates a new rental based on the provided rental request.
     *
     * @param rentalRequest the rental request containing rental details.
     * @return the created rental entity.
     */


    public Rental createRental(RentalRequest rentalRequest) {
    	
    	logger.info("Create a rental service");
        User user = userRepository.findById(rentalRequest.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        Car car = carRepository.findById(rentalRequest.getCarId())
                .orElseThrow(() -> new EntityNotFoundException("Car not found"));

        Rental rental = new Rental();
        rental.setUser(user);
        rental.setCar(car);
        rental.setStartDate(rentalRequest.getStartDate());
        rental.setEndDate(rentalRequest.getEndDate());
        rental.setPrice(rentalRequest.getPrice());
        rental.setCreationDate(rentalRequest.getCreationDate());

        return rentalRepository.save(rental);
    }

    /**
     * Retrieves all rentals from the database.
     *
     * @return a list of all rental entities.
     */
    public List<Rental> getAllRental() {
    	logger.info("Get all rentals service");
        return rentalRepository.findAll();
    }

    /**
     * Retrieves a rental by its ID.
     *
     * @param id the ID of the rental to retrieve.
     * @return the rental entity if found, or null if not found.
     */
    public Rental getRentalById(Integer id) {
    	logger.info("Get rental by id service");
        return rentalRepository.findById(id).orElse(null);
    }

    /**
     * Updates an existing rental in the database.
     *
     * @param id the ID of the rental to update.
     * @param rental the updated rental entity.
     * @return the updated rental entity.
     */
    public Rental updateRental(Integer id, Rental rental) {
    	logger.info("Update rental service");
        rental.setRentalID(id);
        return rentalRepository.save(rental);
    }

    /**
     * Retrieves rentals associated with a specific car.
     *
     * @param car the car entity for which to retrieve rentals.
     * @return a list of rentals associated with the car.
     **/
    public List<Rental> getRentalsByLicensePlate(Car car) {
    	logger.info("Get rentals by license plate service");
        return rentalRepository.findRentalsByCar(car);
    }

    /*
    TODO
    public List<Car> getCarsByUserId(String userId) {
        List<Rental> userRentals = rentalRepository.getUserRentals(Integer.valueOf(userId));
        List<Car> rentedCars = new ArrayList<>();
        for(Rental rental : userRentals) {
            Car car = rental.getCar();
            if (car != null) {
                rentedCars.add(car);
            } else {
                System.out.println("No car associated with this rental.");
            }
        }
        return rentedCars;
    }
    */
    /**
     * Deletes a rental by its ID.
     *
     * @param id the ID of the rental to delete.
     */
    public void deleteRental(Integer id) {
    	logger.info("Delete rental service");
        rentalRepository.deleteById(id);
    }

    /**
     * Retrieves cars rented by a user identified by their email.
     *
     * @param email the email of the user.
     * @return a list of cars rented by the user.
     */
    public List<Car> getCarsRentedByUserEmail(String email) {
    	logger.info("Get Cars by rented user email service");
        return rentalRepository.findAllCarsByUserEmail(email);
    }
}
