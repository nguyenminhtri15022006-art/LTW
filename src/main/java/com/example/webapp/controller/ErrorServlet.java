package com.example.webapp.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Controller handling request routing to the error view.
 */
@org.springframework.stereotype.Controller
@org.springframework.web.bind.annotation.RequestMapping({"/error"})
public class ErrorServlet implements org.springframework.boot.webmvc.error.ErrorController {
    private static final long serialVersionUID = 1L;


    @org.springframework.web.bind.annotation.GetMapping
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Forward the request to the error.jsp view
        WebSupport.view(request, response, "error");
    }


    @org.springframework.web.bind.annotation.PostMapping
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
