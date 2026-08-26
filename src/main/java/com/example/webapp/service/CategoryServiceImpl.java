package com.example.webapp.service;

import com.example.webapp.dao.CategoryDao;
import com.example.webapp.dao.CategoryDaoImpl;
import com.example.webapp.model.Category;
import java.util.List;

/**
 * Triển khai CategoryService, gọi xuống CategoryDao để thao tác dữ liệu.
 */
public class CategoryServiceImpl implements CategoryService {

    private final CategoryDao categoryDao;

    // Constructor mặc định
    public CategoryServiceImpl() {
        this.categoryDao = new CategoryDaoImpl();
    }

    // Constructor cho phép inject DAO (dùng khi test)
    public CategoryServiceImpl(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    @Override
    public List<Category> getAll() {
        return categoryDao.findAll();
    }

    @Override
    public Category getById(int id) {
        return categoryDao.findById(id);
    }

    @Override
    public void create(Category category) {
        categoryDao.insert(category);
    }

    @Override
    public void update(Category category) {
        categoryDao.update(category);
    }

    @Override
    public void delete(int id) {
        categoryDao.delete(id);
    }
}
