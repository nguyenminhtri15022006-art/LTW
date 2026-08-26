package com.example.webapp.service;

import com.example.webapp.model.Category;
import java.util.List;

/**
 * Service interface cho nghiệp vụ liên quan đến Category.
 */
public interface CategoryService {

    /**
     * Lấy tất cả danh mục.
     */
    List<Category> getAll();

    /**
     * Lấy danh mục theo id.
     */
    Category getById(int id);

    /**
     * Tạo mới danh mục.
     */
    void create(Category category);

    /**
     * Cập nhật danh mục.
     */
    void update(Category category);

    /**
     * Xóa danh mục theo id.
     */
    void delete(int id);
}
