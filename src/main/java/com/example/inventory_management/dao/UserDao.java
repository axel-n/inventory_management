package com.example.inventory_management.dao;

import com.example.inventory_management.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDao extends JpaRepository<User, Long> {
    Optional<User> findOneByEmail(String email);
    Optional<User> findById(Long id);
}
