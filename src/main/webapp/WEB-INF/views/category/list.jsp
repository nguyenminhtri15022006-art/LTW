<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="f" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html><html lang="vi"><head><title>Danh sách Category</title></head><body>
<h1 class="mb-4">Danh sách Category</h1>
<%@ include file="/WEB-INF/fragments/messages.jspf" %>
<c:if test="${not empty sessionScope.currentUser}"><a class="btn btn-primary mb-3" href="${pageContext.request.contextPath}/category?action=add">Thêm Category</a></c:if>
<div class="table-responsive"><table class="table table-striped bg-white"><thead><tr><th>ID</th><th>Tên Category</th><th>Thao tác</th></tr></thead><tbody>
<c:forEach var="category" items="${categories}"><tr><td>${category.id}</td><td><c:out value="${category.name}"/></td><td>
<c:if test="${not empty sessionScope.currentUser}"><a class="btn btn-sm btn-outline-primary" href="${pageContext.request.contextPath}/category?action=edit&id=${category.id}">Sửa</a>
<a class="btn btn-sm btn-outline-danger" href="${pageContext.request.contextPath}/category?action=delete&id=${category.id}">Xóa</a></c:if></td></tr></c:forEach>
<c:if test="${empty categories}"><tr><td colspan="3">Chưa có Category.</td></tr></c:if></tbody></table></div>
</body></html>