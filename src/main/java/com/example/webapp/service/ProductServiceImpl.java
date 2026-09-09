package com.example.webapp.service;

import com.example.webapp.dao.*;
import com.example.webapp.dto.ProductDTO;
import com.example.webapp.entity.*;

import java.util.List;

@org.springframework.stereotype.Service
public class ProductServiceImpl implements ProductService {
    private final ProductDao products;
    private final CategoryDao categories;

    public ProductServiceImpl() {
        this(new ProductDaoImpl(), new CategoryDaoImpl());
    }

    @org.springframework.beans.factory.annotation.Autowired
    public ProductServiceImpl(ProductDao products, CategoryDao categories) {
        this.products = products;
        this.categories = categories;
    }

    public List<ProductDTO> newest() {
        return products.findPage(0, 10).stream().map(this::dto).toList();
    }

    public List<ProductDTO> page(int page) {
        if (page < 1 || page > Integer.MAX_VALUE / PAGE_SIZE)
            throw new ValidationException("page", "Trang không hợp lệ.");
        return products.findPage((page - 1) * PAGE_SIZE, PAGE_SIZE).stream()
                .map(this::dto)
                .toList();
    }

    public long count() {
        return products.count();
    }

    public ProductDTO getById(long id) {
        return dto(products.findById(id));
    }

    public void save(ProductDTO d) {
        FormValidation.validate(d);
        Category category = categories.findById(d.getCategoryId());
        if (category == null)
            throw new ValidationException("categoryId", "Category không tồn tại.");
        Product p = new Product();
        p.setId(d.getId());
        p.setName(d.getName().trim());
        p.setDescription(d.getDescription());
        p.setPrice(d.getPrice());
        p.setStock(d.getStock());
        p.setImage(d.getImage());
        p.setCategory(category);
        products.save(p);
    }

    public void delete(long id) {
        products.delete(id);
    }

    private ProductDTO dto(Product p) {
        if (p == null) return null;
        ProductDTO d = new ProductDTO();
        d.setId(p.getId());
        d.setName(p.getName());
        d.setDescription(p.getDescription());
        d.setPrice(p.getPrice());
        d.setStock(p.getStock());
        d.setImage(p.getImage());
        d.setCreatedAt(p.getCreatedAt());
        d.setCategoryId(p.getCategory().getId());
        d.setCategoryName(p.getCategory().getName());
        return d;
    }
}
