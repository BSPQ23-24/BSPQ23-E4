package com.example.client.model;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RentalModel {

    private Integer rentalID;
    private UserModel user;

    private CarModel car;
    private String startDate;
    private String endDate;
    private Double price;
    private String creationDate;
}