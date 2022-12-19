package com.example.MG_RestaurantManager20.user.adapters.database;

import com.example.MG_RestaurantManager20.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT s FROM User s WHERE s.email = ?1")
    Optional<User> getUserByEmail(String email);

    @Modifying
    @Query("UPDATE User u SET u.passwordHash = ?2, u.passwordSalt = ?3 WHERE u.email = ?1")
    void changePassword(String email, String newPassword, String passwordSalt);

}
