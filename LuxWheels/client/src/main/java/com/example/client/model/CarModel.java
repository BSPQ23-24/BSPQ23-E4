package com.example.client.model;

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
    private Double pricePerDay;
    private CarCondition carCondition;
    private String brand;
    private String model;
    private String year;
    private String location;
    private UserModel user;
    private Status status;
    private String description;


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
