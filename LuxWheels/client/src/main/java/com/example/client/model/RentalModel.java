package com.example.client.model;



public class RentalModel {

    private Integer rentalID;
    private UserModel user;

    private CarModel car;

    private String startDate; // Cambiar el tipo de fecha a LocalDate
    private String endDate;
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