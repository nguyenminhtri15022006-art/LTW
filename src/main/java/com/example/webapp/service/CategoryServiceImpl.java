package com.example.webapp.service;

import com.example.webapp.dao.CategoryDao;
import com.example.webapp.dao.CategoryDaoImpl;
import com.example.webapp.dto.CategoryDTO;
import com.example.webapp.entity.Category;

import java.util.List;
import java.util.stream.Collectors;

/** Triển khai CategoryService, gọi xuống CategoryDao để thao tác dữ liệu. */
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
    public List<CategoryDTO> getAll() {
        return categoryDao.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public CategoryDTO getById(int id) {
        return toDTO(categoryDao.findById(id));
    }

    @Override
    public void create(CategoryDTO category) {
        FormValidation.validate(category);
        categoryDao.insert(toEntity(category));
    }

    @Override
    public void update(CategoryDTO category) {
        FormValidation.validate(category);
        if (category.getId() == null || categoryDao.findById(category.getId()) == null) {
            throw new ValidationException("id", "Category không tồn tại.");
        }
        categoryDao.update(toEntity(category));
    }

    @Override
    public void delete(int id) {
        categoryDao.delete(id);
    }

    private CategoryDTO toDTO(Category category) {
        return category == null ? null : new CategoryDTO(category.getId(), category.getName());
    }

    private Category toEntity(CategoryDTO dto) {
        Category category = new Category();
        if (dto.getId() != null) {
            category.setId(dto.getId());
        }
        category.setName(dto.getName());
        return category;
    }
}
