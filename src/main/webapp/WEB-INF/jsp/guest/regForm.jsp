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
            <form method="post" autocomplete="off" action="/mybank/guest/registration">
                <div class="form-group">
                    <label for="email"><fmt:message key="page.message.email"/></label>
                    <input id="email" class="form-control"
                           type="email" name="email" placeholder="<fmt:message key="page.message.email"/>"
                           value="${email}">
                    <p class="text-danger">
                        ${wrongEmail}
                    </p>
                </div>
                <div class="form-group">
                    <label for="password"><fmt:message key="page.message.password"/></label>
                    <input id="password" class="form-control"
                           type="password" name="password" placeholder="<fmt:message key="page.message.password"/>">
                    <p class="text-danger">
                        ${wrongPassword}
                    </p>
                </div>
                <div class="form-group">
                    <label for="name"><fmt:message key="page.message.name"/></label>
                    <input id="name" class="form-control"
                           type="text" name="name" placeholder="<fmt:message key="page.message.name"/>" value="${name}">
                    <p class="text-danger">
                        ${wrongName}
                    </p>
                </div>
                <div class="form-group">
                    <label for="surname"><fmt:message key="page.message.surname"/></label>
                    <input id="surname" class="form-control"
                           type="text" name="surname" placeholder="<fmt:message key="page.message.surname"/>"
                           value="${surname}">
                    <p class="text-danger">
                        ${wrongSurname}
                    </p>
                </div>
                <div class="form-group">
                    <label for="patronymic"><fmt:message key="page.message.patronymic"/></label>
                    <input id="patronymic" class="form-control"
                           type="text" name="patronymic" placeholder="<fmt:message key="page.message.patronymic"/>"
                           value="${patronymic}">
                    <p class="text-danger">
                        ${wrongPatronymic}
                    </p>
                </div>
                <button type="submit" class="btn btn-success" style="margin-top:30px">
                    <fmt:message key="page.message.registration"/>
                </button>
                <a class="btn btn-secondary" style="margin-top:30px" href="/mybank/guest/login" role="button">
                    <fmt:message key="page.message.login"/>
                </a>
            </form>
        </div>
    </div>
</div>
</body>
</html>