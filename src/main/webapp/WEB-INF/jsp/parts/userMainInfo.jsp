<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>

<div>
    <h3><fmt:message key="page.message.user.main.info"/>:</h3>
    <table class="table table-bordered ">
        <tbody>
        <tr>
            <th style="width: 30%"><fmt:message key="page.message.name"/>:</th>
            <td>${user.getName()}</td>
        </tr>
        <tr>
            <th><fmt:message key="page.message.surname"/>:</th>
            <td>${user.getSurname()}</td>
        </tr>
        <tr>
            <th><fmt:message key="page.message.patronymic"/>:</th>
            <td>${user.getPatronymic()}</td>
        </tr>
        <tr>
            <th><fmt:message key="page.message.email"/>:</th>
            <td>${user.getEmail()}</td>
        </tr>
        </tbody>
    </table>
</div>
