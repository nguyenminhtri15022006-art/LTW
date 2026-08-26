package com.example.webapp.dao;

import com.example.webapp.model.User;
import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of UserDao using an in-memory data store.
 */
public class UserDaoImpl implements UserDao {
    private static final Map<String, User> mockDatabase = new HashMap<>();

    static {
        // Populating simulated users
        mockDatabase.put("admin", new User("admin", "admin123", "System Administrator"));
        mockDatabase.put("nguyen", new User("nguyen", "nguyen123", "Nguyen Van A"));
        mockDatabase.put("sinhvien", new User("sinhvien", "123456", "Student User"));
    }

    @Override
    public User getUserByUsername(String username) {
        if (username == null) {
            return null;
        }
        // Case-insensitive check to be user-friendly, or strict if required. Let's do
        // strict.
        return mockDatabase.get(username.trim().toLowerCase());
    }
}
