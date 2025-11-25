package com.example.spring_login_demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.spring_login_demo.model.User;
import com.example.spring_login_demo.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public void registerUser(String username, String rawPassword) {
        String hash = encoder.encode(rawPassword);
        User user = new User(username, hash);
        userRepository.save(user);
    }

    public boolean validateLogin(String username, String rawPassword) {
        User user = userRepository.findByUsername(username);
        return user != null && encoder.matches(rawPassword, user.getPasswordHash());
    }

    public List<User> listUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
