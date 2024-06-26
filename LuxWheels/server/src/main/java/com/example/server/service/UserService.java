package com.example.server.service;

import com.example.server.ServerApplication;
import com.example.server.entity.User;
import com.example.server.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;

import com.example.server.repository.EmailRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
/**
 * Service class for managing user-related operations.
 */
@Service
public class UserService {

    private static final Logger logger = LogManager.getLogger(UserService.class);


    /** The user repository for performing database operations on users. */
    @Autowired
    private UserRepository userRepository;

    /** The email repository for performing database operations based on email. */
    @Autowired
    private EmailRepository emailRepository;

    /**
     * Creates a new user.
     *
     * @param user the user to create
     * @return the created user
     */
    public User createUser(User user) {
    	logger.info("Create a user service");
        return userRepository.save(user);
    }

    /**
     * Retrieves all users.
     *
     * @return a list of all users
     */
    public List<User> getAllUsers() {
    	logger.info("Get all users service");
        return userRepository.findAll();
    }

    /**
     * Retrieves a user by ID.
     *
     * @param id the ID of the user to retrieve
     * @return the retrieved user, or null if not found
     */
    public User getUserById(Integer id) {
    	logger.info("Get user by id service");
        return userRepository.findById(id).orElse(null);
    }

    /**
     * Updates an existing user.
     *
     * @param id the ID of the user to update
     * @param user the user data to update
     * @return the updated user
     */
    public User updateUser(Integer id, User user) {
    	logger.info("Update user service");
        user.setId(id);
        return userRepository.save(user);
    }

    /**
     * Logs in a user.
     *
     * @param user the user to log in
     * @return a JSON representation of the user if the login is successful, or an empty JSON object if not
     */
    public String loginUser(User user) {
    	logger.info("Login user service");

    	Optional<User> foundUser = emailRepository.findByEmail(user.getEmail());
    	ObjectMapper mapper = new ObjectMapper();
        try {
            if (foundUser.isPresent() && foundUser.get().getPassword().equals(user.getPassword())) {
                User user1 = foundUser.get();
                return mapper.writeValueAsString(user1);
            } else {
                // If Optional is empty or password does not match, return an empty JSON object
                return "{}";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        
    }

    /**
     * Deletes a user by ID.
     *
     * @param id the ID of the user to delete
     */
    public void deleteUser(Integer id) {
    	logger.info("Delete user service");
        userRepository.deleteById(id);
    }   
}