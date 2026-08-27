package com.example.webapp.service;

import com.example.webapp.dao.UserDao;
import com.example.webapp.dao.UserDaoImpl;
import com.example.webapp.dto.LoginDTO;
import com.example.webapp.dto.UserDTO;
import com.example.webapp.entity.User;

/**
 * Service implementation for User business logic.
 */
public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    // Dependency injection via constructor (standard human clean code practice)
    public UserServiceImpl() {
        this.userDao = new UserDaoImpl();
    }

    // Constructor allowing manual DAO injection for testing
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public boolean authenticate(LoginDTO loginDTO) {
        if (loginDTO == null || loginDTO.getUsername() == null || loginDTO.getPassword() == null) {
            return false;
        }
        User user = userDao.getUserByUsername(loginDTO.getUsername());
        // Authentication check: matches password
        return user != null && user.getPassword().equals(loginDTO.getPassword());
    }

    @Override
    public UserDTO getUserDetails(String username) {
        User user = userDao.getUserByUsername(username);
        return user == null ? null : new UserDTO(user.getId(), user.getUsername(), user.getFullName());
    }
}
