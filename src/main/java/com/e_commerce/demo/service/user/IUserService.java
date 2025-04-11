package com.e_commerce.demo.service.user;

import com.e_commerce.demo.models.User;

import java.util.List;

public interface IUserService{
    User createUser(User user);

    User getUserById(String userId);

    List<User> getAllUsers();

    User updateUser(String userId, User updatedUser);

    void deleteUser(String userId);

    User getUserByEmail(String email);
}
