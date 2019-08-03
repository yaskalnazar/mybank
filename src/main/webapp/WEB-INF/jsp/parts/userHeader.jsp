<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="/mybank">mybank</a>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item">
                <a class="nav-link" href="/mybank/user/home">Home<span class="sr-only">(current)</span></a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/mybank/user/account/all_accounts/">My Accounts</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/mybank/user/account/deposit/open/">Deposit Open</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/mybank/user/account/credit/open/">Credit open</a>
            </li>
        </ul>
    </div>
    <span style="float: right">
        <button class="btn btn-secondary mr-2" onclick="window.location.href = '/mybank/user/logout';">
                    logout
         </button>
         <fmt:message key="page.message.language"/>: <a href="?locale=en">Eng</a> | <a href="?locale=uk">Укр</a>
    </span>
</nav>

