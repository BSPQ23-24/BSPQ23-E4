package com.example.server.service;

import com.example.server.entity.Car;
import com.example.server.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return carRepository.findByLicensePlate(licensePlate).orElse(null);
    }

    public Car updateCar(Integer licensePlate, Car car) {
        car.setLicensePlate(licensePlate);
        return carRepository.save(car);
    }

    public void deleteAll() { carRepository.deleteAll(); }

    public void deleteCar( Integer licensePlate ) { carRepository.deleteByLicensePlate( licensePlate ); }
}
