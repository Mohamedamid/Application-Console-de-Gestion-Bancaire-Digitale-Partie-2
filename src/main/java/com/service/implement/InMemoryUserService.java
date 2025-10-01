package com.service.implement;

import com.model.User;
import com.service.UserService;
import com.repository.UserRepository;
import com.repository.implement.InMemoryUserRepository;

import java.util.List;

public class InMemoryUserService implements UserService {
    private final UserRepository userRepository = new InMemoryUserRepository();

    @Override
    public List<User> getAllUser() {
        List<User> users = userRepository.findAll();
        return users;
    }

    @Override
    public User createUser(String name, String email, String password,User.Role role) {
        validateUserData(name, email, password);

        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already exists");
        }

        User user = new User(name, email, password, role);
        userRepository.save(user);
        return user;
    }

    @Override
    public User updateUser(String name, String email, User.Role role, String password, String oldEmail) {
        validateUserData(name, email, password);

        if (!userRepository.existsByEmail(oldEmail)) {
            throw new IllegalArgumentException("Old email does not exist");
        }

        if (!oldEmail.equals(email) && userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("New email already exists");
        }

        User user = new User(name, email, password, role);
        userRepository.updateByEmail(user, oldEmail);
        return user;
    }

    @Override
    public void deleteUser(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email is null or empty");
        }
        if (!userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email does not exist");
        }
        userRepository.deleteByEmail(email);
    }

    private void validateUserData(String name, String email, String password) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Username is null or empty");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email is null or empty");
        }
        if (password == null || password.length() < 6) {
            throw new IllegalArgumentException("Password is less than 6 characters");
        }
    }
}
