package com.example.client.helper;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientRentalRequest {
    private Integer userId;
    private Integer carId;
    private String startDate;
    private String endDate;
    private Double price;
    private String creationDate;
}
