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
    private CarCondition carCondition;
    private String brand;
    private String model;
    private String year;
    private String location;
    private UserModel user;
    private String description;


    public Integer getLicensePlate() {
		return licensePlate;
	}

	public void setLicensePlate(Integer licensePlate) {
		this.licensePlate = licensePlate;
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

	public UserModel getUser() {
		return user;
	}

	public void setUser(UserModel user) {
		this.user = user;
	}



	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public enum CarCondition {
        bad, standard, good
    }
    
    public String getSummary() {
        return brand + " - " + year + " " + model + " - " + carCondition;
    }

}
