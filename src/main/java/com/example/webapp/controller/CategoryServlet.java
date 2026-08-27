package com.example.webapp.controller;

import com.example.webapp.dto.CategoryDTO;
import com.example.webapp.service.CategoryService;
import com.example.webapp.service.CategoryServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Controller xử lý các thao tác CRUD cho Category.
 * URL pattern: /category?action=list|add|insert|edit|update|delete
 */
@WebServlet(name = "CategoryServlet", urlPatterns = {"/category"})
public class CategoryServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final CategoryService categoryService;

    public CategoryServlet() {
        this.categoryService = new CategoryServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy action từ query parameter, mặc định là "list"
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "add":
                // Hiển thị form thêm mới category
                showAddForm(request, response);
                break;
            case "edit":
                // Hiển thị form chỉnh sửa category
                showEditForm(request, response);
                break;
            case "delete":
                // Xóa category theo id
                deleteCategory(request, response);
                break;
            case "list":
            default:
                // Hiển thị danh sách category
                listCategories(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Đặt encoding cho request để hỗ trợ tiếng Việt
        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "insert":
                // Thêm mới category
                insertCategory(request, response);
                break;
            case "update":
                // Cập nhật category
                updateCategory(request, response);
                break;
            default:
                listCategories(request, response);
                break;
        }
    }

    /**
     * Hiển thị danh sách tất cả category.
     */
    private void listCategories(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<CategoryDTO> categories = categoryService.getAll();
        request.setAttribute("categories", categories);
        request.getRequestDispatcher("/views/category/list.jsp").forward(request, response);
    }

    /**
     * Forward tới form thêm mới category.
     */
    private void showAddForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/views/category/add.jsp").forward(request, response);
    }

    /**
     * Forward tới form chỉnh sửa category.
     */
    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        CategoryDTO category = categoryService.getById(id);
        request.setAttribute("category", category);
        request.getRequestDispatcher("/views/category/edit.jsp").forward(request, response);
    }

    /**
     * Xử lý thêm mới category từ form POST.
     */
    private void insertCategory(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String name = request.getParameter("name");
        CategoryDTO dto = new CategoryDTO();
        dto.setName(name);
        categoryService.create(dto);
        // Redirect về danh sách sau khi thêm
        response.sendRedirect(request.getContextPath() + "/category?action=list");
    }

    /**
     * Xử lý cập nhật category từ form POST.
     */
    private void updateCategory(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        CategoryDTO dto = new CategoryDTO(id, name);
        categoryService.update(dto);
        // Redirect về danh sách sau khi cập nhật
        response.sendRedirect(request.getContextPath() + "/category?action=list");
    }

    /**
     * Xử lý xóa category theo id.
     */
    private void deleteCategory(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        categoryService.delete(id);
        // Redirect về danh sách sau khi xóa
        response.sendRedirect(request.getContextPath() + "/category?action=list");
    }
}
