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

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CarRepository carRepository;


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

    public List<Rental> getAllRental() {
    	logger.info("Get all rentals service");
        return rentalRepository.findAll();
    }

    public Rental getRentalById(Integer id) {
    	logger.info("Get rental by id service");
        return rentalRepository.findById(id).orElse(null);
    }

    public Rental updateRental(Integer id, Rental rental) {
    	logger.info("Update rental service");
        rental.setRentalID(id);
        return rentalRepository.save(rental);
    }

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
    public void deleteRental(Integer id) {
    	logger.info("Delete rental service");
        rentalRepository.deleteById(id);
    }

    public List<Car> getCarsRentedByUserEmail(String email) {
    	logger.info("Get Cars by rented user email service");
        return rentalRepository.findAllCarsByUserEmail(email);
    }
}
