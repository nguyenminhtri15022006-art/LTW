<%@ page contentType="text/html;charset=UTF-8" %>

<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="f" tagdir="/WEB-INF/tags" %>

<!DOCTYPE html>
<html lang="vi">

<head>
    <title>Đăng nhập</title>
</head>

<body>

<h1 class="mb-4">Đăng nhập</h1>

<%@ include file="/WEB-INF/fragments/messages.jspf" %>

<div class="form-panel">

    <!-- Nếu URL có ?activated=1 -->
    <c:if test="${param.activated == '1'}">
        <div class="alert alert-success">
            Đã kích hoạt tài khoản. Hãy đăng nhập.
        </div>
    </c:if>

    <!-- Nếu URL có ?reset=1 -->
    <c:if test="${param.reset == '1'}">
        <div class="alert alert-success">
            Đã đổi mật khẩu. Hãy đăng nhập.
        </div>
    </c:if>


    <!-- FORM ĐĂNG NHẬP -->
    <form
        method="post"
        action="${pageContext.request.contextPath}/login">

        <!-- CSRF token -->
        <input
            type="hidden"
            name="csrf"
            value="${sessionScope.csrf}"
        >

        <!-- Username -->
        <f:input
            name="username"
            label="Username"
            type="text"
            value="${savedUsername}"
        />

        <!-- Password -->
        <f:input
            name="password"
            label="Mật khẩu"
            type="password"
            value=""
        />

        <!-- Remember me -->
        <div class="form-check mb-3">

            <input
                class="form-check-input"
                id="rememberMe"
                type="checkbox"
                name="rememberMe"
                value="true"
                ${param.rememberMe == 'true' ? 'checked' : ''}
            >

            <label
                class="form-check-label"
                for="rememberMe">
                Ghi nhớ username
            </label>

        </div>

        <button class="btn btn-primary">
            Đăng nhập
        </button>

    </form>


    <!-- CÁC LINK KHÁC -->
    <div class="mt-4 d-flex flex-wrap gap-3">

        <a href="${pageContext.request.contextPath}/register">
            Đăng ký
        </a>

        <a href="${pageContext.request.contextPath}/forgot-password">
            Quên mật khẩu
        </a>

        <a href="${pageContext.request.contextPath}/verify-otp">
            Xác nhận email
        </a>

    </div>

</div>

</body>
</html>