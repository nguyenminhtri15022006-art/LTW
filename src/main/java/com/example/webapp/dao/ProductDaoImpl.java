package com.example.webapp.dao;

import com.example.webapp.config.JpaConfig;
import com.example.webapp.entity.*;
import com.example.webapp.service.ValidationException;

import jakarta.persistence.*;

import java.util.List;

public class ProductDaoImpl implements ProductDao {
    private final java.util.function.Supplier<EntityManager> managers;

    public ProductDaoImpl() {
        this.managers = JpaConfig::getEntityManager;
    }

    public ProductDaoImpl(EntityManagerFactory factory) {
        this.managers = factory::createEntityManager;
    }

    public List<Product> findPage(int offset, int limit) {
        try (EntityManager em = managers.get()) {
            return em.createQuery(
                            "select p from Product p join fetch p.category order by p.createdAt"
                                + " desc,p.id desc",
                            Product.class)
                    .setFirstResult(offset)
                    .setMaxResults(limit)
                    .getResultList();
        }
    }

    public long count() {
        try (EntityManager em = managers.get()) {
            return em.createQuery("select count(p) from Product p", Long.class).getSingleResult();
        }
    }

    public Product findById(long id) {
        try (EntityManager em = managers.get()) {
            return em.createQuery(
                            "select p from Product p join fetch p.category where p.id=:id",
                            Product.class)
                    .setParameter("id", id)
                    .getResultStream()
                    .findFirst()
                    .orElse(null);
        }
    }

    public void save(Product value) {
        try (EntityManager em = managers.get()) {
            var tx = em.getTransaction();
            try {
                tx.begin();
                Product p =
                        value.getId() == null
                                ? new Product()
                                : em.find(
                                        Product.class,
                                        value.getId(),
                                        LockModeType.PESSIMISTIC_WRITE);
                if (p == null) throw new ValidationException("form", "Sản phẩm không tồn tại.");
                Category category = em.find(Category.class, value.getCategory().getId());
                if (category == null)
                    throw new ValidationException("categoryId", "Category không tồn tại.");
                p.setName(value.getName());
                p.setDescription(value.getDescription());
                p.setPrice(value.getPrice());
                p.setStock(value.getStock());
                p.setCategory(category);
                if (value.getImage() != null) p.setImage(value.getImage());
                if (value.getId() == null) em.persist(p);
                tx.commit();
            } catch (RuntimeException e) {
                if (tx.isActive()) tx.rollback();
                throw e;
            }
        }
    }

    public void delete(long id) {
        try (EntityManager em = managers.get()) {
            var tx = em.getTransaction();
            try {
                tx.begin();
                Product p = em.find(Product.class, id);
                if (p != null) em.remove(p);
                tx.commit();
            } catch (RuntimeException e) {
                if (tx.isActive()) tx.rollback();
                throw e;
            }
        }
    }
}
