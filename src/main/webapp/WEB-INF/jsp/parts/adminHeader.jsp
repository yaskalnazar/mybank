<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="/mybank">mybank</a>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item">
                <a class="nav-link" href="/mybank/admin/home">Home</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/mybank/admin/all_users/">all_users</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/mybank/admin/all_accounts">all_accounts</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/mybank/admin/credit_requests/pending">credit_requests</a>
            </li>
        </ul>
    </div>
    <span style="float: right">
        <button class="btn btn-secondary mr-2" onclick="window.location.href = '/mybank/admin/logout';">
                    logout
         </button>
        Laguage: <a href="?language=en">Eng</a> | <a href="?language=uk">Укр</a>
    </span>
</nav>

