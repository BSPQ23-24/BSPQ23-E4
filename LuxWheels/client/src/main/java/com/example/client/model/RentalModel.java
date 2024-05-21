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
/**
 * The RentalModel class represents a rent of a car done by a user
 */
public class RentalModel {

	/** The unique ID of the rental. */
	private Integer rentalID;
	/** The user who made the rental. */
	private UserModel user;
	/** The car that is being rented. */
	private CarModel car;
	/** The start date of the rental period. */
	private String startDate;
	/** The end date of the rental period. */
	private String endDate;
	/** The price of the rental. */
	private Double price;
	/** The creation date of the rental record. */
	private String creationDate;

	/**
	 * Gets the rental ID.
	 *
	 * @return the rental ID.
	 */
	public Integer getRentalID() {
		return rentalID;
	}

	/**
	 * Sets the rental ID.
	 *
	 * @param rentalID the rental ID to set.
	 */
	public void setRentalID(Integer rentalID) {
		this.rentalID = rentalID;
	}

	/**
	 * Gets the user who made the rental.
	 *
	 * @return the user.
	 */
	public UserModel getUser() {
		return user;
	}

	/**
	 * Sets the user who made the rental.
	 *
	 * @param user the user to set.
	 */
	public void setUser(UserModel user) {
		this.user = user;
	}

	/**
	 * Gets the car that is being rented.
	 *
	 * @return the car.
	 */
	public CarModel getCar() {
		return car;
	}

	/**
	 * Sets the car that is being rented.
	 *
	 * @param car the car to set.
	 */
	public void setCar(CarModel car) {
		this.car = car;
	}

	/**
	 * Gets the start date of the rental period.
	 *
	 * @return the start date.
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * Sets the start date of the rental period.
	 *
	 * @param startDate the start date to set.
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * Gets the end date of the rental period.
	 *
	 * @return the end date.
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * Sets the end date of the rental period.
	 *
	 * @param endDate the end date to set.
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * Gets the price of the rental.
	 *
	 * @return the price.
	 */
	public Double getPrice() {
		return price;
	}

	/**
	 * Sets the price of the rental.
	 *
	 * @param price the price to set.
	 */
	public void setPrice(Double price) {
		this.price = price;
	}

	/**
	 * Gets the creation date of the rental record.
	 *
	 * @return the creation date.
	 */
	public String getCreationDate() {
		return creationDate;
	}

	/**
	 * Sets the creation date of the rental record.
	 *
	 * @param creationDate the creation date to set.
	 */
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

}