<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="f" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html><html lang="vi"><head><title>Đăng ký tài khoản</title></head><body>
<h1 class="mb-4">Đăng ký tài khoản</h1>
<%@ include file="/WEB-INF/fragments/messages.jspf" %>
<div class="form-panel"><form method="post" action="${pageContext.request.contextPath}/register"><input type="hidden" name="csrf" value="${sessionScope.csrf}">
<f:input name="username" label="Username" type="text" value="${param.username}" /><f:input name="password" label="Mật khẩu (8–128 ký tự)" type="password" value="" /><f:input name="fullName" label="Họ tên" type="text" value="${param.fullName}" /><f:input name="email" label="Email" type="email" value="${param.email}" />
<button class="btn btn-primary">Đăng ký và gửi OTP</button></form>
<p class="mt-3">Đã đăng ký nhưng chưa nhận mã? <a href="${pageContext.request.contextPath}/verify-otp">Nhập email để gửi lại OTP</a>.</p></div>
</body></html>