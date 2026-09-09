package com.example.webapp.controller;

import com.example.webapp.service.*;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@org.springframework.stereotype.Controller
@org.springframework.web.bind.annotation.RequestMapping({"/", "/home"})
public class HomeServlet {
    @org.springframework.beans.factory.annotation.Autowired
    private ProductService products;

    @org.springframework.web.bind.annotation.GetMapping
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("products", products.newest());
        WebSupport.view(req, resp, "index");
    }

    @org.springframework.web.bind.annotation.PostMapping
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendRedirect(req.getContextPath() + "/home");
    }
}
