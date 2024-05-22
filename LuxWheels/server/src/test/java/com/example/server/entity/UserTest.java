package com.example.server.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
    }

    @Test
    void testSetAndGetId() {
        user.setId(1);
        assertEquals(1, user.getId());
    }

    @Test
    void testSetAndGetName() {
        user.setName("John");
        assertEquals("John", user.getName());
    }

    @Test
    void testSetAndGetSurname() {
        user.setSurname("Doe");
        assertEquals("Doe", user.getSurname());
    }

    @Test
    void testSetAndGetBirthdate() {
        user.setBirthdate("1990-01-01");
        assertEquals("1990-01-01", user.getBirthdate());
    }

    @Test
    void testSetAndGetLicenseNumber() {
        user.setLicensenumber("AB123456");
        assertEquals("AB123456", user.getLicensenumber());
    }

    @Test
    void testSetAndGetEmail() {
        user.setEmail("john.doe@example.com");
        assertEquals("john.doe@example.com", user.getEmail());
    }

    @Test
    void testSetAndGetPassword() {
        user.setPassword("password123");
        assertEquals("password123", user.getPassword());
    }
}
