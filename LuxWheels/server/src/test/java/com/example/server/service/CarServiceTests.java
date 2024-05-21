package com.example.server.service;

import com.example.server.entity.Car;
import com.example.server.entity.User;
import com.example.server.repository.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarServiceTests {

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarService carService;

    private Car car;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        car = new Car();
        car.setUser(user);
    }

    @Test
    void testCreateCar() {
        when(carRepository.save(car)).thenReturn(car);

        Car createdCar = carService.createCar(car);

        assertEquals(car, createdCar);
        verify(carRepository, times(1)).save(car);
    }

    @Test
    void testGetAllCars() {
        List<Car> cars = Arrays.asList(car);
        when(carRepository.findAll()).thenReturn(cars);

        List<Car> allCars = carService.getAllCars();

        assertEquals(cars, allCars);
        verify(carRepository, times(1)).findAll();
    }

    @Test
    void testGetCarByLicensePlate() {
        when(carRepository.findByLicensePlate(12345)).thenReturn(Optional.of(car));

        Car foundCar = carService.getCarByLicensePlate(12345);

        assertEquals(car, foundCar);
        verify(carRepository, times(1)).findByLicensePlate(12345);
    }

    @Test
    void testUpdateCar() {
        when(carRepository.save(car)).thenReturn(car);

        Car updatedCar = carService.updateCar(12345, car);

        assertEquals(car, updatedCar);
        verify(carRepository, times(1)).save(car);
    }

    @Test
    void testDeleteAll() {
        doNothing().when(carRepository).deleteAll();

        carService.deleteAll();

        verify(carRepository, times(1)).deleteAll();
    }

    @Test
    void testDeleteCar() {
        doNothing().when(carRepository).deleteByLicensePlate(12345);

        carService.deleteCar(12345);

        verify(carRepository, times(1)).deleteByLicensePlate(12345);
    }
}

