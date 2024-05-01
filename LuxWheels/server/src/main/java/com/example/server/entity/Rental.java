package com.example.server.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate; // Importar LocalDate para el manejo de fechas

@Entity
@Table(name = "Rental")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer rentalID;

    @ManyToOne
    @JoinColumn(name = "userID", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "carID", referencedColumnName = "licensePlate")
    private Car car;

    private String startDate; // Cambiar el tipo de fecha a LocalDate
    private String endDate;
    private Integer price;
    private String creationDate;
    
    public Rental(User user, Car car, String startDate, String endDate, Integer price, String creationDate) {
    	this.user = user;
    	this.car = car;
    	this.startDate = startDate;
    	this.endDate = endDate;
    	this.price = price;
    	this.creationDate = creationDate;
    	
    }
    
    public Rental() {
		// TODO Auto-generated constructor stub
	}

	public Integer getRentalID() {
        return rentalID;
    }

    public void setRentalID(Integer rentalID) {
        this.rentalID = rentalID;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
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


