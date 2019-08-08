<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>

<html>
<head>
    <title><fmt:message key="page.message.replenish.account"/></title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

</head>
<body>
<jsp:include page="../parts/userHeader.jsp"/>
<div class="container" style="margin-top: 60px">
    <div class="d-flex justify-content-center">
        <div class="col-md-8 col-md-offset-2">
            <c:if test="${not empty replenishSuccess}">
                <div class="alert alert-success" role="alert">
                    <fmt:message key="page.message.replenish.success"/>
                </div>
            </c:if>

            <c:if test="${not empty activeUserAccounts}">
                <form method="post" name="form" autocomplete="off">
                    <fmt:message key="page.message.account.id"/>:
                    <select class="custom-select mb-3" name="accountId">
                        <c:forEach items="${activeUserAccounts}" var="account">
                            <option value="${account.getId()}"><fmt:message key="page.message.id"/>: ${account.getId()} &nbsp;
                                [<fmt:message key="page.message.balance"/>: ${account.getBalance()}]
                            </option>
                        </c:forEach>
                    </select>
                    <label for="amount"><fmt:message key="page.message.amount"/>:</label>
                    <div class="form-inline">
                        <input class="form-control" type="number" min="0"
                               name="amount" id="amount">

                        <button type="submit" class="btn btn-success ml-2">
                            <fmt:message key="page.message.submit"/>
                        </button>
                    </div>
                </form>
            </c:if>
            <c:if test="${empty activeUserAccounts}">
                <div class="alert alert-warning" role="alert">
                    <fmt:message key="page.message.no.active.accounts"/>
                </div>
            </c:if>
        </div>
    </div>
</div>
</body>
</html>