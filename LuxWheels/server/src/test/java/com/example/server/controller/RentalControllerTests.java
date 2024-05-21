package com.example.server.controller;

import com.example.server.entity.Car;
import com.example.server.entity.Rental;
import com.example.server.helper.RentalRequest;
import com.example.server.service.CarService;
import com.example.server.service.RentalService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RentalControllerTests {

    @Mock
    private RentalService rentalService;

    @Mock
    private CarService carService;

    @InjectMocks
    private RentalController rentalController;

    @Test
    void testCreate() {
        RentalRequest rentalRequest = new RentalRequest();
        Rental rental = new Rental();
        when(rentalService.createRental(rentalRequest)).thenReturn(rental);

        Rental createdRental = rentalController.create(rentalRequest);

        assertEquals(rental, createdRental);
        verify(rentalService, times(1)).createRental(rentalRequest);
    }

    @Test
    void testGetRentalById() {
        Integer rentalId = 1;
        Rental rental = new Rental();
        when(rentalService.getRentalById(rentalId)).thenReturn(rental);

        Rental retrievedRental = rentalController.getRentalById(rentalId);

        assertEquals(rental, retrievedRental);
        verify(rentalService, times(1)).getRentalById(rentalId);
    }

    @Test
    void testGetAllRentals() {
        List<Rental> rentals = Arrays.asList(new Rental(), new Rental());
        when(rentalService.getAllRental()).thenReturn(rentals);

        List<Rental> retrievedRentals = rentalController.getAllRentals();

        assertEquals(rentals, retrievedRentals);
        verify(rentalService, times(1)).getAllRental();
    }

    @Test
    void testDeleteRental() {
        Integer rentalId = 1;

        rentalController.deleteRental(rentalId);

        verify(rentalService, times(1)).deleteRental(rentalId);
    }

    @Test
    void testGetAllRentedCarsByUserEmail() {
        String email = "test@example.com";
        List<Car> cars = Arrays.asList(new Car(), new Car());
        when(rentalService.getCarsRentedByUserEmail(email)).thenReturn(cars);

        ResponseEntity<List<Car>> response = rentalController.getAllRentedCarsByUserEmail(email);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cars, response.getBody());
        verify(rentalService, times(1)).getCarsRentedByUserEmail(email);
    }

    @Test
    void testGetRentalsByLicensePlate() {
        Integer licensePlate = 12345;
        Car car = new Car();
        List<Rental> rentals = Arrays.asList(new Rental(), new Rental());
        when(carService.getCarByLicensePlate(licensePlate)).thenReturn(car);
        when(rentalService.getRentalsByLicensePlate(car)).thenReturn(rentals);

        List<Rental> retrievedRentals = rentalController.getRentalsByLicensePlate(licensePlate);

        assertEquals(rentals, retrievedRentals);
        verify(carService, times(1)).getCarByLicensePlate(licensePlate);
        verify(rentalService, times(1)).getRentalsByLicensePlate(car);
    }
}
