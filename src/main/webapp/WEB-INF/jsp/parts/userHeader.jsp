<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="${pageContext.request.contextPath}">mybank</a>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/api/user/home"><fmt:message key="page.message.home"/></a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/api/user/account/all"><fmt:message key="page.message.my.accounts"/></a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/api/user/account/deposit/open"><fmt:message key="page.message.deposit.open"/></a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/api/user/account/credit/open"><fmt:message key="page.message.credit.open"/></a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/api/user/account/replenish"><fmt:message key="page.message.replenish.account"/></a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/api/user/account/make_transaction"><fmt:message key="page.message.make.transaction"/></a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/api/user/payment/make_new"><fmt:message key="page.message.make.payment"/></a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/api/user/payment/all"><fmt:message key="page.message.payments"/></a>
            </li>

        </ul>
    </div>
    <span style="float: right">
        <button class="btn btn-secondary mr-2" onclick="window.location.href = '${pageContext.request.contextPath}/api/user/logout';">
                    <fmt:message key="page.message.logout"/>
         </button>
         <fmt:message key="page.message.language"/>: <a href="?locale=en&${pageContext.request.queryString}">Eng</a> |
        <a href="?locale=uk&${pageContext.request.queryString}">Укр</a>
    </span>
</nav>

