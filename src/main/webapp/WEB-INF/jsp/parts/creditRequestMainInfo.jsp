<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>

<div>
    <h3><fmt:message key="page.message.credit.request.info"/>:</h3>
    <table class="table table-bordered ">
        <tbody>
        <tr>
            <th style="width: 30%"><fmt:message key="page.message.id"/>:</th>
            <td>${creditRequest.getId()}</td>
        </tr>
        <tr>
            <th><fmt:message key="page.message.applicant.id"/>:</th>
            <td>${creditRequest.getApplicantId()}</td>
        </tr>
        <tr>
            <th><fmt:message key="page.message.credit.limit"/>:</th>
            <td>${creditRequest.getCreditRate()}</td>
        </tr>
        <tr>
            <th><fmt:message key="page.message.creation.date"/>:</th>
            <td>${creditRequest.getCreationDate()}</td>
        </tr>
        <tr>
            <th><fmt:message key="page.message.credit.requests.status"/>:</th>
            <td>${creditRequest.getCreditRequestStatus()}</td>
        </tr>
        </tbody>
    </table>
</div>