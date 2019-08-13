<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>

<html>
<head>
    <title><fmt:message key="page.message.all.accounts"/></title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
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

<jsp:include page="../parts/footer.jsp"/>
</body>
</html>
