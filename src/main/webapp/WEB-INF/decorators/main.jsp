<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="f" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html lang="vi"><head><meta charset="UTF-8"><meta name="viewport" content="width=device-width,initial-scale=1">
<title>Web Java</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
</head><body>
<nav class="navbar navbar-expand-md navbar-dark bg-dark"><div class="container">
<a class="navbar-brand" href="${pageContext.request.contextPath}/home">Web Java</a>
<button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navigation" aria-label="Mở menu"><span class="navbar-toggler-icon"></span></button>
<div class="collapse navbar-collapse" id="navigation"><div class="navbar-nav me-auto">
<a class="nav-link" href="${pageContext.request.contextPath}/home">Home</a>
<a class="nav-link" href="${pageContext.request.contextPath}/product">Product</a>
<a class="nav-link" href="${pageContext.request.contextPath}/category">Category</a>
<a class="nav-link" href="${pageContext.request.contextPath}/profile">Profile</a></div>
<c:choose><c:when test="${not empty sessionScope.currentUser}">
<span class="navbar-text me-3"><c:out value="${sessionScope.currentUser.fullName}"/></span>
<form action="${pageContext.request.contextPath}/logout" method="post"><input type="hidden" name="csrf" value="${sessionScope.csrf}"><button class="btn btn-outline-light">Logout</button></form>
</c:when><c:otherwise><a class="btn btn-outline-light" href="${pageContext.request.contextPath}/login">Login</a></c:otherwise></c:choose>
</div></div></nav>
<main class="container py-5 flex-grow-1">
    <jsp:include page="${decoratedView}" />
</main>
<footer class="border-top bg-white py-4"><div class="container text-secondary">© 2026 · Bài tập Lập trình Web Java</div></footer>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body></html>