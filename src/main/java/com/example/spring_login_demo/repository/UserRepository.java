package com.example.spring_login_demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.spring_login_demo.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
