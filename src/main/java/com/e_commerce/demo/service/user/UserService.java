package com.e_commerce.demo.service.user;

import com.e_commerce.demo.Response.ApiResponse;
import com.e_commerce.demo.exceptions.AlreadyExistsException;
import com.e_commerce.demo.exceptions.ResourceNotFoundException;
import com.e_commerce.demo.models.User;
import com.e_commerce.demo.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@Service
public class UserService implements IUserService{

    @Autowired
    private IUserRepository userRepository;

    public User createUser(User user) {

        try {
            if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
                throw new IllegalArgumentException("Email must not be empty");
            }

            if (user.getOrderStatus() == null || user.getOrderStatus().trim().isEmpty()) {
                throw new IllegalArgumentException("Order status must not be empty");
            }

            Optional<User> existingUser = userRepository.findByEmail(user.getEmail());

            if (existingUser.isPresent()) {
                throw new AlreadyExistsException("User with email " + user.getEmail() + " already exists.");
            }

            user.setUserId(UUID.randomUUID().toString());
            return userRepository.save(user);

        } catch (IllegalArgumentException | AlreadyExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to create user: " + e.getMessage());
        }
    }

    public User getUserById(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User updateUser(String userId, User updatedUser) {
        try {

            User existingUser = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));


            if (updatedUser.getEmail() != null && !updatedUser.getEmail().trim().isEmpty()) {
                existingUser.setEmail(updatedUser.getEmail().trim());
            }

            if (updatedUser.getOrderStatus() != null && !updatedUser.getOrderStatus().trim().isEmpty()) {
                existingUser.setOrderStatus(updatedUser.getOrderStatus().trim());
            }

            return userRepository.save(existingUser);

        } catch (Exception e) {
            throw new RuntimeException("Failed to update user: " + e.getMessage());
        }
    }

    public void deleteUser(String userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id: " + userId);
        }
        userRepository.deleteById(userId);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }
}
