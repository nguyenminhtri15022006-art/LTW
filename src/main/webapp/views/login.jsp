<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đăng Nhập - BT25-08-2026</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

    <div class="container">
        <h1>Đăng Nhập</h1>
        <p class="subtitle">Truy cập vào hệ thống thành viên</p>

        <!-- Login Form posting to /login servlet -->
        <form action="${pageContext.request.contextPath}/login" method="POST">
            <div class="form-group">
                <label for="username" class="form-label">Tài khoản</label>
                <input type="text" id="username" name="username" class="form-input" 
                       placeholder="Nhập tên đăng nhập (ví dụ: admin)" required autocomplete="username"
                       value="${savedUsername}">
            </div>

            <div class="form-group">
                <label for="password" class="form-label">Mật khẩu</label>
                <input type="password" id="password" name="password" class="form-input" 
                       placeholder="Nhập mật khẩu (ví dụ: admin123)" required autocomplete="current-password">
            </div>

            <!-- Checkbox Remember Me: lưu username vào Cookie -->
            <div class="form-group" style="display: flex; align-items: center; gap: 0.5rem;">
                <input type="checkbox" id="rememberMe" name="rememberMe" value="true"
                       style="width: auto; accent-color: var(--accent);">
                <label for="rememberMe" style="color: var(--text-secondary); font-size: 0.9rem; margin: 0; text-transform: none; letter-spacing: normal;">Ghi nhớ tài khoản (Remember Me)</label>
            </div>

            <div class="form-group" style="margin-top: 2rem;">
                <button type="submit" class="btn">Xác nhận Đăng nhập</button>
            </div>

            <div class="form-group">
                <a href="${pageContext.request.contextPath}/home" class="btn btn-secondary">Quay lại Trang chủ</a>
            </div>
        </form>

        <div class="footer-text">
            * Thử tài khoản: <strong>admin</strong> / <strong>admin123</strong> hoặc <strong>nguyen</strong> / <strong>nguyen123</strong>
        </div>
    </div>

</body>
</html>
