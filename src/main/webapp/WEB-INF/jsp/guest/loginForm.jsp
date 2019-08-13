<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>


<html>
<head>
    <title><fmt:message key="page.message.login"/></title>
    <link href="<c:url value='/bootstrap/css/bootstrap.min.css' />" rel="stylesheet">

</head>
<body>
<jsp:include page="../parts/guestHeader.jsp"/>



<div class="container" style="margin-top: 60px">
    <div class="row">
        <div class="col-md-8 col-md-offset-2">
            <c:if test="${not empty logoutSuccessfully}">
                <div class="alert alert-primary" role="alert">
                    <fmt:message key="page.message.logout.successfully"/>
                </div>
            </c:if>
            <c:if test="${not empty regSuccessfully}">
                <div class="alert alert-primary" role="alert">
                    <fmt:message key="page.message.registered.successfully"/>
                </div>
            </c:if>
            <c:if test="${not empty wrongInput}">
                <div class="alert alert-danger" role="alert">
                    <fmt:message key="page.message.wrong.input"/>
                </div>
            </c:if>
            <h2 class="page-header"><fmt:message key="page.message.login"/></h2>
            <form method="post" autocomplete="off" action="${pageContext.request.contextPath}/api/guest/login">
                <div class="form-group">
                    <label for="email"><fmt:message key="page.message.email"/></label>
                    <input id="email" class="form-control" required="required"
                           type="text" name="email" pattern="^[\w\.]{3,32}@[a-z]{3,10}\.[a-z]{2,4}$"
                           placeholder="<fmt:message key="page.message.email"/>" value="${email}">

                </div>
                <div class="form-group">
                    <label for="password"><fmt:message key="page.message.password"/></label>
                    <input id="password" class="form-control" required="required" pattern=".{5,40}"
                           type="password" name="password" placeholder="<fmt:message key="page.message.password"/>">
                </div>
                <button type="submit" class="btn btn-success" style="margin-top:30px">
                    <fmt:message key="page.message.login"/>
                </button>
                <a class="btn btn-secondary" style="margin-top:30px" href="${pageContext.request.contextPath}/api/guest/registration" role="button">
                    <fmt:message key="page.message.registration"/>
                </a>
            </form>
        </div>
    </div>
</div>
<jsp:include page="../parts/footer.jsp"/>

</body>
</html>