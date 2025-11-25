package com.example.spring_login_demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.spring_login_demo.model.User;
import com.example.spring_login_demo.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // ✅ usa o bean da SecurityConfig

    public void registerUser(String username, String rawPassword) {
        String hash = passwordEncoder.encode(rawPassword);
        User user = new User(username, hash);
        userRepository.save(user);
    }

    public boolean validateLogin(String username, String rawPassword) {
        User user = userRepository.findByUsername(username);
        return user != null && passwordEncoder.matches(rawPassword, user.getPasswordHash());
    }

    public List<User> listUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public boolean validateDelete(Long id, String pwd) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            return passwordEncoder.matches(pwd, user.getPasswordHash());
        }
        return false;
    }
}
