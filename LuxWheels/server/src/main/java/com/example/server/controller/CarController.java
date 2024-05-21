package com.example.server.controller;

import java.util.List;

import com.example.server.entity.Rental;
import com.example.server.entity.User;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

import com.example.server.ServerApplication;
import com.example.server.entity.Car;
import com.example.server.service.CarService;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/api/cars")
public class CarController {
	
    private static final Logger logger = LogManager.getLogger(CarController.class);

    @Autowired
    private CarService carService;

    @PostMapping("/create")
    public Car create(@RequestBody Car car) {
    	logger.info("Create a car controller");
        return carService.createCar(car);
    }

    @GetMapping
    public ResponseEntity<List<Car>> getAllCars() {
    	logger.info("Get all cars controller");
        return ResponseEntity.ok(carService.getAllCars());
    }

    @GetMapping("/{licensePlate}")
    public ResponseEntity<Car> getCarByLicensePlate(@PathVariable Integer licensePlate) {
    	logger.info("Get car by licensePlate controller");
        return ResponseEntity.ok(carService.getCarByLicensePlate(licensePlate));
    }

    @PutMapping("/{licensePlate}")
    public ResponseEntity<Car> updateCar(@PathVariable Integer licensePlate, @RequestBody Car car) {
    	logger.info("UpdateCar controller");
    	car.setLicensePlate(licensePlate);
        Car updatedCar = carService.updateCar(licensePlate, car);
        return ResponseEntity.ok(updatedCar);
    }

    @DeleteMapping("/{licensePlate}")
    @Transactional
    public ResponseEntity<Void> deleteCar(@PathVariable Integer licensePlate) {
    	logger.info("Delete a car controller");
        carService.deleteCar(licensePlate);
        return ResponseEntity.noContent().build();
    }
}
