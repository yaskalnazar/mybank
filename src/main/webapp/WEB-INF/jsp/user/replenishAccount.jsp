<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>

<html>
<head>
    <title><fmt:message key="page.message.replenish.account"/></title>
    <link href="<c:url value='/bootstrap/css/bootstrap.min.css' />" rel="stylesheet">

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
                        <input class="form-control" type="number" required="required"
                               step="0.01" min="0"
                               name="amount" id="amount">
                        <button type="button" class="btn btn-success ml-2" id="myBtn2">
                            <fmt:message key="page.message.submit"/>
                        </button>
                        <jsp:include page="../popUp/replenish.jsp"/>
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
<jsp:include page="../parts/footer.jsp"/>
</body>
</html>