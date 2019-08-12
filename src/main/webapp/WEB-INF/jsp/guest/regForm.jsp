<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>

<html>
<head>
    <title><fmt:message key="page.message.registration"/></title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body>
<jsp:include page="../parts/guestHeader.jsp"/>

<div class="container" style="margin-top: 60px">
    <div class="row">
        <div class="col-md-8 col-md-offset-2">
            <h2 class="page-header"><fmt:message key="page.message.registration"/></h2>
            <form method="post" autocomplete="off" action="${pageContext.request.contextPath}/guest/registration">
                <div class="form-group">
                    <label for="email"><fmt:message key="page.message.email"/></label>
                    <input id="email" class="form-control" required="required"
                           pattern="^[\w\.]{3,32}@[a-z]{3,10}\.[a-z]{2,4}$"
                           type="text" name="email" placeholder="<fmt:message key="page.message.email"/>  |  pattern=^[\w\.]{3,32}@[a-z]{3,10}\.[a-z]{2,4}$"
                           value="${email}">
                    <c:if test="${not empty wrongEmail}">
                        <p class="text-danger">
                            <fmt:message key="info.exception.${wrongEmail}"/>
                        </p>
                    </c:if>
                </div>
                <div class="form-group">
                    <label for="password"><fmt:message key="page.message.password"/></label>
                    <input id="password" class="form-control"  required="required"
                           pattern=".{5,40}"
                           type="password" name="password" placeholder="<fmt:message key="page.message.password"/> |  pattern=.{5,40}">
                    <c:if test="${not empty wrongPassword}">
                        <p class="text-danger">
                            <fmt:message key="info.exception.${wrongPassword}"/>
                        </p>
                    </c:if>
                </div>
                <div class="form-group">
                    <label for="name"><fmt:message key="page.message.name"/></label>
                    <input id="name" class="form-control"
                           type="text" name="name"  required="required"
                           pattern="^[A-Z]{1}[a-z]{0,32}$"
                           placeholder="<fmt:message key="page.message.name"/>" value="${name}">

                    <c:if test="${not empty wrongName}">
                        <p class="text-danger">
                            <fmt:message key="info.exception.${wrongName}"/>
                        </p>
                    </c:if>
                </div>
                <div class="form-group">
                    <label for="surname"><fmt:message key="page.message.surname"/></label>
                    <input id="surname" class="form-control"
                           type="text" name="surname"  required="required"
                           pattern="^[A-Z]{1}[a-z]{0,32}$"
                           placeholder="<fmt:message key="page.message.surname"/>"
                           value="${surname}">
                    <c:if test="${not empty wrongSurname}">
                        <p class="text-danger">
                            <fmt:message key="info.exception.${wrongSurname}"/>
                        </p>
                    </c:if>
                </div>
                <div class="form-group">
                    <label for="patronymic"><fmt:message key="page.message.patronymic"/></label>
                    <input id="patronymic" class="form-control"
                           type="text" name="patronymic"  required="required"
                           pattern="^[A-Z]{1}[a-z]{0,32}$"
                           placeholder="<fmt:message key="page.message.patronymic"/>"
                           value="${patronymic}">
                    <c:if test="${not empty wrongPatronymic}">
                        <p class="text-danger">
                            <fmt:message key="info.exception.${wrongPatronymic}"/>
                        </p>
                    </c:if>
                </div>
                <button type="submit" class="btn btn-success" style="margin-top:30px">
                    <fmt:message key="page.message.registration"/>
                </button>
                <a class="btn btn-secondary" style="margin-top:30px"
                   href="${pageContext.request.contextPath}/guest/login" role="button">
                    <fmt:message key="page.message.login"/>
                </a>
            </form>
        </div>
    </div>
</div>
</body>
</html>