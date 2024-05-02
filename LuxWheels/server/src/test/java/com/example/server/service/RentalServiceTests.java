package com.example.server.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
	private Rental rental;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        User user = new User();
        user.setEmail("test@mail.com");
        user.setPassword("test");
        
        String isRegistered = userService.loginUser(user);
        if (isRegistered.equals("{}")){
        	userService.createUser(user);
        	}

    }


    @Test
    public void testCreateRental() {
    	
        User user = emailRepository.findByEmail("test@mail.com")
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setEmail("test@mail.com");
                    newUser.setPassword("test");
                    return userRepository.save(newUser);
                });
        Car car = new Car();

    	car.setUser(user);
    	carService.createCar(car);
        Rental rental = new Rental();
        rental.setCar(car);
        rental.setUser(user);
        rentalService.createRental(rental);
        this.rental = rental;
        this.car = car;

        assertEquals(rental.toString(), rentalService.getRentalById(rental.getRentalID()).toString());
    }
    


}
