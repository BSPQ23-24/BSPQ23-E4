package com.example.server.controller;

import com.example.server.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.example.server.ServerApplication;
import com.example.server.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpRequest;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	private static final Logger logger = LogManager.getLogger(ServerApplication.class);

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public User register(@RequestBody User user) {
    	logger.info("register request");
    	logger.info(user);
        return userService.createUser(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody User user) {
    	logger.info("login request");
    	logger.info(user);
        return userService.loginUser(user);
    }

    @GetMapping
    public List<User> getAllUsers() {
    	logger.info("get all users request");
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Integer id) {
    	logger.info("delete user", id);
        return userService.getUserById(id);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Integer id, @RequestBody User user) {
    	logger.info("update user request", id);
    	logger.info(user);
        return userService.updateUser(id, user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Integer id) {
    	logger.info("delete user request", id);
        userService.deleteUser(id);
    }
}