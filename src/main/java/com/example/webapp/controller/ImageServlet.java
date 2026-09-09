package com.example.webapp.controller;

import com.example.webapp.service.UploadService;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.nio.file.*;

@org.springframework.stereotype.Controller
@org.springframework.web.bind.annotation.RequestMapping({"/images/{filename}"})
public class ImageServlet {
    @org.springframework.beans.factory.annotation.Autowired
    private UploadService uploads;

    @org.springframework.web.bind.annotation.GetMapping
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String path = req.getRequestURI().substring(req.getContextPath().length() + "/images".length());
        Path file = uploads.resolve(path == null ? null : path.substring(1));
        if (file == null || !Files.isRegularFile(file)) {
            resp.sendError(404);
            return;
        }
        String ext = file.toString().substring(file.toString().lastIndexOf('.') + 1);
        resp.setContentType("image/" + ("jpg".equals(ext) ? "jpeg" : ext));
        resp.setHeader("X-Content-Type-Options", "nosniff");
        resp.setHeader("Cache-Control", "public, max-age=86400");
        resp.setContentLengthLong(Files.size(file));
        Files.copy(file, resp.getOutputStream());
    }
}
