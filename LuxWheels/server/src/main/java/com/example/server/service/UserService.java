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

@Service
public class UserService {
	
    private static final Logger logger = LogManager.getLogger(UserService.class);
	
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailRepository emailRepository;

    public User createUser(User user) {
    	logger.info("Create a user service");
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
    	logger.info("Get all users service");
        return userRepository.findAll();
    }

    public User getUserById(Integer id) {
    	logger.info("Get user by id service");
        return userRepository.findById(id).orElse(null);
    }

    public User updateUser(Integer id, User user) {
    	logger.info("Update user service");
        user.setId(id);
        return userRepository.save(user);
    }

    public String loginUser(User user) {
    	logger.info("Login user service");

    	Optional<User> foundUser = emailRepository.findByEmail(user.getEmail());
    	ObjectMapper mapper = new ObjectMapper();
        try {
            if (foundUser.isPresent() && foundUser.get().getPassword().equals(user.getPassword())) {
                User user1 = foundUser.get();
                return mapper.writeValueAsString(user1);
            } else {
                // If Optional is empty, return an empty JSON object
                return "{}";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        
    }
    
    public void deleteUser(Integer id) {
    	logger.info("Delete user service");
        userRepository.deleteById(id);
    }   
}