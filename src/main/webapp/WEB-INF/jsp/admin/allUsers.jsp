<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>

<html>
<head>
    <title><fmt:message key="page.message.all.users"/></title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body>
<jsp:include page="../parts/adminHeader.jsp"/>
<div class="mx-1">
    <c:if test="${not empty users}">
        <h1><fmt:message key="page.message.all.users"/>:</h1>
        <table class="table">
            <thead>
            <tr>
                <td><fmt:message key="page.message.id"/></td>
                <td><fmt:message key="page.message.email"/></td>
                <td><fmt:message key="page.message.name"/></td>
                <td><fmt:message key="page.message.surname"/></td>
                <td><fmt:message key="page.message.patronymic"/></td>
                <td><fmt:message key="page.message.role"/></td>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${users}" var="user">
                <tr>
                    <td>${user.getId()}</td>
                    <td>${user.getEmail()}</td>
                    <td>${user.getName()}</td>
                    <td>${user.getSurname()}</td>
                    <td>${user.getPatronymic()}</td>
                    <td>${user.getRole()}</td>
                </tr>
            </c:forEach>

            </tbody>
        </table>
    </c:if>
    <c:if test="${empty users}">
        <div class="alert alert-warning" role="alert">
            <fmt:message key="page.message.failed.get.users"/>
        </div>
    </c:if>
</div>
</body>
</html>
