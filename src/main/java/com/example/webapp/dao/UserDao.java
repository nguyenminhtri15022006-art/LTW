package com.example.webapp.dao;

import com.example.webapp.entity.User;

/**
 * Data Access Object (DAO) interface for User operations.
 */
public interface UserDao {
    /**
     * Retrieve a user by their username.
     * @param username the username to search for
     * @return the User object if found, otherwise null
     */
    User getUserByUsername(String username);
}
