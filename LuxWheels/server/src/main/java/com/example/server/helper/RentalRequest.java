package com.example.server.helper;

import lombok.Getter;

@Getter
public class RentalRequest {
    private Integer userId;
    private Integer carId;
    private String startDate;
    private String endDate;
    private Double price;
    private String creationDate;
}
