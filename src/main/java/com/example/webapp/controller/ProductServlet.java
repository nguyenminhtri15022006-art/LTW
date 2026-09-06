package com.example.webapp.controller;

import com.example.webapp.dto.ProductDTO;
import com.example.webapp.service.*;

import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/product")
@MultipartConfig(maxFileSize = 5242880, maxRequestSize = 6291456, fileSizeThreshold = 0)
public class ProductServlet extends HttpServlet {
    private final ProductService products = new ProductServiceImpl();
    private final CategoryService categories = new CategoryServiceImpl();
    private final UploadService uploads = new UploadService();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter("action");
        try {
            if ("add".equals(action) || "edit".equals(action)) {
                if (!WebSupport.authenticated(req, resp)) return;
                ProductDTO d =
                        "edit".equals(action)
                                ? products.getById(WebSupport.id(req))
                                : new ProductDTO();
                if (d == null) {
                    resp.sendError(404);
                    return;
                }
                req.setAttribute("product", d);
                form(req, resp);
                return;
            }
            if ("detail".equals(action)) {
                ProductDTO d = products.getById(WebSupport.id(req));
                if (d == null) {
                    resp.sendError(404);
                    return;
                }
                req.setAttribute("product", d);
                WebSupport.view(req, resp, "product/detail");
                return;
            }
            if (action != null && !"list".equals(action)) {
                resp.sendError(400);
                return;
            }
            long total = products.count();
            long pages = (total + ProductService.PAGE_SIZE - 1) / ProductService.PAGE_SIZE;
            int page = 1;
            try {
                if (req.getParameter("page") != null)
                    page = Integer.parseInt(req.getParameter("page"));
            } catch (NumberFormatException ignored) {
            }
            page =
                    (int)
                            Math.max(
                                    1,
                                    Math.min(
                                            page,
                                            Math.max(
                                                    1,
                                                    Math.min(
                                                            pages,
                                                            Integer.MAX_VALUE
                                                                    / ProductService.PAGE_SIZE))));
            req.setAttribute("products", products.page(page));
            req.setAttribute("currentPage", page);
            req.setAttribute("pageSize", ProductService.PAGE_SIZE);
            req.setAttribute("totalProducts", total);
            req.setAttribute("totalPages", pages);
            WebSupport.view(req, resp, "product/list");
        } catch (ValidationException e) {
            resp.setStatus(400);
            req.setAttribute("errors", e.getErrors());
            WebSupport.view(req, resp, "error");
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (!WebSupport.authenticated(req, resp)) return;
        req.setCharacterEncoding("UTF-8");
        String uploaded = null;
        ProductDTO d = new ProductDTO();
        try {
            String action = req.getParameter("action");
            if ("delete".equals(action)) {
                products.delete(WebSupport.id(req));
                resp.sendRedirect(req.getContextPath() + "/product");
                return;
            }
            if (!"insert".equals(action) && !"update".equals(action)) {
                resp.sendError(400);
                return;
            }
            if ("update".equals(action)) d.setId(WebSupport.id(req));
            d.setName(req.getParameter("name"));
            d.setDescription(req.getParameter("description"));
            try {
                d.setPrice(new BigDecimal(req.getParameter("price")));
            } catch (Exception ignored) {
            }
            try {
                d.setStock(Integer.valueOf(req.getParameter("stock")));
            } catch (Exception ignored) {
            }
            try {
                d.setCategoryId(Integer.valueOf(req.getParameter("categoryId")));
            } catch (Exception ignored) {
            }
            FormValidation.validate(d);
            uploaded = uploads.save(req.getPart("image"));
            d.setImage(uploaded);
            products.save(d);
            resp.sendRedirect(req.getContextPath() + "/product");
        } catch (ValidationException e) {
            uploads.discard(uploaded);
            req.setAttribute("errors", e.getErrors());
            req.setAttribute("product", d);
            req.setAttribute("submitted", true);
            form(req, resp);
        } catch (IllegalStateException e) {
            uploads.discard(uploaded);
            req.setAttribute(
                    "errors", java.util.Map.of("image", "Ảnh tối đa 5 MB; tổng form tối đa 6 MB."));
            req.setAttribute("product", d);
            form(req, resp);
        } catch (IOException e) {
            uploads.discard(uploaded);
            req.setAttribute(
                    "errors",
                    java.util.Map.of("image", "Không lưu được ảnh. Kiểm tra thư mục upload."));
            req.setAttribute("product", d);
            form(req, resp);
        } catch (RuntimeException e) {
            uploads.discard(uploaded);
            throw e;
        }
    }

    private void form(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("categories", categories.getAll());
        WebSupport.view(req, resp, "product/form");
    }
}
