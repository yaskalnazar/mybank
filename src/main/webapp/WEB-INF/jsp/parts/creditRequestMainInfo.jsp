<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>

<div>
    <table class="table table-bordered ">
        <tbody>
        <tr>
            <th style="width: 30%"><fmt:message key="page.message.id"/>:</th>
            <td>${creditRequest.getId()}</td>
        </tr>
        <tr>
            <th><fmt:message key="page.message.applicant.id"/>:</th>
            <td>
                <c:if test="${sessionScope.get('user').getRole() == 'ADMIN'}">
                <a href="${pageContext.request.contextPath}/api/admin/user_page?id=${creditRequest.getApplicantId()}">
                    ${creditRequest.getApplicantId()}
                </a>
                </c:if>
                <c:if test="${sessionScope.get('user').getRole() == 'USER'}">
                    ${creditRequest.getApplicantId()}
                </c:if>
            </td>
        </tr>
        <tr>
            <th><fmt:message key="page.message.credit.limit"/>:</th>
            <td>${creditRequest.getCreditLimit()}</td>
        </tr>
        <tr>
            <th><fmt:message key="page.message.credit.rate"/>:</th>
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