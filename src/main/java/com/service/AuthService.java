package com.service;

import com.model.User;

public interface AuthService {
    User login(String email, String password);
}
