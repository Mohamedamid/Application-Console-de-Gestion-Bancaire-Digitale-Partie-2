package com.repository;

import com.model.User;

import java.util.List;

public interface UserRepository {

    User findByEmail(String email);
    List<User> findAll();
    boolean existsByEmail(String email);
    void save(User user);
    void updateByEmail(User user ,String oldEmail);
    void deleteByEmail(String email);

}
