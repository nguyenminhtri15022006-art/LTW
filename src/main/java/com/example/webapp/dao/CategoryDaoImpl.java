package com.example.webapp.dao;

import com.example.webapp.model.Category;
import java.util.ArrayList;
import java.util.List;

/**
 * Triển khai CategoryDao sử dụng ArrayList lưu trữ trong bộ nhớ.
 * Có thể thay thế bằng JDBC/Database sau này mà không cần sửa các lớp khác.
 */
public class CategoryDaoImpl implements CategoryDao {

    // Danh sách in-memory thay cho database (tạm thời)
    private static final List<Category> categoryList = new ArrayList<>();

    // Biến tự tăng id, giống AUTO_INCREMENT trong database
    private static int autoIncrementId = 1;

    // Dữ liệu mẫu ban đầu
    static {
        categoryList.add(new Category(autoIncrementId++, "Công nghệ"));
        categoryList.add(new Category(autoIncrementId++, "Giáo dục"));
        categoryList.add(new Category(autoIncrementId++, "Giải trí"));
    }

    @Override
    public List<Category> findAll() {
        // Trả về bản sao để tránh thay đổi trực tiếp từ bên ngoài
        return new ArrayList<>(categoryList);
    }

    @Override
    public Category findById(int id) {
        // Duyệt danh sách tìm category theo id
        for (Category c : categoryList) {
            if (c.getId() == id) {
                return c;
            }
        }
        return null;
    }

    @Override
    public void insert(Category category) {
        // Gán id tự tăng và thêm vào danh sách
        category.setId(autoIncrementId++);
        categoryList.add(category);
    }

    @Override
    public void update(Category category) {
        // Tìm category cũ theo id và cập nhật thông tin
        for (int i = 0; i < categoryList.size(); i++) {
            if (categoryList.get(i).getId() == category.getId()) {
                categoryList.set(i, category);
                return;
            }
        }
    }

    @Override
    public void delete(int id) {
        // Xóa category theo id khỏi danh sách
        categoryList.removeIf(c -> c.getId() == id);
    }
}
