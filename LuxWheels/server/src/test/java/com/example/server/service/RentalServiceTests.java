package com.example.server.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.server.helper.RentalRequest;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.server.entity.Car;
import com.example.server.entity.Rental;
import com.example.server.entity.User;
import com.example.server.repository.CarRepository;
import com.example.server.repository.EmailRepository;
import com.example.server.repository.RentalRepository;
import com.example.server.repository.UserRepository;

@SpringBootTest
public class RentalServiceTests {
	@Autowired
	private RentalRepository rentalRepository;

	@Autowired
	private EmailRepository emailRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CarRepository carRepository;

	@Autowired
	private RentalService rentalService;

	@Autowired
	private UserService userService;

	@Autowired
	private CarService carService;
	
	private Car car;
	private User user;
	private Rental rental;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        User user = new User();
        user.setEmail("test@mail.com");
        user.setPassword("test");
        userService.createUser(user);
        this.user = user;
       	Car car = new Car();
    	car.setUser(user);
    	carService.createCar(car);
    	this.car = car;
    }


    @Test
    public void testCreateRental() {
    	RentalRequest rentalRequest = new RentalRequest();
    	rentalRequest.setCarId(this.car.getLicensePlate());
    	rentalRequest.setUserId(this.user.getId());

        rentalService.createRental(rentalRequest);

		Rental rental = new Rental();
		rental.setCar(this.car);
		rental.setUser(this.user);

        this.rental = rental;

        assertEquals(rental.toString(), rentalService.getRentalById(rental.getRentalID()).toString());
    }
    
    @AfterEach
    public void cleanup() {
    	rentalService.deleteRental(this.rental.getRentalID());
        carService.deleteCar(this.car.getLicensePlate());
        userService.deleteUser(this.user.getId());
    }

}
