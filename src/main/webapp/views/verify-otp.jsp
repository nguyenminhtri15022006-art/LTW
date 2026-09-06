<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="f" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html><html lang="vi"><head><title>Xác nhận OTP email</title></head><body>
<h1 class="mb-4">Xác nhận OTP email</h1>
<%@ include file="/WEB-INF/fragments/messages.jspf" %>
<div class="form-panel"><p>Nhập mã 6 chữ số đã gửi đến email. OTP có hiệu lực 5 phút.</p>
<form method="post" action="${pageContext.request.contextPath}/verify-otp"><input type="hidden" name="csrf" value="${sessionScope.csrf}">
<f:input name="email" label="Email" type="email" value="${not empty param.email ? param.email : sessionScope.activationEmail}" /><f:input name="otp" label="Mã OTP" type="text" value="${param.otp}" />
<button class="btn btn-primary" name="action" value="verify">Kích hoạt tài khoản</button>
<button class="btn btn-outline-secondary" name="action" value="resend" formnovalidate>Gửi lại OTP</button></form></div>
</body></html>