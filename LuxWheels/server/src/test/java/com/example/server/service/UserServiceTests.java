package com.example.server.service;

import static org.mockito.Mockito.*;
import java.util.Optional;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import com.example.server.entity.User;
import com.example.server.repository.EmailRepository;

@SpringBootTest
public class UserServiceTests {
	
    @Mock
    private EmailRepository emailRepository;

    @Autowired
    private UserService userService;

    @Test
    public void testLoginUserWithInvalidCredentials() throws Exception {
        // Mocking the user and its email
        User user = new User();
        user.setEmail("prueba@mail.com");
        user.setPassword("prueba");

        // Testing loginUser method
        String expectedJson = "{}";
        assertEquals(expectedJson, userService.loginUser(user));
    }
    
    @Test
    public void registerUser() throws Exception {
        // Mocking the user and its email
        User user = new User();
        user.setEmail("test@mail.com");
        user.setPassword("test");
        
        String isRegistered = userService.loginUser(user);
        if (isRegistered.equals("{}")){
        	userService.createUser(user);
        	}
        }

    @Test
    public void testLoginUser() throws Exception {
        // Mocking the user and its email
        User user = new User();
        user.setEmail("test@mail.com");
        user.setPassword("test");
        
        String isRegistered = userService.loginUser(user);
        if (isRegistered.equals("{}")){
        	userService.createUser(user);
        	}

        // Creating the expected JSON string
        String expectedJson = "\"name\":null,\"surname\":null,\"birthdate\":null,\"licensenumber\":null,\"email\":\"test@mail.com\",\"password\":\"test\"}";

        // Testing loginUser method
        String result = userService.loginUser(user).substring(9);
        
        assertEquals(expectedJson, result);
    }
}
