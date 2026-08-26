<%@ page contentType="text/html;charset=UTF-8" language="java" %>

    <!DOCTYPE html>
    <html lang="vi">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Thêm Category</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    </head>

    <body>

        <div class="container">
            <h1>Thêm Category</h1>
            <p class="subtitle">Nhập thông tin category mới</p>

            <div class="form-card">
                <form method="post" action="${pageContext.request.contextPath}/category?action=insert">

                    <div class="form-group">
                        <label for="name">Tên Category</label>
                        <input type="text" id="name" name="name" placeholder="Ví dụ: Công nghệ" required>
                    </div>

                    <div class="form-actions">
                        <button type="submit" class="btn">Lưu</button>

                        <a class="btn btn-secondary" href="${pageContext.request.contextPath}/category?action=list">
                            Hủy
                        </a>
                    </div>
                </form>
            </div>
        </div>

    </body>

    </html>