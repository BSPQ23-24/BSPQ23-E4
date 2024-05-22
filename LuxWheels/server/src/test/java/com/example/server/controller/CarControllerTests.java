package com.example.server.controller;


import com.example.server.entity.Car;
import com.example.server.service.CarService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarControllerTests {

    @Mock
    private CarService carService;

    @InjectMocks
    private CarController carController;

    @Test
    void testCreateCar() {
        Car car = new Car();
        when(carService.createCar(car)).thenReturn(car);

        Car createdCar = carController.create(car);

        assertEquals(car, createdCar);
        verify(carService, times(1)).createCar(car);
    }

    @Test
    void testGetAllCars() {
        List<Car> cars = Arrays.asList(new Car(), new Car());
        when(carService.getAllCars()).thenReturn(cars);

        ResponseEntity<List<Car>> response = carController.getAllCars();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cars, response.getBody());
        verify(carService, times(1)).getAllCars();
    }

    @Test
    void testGetCarByLicensePlate() {
        Integer licensePlate = 12345;
        Car car = new Car();
        when(carService.getCarByLicensePlate(licensePlate)).thenReturn(car);

        ResponseEntity<Car> response = carController.getCarByLicensePlate(licensePlate);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(car, response.getBody());
        verify(carService, times(1)).getCarByLicensePlate(licensePlate);
    }

    @Test
    void testUpdateCar() {
        Integer licensePlate = 12345;
        Car car = new Car();
        when(carService.updateCar(licensePlate, car)).thenReturn(car);

        ResponseEntity<Car> response = carController.updateCar(licensePlate, car);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(car, response.getBody());
        verify(carService, times(1)).updateCar(licensePlate, car);
    }

    @Test
    void testDeleteCar() {
        Integer licensePlate = 12345;
        ResponseEntity<Void> expectedResponse = ResponseEntity.noContent().build();

        ResponseEntity<Void> response = carController.deleteCar(licensePlate);

        assertEquals(expectedResponse.getStatusCode(), response.getStatusCode());
        verify(carService, times(1)).deleteCar(licensePlate);
    }
}

