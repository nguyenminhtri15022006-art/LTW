package com.example.webapp.dao;

import com.example.webapp.entity.Product;

import java.util.List;

public interface ProductDao {
    List<Product> findPage(int offset, int limit);

    long count();

    Product findById(long id);

    void save(Product product);

    void delete(long id);
}
