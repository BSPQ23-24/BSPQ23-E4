package com.example.server.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RentalTest {

    private Rental rental;
    private User user;
    private Car car;

    @BeforeEach
    void setUp() {
        user = new User();
        car = new Car();
        car.setUser(user);
        rental = new Rental();
    }

    @Test
    void testSetAndGetRentalID() {
        rental.setRentalID(1);
        assertEquals(1, rental.getRentalID());
    }

    @Test
    void testSetAndGetUser() {
        rental.setUser(user);
        assertEquals(user, rental.getUser());
    }

    @Test
    void testSetAndGetCar() {
        rental.setCar(car);
        assertEquals(car, rental.getCar());
    }

    @Test
    void testSetAndGetStartDate() {
        rental.setStartDate("2023-01-01");
        assertEquals("2023-01-01", rental.getStartDate());
    }

    @Test
    void testSetAndGetEndDate() {
        rental.setEndDate("2023-01-10");
        assertEquals("2023-01-10", rental.getEndDate());
    }

    @Test
    void testSetAndGetPrice() {
        rental.setPrice(599.99);
        assertEquals(599.99, rental.getPrice());
    }

    @Test
    void testSetAndGetCreationDate() {
        rental.setCreationDate("2023-01-01");
        assertEquals("2023-01-01", rental.getCreationDate());
    }

    @Test
    void testToString() {
        rental = new Rental(user, car, "2023-01-01", "2023-01-10", 599.99, "2023-01-01");
        String expected = "Rental{rentalID=null, user=" + user.toString() + ", car=" + car.toString() + 
                          ", startDate='2023-01-01', endDate='2023-01-10', price=599.99, creationDate='2023-01-01'}";
        assertEquals(expected, rental.toString());
    }
}
