package com.example.server.service;
import java.util.*;

import com.example.server.helper.RentalRequest;
import com.example.server.entity.User;
import com.example.server.repository.CarRepository;
import com.example.server.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.server.entity.Rental;
import com.example.server.entity.Car;

import com.example.server.repository.RentalRepository;

@Service
public class RentalService {

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CarRepository carRepository;


    public Rental createRental(RentalRequest rentalRequest) {
        User user = userRepository.findById(rentalRequest.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        Car car = carRepository.findById(rentalRequest.getCarId())
                .orElseThrow(() -> new EntityNotFoundException("Car not found"));

        Rental rental = new Rental();
        rental.setUser(user);
        rental.setCar(car);
        rental.setStartDate(rentalRequest.getStartDate());
        rental.setEndDate(rentalRequest.getEndDate());
        rental.setPrice(rentalRequest.getPrice());
        rental.setCreationDate(rentalRequest.getCreationDate());

        return rentalRepository.save(rental);
    }

    public List<Rental> getAllRental() {
        return rentalRepository.findAll();
    }

    public Rental getRentalById(Integer id) {
        return rentalRepository.findById(id).orElse(null);
    }

    public Rental updateRental(Integer id, Rental rental) {
        rental.setRentalID(id);
        return rentalRepository.save(rental);
    }

    public List<Rental> getRentalsByLicensePlate(Car car) {
        return rentalRepository.findRentalsByCar(car);
    }

    /*
    TODO
    public List<Car> getCarsByUserId(String userId) {
        List<Rental> userRentals = rentalRepository.getUserRentals(Integer.valueOf(userId));
        List<Car> rentedCars = new ArrayList<>();
        for(Rental rental : userRentals) {
            Car car = rental.getCar();
            if (car != null) {
                rentedCars.add(car);
            } else {
                System.out.println("No car associated with this rental.");
            }
        }
        return rentedCars;
    }
    */
    public void deleteRental(Integer id) {
        rentalRepository.deleteById(id);
    }
}
