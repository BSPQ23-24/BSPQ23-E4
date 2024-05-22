package com.example.server.controller;

import com.example.server.ServerApplication;
import com.example.server.service.UserService;
import com.example.server.entity.User;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * UserController class handles HTTP requests related to user operations.
 * It includes endpoints to create, retrieve, update, and delete users, as well as user login.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Logger logger = LogManager.getLogger(UserController.class);

    @Autowired
    private UserService userService;
    /**
     * Endpoint to create a new user.
     *
     * @param user the User object containing user details.
     * @return the created User object.
     */
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        logger.info("register request controller");
        logger.info(user);
        User createdUser = userService.createUser(user);
        return ResponseEntity.ok(createdUser);
    }
    /**
     * Endpoint for user login.
     *
     * @param user the User object containing login details.
     * @return a ResponseEntity containing the User object or an error message.
     */
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        logger.info("login request controller");
        logger.info(user);
        String response = userService.loginUser(user);
        if (response == null || response.isEmpty() || response.equals("{}")) {
            return ResponseEntity.status(401).body("Invalid email or password");
        }
        return ResponseEntity.ok(response);
    }
    /**
     * Endpoint to get all users.
     *
     * @return a list of all User objects.
     */
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        logger.info("get all users request controller");
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
    /**
     * Endpoint to get a user by their ID.
     *
     * @param id the ID of the user to retrieve.
     * @return the User object.
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id) {
        logger.info("get user by id request: {}", id);
        User user = userService.getUserById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }
    /**
     * Endpoint to update a user.
     *
     * @param id the ID of the user to update.
     * @param user the User object containing updated details.
     * @return the updated User object.
     */
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Integer id, @RequestBody User user) {
        logger.info("update user request: {}", id);
        logger.info(user);
        User updatedUser = userService.updateUser(id, user);
        if (updatedUser == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedUser);
    }
    /**
     * Endpoint to delete a user by their ID.
     *
     * @param id the ID of the user to delete.
     * @return a ResponseEntity indicating the result of the operation.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        logger.info("delete user request: {}", id);
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
