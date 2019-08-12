<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>

<html>
<head>
    <title><fmt:message key="page.message.account"/></title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body>
<jsp:include page="../parts/adminHeader.jsp"/>
<div class="container" style="margin-top: 60px">
    <div class="d-flex justify-content-center">
        <div class="col-md-8 col-md-offset-2">
            <h3><fmt:message key="page.message.main.info"/>:</h3>
            <jsp:include page="../parts/creditMainInfo.jsp"/>
            <c:if test="${not empty answer}">
                <div class="alert alert-primary" role="alert">
                    <fmt:message key="page.message.answer.${answer}"/>
                </div>
            </c:if>
            <c:if test="${credit.getAccountStatus().toString().equals('ACTIVE')}">
                <form method="post" style="margin-bottom: 30px" name="form" autocomplete="off">
                    <input type="hidden" name="id" value="${credit.getId()}">
                    <label for="blockingReason"><fmt:message key="page.message.blocking.reason"/>:</label>
                    <input class="form-control" type="text" required="required"  pattern=".{5,80}"
                           name="blockingReason" id="blockingReason">
                    <button name="answer" value="block" type="submit" class="btn btn-danger" style="margin-top:30px">
                        <fmt:message key="page.message.block"/>
                    </button>
                </form>
            </c:if>
            <c:if test="${credit.getAccountStatus().toString().equals('BLOCKED')}">
                <form method="post" style="margin-bottom: 30px" name="form" autocomplete="off">
                    <input type="hidden" name="id" value="${credit.getId()}">
                    <button name="answer" value="unblock" type="submit" class="btn btn-success" style="margin-top:30px">
                        <fmt:message key="page.message.unblock"/>
                    </button>
                </form>
            </c:if>
        </div>
        <div class="col-md-8 col-md-offset-2">
            <h3><fmt:message key="page.message.transactions"/>:</h3>
            <c:set var="transactions" value="${accountTransactions}" scope="request"/>
            <jsp:include page="../parts/transactionsTable.jsp"/>
        </div>

    </div>
</div>
</body>
</html>