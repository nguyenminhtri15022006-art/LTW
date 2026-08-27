package com.example.webapp.dao;

import com.example.webapp.entity.Category;
import java.util.List;

/**
 * Data Access Object (DAO) interface cho các thao tác CRUD với Category.
 */
public interface CategoryDao {

    /**
     * Lấy tất cả danh mục.
     * @return danh sách Category
     */
    List<Category> findAll();

    /**
     * Tìm danh mục theo id.
     * @param id id cần tìm
     * @return Category nếu tìm thấy, null nếu không
     */
    Category findById(int id);

    /**
     * Thêm mới một danh mục.
     * @param category đối tượng Category cần thêm
     */
    void insert(Category category);

    /**
     * Cập nhật một danh mục đã tồn tại.
     * @param category đối tượng Category chứa thông tin mới
     */
    void update(Category category);

    /**
     * Xóa danh mục theo id.
     * @param id id của danh mục cần xóa
     */
    void delete(int id);
}
