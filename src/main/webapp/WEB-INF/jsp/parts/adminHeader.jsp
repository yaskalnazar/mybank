<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="${pageContext.request.contextPath}">mybank</a>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/api/admin/home">
                    <fmt:message key="page.message.home"/></a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/api/admin/all_users">
                    <fmt:message key="page.message.all.users"/></a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/api/admin/account/all/deposits">
                    <fmt:message key="page.message.all.deposits"/></a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/api/admin/account/all/credits">
                    <fmt:message key="page.message.all.credits"/></a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/api/admin/credit_request/all">
                    <fmt:message key="page.message.credit.requests"/></a>
            </li>
        </ul>
    </div>
    <span style="float: right">
        <button class="btn btn-secondary mr-2" onclick="window.location.href = '${pageContext.request.contextPath}/api/admin/logout';">
                    <fmt:message key="page.message.logout"/>
         </button>
         <fmt:message key="page.message.language"/>: <a href="?locale=en&${pageContext.request.queryString}">Eng</a> |
        <a href="?locale=uk&${pageContext.request.queryString}">Укр</a>
    </span>
</nav>

