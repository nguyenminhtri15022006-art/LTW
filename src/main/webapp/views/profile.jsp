<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="f" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html><html lang="vi"><head><title>Hồ sơ cá nhân</title></head><body>
<h1 class="mb-4">Hồ sơ cá nhân</h1>
<%@ include file="/WEB-INF/fragments/messages.jspf" %>
<div class="form-panel">
<c:if test="${param.saved == '1'}"><div class="alert alert-success">Đã cập nhật hồ sơ.</div></c:if>
<c:if test="${not empty sessionScope.currentUser.image}"><img class="profile-image mb-3" src="${pageContext.request.contextPath}/images/<c:out value='${sessionScope.currentUser.image}'/>" alt="Ảnh đại diện"></c:if>
<form method="post" action="${pageContext.request.contextPath}/profile" enctype="multipart/form-data"><input type="hidden" name="csrf" value="${sessionScope.csrf}">
<f:input name="username" label="Username" type="text" value="${sessionScope.currentUser.username}" readonly="true"/><f:input name="email" label="Email" type="email" value="${sessionScope.currentUser.email}" readonly="true"/>
<f:input name="fullName" label="Họ tên" type="text" value="${submitted ? param.fullName : sessionScope.currentUser.fullName}" /><f:input name="phone" label="Số điện thoại" type="text" value="${submitted ? param.phone : sessionScope.currentUser.phone}" />
<%@ include file="/WEB-INF/fragments/upload.jspf" %>
<button class="btn btn-primary">Lưu hồ sơ</button></form></div>
</body></html>