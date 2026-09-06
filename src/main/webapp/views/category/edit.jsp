<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="f" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html><html lang="vi"><head><title>Sửa Category</title></head><body>
<h1 class="mb-4">Sửa Category</h1>
<%@ include file="/WEB-INF/fragments/messages.jspf" %>
<div class="form-panel"><form method="post" action="${pageContext.request.contextPath}/category"><input type="hidden" name="csrf" value="${sessionScope.csrf}">
<input type="hidden" name="action" value="update"><input type="hidden" name="id" value="${category.id}">
<f:input name="name" label="Tên Category" type="text" value="${category.name}" /><button class="btn btn-primary">Lưu</button> <a class="btn btn-outline-secondary" href="${pageContext.request.contextPath}/category">Hủy</a></form></div>
</body></html>