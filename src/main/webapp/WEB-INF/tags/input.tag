<%@ tag pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ attribute name="name" required="true" %>
<%@ attribute name="label" required="true" %>
<%@ attribute name="type" required="false" %>
<%@ attribute name="value" required="false" %>
<%@ attribute name="readonly" required="false" type="java.lang.Boolean" %>
<div class="mb-3">
<label class="form-label" for="${name}"><c:out value="${label}"/></label>
<c:choose><c:when test="${readonly}">
<input class="form-control" id="${name}" name="${name}" type="${empty type ? 'text' : type}" value="<c:out value='${value}'/>" readonly>
</c:when><c:otherwise>
<input class="form-control ${not empty requestScope.errors[name] ? 'is-invalid' : ''}" id="${name}" name="${name}" type="${empty type ? 'text' : type}" value="<c:out value='${value}'/>" ${name != 'phone' ? 'required' : ''}>
</c:otherwise></c:choose>
<div class="text-danger small" role="alert"><c:out value="${requestScope.errors[name]}"/></div>
</div>
