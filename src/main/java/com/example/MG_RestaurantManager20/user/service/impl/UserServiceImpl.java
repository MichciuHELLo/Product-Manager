package com.example.MG_RestaurantManager20.user.service.impl;

import com.example.MG_RestaurantManager20.user.adapters.database.UserRepository;
import com.example.MG_RestaurantManager20.user.domain.User;
import com.example.MG_RestaurantManager20.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    final private UserRepository userRepository;

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    @Override
    public User addNewUser(User user) {
        return userRepository.save(user);
    }
}
