<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>

<html>
<head>
    <title><fmt:message key="page.message.make.payment"/></title>
    <link href="<c:url value='/bootstrap/css/bootstrap.min.css' />" rel="stylesheet">

</head>
<body>
<jsp:include page="../parts/userHeader.jsp"/>
<div class="container" style="margin-top: 60px">
    <div class="d-flex justify-content-center">
        <div class="col-md-8 col-md-offset-2">
            <c:if test="${not empty paymentSuccess}">
                <div class="alert alert-success" role="alert">
                    <fmt:message key="page.message.payment.success"/>
                </div>
            </c:if>
            <c:if test="${not empty paymentError}">
                <div class="alert alert-danger" role="alert">
                    <fmt:message key="${paymentError}"/>
                </div>
            </c:if>


            <c:if test="${not empty activeUserAccounts}">
                <form method="post" name="form" autocomplete="off">
                    <div class="form-group">
                        <label for="payerAccountId"><fmt:message key="page.message.to.the.account"/>:</label>
                        <input class="form-control" type="number" min="0" required="required"
                               name="payerAccountId" id="payerAccountId">
                    </div>
                    <div class="form-group">
                        <label><fmt:message key="page.message.recipients.card.id"/>:</label>

                        <select class="custom-select" name="requesterAccountId" id="requesterAccountId">
                            <c:forEach items="${activeUserAccounts}" var="requesterAccountId">
                                <option value="${requesterAccountId.getId()}">
                                    <fmt:message key="page.message.id"/>: ${requesterAccountId.getId()} &nbsp;
                                    [<fmt:message key="page.message.balance"/>: ${requesterAccountId.getBalance()}]
                                </option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="form-group">
                        <label for="amount"><fmt:message key="page.message.amount"/>:</label>

                        <input class="form-control" type="number" step="0.01" min="0" required="required"
                               name="amount" id="amount">
                    </div>
                    <div class="form-group">
                        <label for="message"><fmt:message key="page.message.message"/>:</label>

                        <input class="form-control" type="text" name="message" id="message" maxlength="80">
                    </div>
                    <div class="form-group">
                        <button type="submit" class="btn btn-success">
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
<jsp:include page="../parts/footer.jsp"/>
</body>
</html>