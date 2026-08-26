package com.example.webapp.service;

import com.example.webapp.model.User;

/**
 * Service interface for User business logic operations.
 */
public interface UserService {
    /**
     * Authenticate a user by username and password.
     * @param username the username input
     * @param password the password input
     * @return true if credentials are valid, false otherwise
     */
    boolean authenticate(String username, String password);

    /**
     * Get details of a user by username.
     * @param username the username
     * @return User object or null if not found
     */
    User getUserDetails(String username);
}
