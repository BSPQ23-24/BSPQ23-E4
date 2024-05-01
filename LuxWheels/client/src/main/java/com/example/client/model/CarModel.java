package com.example.client.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import com.example.client.model.CarModel.Status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CarModel {
    private Integer licensePlate;
    private String brand;
    private String model;
    private String year;
    private CarCondition carCondition;
    private String location;
    private Integer userId;
    private String description;
    private Status status;

    public CarModel() {
        // Default constructor
    }

    public CarModel(Integer licensePlate, String brand, String model, String year, CarCondition carCondition, String location, Integer userId, String description, Status status) {
        this.licensePlate = licensePlate;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.carCondition = carCondition;
        this.location = location;
        this.userId = userId;
        this.description = description;
        this.status = status;
        }

    public Integer getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(Integer licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public CarCondition getCarCondition() {
        return carCondition;
    }

    public void setCarCondition(CarCondition carCondition) {
        this.carCondition = carCondition;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public enum CarCondition {
        bad, standard, good
    }
    
    public enum Status {
        OPEN, BOOKED
    }

    public String getSummary() {
        return brand + " - " + year + " " + model + " - " + carCondition;
    }

}