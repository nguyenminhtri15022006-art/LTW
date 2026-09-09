<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="f" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html><html lang="vi"><head><title>Danh sách Product</title></head><body>
<h1 class="mb-4">Danh sách Product</h1>
<%@ include file="/WEB-INF/fragments/messages.jspf" %>
<div class="d-flex justify-content-between mb-4"><p>Tổng: ${totalProducts} sản phẩm · ${pageSize} sản phẩm/trang</p>
<c:if test="${not empty sessionScope.currentUser}"><a class="btn btn-primary" href="${pageContext.request.contextPath}/product?action=add">Thêm Product</a></c:if></div>
<%@ include file="/WEB-INF/fragments/products.jspf" %>
<c:if test="${totalPages > 0}"><nav class="mt-4" aria-label="Phân trang sản phẩm"><ul class="pagination flex-wrap">
<c:if test="${currentPage > 1}"><li class="page-item"><a class="page-link" href="${pageContext.request.contextPath}/product?page=${currentPage-1}">Trước</a></li></c:if>
<c:forEach begin="1" end="${totalPages}" var="i"><li class="page-item ${i == currentPage ? 'active' : ''}"><a class="page-link" href="${pageContext.request.contextPath}/product?page=${i}">${i}</a></li></c:forEach>
<c:if test="${currentPage < totalPages}"><li class="page-item"><a class="page-link" href="${pageContext.request.contextPath}/product?page=${currentPage+1}">Sau</a></li></c:if>
</ul></nav></c:if>
</body></html>