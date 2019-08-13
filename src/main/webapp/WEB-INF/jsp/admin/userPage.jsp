<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>

<html>
<head>
    <title><fmt:message key="page.message.user"/></title>
    <link href="<c:url value='/bootstrap/css/bootstrap.min.css' />" rel="stylesheet">

</head>
<body>
<jsp:include page="../parts/adminHeader.jsp"/>
<div class="container" style="margin-top: 60px">
    <div class="d-flex justify-content-center">
        <div class="col-md-8 col-md-offset-2">
            <c:set var="user" value="${requestUser}" scope="request"/>
            <jsp:include page="../parts/userMainInfo.jsp"/>

        </div>
        <div class="col-md-8 col-md-offset-2">
            <h3><fmt:message key="page.message.all.credits"/>:</h3>
            <jsp:include page="../parts/creditsTable.jsp"/>
            <h3><fmt:message key="page.message.all.deposits"/>:</h3>
            <jsp:include page="../parts/depositsTable.jsp"/>


        </div>
    </div>
</div>
<jsp:include page="../parts/footer.jsp"/>
</body>
</html>