<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>

        <!DOCTYPE html>
        <html lang="vi">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Sửa Category</title>
            <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
        </head>

        <body>

            <div class="container">
                <h1>Sửa Category</h1>
                <p class="subtitle">Cập nhật thông tin category</p>

                <div class="form-card">
                    <form method="post" action="${pageContext.request.contextPath}/category?action=update">

                        <input type="hidden" name="id" value="${category.id}">

                        <div class="form-group">
                            <label>ID</label>
                            <input type="text" value="${category.id}" disabled>
                        </div>

                        <div class="form-group">
                            <label for="name">Tên Category</label>
                            <input type="text" id="name" name="name" value="${category.name}" required>
                        </div>

                        <div class="form-actions">
                            <button type="submit" class="btn">Cập nhật</button>

                            <a class="btn btn-secondary" href="${pageContext.request.contextPath}/category?action=list">
                                Hủy
                            </a>
                        </div>
                    </form>
                </div>
            </div>

        </body>

        </html>