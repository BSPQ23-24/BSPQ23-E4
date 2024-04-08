package com.example.server.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/create")
    public Car create(@RequestBody Car car) {
        return carService.createCar(car);
    }

    @GetMapping
    public List<Car> getAllCar() {
        return carService.getAllCars();
    }

    @GetMapping("/{LicensePlate}")
    public Car getUserByLicensePlate(@PathVariable Integer lp) {
        return carService.getCarByLicensePlate(lp);
    }

    @PutMapping("/{LicensePlate}")
    public Car updateCar(@PathVariable Integer lp, @RequestBody Car car) {
        return carService.updateCar(lp, car);
    }

    @DeleteMapping("/{LicensePlate}")
    public void deleteCar(@PathVariable Integer lp) {
        carService.deleteCar(lp);
    }
}
