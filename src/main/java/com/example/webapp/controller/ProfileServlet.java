package com.example.webapp.controller;

import com.example.webapp.dto.*;
import com.example.webapp.service.*;

import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;

import java.io.IOException;

@org.springframework.stereotype.Controller
@org.springframework.web.bind.annotation.RequestMapping({"/profile"})
public class ProfileServlet {
    @org.springframework.beans.factory.annotation.Autowired
    private UserService users;
    @org.springframework.beans.factory.annotation.Autowired
    private UploadService uploads;

    @org.springframework.web.bind.annotation.GetMapping
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (!WebSupport.authenticated(req, resp)) return;
        WebSupport.view(req, resp, "profile");
    }

    @org.springframework.web.bind.annotation.PostMapping
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (!WebSupport.authenticated(req, resp)) return;
        req.setCharacterEncoding("UTF-8");
        String uploaded = null;
        try {
            ProfileDTO d = new ProfileDTO();
            d.setFullName(req.getParameter("fullName"));
            d.setPhone(req.getParameter("phone"));
            FormValidation.validate(d);
            uploaded = uploads.save(req.getPart("image"));
            UserDTO current = (UserDTO) req.getSession().getAttribute("currentUser");
            req.getSession()
                    .setAttribute("currentUser", users.updateProfile(current.getId(), d, uploaded));
            resp.sendRedirect(req.getContextPath() + "/profile?saved=1");
            return;
        } catch (ValidationException e) {
            uploads.discard(uploaded);
            req.setAttribute("errors", e.getErrors());
            req.setAttribute("submitted", true);
        } catch (IllegalStateException e) {
            uploads.discard(uploaded);
            req.setAttribute(
                    "errors", java.util.Map.of("image", "Ảnh tối đa 5 MB; tổng form tối đa 6 MB."));
        } catch (IOException e) {
            uploads.discard(uploaded);
            req.setAttribute(
                    "errors",
                    java.util.Map.of("image", "Không lưu được ảnh. Kiểm tra thư mục upload."));
        } catch (RuntimeException e) {
            uploads.discard(uploaded);
            throw e;
        }
        WebSupport.view(req, resp, "profile");
    }
}
