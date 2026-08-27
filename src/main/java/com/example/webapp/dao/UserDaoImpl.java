package com.example.webapp.dao;

import com.example.webapp.config.JpaConfig;
import com.example.webapp.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

/**
 * Implementation of UserDao using JPA/Hibernate with SQL Server.
 */
public class UserDaoImpl implements UserDao {

    @Override
    public User getUserByUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            return null;
        }

        String normalizedUsername = username.trim();
        EntityManager em = JpaConfig.getEntityManager();
        try {
            TypedQuery<User> query = em.createQuery(
                    "SELECT u FROM User u WHERE LOWER(u.username) = LOWER(:username)",
                    User.class
            );
            query.setParameter("username", normalizedUsername);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
}
