package com.example.webapp.service;

import com.example.webapp.dao.UserDao;
import com.example.webapp.dao.UserDaoImpl;
import com.example.webapp.model.User;

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
    public boolean authenticate(String username, String password) {
        if (username == null || password == null) {
            return false;
        }
        User user = userDao.getUserByUsername(username);
        // Authentication check: matches password
        return user != null && user.getPassword().equals(password);
    }

    @Override
    public User getUserDetails(String username) {
        return userDao.getUserByUsername(username);
    }
}
