<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>

<html>
<head>
    <title><fmt:message key="page.message.all.users"/></title>
    <link href="<c:url value='/bootstrap/css/bootstrap.min.css' />" rel="stylesheet">

</head>
<body>
<jsp:include page="../parts/adminHeader.jsp"/>
<div class="mx-1">
    <c:if test="${not empty page.getItems()}">
        <h1><fmt:message key="page.message.all.users"/>:</h1>
        <table class="table">
            <thead>
            <tr>
                <td><fmt:message key="page.message.id"/></td>
                <td><fmt:message key="page.message.email"/></td>
                <td><fmt:message key="page.message.name"/></td>
                <td><fmt:message key="page.message.surname"/></td>
                <td><fmt:message key="page.message.patronymic"/></td>
                <td><fmt:message key="page.message.role"/></td>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${page.getItems()}" var="user">
                <tr>
                    <td>
                        <a href="${pageContext.request.contextPath}/api/admin/user_page?id=${user.getId()}">
                            ${user.getId()}
                        </a>
                    </td>
                    <td>${user.getEmail()}</td>
                    <td>${user.getName()}</td>
                    <td>${user.getSurname()}</td>
                    <td>${user.getPatronymic()}</td>
                    <td>${user.getRole()}</td>
                </tr>
            </c:forEach>

            </tbody>
        </table>
        <nav aria-label="pagination">
            <ul class="pagination justify-content-center">
                <li class="page-item ${page.getCurrentPage() == 1 ? 'disabled' : ''}">
                    <a class="page-link"
                       href="${pageContext.request.contextPath}/api/admin/all_users?currentPage=${page.getCurrentPage() - 1}">
                        <span>&laquo;</span>
                    </a>
                </li>
                <c:forEach var="i" begin="1" end="${page.getPagesNumber()}">
                    <li class="page-item ${page.getCurrentPage() eq i ? 'active' : ''}">
                        <a class="page-link"
                           href="${pageContext.request.contextPath}/api/admin/all_users?currentPage=${i}">${i}</a>
                    </li>
                </c:forEach>
                <li class="page-item ${page.getCurrentPage() == page.getPagesNumber() ? 'disabled' : ''}">
                    <a class="page-link"
                       href="${pageContext.request.contextPath}/api/admin/all_users?currentPage=${page.getCurrentPage() + 1}">
                        <span>&raquo;</span>
                    </a>
                </li>
            </ul>
        </nav>
    </c:if>
    <c:if test="${empty page.getItems()}">
        <div class="alert alert-warning" role="alert">
            <fmt:message key="page.message.failed.get.users"/>
        </div>
    </c:if>
</div>
<jsp:include page="../parts/footer.jsp"/>
</body>
</html>
