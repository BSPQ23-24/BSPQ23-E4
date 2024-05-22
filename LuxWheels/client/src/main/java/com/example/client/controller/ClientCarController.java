package com.example.client.controller;

import com.example.client.model.CarModel;
import com.example.client.service.CarService;
import com.example.client.view.LoginView;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/**
 * The ClientCarController class provides methods to manage cars in the system.
 * It interacts with the CarService to perform CRUD operations on CarModel objects.
 * @see CarService
 */
public class ClientCarController {

    private static CarService carService = new CarService();
    /**
     * Retrieves a list of all cars.
     *
     * @return a list of CarModel objects representing all cars in the database.
     * @see CarModel
     */
    public static List<CarModel> getAllCars() {
        return carService.getAllCars();
    }

	private static final Logger logger = LogManager.getLogger(LoginView.class);
    /**
     * Retrieves a car by its license plate number.
     *
     * @param licensePlate the license plate number to search the car to retrieve.
     * @return the CarModel object corresponding to the provided license plate number.
     */
    public static CarModel getCarByLicensePlate(Integer licensePlate) {
        return carService.getCarByLicensePlate(licensePlate);
    }
    /**
     * Creates a new car with the provided details.
     *
     * @param brand the brand of the car.
     * @param location the location where the car is available.
     * @param model the model of the car.
     * @param year the manufacturing year of the car.
     * @param condition the condition of the car.
     * @param description a description of the car.
     * @param pricePerDay the rental price per day for the car.
     */
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
    /**
     * Deletes a car by its license plate number.
     *
     * @param licensePlate the license plate number of the car to delete.
     */
    public static void deleteCar(int licensePlate) {
    	logger.info("Delete Car");
        logger.info(licensePlate);
    	carService.deleteCar(licensePlate);
    }
    /**
     * Updates the details of an existing car.
     *
     * @param car the CarModel object containing updated details of the car.
     */
    public static void updateCar(CarModel car) {
    	logger.info("Modify Car");
        logger.info(car.getLicensePlate());
    	carService.updateCar(car);
    	
    }
    

}
