<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>

        <!DOCTYPE html>
        <html lang="vi">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Quản lý Category</title>
            <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
        </head>

        <body>

            <div class="container">
                <h1>Quản lý Category</h1>
                <p class="subtitle">Danh sách các category trong hệ thống</p>

                <div class="top-actions">
                    <a class="btn" href="${pageContext.request.contextPath}/category?action=add">
                        + Thêm Category
                    </a>

                    <a class="btn btn-secondary" href="${pageContext.request.contextPath}/home">
                        ← Về trang chủ
                    </a>
                </div>

                <div class="table-card">
                    <table class="custom-table">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Tên Category</th>
                                <th>Thao tác</th>
                            </tr>
                        </thead>

                        <tbody>
                            <c:choose>
                                <c:when test="${not empty categories}">
                                    <c:forEach var="category" items="${categories}">
                                        <tr>
                                            <td>
                                                <c:out value="${category.id}" />
                                            </td>
                                            <td>
                                                <c:out value="${category.name}" />
                                            </td>
                                            <td>
                                                <div class="action-group">
                                                    <a class="btn btn-sm"
                                                        href="${pageContext.request.contextPath}/category?action=edit&id=${category.id}">
                                                        Sửa
                                                    </a>

                                                    <a class="btn btn-danger btn-sm"
                                                        href="${pageContext.request.contextPath}/category?action=delete&id=${category.id}"
                                                        onclick="return confirm('Bạn có chắc muốn xóa category này không?');">
                                                        Xóa
                                                    </a>
                                                </div>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:when>

                                <c:otherwise>
                                    <tr>
                                        <td colspan="3" class="empty-cell">
                                            Chưa có category nào.
                                        </td>
                                    </tr>
                                </c:otherwise>
                            </c:choose>
                        </tbody>
                    </table>
                </div>
            </div>

        </body>

        </html>