package com.example.server.controller;

import java.util.List;

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

@RestController
@RequestMapping("/api/cars")
public class CarController {

    @Autowired
    private CarService carService;

    @PostMapping
    public ResponseEntity<Car> create(@RequestBody Car car) {
        return ResponseEntity.ok(carService.createCar(car));
    }

    @GetMapping
    public ResponseEntity<List<Car>> getAllCars() {
        return ResponseEntity.ok(carService.getAllCars());
    }

    @GetMapping("/{licensePlate}")
    public ResponseEntity<Car> getCarByLicensePlate(@PathVariable Integer licensePlate) {
        return ResponseEntity.ok(carService.getCarByLicensePlate(licensePlate));
    }

    @PutMapping("/{licensePlate}")
    public ResponseEntity<Car> updateCar(@PathVariable Integer licensePlate, @RequestBody Car car) {
        Car updatedCar = carService.updateCar(licensePlate, car);
        return ResponseEntity.ok(updatedCar);
    }

    @DeleteMapping("/{licensePlate}")
    public ResponseEntity<Void> deleteCar(@PathVariable Integer licensePlate) {
        carService.deleteCar(licensePlate);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAll() {
        carService.deleteAll();
        return ResponseEntity.ok().build();
    }
}
