package com.example.server.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Car")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer licensePlate;

    @Enumerated(EnumType.STRING) // Maps the ENUM type in SQL to a Java Enum
    private CarCondition carCondition;

    private String brand;
    private String model;

    private String year;
	private String description;

	private Status status;
	private Double pricePerDay;

    public Integer getLicensePlate() {
		return licensePlate;
	}

	public void setLicensePlate(Integer licensePlate) {
		this.licensePlate = licensePlate;
	}

	public Double getPricePerDay() {
		return pricePerDay;
	}

	public void setPricePerDay(Double pricePerDay) {
		this.pricePerDay = pricePerDay;
	}

	public CarCondition getCarCondition() {
		return carCondition;
	}

	public void setCarCondition(CarCondition carCondition) {
		this.carCondition = carCondition;
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

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	private String location;

    @ManyToOne // Assuming many cars can be owned by one user
    @JoinColumn(name = "UserID", referencedColumnName = "id")
    private User user;

    @Override
    public String toString() {
        return "Car{" +
                "licensePlate=" + licensePlate +
                ", carCondition=" + carCondition +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", year='" + year + '\'' +
                ", location='" + location + '\'' +
                ", user=" + (user != null ? user.getId() : "null") + // Safely accessing user ID
                '}';
    }

    // Enum to represent the CarCondition SQL ENUM
    public enum CarCondition {
        bad, standard, good
    }

	public enum Status {
		OPEN, BOOKED
	}

}