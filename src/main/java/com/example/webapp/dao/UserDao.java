package com.example.webapp.dao;

import com.example.webapp.entity.User;

import java.util.function.Consumer;

public interface UserDao {
    User getUserByUsername(String username);

    User findByEmail(String email);

    User findById(Long id);

    void insert(User user);

    User change(Long id, Consumer<User> change);
}
