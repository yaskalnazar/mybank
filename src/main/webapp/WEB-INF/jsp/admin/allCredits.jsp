<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>

<html>
<head>
    <title><fmt:message key="page.message.all.credits"/></title>
    <link href="<c:url value='/bootstrap/css/bootstrap.min.css' />" rel="stylesheet">

</head>
<body>
<jsp:include page="../parts/adminHeader.jsp"/>

<div class="mx-1">
    <c:set var="credits" value="${page.getItems()}" scope="request"/>
        <h3><fmt:message key="page.message.all.credits"/>:</h3>
        <jsp:include page="../parts/creditsTable.jsp"/>
    <nav aria-label="pagination">
        <ul class="pagination justify-content-center">
            <li class="page-item ${page.getCurrentPage() == 1 ? 'disabled' : ''}">
                <a class="page-link"
                   href="${pageContext.request.contextPath}/api/admin/account/all/credits?currentPage=${page.getCurrentPage() - 1}">
                    <span>&laquo;</span>
                </a>
            </li>
            <c:forEach var="i" begin="1" end="${page.getPagesNumber()}">
                <li class="page-item ${page.getCurrentPage() eq i ? 'active' : ''}">
                    <a class="page-link"
                       href="${pageContext.request.contextPath}/api/admin/account/all/credits?currentPage=${i}">${i}</a>
                </li>
            </c:forEach>
            <li class="page-item ${page.getCurrentPage() == page.getPagesNumber() ? 'disabled' : ''}">
                <a class="page-link"
                   href="${pageContext.request.contextPath}/api/admin/account/all/credits?currentPage=${page.getCurrentPage() + 1}">
                    <span>&raquo;</span>
                </a>
            </li>
        </ul>
    </nav>
</div>
<jsp:include page="../parts/footer.jsp"/>
</body>
</html>
