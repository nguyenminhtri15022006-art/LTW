package com.example.webapp.controller;

import com.example.webapp.dto.CategoryDTO;
import com.example.webapp.service.*;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet(name = "CategoryServlet", urlPatterns = "/category")
public class CategoryServlet extends HttpServlet {
    private final CategoryService categories = new CategoryServiceImpl();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            String action = req.getParameter("action");
            if ("add".equals(action)) {
                if (WebSupport.authenticated(req, resp)) WebSupport.view(req, resp, "category/add");
                return;
            }
            if ("edit".equals(action) || "delete".equals(action)) {
                if (!WebSupport.authenticated(req, resp)) return;
                CategoryDTO d = categories.getById(id(req));
                if (d == null) {
                    resp.sendError(404);
                    return;
                }
                req.setAttribute("category", d);
                WebSupport.view(
                        req, resp, "delete".equals(action) ? "category/delete" : "category/edit");
                return;
            }
            list(req, resp);
        } catch (ValidationException e) {
            resp.setStatus(400);
            req.setAttribute("errors", e.getErrors());
            list(req, resp);
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (!WebSupport.authenticated(req, resp)) return;
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        CategoryDTO d = new CategoryDTO();
        d.setName(req.getParameter("name"));
        try {
            if ("delete".equals(action)) {
                categories.delete(id(req));
            } else if ("insert".equals(action)) {
                categories.create(d);
            } else if ("update".equals(action)) {
                d.setId(id(req));
                categories.update(d);
            } else {
                resp.sendError(400);
                return;
            }
            resp.sendRedirect(req.getContextPath() + "/category");
        } catch (ValidationException e) {
            req.setAttribute("errors", e.getErrors());
            req.setAttribute("category", d);
            if ("delete".equals(action)) list(req, resp);
            else
                WebSupport.view(
                        req, resp, "update".equals(action) ? "category/edit" : "category/add");
        } catch (jakarta.persistence.PersistenceException e) {
            req.setAttribute(
                    "errors",
                    java.util.Map.of(
                            "form",
                            "Không thể lưu/xóa Category. Hãy chuyển hoặc xóa các Product thuộc"
                                + " Category trước."));
            if ("delete".equals(action)) list(req, resp);
            else {
                req.setAttribute("category", d);
                WebSupport.view(
                        req, resp, "update".equals(action) ? "category/edit" : "category/add");
            }
        }
    }

    private int id(HttpServletRequest req) {
        long id = WebSupport.id(req);
        if (id > Integer.MAX_VALUE) throw new ValidationException("id", "ID quá lớn.");
        return (int) id;
    }

    private void list(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("categories", categories.getAll());
        WebSupport.view(req, resp, "category/list");
    }
}
