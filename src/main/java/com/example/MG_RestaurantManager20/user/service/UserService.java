package com.example.MG_RestaurantManager20.user.service;

import com.example.MG_RestaurantManager20.user.domain.User;

import java.util.Optional;

public interface UserService {

    Optional<User> getUserByEmail(String email);

    User addNewUser(User user);

    void changePassword(String email, String newPassword);

}
