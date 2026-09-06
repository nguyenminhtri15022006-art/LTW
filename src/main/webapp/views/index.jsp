<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="f" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html><html lang="vi"><head><title>10 sản phẩm mới nhất</title></head><body>
<h1 class="mb-4">10 sản phẩm mới nhất</h1>
<%@ include file="/WEB-INF/fragments/messages.jspf" %>
<c:if test="${not empty sessionScope.currentUser}"><p>Xin chào, <c:out value="${sessionScope.currentUser.fullName}"/>!</p></c:if>
<%@ include file="/WEB-INF/fragments/products.jspf" %>
</body></html>