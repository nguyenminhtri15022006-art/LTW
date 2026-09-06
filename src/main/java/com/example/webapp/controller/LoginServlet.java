package com.example.webapp.controller;

import com.example.webapp.dto.*;
import com.example.webapp.service.*;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;

@WebServlet(
        name = "LoginServlet",
        urlPatterns = {"/login", "/home/login", "/logout"})
public class LoginServlet extends HttpServlet {
    private final UserService users = new UserServiceImpl();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if ("logout".equals(req.getParameter("action")) || "/logout".equals(req.getServletPath())) {
            logout(req, resp);
            return;
        }
        if (req.getCookies() != null)
            for (Cookie c : req.getCookies())
                if ("username".equals(c.getName())) {
                    try {
                        req.setAttribute(
                                "savedUsername",
                                URLDecoder.decode(c.getValue(), StandardCharsets.UTF_8));
                    } catch (IllegalArgumentException ignored) {
                    }
                }
        req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if ("/logout".equals(req.getServletPath()) || "logout".equals(req.getParameter("action"))) {
            logout(req, resp);
            return;
        }
        req.setCharacterEncoding("UTF-8");
        try {
            UserDTO user =
                    users.login(
                            new LoginDTO(
                                    req.getParameter("username"), req.getParameter("password")));
            req.getSession();
            req.changeSessionId();
            req.getSession().setAttribute("currentUser", user);
            Cookie c =
                    new Cookie(
                            "username",
                            URLEncoder.encode(user.getUsername(), StandardCharsets.UTF_8));
            c.setPath(req.getContextPath().isEmpty() ? "/" : req.getContextPath());
            c.setHttpOnly(true);
            c.setSecure(req.isSecure());
            c.setAttribute("SameSite", "Lax");
            c.setMaxAge("true".equals(req.getParameter("rememberMe")) ? 7 * 24 * 3600 : 0);
            resp.addCookie(c);
            resp.sendRedirect(req.getContextPath() + "/home");
        } catch (ValidationException e) {
            req.setAttribute("errors", e.getErrors());
            req.setAttribute("savedUsername", req.getParameter("username"));
            req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
        }
    }

    private void logout(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession s = req.getSession(false);
        if (s != null) s.invalidate();
        resp.sendRedirect(req.getContextPath() + "/home");
    }
}
