<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>
<%@ page language="java" isErrorPage="true"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<html>
<head>
    <title><fmt:message key="page.no.such"/></title>
    <link href="<c:url value='/bootstrap/css/bootstrap.min.css' />" rel="stylesheet">

</head>
<body>
<c:choose>
    <c:when test="${sessionScope.get('user').getRole() == 'ADMIN'}">
        <jsp:include page="../parts/adminHeader.jsp"/>
    </c:when>
    <c:when test="${sessionScope.get('user').getRole() == 'USER'}">
        <jsp:include page="../parts/userHeader.jsp"/>
    </c:when>
    <c:otherwise>
        <jsp:include page="../parts/guestHeader.jsp"/>
    </c:otherwise>
</c:choose>

<div class="container" style="margin-top: 60px">
    <div class="d-flex justify-content-center">
        <div class="col-md-8 col-md-offset-2">
            <div class="alert alert-danger" role="alert">
                <fmt:message key="page.message.error"/>:
                <fmt:message key="<%= exception.getMessage() %>"/>
            </div>
        </div>
    </div>
</div>
<jsp:include page="../parts/footer.jsp"/>
</body>
</html>