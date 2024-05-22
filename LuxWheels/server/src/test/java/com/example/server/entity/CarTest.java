package com.example.server.entity;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarTest {

    private Car car;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        car = new Car();
    }

    @Test
    void testSetAndGetLicensePlate() {
        car.setLicensePlate(12345);
        assertEquals(12345, car.getLicensePlate());
    }

    @Test
    void testSetAndGetCarCondition() {
        car.setCarCondition(Car.CarCondition.good);
        assertEquals(Car.CarCondition.good, car.getCarCondition());
    }

    @Test
    void testSetAndGetBrand() {
        car.setBrand("Toyota");
        assertEquals("Toyota", car.getBrand());
    }

    @Test
    void testSetAndGetModel() {
        car.setModel("Corolla");
        assertEquals("Corolla", car.getModel());
    }

    @Test
    void testSetAndGetYear() {
        car.setYear("2020");
        assertEquals("2020", car.getYear());
    }

    @Test
    void testSetAndGetLocation() {
        car.setLocation("New York");
        assertEquals("New York", car.getLocation());
    }

    @Test
    void testSetAndGetUser() {
        car.setUser(user);
        assertEquals(user, car.getUser());
    }

    @Test
    void testSetAndGetPricePerDay() {
        car.setPricePerDay(59.99);
        assertEquals(59.99, car.getPricePerDay());
    }

}

