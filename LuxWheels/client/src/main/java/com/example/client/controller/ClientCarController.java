package com.example.client.controller;

import com.example.client.model.CarModel;
import com.example.client.service.CarService;

public class ClientCarController {

    private static CarService carService = new CarService();

    public static void createCar(Integer licensePlate, String brand, String location, String model, String year, CarModel.CarCondition condition) {

        System.out.println(ClientUserController.loggedUser.getName());

        CarModel car = new CarModel();
        car.setLicensePlate(licensePlate);
        car.setBrand(brand);
        car.setLocation(location);
        car.setModel(model);
        car.setYear(year);
        car.setCarCondition(condition);
        car.setUser(ClientUserController.loggedUser);

        carService.createCar(car);
    }

}
