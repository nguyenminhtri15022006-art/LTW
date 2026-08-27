package com.example.webapp.service;

import com.example.webapp.dto.CategoryDTO;
import java.util.List;

/**
 * Service interface cho nghiệp vụ liên quan đến Category.
 */
public interface CategoryService {

    /**
     * Lấy tất cả danh mục.
     */
    List<CategoryDTO> getAll();

    /**
     * Lấy danh mục theo id.
     */
    CategoryDTO getById(int id);

    /**
     * Tạo mới danh mục.
     */
    void create(CategoryDTO category);

    /**
     * Cập nhật danh mục.
     */
    void update(CategoryDTO category);

    /**
     * Xóa danh mục theo id.
     */
    void delete(int id);
}
