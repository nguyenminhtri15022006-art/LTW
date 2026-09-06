<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="f" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html><html lang="vi"><head><title>Quên mật khẩu</title></head><body>
<h1 class="mb-4">Quên mật khẩu</h1>
<%@ include file="/WEB-INF/fragments/messages.jspf" %>
<div class="form-panel"><form method="post" action="${pageContext.request.contextPath}/forgot-password"><input type="hidden" name="csrf" value="${sessionScope.csrf}">
<f:input name="email" label="Email đã đăng ký" type="email" value="${param.email}" />
<button class="btn btn-primary">Gửi OTP đặt lại mật khẩu</button></form></div>
</body></html>