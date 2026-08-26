package com.example.webapp.controller;

import com.example.webapp.model.User;
import com.example.webapp.service.UserService;
import com.example.webapp.service.UserServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Controller handling user login, authentication, and logout operations.
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final UserService userService;

    public LoginServlet() {
        this.userService = new UserServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        // Handle logout
        if ("logout".equals(action)) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }

        // Check if user is already logged in, redirect to home if so
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("currentUser") != null) {
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }

        // Đọc Cookie "username" để tự động điền vào ô username (Remember Me)
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("username".equals(cookie.getName())) {
                    request.setAttribute("savedUsername", cookie.getValue());
                }
            }
        }

        // Forward to the login page view
        request.getRequestDispatcher("/views/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Authenticate credentials using the Service layer
        boolean isAuthenticated = userService.authenticate(username, password);

        if (isAuthenticated) {
            // Get detailed User info to store in session
            User user = userService.getUserDetails(username);
            HttpSession session = request.getSession(true);
            session.setAttribute("currentUser", user);

            // Nếu người dùng chọn "Remember Me", tạo Cookie lưu username (7 ngày)
            String rememberMe = request.getParameter("rememberMe");
            if ("true".equals(rememberMe)) {
                Cookie usernameCookie = new Cookie("username", username);
                usernameCookie.setMaxAge(7 * 24 * 60 * 60); // 7 ngày
                usernameCookie.setPath("/");
                response.addCookie(usernameCookie);
            }

            // Redirect to home page URL
            response.sendRedirect(request.getContextPath() + "/home");
        } else {
            // Redirect to error page URL on authentication failure
            response.sendRedirect(request.getContextPath() + "/error");
        }
    }
}
