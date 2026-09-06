package com.example.webapp.dao;

import com.example.webapp.config.JpaConfig;
import com.example.webapp.entity.User;
import com.example.webapp.service.ValidationException;

import jakarta.persistence.*;

import java.util.function.Consumer;

public class UserDaoImpl implements UserDao {
    public User getUserByUsername(String username) {
        return find("username", username);
    }

    public User findByEmail(String email) {
        return find("email", email);
    }

    private User find(String field, String value) {
        if (value == null || value.isBlank()) return null;
        try (EntityManager em = JpaConfig.getEntityManager()) {
            return em.createQuery(
                            "select u from User u where lower(u." + field + ") = :value",
                            User.class)
                    .setParameter("value", value.trim().toLowerCase(java.util.Locale.ROOT))
                    .getResultStream()
                    .findFirst()
                    .orElse(null);
        }
    }

    public User findById(Long id) {
        try (EntityManager em = JpaConfig.getEntityManager()) {
            return em.find(User.class, id);
        }
    }

    public void insert(User user) {
        try (EntityManager em = JpaConfig.getEntityManager()) {
            var tx = em.getTransaction();
            try {
                tx.begin();
                em.persist(user);
                tx.commit();
            } catch (RuntimeException e) {
                if (tx.isActive()) tx.rollback();
                throw e;
            }
        }
    }

    public User change(Long id, Consumer<User> change) {
        try (EntityManager em = JpaConfig.getEntityManager()) {
            var tx = em.getTransaction();
            try {
                tx.begin();
                User user = em.find(User.class, id, LockModeType.PESSIMISTIC_WRITE);
                if (user == null) throw new ValidationException("form", "Tài khoản không tồn tại.");
                change.accept(user);
                tx.commit();
                return user;
            } catch (RuntimeException e) {
                if (tx.isActive()) tx.rollback();
                throw e;
            }
        }
    }
}
