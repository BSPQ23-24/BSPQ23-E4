package com.example.server.service;
import com.example.server.entity.User;
import com.example.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    public User updateUser(Integer id, User user) {
        user.setId(id);
        return userRepository.save(user);
    }

    public String loginUser(User user) {
        Optional<User> foundUser = userRepository.findById(user.getId());
        if (foundUser.isPresent() && foundUser.get().getPassword().equals(user.getPassword())) {
            return "User logged in successfully!";
        }
        return "Invalid username or password!";
    }

    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }
}