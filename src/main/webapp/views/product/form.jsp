<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="f" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html><html lang="vi"><head><title>Thông tin Product</title></head><body>
<h1 class="mb-4">Thông tin Product</h1>
<%@ include file="/WEB-INF/fragments/messages.jspf" %>
<div class="form-panel"><form method="post" action="${pageContext.request.contextPath}/product" enctype="multipart/form-data"><input type="hidden" name="csrf" value="${sessionScope.csrf}">
<input type="hidden" name="action" value="${empty product.id ? 'insert' : 'update'}"><input type="hidden" name="id" value="${product.id}">
<f:input name="name" label="Tên sản phẩm" type="text" value="${product.name}" /><f:input name="price" label="Giá (VND)" type="text" value="${submitted ? param.price : product.price}" /><f:input name="stock" label="Tồn kho" type="text" value="${submitted ? param.stock : product.stock}" />
<div class="mb-3"><label class="form-label" for="categoryId">Category</label><select class="form-select" name="categoryId" id="categoryId" required><option value="">Chọn Category</option>
<c:forEach var="c" items="${categories}"><option value="${c.id}" ${c.id == product.categoryId ? 'selected' : ''}><c:out value="${c.name}"/></option></c:forEach></select><div class="text-danger small"><c:out value="${errors.categoryId}"/></div></div>
<div class="mb-3"><label class="form-label" for="description">Mô tả</label><textarea class="form-control" id="description" name="description" rows="5"><c:out value="${product.description}"/></textarea><div class="text-danger small"><c:out value="${errors.description}"/></div></div>
<c:if test="${not empty product.image}"><img class="product-image mb-3" src="${pageContext.request.contextPath}/images/<c:out value='${product.image}'/>" alt="Ảnh hiện tại"></c:if>
<%@ include file="/WEB-INF/fragments/upload.jspf" %>
<button class="btn btn-primary">Lưu sản phẩm</button> <a class="btn btn-outline-secondary" href="${pageContext.request.contextPath}/product">Hủy</a>
</form></div>
</body></html>
