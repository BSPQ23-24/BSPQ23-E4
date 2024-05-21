package com.example.client.model;

import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * The CarModel class represents a car with various attributes such as license plate,
 * price per day, condition, status, brand, model, year, location, user, and description.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CarModel {
	/** The license plate number of the car. */
    private Integer licensePlate;
	/** The rental price per day for the car. */
    private Double pricePerDay;
	/** The condition of the car. */
    private CarCondition carCondition;
	/** The current status of the car (e.g., OPEN, BOOKED). */
	private Status status;
	/** The brand of the car. */
    private String brand;
	/** The model of the car. */
    private String model;
	/** The manufacturing year of the car. */
    private String year;
	/** The location where the car is available. */
    private String location;
	/** The user associated with the car.
	 * @see UserModel
	 * */
    private UserModel user;
	/** A description of the car. */
    private String description;
	/**
	 * Gets the rental price per day for the car.
	 *
	 * @return the rental price per day.
	 */
	public Double getPricePerDay() {
		return pricePerDay;
	}

	/**
	 * Sets the rental price per day for the car.
	 *
	 * @param pricePerDay the rental price per day.
	 */
	public void setPricePerDay(Double pricePerDay) {
		this.pricePerDay = pricePerDay;
	}

	/**
	 * Gets the current status of the car.
	 *
	 * @return the status of the car.
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * Sets the current status of the car.
	 *
	 * @param status the status of the car.
	 */
	public void setStatus(Status status) {
		this.status = status;
	}

	/**
	 * Gets the license plate number of the car.
	 *
	 * @return the license plate number.
	 */
	public Integer getLicensePlate() {
		return licensePlate;
	}

	/**
	 * Sets the license plate number of the car.
	 *
	 * @param licensePlate the license plate number.
	 */
	public void setLicensePlate(Integer licensePlate) {
		this.licensePlate = licensePlate;
	}

	/**
	 * Gets the condition of the car.
	 *
	 * @return the condition of the car.
	 */
	public CarCondition getCarCondition() {
		return carCondition;
	}

	/**
	 * Sets the condition of the car.
	 *
	 * @param carCondition the condition of the car.
	 */
	public void setCarCondition(CarCondition carCondition) {
		this.carCondition = carCondition;
	}

	/**
	 * Gets the brand of the car.
	 *
	 * @return the brand of the car.
	 */
	public String getBrand() {
		return brand;
	}

	/**
	 * Sets the brand of the car.
	 *
	 * @param brand the brand of the car.
	 */
	public void setBrand(String brand) {
		this.brand = brand;
	}

	/**
	 * Gets the model of the car.
	 *
	 * @return the model of the car.
	 */
	public String getModel() {
		return model;
	}

	/**
	 * Sets the model of the car.
	 *
	 * @param model the model of the car.
	 */
	public void setModel(String model) {
		this.model = model;
	}

	/**
	 * Gets the manufacturing year of the car.
	 *
	 * @return the year of the car.
	 */
	public String getYear() {
		return year;
	}

	/**
	 * Sets the manufacturing year of the car.
	 *
	 * @param year the year of the car.
	 */
	public void setYear(String year) {
		this.year = year;
	}

	/**
	 * Gets the location where the car is available.
	 *
	 * @return the location of the car.
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * Sets the location where the car is available.
	 *
	 * @param location the location of the car.
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * Gets the user associated with the car.
	 *
	 * @return the user of the car.
	 */
	public UserModel getUser() {
		return user;
	}

	/**
	 * Sets the user associated with the car.
	 *
	 * @param user the user of the car.
	 */
	public void setUser(UserModel user) {
		this.user = user;
	}

	/**
	 * Gets the description of the car.
	 *
	 * @return the description of the car.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description of the car.
	 *
	 * @param description the description of the car.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Represents the condition of the car.
	 */
	public enum CarCondition {
		/** The car is in bad condition. */
		bad,
		/** The car is in standard condition. */
		standard,
		/** The car is in good condition. */
		good
	}

	/**
	 * Represents the status of the car.
	 * @see java.lang.Enum
	 */
	public enum Status {
		/** The car is available for booking. */
		OPEN,
		/** The car is currently booked. */
		BOOKED
	}

	/**
	 * Gets a summary of the car's details.
	 *
	 * @return a string containing the brand, year, model, and condition of the car.
	 */
	public String getSummary() {
		return brand + " - " + year + " " + model + " - " + carCondition;
	}

}
