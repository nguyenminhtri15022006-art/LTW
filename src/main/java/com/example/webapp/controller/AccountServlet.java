package com.example.webapp.controller;

import com.example.webapp.dto.*;
import com.example.webapp.service.*;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@org.springframework.stereotype.Controller
@org.springframework.web.bind.annotation.RequestMapping({"/register", "/verify-otp", "/forgot-password", "/reset-password"})
public class AccountServlet {
    @org.springframework.beans.factory.annotation.Autowired
    private UserService users;

    @org.springframework.web.bind.annotation.GetMapping
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        view(req, resp);
    }

    @org.springframework.web.bind.annotation.PostMapping
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String email = req.getParameter("email");
        try {
            switch (req.getServletPath()) {
                case "/register":
                    RegisterDTO d = new RegisterDTO();
                    d.setUsername(req.getParameter("username"));
                    d.setPassword(req.getParameter("password"));
                    d.setFullName(req.getParameter("fullName"));
                    d.setEmail(email);
                    users.register(d);
                    req.getSession().setAttribute("activationEmail", email);
                    resp.sendRedirect(req.getContextPath() + "/verify-otp");
                    return;
                case "/verify-otp":
                    if ("resend".equals(req.getParameter("action"))) {
                        users.sendActivation(email);
                        req.setAttribute("message", "Đã gửi OTP mới. Mã có hiệu lực 5 phút.");
                    } else {
                        users.activate(email, req.getParameter("otp"));
                        req.getSession().removeAttribute("activationEmail");
                        resp.sendRedirect(req.getContextPath() + "/login?activated=1");
                        return;
                    }
                    break;
                case "/forgot-password":
                    users.forgotPassword(email);
                    req.getSession().setAttribute("resetEmail", email);
                    resp.sendRedirect(req.getContextPath() + "/reset-password");
                    return;
                case "/reset-password":
                    users.resetPassword(
                            email, req.getParameter("otp"), req.getParameter("password"));
                    HttpSession session = req.getSession(false);
                    if (session != null) session.invalidate();
                    resp.sendRedirect(req.getContextPath() + "/login?reset=1");
                    return;
                default:
                    resp.sendError(404);
                    return;
            }
        } catch (ValidationException e) {
            req.setAttribute("errors", e.getErrors());
        }
        view(req, resp);
    }

    private void view(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        WebSupport.view(req, resp, req.getServletPath().substring(1));
    }
}
