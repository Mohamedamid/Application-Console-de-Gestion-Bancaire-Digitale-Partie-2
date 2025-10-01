package com.service.implement;

import com.model.User;
import com.repository.UserRepository;
import com.repository.implement.InMemoryUserRepository;
import com.service.AuthService;

public class InMemoryAuthService implements AuthService {

    private final UserRepository userRepository = new InMemoryUserRepository();

    public User login(String email, String password) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email is null or empty");
        }

        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password is null or empty");
        }

        User user = userRepository.findByEmail(email.trim().toLowerCase());
        if (user != null && user.getPassword().equals(password)) {
            return user;
        } else {
            return null;
        }
    }
}
