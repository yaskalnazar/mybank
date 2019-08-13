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
<jsp:include page="../parts/userHeader.jsp"/>
<div class="container" style="margin-top: 60px">
    <div class="d-flex justify-content-center">
        <div class="col-md-8 col-md-offset-2">
            <h3><fmt:message key="page.message.main.info"/>:</h3>
            <jsp:include page="../parts/creditMainInfo.jsp"/>
        </div>
        <div class="col-md-8 col-md-offset-2">
            <h3><fmt:message key="page.message.transactions"/>:</h3>
            <c:set var="transactions" value="${page.getItems()}" scope="request"/>
            <jsp:include page="../parts/transactionsTable.jsp"/>

            <nav aria-label="pagination">
                <ul class="pagination justify-content-center">
                    <li class="page-item ${page.getCurrentPage() == 1 ? 'disabled' : ''}">
                        <a class="page-link" href="${pageContext.request.contextPath}/api/user/account/credit_page?currentPage=${page.getCurrentPage() - 1}&id=${credit.getId()}">
                            <span>&laquo;</span>
                        </a>
                    </li>
                    <c:forEach var="i" begin="1" end="${page.getPagesNumber()}">
                        <li class="page-item ${page.getCurrentPage() eq i ? 'active' : ''}">
                            <a class="page-link" href="${pageContext.request.contextPath}/api/user/account/credit_page?currentPage=${i}&id=${credit.getId()}">${i}</a>
                        </li>
                    </c:forEach>
                    <li class="page-item ${page.getCurrentPage() == page.getPagesNumber() ? 'disabled' : ''}">
                        <a class="page-link" href="${pageContext.request.contextPath}/api/user/account/credit_page?currentPage=${page.getCurrentPage() + 1}&id=${credit.getId()}">
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