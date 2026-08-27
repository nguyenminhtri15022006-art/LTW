package com.example.webapp.service;

import com.example.webapp.dto.LoginDTO;
import com.example.webapp.dto.UserDTO;

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
    boolean authenticate(LoginDTO loginDTO);

    /**
     * Get details of a user by username.
     * @param username the username
     * @return user data safe for the web layer, or null if not found
     */
    UserDTO getUserDetails(String username);
}
