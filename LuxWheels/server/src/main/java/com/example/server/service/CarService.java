package com.example.server.service;

import com.example.server.ServerApplication;
import com.example.server.entity.Car;
import com.example.server.repository.CarRepository;

import jakarta.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * Service class for managing car-related operations.
 */
@Service
public class CarService {

    private static final Logger logger = LogManager.getLogger(CarService.class);


    /** The car repository for performing database operations. */
    @Autowired
    private CarRepository carRepository;

    /**
     * Creates a new car and saves it to the database.
     *
     * @param car the car entity to create.
     * @return the created car entity.
     */
    public Car createCar(Car car) {
    	logger.info("Create a car service");
        return carRepository.save(car);
    }

    /**
     * Retrieves all cars from the database.
     *
     * @return a list of all car entities.
     */
    public List<Car> getAllCars() {
    	logger.info("Get all cars service");
        return carRepository.findAll();
    }

    /**
     * Retrieves a car by its license plate.
     *
     * @param licensePlate the license plate of the car to retrieve.
     * @return the car entity if found, or null if not found.
     */
    public Car getCarByLicensePlate(Integer licensePlate) {
    	logger.info("Get car by license plate service");
        return carRepository.findByLicensePlate(licensePlate).orElse(null);
    }

    /**
     * Updates an existing car in the database.
     *
     * @param licensePlate the license plate of the car to update.
     * @param car the updated car entity.
     * @return the updated car entity.
     */
    public Car updateCar(Integer licensePlate, Car car) {
    	logger.info("Update a car service");
        car.setLicensePlate(licensePlate);
        return carRepository.save(car);
    }

    public void deleteAll() {
    	logger.info("Delete all cars service");
    	carRepository.deleteAll();
    	}
    
    @Transactional
    public void deleteCar( Integer licensePlate ) {
    	logger.info("Delete a car service");
    	carRepository.deleteByLicensePlate( licensePlate );
    	}
}
