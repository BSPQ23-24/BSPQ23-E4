package com.example.server.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.server.entity.Car;
import com.example.server.repository.CarRepository;


@Service
public class CarService {
    @Autowired
    private CarRepository carRepository;

    public Car createCar(Car car) {
        return carRepository.save(car);
    }

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public Car getCarByLicensePlate(Integer licensePlate) {
        return carRepository.findById(licensePlate).orElse(null);
    }

    public Car updateCar(Integer licensePlate, Car updatedCar) {
        return carRepository.findById(licensePlate).map(car -> {
            car.setBrand(updatedCar.getBrand());
            car.setModel(updatedCar.getModel());
            car.setYear(updatedCar.getYear());
            car.setCarCondition(updatedCar.getCarCondition());
            car.setLocation(updatedCar.getLocation());
            car.setUser(updatedCar.getUser());
            return carRepository.save(car);
        }).orElseGet(() -> {
            updatedCar.setLicensePlate(licensePlate);
            return carRepository.save(updatedCar);
        });
    }
    public void deleteCar(Integer licensePlate) {
        carRepository.deleteById(licensePlate);
    }
}