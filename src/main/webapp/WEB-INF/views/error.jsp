<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="f" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html><html lang="vi"><head><title>Không thể thực hiện yêu cầu</title></head><body>
<h1 class="mb-4">Không thể thực hiện yêu cầu</h1>
<%@ include file="/WEB-INF/fragments/messages.jspf" %>
<c:forEach items="${errors}" var="entry"><c:if test="${entry.key != 'form' && entry.key != 'id'}"><p class="text-danger"><c:out value="${entry.value}"/></p></c:if></c:forEach>
<p>Vui lòng kiểm tra dữ liệu và thử lại.</p><a class="btn btn-primary" href="${pageContext.request.contextPath}/home">Home</a> <a class="btn btn-outline-primary" href="${pageContext.request.contextPath}/login">Login</a>
</body></html>