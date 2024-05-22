package com.example.server.controller;

import java.util.List;

import com.example.server.entity.Rental;
import com.example.server.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.server.entity.Car;
import com.example.server.service.CarService;

import jakarta.transaction.Transactional;

/**
 * CarController class handles HTTP requests related to car operations.
 * It includes endpoints to create, retrieve, update, and delete cars.
 */
@RestController
@RequestMapping("/api/cars")
public class CarController {

    /** CarService instance for car-related operations. */
    @Autowired
    private CarService carService;

    /**
     * Endpoint to create a new car.
     *
     * @param car the Car object containing car details.
     * @return the created Car object.
     */
    @PostMapping("/create")
    public Car create(@RequestBody Car car) {
        System.out.println(car);
        return carService.createCar(car);
    }

    /**
     * Endpoint to retrieve all cars.
     *
     * @return a ResponseEntity containing a list of all Car objects.
     */
    @GetMapping
    public ResponseEntity<List<Car>> getAllCars() {
        return ResponseEntity.ok(carService.getAllCars());
    }

    /**
     * Endpoint to retrieve a car by its license plate.
     *
     * @param licensePlate the license plate of the car to retrieve.
     * @return a ResponseEntity containing the Car object.
     */
    @GetMapping("/{licensePlate}")
    public ResponseEntity<Car> getCarByLicensePlate(@PathVariable Integer licensePlate) {
        return ResponseEntity.ok(carService.getCarByLicensePlate(licensePlate));
    }

    /**
     * Endpoint to update a car.
     *
     * @param licensePlate the license plate of the car to update.
     * @param car the Car object containing updated car details.
     * @return a ResponseEntity containing the updated Car object.
     */
    @PutMapping("/{licensePlate}")
    public ResponseEntity<Car> updateCar(@PathVariable Integer licensePlate, @RequestBody Car car) {
        System.out.println(licensePlate);
        car.setLicensePlate(licensePlate);
        System.out.println(car);
        Car updatedCar = carService.updateCar(licensePlate, car);
        System.out.println(car);
        return ResponseEntity.ok(updatedCar);
    }

    /**
     * Endpoint to delete a car by its license plate.
     *
     * @param licensePlate the license plate of the car to delete.
     * @return a ResponseEntity with no content.
     */
    @DeleteMapping("/{licensePlate}")
    @Transactional
    public ResponseEntity<Void> deleteCar(@PathVariable Integer licensePlate) {
        carService.deleteCar(licensePlate);
        return ResponseEntity.noContent().build();
    }
}
