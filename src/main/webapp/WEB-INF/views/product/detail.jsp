<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="f" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html><html lang="vi"><head><title>Chi tiết Product</title></head><body>
<h1 class="mb-4">Chi tiết Product</h1>
<%@ include file="/WEB-INF/fragments/messages.jspf" %>
<div class="row g-4"><div class="col-md-6">
<c:choose><c:when test="${not empty product.image}"><img class="product-detail-image" src="${pageContext.request.contextPath}/images/<c:out value='${product.image}'/>" alt="<c:out value='${product.name}'/>"></c:when><c:otherwise><div class="image-placeholder">Chưa có ảnh</div></c:otherwise></c:choose>
</div><div class="col-md-6"><h2><c:out value="${product.name}"/></h2>
<p>Category: <c:out value="${product.categoryName}"/></p><p class="fs-4 fw-bold"><fmt:formatNumber value="${product.price}" maxFractionDigits="2"/> ₫</p><p>Tồn kho: ${product.stock}</p>
<p class="description"><c:out value="${product.description}"/></p>
<c:if test="${not empty sessionScope.currentUser}"><div class="d-flex gap-2">
<a class="btn btn-primary" href="${pageContext.request.contextPath}/product?action=edit&amp;id=${product.id}">Sửa</a>
<form method="post" action="${pageContext.request.contextPath}/product" onsubmit="return confirm('Xóa sản phẩm này?')"><input type="hidden" name="csrf" value="${sessionScope.csrf}"><input type="hidden" name="action" value="delete"><input type="hidden" name="id" value="${product.id}"><button class="btn btn-danger">Xóa</button></form>
</div></c:if></div></div>
</body></html>