<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lỗi Đăng Nhập - BT25-08-2026</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

    <div class="container" style="max-width: 400px;">
        <h1>Đăng Nhập Thất Bại</h1>
        <p class="subtitle">Có lỗi xảy ra trong quá trình xác thực</p>

        <div class="alert alert-error">
            <span class="alert-icon">⚠️</span>
            <div class="alert-title">Sai Thông Tin Đăng Nhập</div>
            <div class="alert-desc">Tên tài khoản hoặc mật khẩu bạn nhập chưa chính xác. Vui lòng kiểm tra lại.</div>
        </div>

        <div class="form-group">
            <a href="${pageContext.request.contextPath}/login" class="btn">Thử lại Đăng nhập</a>
        </div>

        <div class="form-group">
            <a href="${pageContext.request.contextPath}/home" class="btn btn-secondary">Quay lại Trang chủ</a>
        </div>

        <div class="footer-text">
            © 2026 - Phát triển bởi Antigravity
        </div>
    </div>

</body>
</html>
