package com.example.server.service;

import com.example.server.entity.Car;
import com.example.server.repository.CarRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * Service class for managing car-related operations.
 */
@Service
public class CarService {

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
        return carRepository.save(car);
    }

    /**
     * Retrieves all cars from the database.
     *
     * @return a list of all car entities.
     */
    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    /**
     * Retrieves a car by its license plate.
     *
     * @param licensePlate the license plate of the car to retrieve.
     * @return the car entity if found, or null if not found.
     */
    public Car getCarByLicensePlate(Integer licensePlate) {
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
        car.setLicensePlate(licensePlate);
        return carRepository.save(car);
    }

    /**
     * Deletes all cars from the database.
     */
    public void deleteAll() {
        carRepository.deleteAll();
    }

    /**
     * Deletes a car by its license plate.
     *
     * @param licensePlate the license plate of the car to delete.
     */
    @Transactional
    public void deleteCar(Integer licensePlate) {
        carRepository.deleteByLicensePlate(licensePlate);
    }
}