package com.example.server.service;

import com.example.server.entity.User;
import com.example.server.repository.UserRepository;
import com.example.server.repository.EmailRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private EmailRepository emailRepository;

    @InjectMocks
    private UserService userService;

    private List<Integer> createdUserIds;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        createdUserIds = new ArrayList<>();
    }

    @AfterEach
    void cleanUp() {
        for (Integer id : createdUserIds) {
            userService.deleteUser(id);
        }
        createdUserIds.clear();
    }

    @Test
    void createUser() {
        User user = new User();
        when(userRepository.save(user)).thenAnswer(invocation -> {
            user.setId(1); // Set ID for the user
            return user;
        });
        User createdUser = userService.createUser(user);
        createdUserIds.add(createdUser.getId());
        assertNotNull(createdUser);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void getAllUsers() {
        List<User> users = Arrays.asList(new User(), new User());
        when(userRepository.findAll()).thenReturn(users);
        List<User> retrievedUsers = userService.getAllUsers();
        assertEquals(2, retrievedUsers.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void getUserById() {
        User user = new User();
        user.setId(1);
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        User retrievedUser = userService.getUserById(1);
        assertNotNull(retrievedUser);
        verify(userRepository, times(1)).findById(1);
    }

    @Test
    void getUserById_NotFound() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());
        User retrievedUser = userService.getUserById(1);
        assertNull(retrievedUser);
        verify(userRepository, times(1)).findById(1);
    }

    @Test
    void updateUser() {
        User user = new User();
        user.setId(1);
        when(userRepository.save(user)).thenReturn(user);
        User updatedUser = userService.updateUser(1, user);
        createdUserIds.add(updatedUser.getId());
        assertNotNull(updatedUser);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void loginUser_Success() throws Exception {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password");
        when(emailRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        ObjectMapper mapper = new ObjectMapper();
        String expectedResponse = mapper.writeValueAsString(user);
        String response = userService.loginUser(user);
        assertEquals(expectedResponse, response);
    }

    @Test
    void loginUser_Failure() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("wrongpassword");
        when(emailRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());
        String response = userService.loginUser(user);
        assertEquals("{}", response);
    }

    @Test
    void deleteUser() {
        userService.deleteUser(1);
        verify(userRepository, times(1)).deleteById(1);
    }
}
