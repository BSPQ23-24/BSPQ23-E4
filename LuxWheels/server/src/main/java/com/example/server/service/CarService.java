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

@Service
public class CarService {
	
    private static final Logger logger = LogManager.getLogger(CarService.class);
	
    @Autowired
    private CarRepository carRepository;

    public Car createCar(Car car) {
    	logger.info("Create a car service");
        return carRepository.save(car);
    }

    public List<Car> getAllCars() {
    	logger.info("Get all cars service");
        return carRepository.findAll();
    }

    public Car getCarByLicensePlate(Integer licensePlate) {
    	logger.info("Get car by license plate service");
        return carRepository.findByLicensePlate(licensePlate).orElse(null);
    }

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
