package com.example.webapp;

import static org.junit.jupiter.api.Assertions.*;

import com.example.webapp.dao.*;
import com.example.webapp.entity.*;

import jakarta.persistence.*;

import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

class ProductJpaTest {
    EntityManagerFactory factory;
    ProductDao dao;

    @BeforeEach
    void setup() {
        Map<String, Object> p = new HashMap<>();
        p.put("jakarta.persistence.jdbc.driver", "org.h2.Driver");
        p.put("jakarta.persistence.jdbc.url", "jdbc:h2:mem:products;DB_CLOSE_DELAY=-1");
        p.put("jakarta.persistence.jdbc.user", "sa");
        p.put("jakarta.persistence.jdbc.password", "");
        p.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        p.put("hibernate.hbm2ddl.auto", "create-drop");
        factory = Persistence.createEntityManagerFactory("jakartaJpaPU", p);
        dao = new ProductDaoImpl(factory);
        try (EntityManager em = factory.createEntityManager()) {
            em.getTransaction().begin();
            Category c = new Category();
            c.setName("Category cũ");
            em.persist(c);
            for (int i = 1; i <= 13; i++) {
                Product v = new Product();
                v.setName("Product " + i);
                v.setPrice(BigDecimal.TEN);
                v.setStock(i);
                v.setCategory(c);
                v.setCreatedAt(LocalDateTime.of(2026, 1, 1, 0, 0).plusDays(i / 3));
                em.persist(v);
            }
            em.getTransaction().commit();
        }
    }

    @AfterEach
    void close() {
        if (factory != null) factory.close();
    }

    @Test
    void paginationUsesDatabaseLimitsAndNewestOrder() {
        assertEquals(13, dao.count());
        var first = dao.findPage(0, 6);
        var second = dao.findPage(6, 6);
        var third = dao.findPage(12, 6);
        assertEquals(6, first.size());
        assertEquals(6, second.size());
        assertEquals(1, third.size());
        assertEquals("Product 13", first.get(0).getName());
        assertEquals("Product 12", first.get(1).getName());
        assertEquals("Product 7", second.get(0).getName());
        assertEquals("Product 1", third.get(0).getName());
        assertEquals("Category cũ", first.get(0).getCategory().getName());
        assertEquals(10, dao.findPage(0, 10).size());
        var service = new com.example.webapp.service.ProductServiceImpl(dao, new CategoryDaoImpl());
        assertEquals(6, service.page(2).size());
        assertEquals(10, service.newest().size());
    }

    @Test
    void crudPreservesCreatedAtAndCategoryAndExistingImage() {
        Product p = dao.findPage(0, 1).get(0);
        LocalDateTime created = p.getCreatedAt();
        p.setName("Edited");
        p.setImage("sample.png");
        dao.save(p);
        p.setImage(null);
        p.setCreatedAt(created.plusYears(1));
        dao.save(p);
        Product actual = dao.findById(p.getId());
        assertEquals("Edited", actual.getName());
        assertEquals(created, actual.getCreatedAt());
        assertEquals("sample.png", actual.getImage());
        dao.delete(p.getId());
        assertNull(dao.findById(p.getId()));
        assertEquals(12, dao.count());
    }

    @Test
    void categoryCannotBeDeletedWhileProductsReferenceIt() {
        try (EntityManager em = factory.createEntityManager()) {
            em.getTransaction().begin();
            em.remove(em.find(Category.class, 1));
            assertThrows(PersistenceException.class, () -> em.getTransaction().commit());
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
        }
    }
}
