package com.example.webapp.dao;

import com.example.webapp.config.JpaConfig;
import com.example.webapp.entity.Category;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;

/**
 * Triển khai CategoryDao sử dụng JPA/Hibernate và SQL Server.
 */
public class CategoryDaoImpl implements CategoryDao {

    @Override
    public List<Category> findAll() {
        EntityManager em = JpaConfig.getEntityManager();
        try {
            return em.createQuery("SELECT c FROM Category c ORDER BY c.id", Category.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Category findById(int id) {
        EntityManager em = JpaConfig.getEntityManager();
        try {
            return em.find(Category.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public void insert(Category category) {
        EntityManager em = JpaConfig.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(category);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void update(Category category) {
        EntityManager em = JpaConfig.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.merge(category);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void delete(int id) {
        EntityManager em = JpaConfig.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            Category category = em.find(Category.class, id);
            if (category != null) {
                em.remove(category);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }
}
