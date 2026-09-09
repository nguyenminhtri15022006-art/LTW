package com.example.webapp.controller;

import com.example.webapp.service.ValidationException;

import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.IOException;

public final class WebSupport {
    private WebSupport() {}

    public static long id(HttpServletRequest req) {
        try {
            long id = Long.parseLong(req.getParameter("id"));
            if (id <= 0) throw new NumberFormatException();
            return id;
        } catch (NumberFormatException e) {
            throw new ValidationException("id", "ID không hợp lệ.");
        }
    }

    public static boolean authenticated(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        HttpSession s = req.getSession(false);
        if (s != null && s.getAttribute("currentUser") != null) return true;
        resp.sendRedirect(req.getContextPath() + "/login");
        return false;
    }

    public static void view(HttpServletRequest req, HttpServletResponse resp, String path)
            throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        req.setAttribute("decoratedView", "/WEB-INF/views/" + path + ".jsp");
        req.getRequestDispatcher("/WEB-INF/decorators/main.jsp").forward(req, resp);
    }
}
