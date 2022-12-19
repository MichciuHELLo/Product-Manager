package com.example.MG_RestaurantManager20.user.service.impl;

import com.example.MG_RestaurantManager20.user.adapters.database.UserRepository;
import com.example.MG_RestaurantManager20.user.domain.User;
import com.example.MG_RestaurantManager20.user.service.UserService;
import lombok.AllArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Random;

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

    @Override
    @Transactional
    public void changePassword(String email, String newPassword) {
        String passwordSalt = new Random().ints(97, 122 + 1)
                .limit(32)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        String passwordHash = DigestUtils.sha1Hex(newPassword + passwordSalt);
        userRepository.changePassword(email, passwordHash, passwordSalt);
    }
}
