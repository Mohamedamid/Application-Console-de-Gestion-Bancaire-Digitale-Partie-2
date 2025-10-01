package com.service;

import com.model.User;

import java.util.List;

public interface UserService {
    User createUser(String name, String email, String password,User.Role role);
    User updateUser(String name, String email, User.Role role, String password, String oldEmail);
    void deleteUser(String email);
    List<User> getAllUser();
}
