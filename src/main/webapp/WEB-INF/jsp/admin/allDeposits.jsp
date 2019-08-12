<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>

<html>
<head>
    <title><fmt:message key="page.message.all.deposits"/></title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body>
<jsp:include page="../parts/adminHeader.jsp"/>

<div class="mx-1">
    <c:set var="deposits" value="${page.getItems()}" scope="request"/>
        <h3><fmt:message key="page.message.all.deposits"/>:</h3>
        <jsp:include page="../parts/depositsTable.jsp"/>
        <nav aria-label="pagination">
            <ul class="pagination justify-content-center">
                <li class="page-item ${page.getCurrentPage() == 1 ? 'disabled' : ''}">
                    <a class="page-link"
                       href="${pageContext.request.contextPath}/admin/account/all/deposits?currentPage=${page.getCurrentPage() - 1}">
                        <span>&laquo;</span>
                    </a>
                </li>
                <c:forEach var="i" begin="1" end="${page.getPagesNumber()}">
                    <li class="page-item ${page.getCurrentPage() eq i ? 'active' : ''}">
                        <a class="page-link"
                           href="${pageContext.request.contextPath}/admin/account/all/deposits?currentPage=${i}">${i}</a>
                    </li>
                </c:forEach>
                <li class="page-item ${page.getCurrentPage() == page.getPagesNumber() ? 'disabled' : ''}">
                    <a class="page-link"
                       href="${pageContext.request.contextPath}/admin/account/all/deposits?currentPage=${page.getCurrentPage() + 1}">
                        <span>&raquo;</span>
                    </a>
                </li>
            </ul>
        </nav>
</div>
</body>
</html>
