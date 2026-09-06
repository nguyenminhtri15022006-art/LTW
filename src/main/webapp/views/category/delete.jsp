<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="f" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html><html lang="vi"><head><title>Xóa Category</title></head><body>
<h1 class="mb-4">Xóa Category</h1>
<%@ include file="/WEB-INF/fragments/messages.jspf" %>
<p>Xác nhận xóa Category: <strong><c:out value="${category.name}"/></strong>?</p>
<form method="post" action="${pageContext.request.contextPath}/category"><input type="hidden" name="csrf" value="${sessionScope.csrf}"><input type="hidden" name="action" value="delete"><input type="hidden" name="id" value="${category.id}">
<button class="btn btn-danger">Xóa</button> <a href="${pageContext.request.contextPath}/category">Hủy</a></form>
</body></html>