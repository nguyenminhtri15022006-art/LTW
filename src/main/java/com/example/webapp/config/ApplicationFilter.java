package com.example.webapp.config;

import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.IOException;

public class ApplicationFilter implements Filter {
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("X-Content-Type-Options", "nosniff");

        String servletPath = req.getServletPath() == null ? "" : req.getServletPath();
        if (servletPath.startsWith("/views/")) {
            resp.sendError(404);
            return;
        }

        if (!servletPath.equals("/images") && !servletPath.startsWith("/css/") && !servletPath.startsWith("/js/")) {
            resp.setHeader("Cache-Control", "no-store");
            HttpSession session = req.getSession();
            if (session.getAttribute("csrf") == null) {
                session.setAttribute("csrf", java.util.UUID.randomUUID().toString());
            }
            if ("POST".equals(req.getMethod())) {
                String token;
                try {
                    token = req.getParameter("csrf");
                } catch (IllegalStateException e) {
                    resp.sendError(413, "Ảnh tối đa 5 MB; tổng form tối đa 6 MB.");
                    return;
                }
                if (token == null || !session.getAttribute("csrf").equals(token)) {
                    resp.sendError(403, "Phiên form đã hết hạn. Hãy tải lại trang.");
                    return;
                }
            }
        }

        chain.doFilter(req, resp);
    }
}
