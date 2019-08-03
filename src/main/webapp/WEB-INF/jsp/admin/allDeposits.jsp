<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>

<html>
<head>
    <title><fmt:message key="page.message.all.deposites"/></title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body>
<jsp:include page="../parts/adminHeader.jsp"/>

<div class="mr-5">
    <c:if test="${not empty deposits}">
        <h1><fmt:message key="page.message.all.deposites"/>:</h1>
        <table class="table">
            <thead>
            <tr>
                <td><fmt:message key="page.message.id"/></td>
                <td><fmt:message key="page.message.balance"/></td>
                <td><fmt:message key="page.message.closing.date"/></td>
                <td><fmt:message key="page.message.owner.id"/></td>
                <td><fmt:message key="page.message.account.status"/></td>
                <td><fmt:message key="page.message.deposit.amount"/></td>
                <td><fmt:message key="page.message.deposit.rate"/></td>
                <td><fmt:message key="page.message.deposit.end.data"/></td>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${deposits}" var="deposit">
                <tr>
                    <td>${deposit.getId()}</td>
                    <td>${deposit.getBalance()}</td>
                    <td>${deposit.getClosingDate()}</td>
                    <td>${deposit.getOwnerId()}</td>
                    <td>${deposit.getAccountStatus()}</td>
                    <td>${deposit.getDepositAmount()}</td>
                    <td>${deposit.getDepositRate()}</td>
                    <td>${deposit.getDepositEndDate()}</td>
                </tr>
            </c:forEach>

            </tbody>
        </table>
    </c:if>
    <c:if test="${empty deposits}">
        <div class="alert alert-warning" role="alert">
            <fmt:message key="page.message.no.deposit"/>
        </div>
    </c:if>
</div>
</body>
</html>
