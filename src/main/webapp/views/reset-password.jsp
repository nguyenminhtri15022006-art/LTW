<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="f" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html><html lang="vi"><head><title>Đặt lại mật khẩu</title></head><body>
<h1 class="mb-4">Đặt lại mật khẩu</h1>
<%@ include file="/WEB-INF/fragments/messages.jspf" %>
<div class="form-panel"><p>OTP có hiệu lực 5 phút và chỉ dùng một lần.</p>
<form method="post" action="${pageContext.request.contextPath}/reset-password"><input type="hidden" name="csrf" value="${sessionScope.csrf}">
<f:input name="email" label="Email" type="email" value="${not empty param.email ? param.email : sessionScope.resetEmail}" /><f:input name="otp" label="Mã OTP" type="text" value="${param.otp}" /><f:input name="password" label="Mật khẩu mới (8–128 ký tự)" type="password" value="" />
<button class="btn btn-primary">Đổi mật khẩu</button></form><a class="d-inline-block mt-3" href="${pageContext.request.contextPath}/forgot-password">Yêu cầu OTP mới</a></div>
</body></html>