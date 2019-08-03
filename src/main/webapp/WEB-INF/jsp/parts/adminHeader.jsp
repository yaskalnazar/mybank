<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="/mybank">mybank</a>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item">
                <a class="nav-link" href="/mybank/admin/home"><fmt:message key="page.message.home"/></a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/mybank/admin/all_users"><fmt:message key="page.message.all.users"/></a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/mybank/admin/all_accounts"><fmt:message key="page.message.all.accounts"/></a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/mybank/admin/credit_requests/pending"><fmt:message key="page.message.credit.requests"/></a>
            </li>
        </ul>
    </div>
    <span style="float: right">
        <button class="btn btn-secondary mr-2" onclick="window.location.href = '/mybank/admin/logout';">
                    <fmt:message key="page.message.logout"/>
         </button>
         <fmt:message key="page.message.language"/>: <a href="?locale=en">Eng</a> | <a href="?locale=uk">Укр</a>
    </span>
</nav>

