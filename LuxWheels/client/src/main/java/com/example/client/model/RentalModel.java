package com.example.client.model;


import org.springframework.cglib.core.Local;

import java.time.LocalDate;

public class RentalModel {

    private Integer rentalID;
    private UserModel user;

    private CarModel car;

    private LocalDate startDate; // Cambiar el tipo de fecha a LocalDate
    private LocalDate endDate;
    private Integer price;
    private String creationDate;
    
    
    public RentalModel() {
	}

	public Integer getRentalID() {
        return rentalID;
    }

    public void setRentalID(Integer rentalID) {
        this.rentalID = rentalID;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public CarModel getCar() {
        return car;
    }

    public void setCar(CarModel car) {
        this.car = car;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    @Override
	public String toString() {
		return "Rental [rentalID=" + rentalID + ", user=" + user + ", car=" + car + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", price=" + price + ", creationDate=" + creationDate + "]";
	}

	public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }
}