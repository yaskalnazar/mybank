<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>

<html>
<head>
    <title><fmt:message key="page.message.account"/></title>
    <link href="<c:url value='/bootstrap/css/bootstrap.min.css' />" rel="stylesheet">

</head>
<body>
<jsp:include page="../parts/adminHeader.jsp"/>
<div class="container" style="margin-top: 60px">
    <div class="d-flex justify-content-center">
        <div class="col-md-8 col-md-offset-2">
            <h3><fmt:message key="page.message.main.info"/>:</h3>
            <jsp:include page="../parts/depositMainInfo.jsp"/>
            <c:if test="${not empty answer}">
                <div class="alert alert-primary" role="alert">
                    <fmt:message key="page.message.answer.${answer}"/>
                </div>
            </c:if>
            <c:if test="${deposit.getAccountStatus().toString().equals('ACTIVE')}">
                <form method="post" style="margin-bottom: 30px" name="form" autocomplete="off">
                    <input type="hidden" name="id" value="${deposit.getId()}">
                    <label for="blockingReason"><fmt:message key="page.message.blocking.reason"/>:</label>
                    <input class="form-control" type="text" required="required"  pattern=".{5,80}"
                           name="blockingReason" id="blockingReason">
                    <button name="answer" value="block" type="submit" class="btn btn-danger" style="margin-top:30px">
                        <fmt:message key="page.message.block"/>
                    </button>
                </form>
            </c:if>
            <c:if test="${deposit.getAccountStatus().toString().equals('BLOCKED')}">
                <form method="post" style="margin-bottom: 30px" name="form" autocomplete="off">
                    <input type="hidden" name="id" value="${deposit.getId()}">
                    <button name="answer" value="unblock" type="submit" class="btn btn-success" style="margin-top:30px">
                        <fmt:message key="page.message.unblock"/>
                    </button>
                </form>
            </c:if>
        </div>
        <div class="col-md-8 col-md-offset-2">
            <h3><fmt:message key="page.message.transactions"/>:</h3>
            <c:set var="transactions" value="${page.getItems()}" scope="request"/>
            <jsp:include page="../parts/transactionsTable.jsp"/>

            <nav aria-label="pagination">
                <ul class="pagination justify-content-center">
                    <li class="page-item ${page.getCurrentPage() == 1 ? 'disabled' : ''}">
                        <a class="page-link" href="${pageContext.request.contextPath}/api/admin/account/deposit_page?currentPage=${page.getCurrentPage() - 1}&id=${deposit.getId()}">
                            <span>&laquo;</span>
                        </a>
                    </li>
                    <c:forEach var="i" begin="1" end="${page.getPagesNumber()}">
                        <li class="page-item ${page.getCurrentPage() eq i ? 'active' : ''}">
                            <a class="page-link" href="${pageContext.request.contextPath}/api/admin/account/deposit_page?currentPage=${i}&id=${deposit.getId()}">${i}</a>
                        </li>
                    </c:forEach>
                    <li class="page-item ${page.getCurrentPage() == page.getPagesNumber() ? 'disabled' : ''}">
                        <a class="page-link" href="${pageContext.request.contextPath}/api/admin/account/deposit_page?currentPage=${page.getCurrentPage() + 1}&id=${deposit.getId()}">
                            <span>&raquo;</span>
                        </a>
                    </li>
                </ul>
            </nav>
        </div>

    </div>
</div>
<jsp:include page="../parts/footer.jsp"/>
</body>
</html>