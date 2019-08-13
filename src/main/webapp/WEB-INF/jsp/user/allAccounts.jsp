<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>

<html>
<head>
    <title><fmt:message key="page.message.all.accounts"/></title>
    <link href="<c:url value='/bootstrap/css/bootstrap.min.css' />" rel="stylesheet">
</head>
<body>
<jsp:include page="../parts/userHeader.jsp"/>

<div class="mx-1">
    <h3><fmt:message key="page.message.all.deposits"/>:</h3>
    <jsp:include page="../parts/depositsTable.jsp"/>
</div>

<div class="mx-1">
        <h3><fmt:message key="page.message.all.credits"/>:</h3>
        <jsp:include page="../parts/creditsTable.jsp"/>
</div>


</body>
</html>
