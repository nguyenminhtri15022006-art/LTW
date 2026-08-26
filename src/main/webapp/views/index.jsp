<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>

        <!DOCTYPE html>
        <html lang="vi">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">

            <title>Trang Chủ - BT25-08-2026</title>

            <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
        </head>

        <body>

            <div class="container">

                <h1>Hệ Thống Web</h1>

                <p class="subtitle">
                    Trang chủ của dự án Java Servlet & JSP
                </p>

                <c:choose>

                    <c:when test="${not empty currentUser}">

                        <div class="alert alert-success">

                            <span class="alert-icon">👤</span>

                            <div class="alert-title">
                                Xin chào,
                                <c:out value="${currentUser.fullName}" />!
                            </div>

                            <div class="alert-desc">
                                Bạn đã đăng nhập thành công vào hệ thống.
                            </div>

                        </div>


                        <div class="user-panel">

                            <div class="user-row">

                                <span class="user-label">
                                    Tài khoản:
                                </span>

                                <span class="user-val">
                                    <c:out value="${currentUser.username}" />
                                </span>

                            </div>


                            <div class="user-row">

                                <span class="user-label">
                                    Họ và tên:
                                </span>

                                <span class="user-val">
                                    <c:out value="${currentUser.fullName}" />
                                </span>

                            </div>


                            <div class="user-row">

                                <span class="user-label">
                                    Vai trò:
                                </span>

                                <span class="user-val">
                                    Thành viên chính thức
                                </span>

                            </div>

                        </div>


                        <a href="${pageContext.request.contextPath}/category?action=list" class="btn">
                            Quản lý Category
                        </a>


                        <a href="${pageContext.request.contextPath}/login?action=logout" class="btn btn-danger">
                            Đăng xuất
                        </a>

                    </c:when>


                    <c:otherwise>

                        <div class="alert alert-error" style="background: rgba(255,255,255,0.03);
                        border-color: rgba(255,255,255,0.1);
                        color: var(--text-secondary);">

                            <span class="alert-icon" style="filter: grayscale(1);">
                                🔒
                            </span>

                            <div class="alert-title" style="color: var(--text-primary);">
                                Chưa Đăng Nhập
                            </div>

                            <div class="alert-desc">
                                Vui lòng đăng nhập để xem thông tin chi tiết.
                            </div>

                        </div>


                        <a href="${pageContext.request.contextPath}/login" class="btn">
                            Đăng nhập ngay
                        </a>

                    </c:otherwise>

                </c:choose>


                <div class="footer-text">
                    © 2026 - Phát triển bởi Antigravity
                </div>

            </div>

        </body>

        </html>