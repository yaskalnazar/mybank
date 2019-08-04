<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>

<nav class="navbar navbar-light bg-light">
    <a class="navbar-brand" href="${pageContext.request.contextPath}">mybank</a>
    <span style="float: right">
         <fmt:message key="page.message.language"/>: <a href="?locale=en&${pageContext.request.queryString}">Eng</a> |
        <a href="?locale=uk&${pageContext.request.queryString}">Укр</a>
    </span>
</nav>

