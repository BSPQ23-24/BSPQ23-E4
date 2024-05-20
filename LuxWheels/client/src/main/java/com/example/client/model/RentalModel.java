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
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}
	private Double price;
    private String creationDate;
}