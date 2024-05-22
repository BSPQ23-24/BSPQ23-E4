package com.example.server.controller;


import com.example.server.entity.User;
import com.example.server.service.UserService;
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
class UserControllerTests {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    void testRegister() {
        User user = new User();
        when(userService.createUser(user)).thenReturn(user);

        ResponseEntity<User> response = userController.register(user);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
        verify(userService, times(1)).createUser(user);
    }


    @Test
    void testLoginInvalidCredentials() {
        User user = new User();
        String responseBody = "{}";
        when(userService.loginUser(user)).thenReturn(null);

        ResponseEntity<String> response = userController.login(user);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Invalid email or password", response.getBody());
        verify(userService, times(1)).loginUser(user);
    }

    @Test
    void testGetAllUsers() {
        List<User> users = Arrays.asList(new User(), new User());
        when(userService.getAllUsers()).thenReturn(users);

        ResponseEntity<List<User>> response = userController.getAllUsers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(users, response.getBody());
        verify(userService, times(1)).getAllUsers();
    }

    @Test
    void testGetUserByIdFound() {
        Integer id = 1;
        User user = new User();
        when(userService.getUserById(id)).thenReturn(user);

        ResponseEntity<User> response = userController.getUserById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
        verify(userService, times(1)).getUserById(id);
    }

    @Test
    void testGetUserByIdNotFound() {
        Integer id = 1;
        when(userService.getUserById(id)).thenReturn(null);

        ResponseEntity<User> response = userController.getUserById(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(userService, times(1)).getUserById(id);
    }

    @Test
    void testUpdateUserFound() {
        Integer id = 1;
        User user = new User();
        when(userService.updateUser(id, user)).thenReturn(user);

        ResponseEntity<User> response = userController.updateUser(id, user);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
        verify(userService, times(1)).updateUser(id, user);
    }

    @Test
    void testUpdateUserNotFound() {
        Integer id = 1;
        User user = new User();
        when(userService.updateUser(id, user)).thenReturn(null);

        ResponseEntity<User> response = userController.updateUser(id, user);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(userService, times(1)).updateUser(id, user);
    }

    @Test
    void testDeleteUser() {
        Integer id = 1;
        ResponseEntity<Void> expectedResponse = ResponseEntity.noContent().build();

        ResponseEntity<Void> response = userController.deleteUser(id);

        assertEquals(expectedResponse.getStatusCode(), response.getStatusCode());
        verify(userService, times(1)).deleteUser(id);
    }
}

