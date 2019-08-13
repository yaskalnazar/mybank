<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>

<html>
<head>
    <title><fmt:message key="page.message.payments"/></title>
    <link href="<c:url value='/bootstrap/css/bootstrap.min.css' />" rel="stylesheet">
</head>
<body>
<div class="mx-3">
    <jsp:include page="../parts/userHeader.jsp"/>
    <br>
    <h3><fmt:message key="page.message.${user_status}"/>:</h3>

    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item">
                    <a class="nav-link" href="?user_status=requester"><fmt:message key="page.message.sent"/></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="?user_status=payer"><fmt:message key="page.message.received"/></a>
                </li>
            </ul>
        </div>
    </nav>

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
                <c:if test="${not empty payments}">
                    <c:forEach items="${payments}" var="payment">
                        <table class="table table-bordered ">
                            <tbody>
                            <tr>
                                <th style="width: 30%"><fmt:message key="page.message.id"/>:</th>
                                <td>${payment.getId()}</td>
                            </tr>
                            <tr>
                                <th><fmt:message key="page.message.requester.account.id"/>:</th>
                                <td>${payment.getRequesterAccountId()}</td>
                            </tr>
                            <tr>
                                <th><fmt:message key="page.message.payer.account.id"/>:</th>
                                <td>${payment.getPayerAccountId()}</td>
                            </tr>
                            <tr>
                                <th><fmt:message key="page.message.amount"/>:</th>
                                <td>${payment.getAmount()}</td>
                            </tr>
                            <tr>
                                <th><fmt:message key="page.message.status"/>:</th>
                                <td>${payment.getPaymentStatus()}</td>
                            </tr>
                            <tr>
                                <th><fmt:message key="page.message.date"/>:</th>
                                <td>${payment.getDate()}</td>
                            </tr>
                            <tr>
                                <th><fmt:message key="page.message.message"/>:</th>
                                <td>${payment.getMessage()}</td>
                            </tr>

                            <c:if test="${payment.getPaymentStatus().toString().equals('PENDING')
                                and user_status.equals('received')}">
                                <tr>
                                    <td colspan="2">
                                        <div class="d-flex justify-content-center my-0 py-0">
                                            <form method="post" name="form" autocomplete="off">
                                                <input id="paymentId" type="hidden" name="paymentId"
                                                       value="${payment.getId()}">
                                                <button name="answer" value="paid" type="submit" class="btn btn-success"
                                                        style="margin-top:30px">
                                                    <fmt:message key="page.message.pay"/>
                                                </button>
                                                <button name="answer" value="rejected" type="submit"
                                                        class="btn btn-danger"
                                                        style="margin-top:30px">
                                                    <fmt:message key="page.message.reject"/>
                                                </button>
                                            </form>
                                        </div>
                                    </td>
                                </tr>
                            </c:if>
                            </tbody>
                        </table>
                    </c:forEach>
                    </tbody>
                    </table>
                </c:if>
                <c:if test="${empty payments}">
                    <div class="alert alert-warning" role="alert">
                        <fmt:message key="page.message.no.payments"/>
                    </div>
                </c:if>
            </div>
        </div>
    </div>
</div>
<jsp:include page="../parts/footer.jsp"/>
</body>
</html>
