package com.example.client.controller;

import com.example.client.model.CarModel;
import com.example.client.service.CarService;
import com.example.client.view.LoginView;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
public class ClientCarController {

    private static CarService carService = new CarService();

    public static List<CarModel> getAllCars() {
        return carService.getAllCars();
    }

	private static final Logger logger = LogManager.getLogger(LoginView.class);
    public static CarModel getCarByLicensePlate(Integer licensePlate) {
        return carService.getCarByLicensePlate(licensePlate);
    }

    public static void createCar(String brand, String location, String model, String year, CarModel.CarCondition condition, String description, Double pricePerDay) {

        System.out.println("Creating car! - Owner: " + ClientUserController.loggedUser.toString());

        CarModel car = new CarModel();
        //car.setLicensePlate(licensePlate);
        car.setBrand(brand);
        car.setLocation(location);
        car.setModel(model);
        car.setYear(year);
        car.setCarCondition(condition);
        car.setUser(ClientUserController.loggedUser);
        car.setDescription(description);
        car.setPricePerDay(pricePerDay);

        carService.createCar(car);
    }
    
    public static void deleteCar(int licensePlate) {
    	logger.info("Delete Car");
        logger.info(licensePlate);
    	carService.deleteCar(licensePlate);
    }
}
