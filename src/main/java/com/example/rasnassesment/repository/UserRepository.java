package com.example.rasnassesment.repository;

import com.example.rasnassesment.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

   User findByEmail(String email);

   Optional<User> findByUserId(String id);
}
