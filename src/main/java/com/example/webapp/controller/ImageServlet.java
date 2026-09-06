package com.example.webapp.controller;

import com.example.webapp.service.UploadService;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.nio.file.*;

@WebServlet("/images/*")
public class ImageServlet extends HttpServlet {
    private final UploadService uploads = new UploadService();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String path = req.getPathInfo();
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
