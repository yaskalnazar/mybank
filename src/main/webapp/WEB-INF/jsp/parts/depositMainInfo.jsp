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
            <td> ${deposit.getId()}</td>
        </tr>
        <tr>
            <th><fmt:message key="page.message.balance"/>:</th>
            <td>${deposit.getBalance()}</td>
        </tr>
        <tr>
            <th><fmt:message key="page.message.closing.date"/>:</th>
            <td>${deposit.getClosingDate()}</td>
        </tr>
        <tr>
            <th><fmt:message key="page.message.owner.id"/>:</th>
            <td>
                <c:if test="${sessionScope.get('user').getRole() == 'ADMIN'}">
                <a href="${pageContext.request.contextPath}/api/admin/user_page?id=${deposit.getOwnerId()}">
                        ${deposit.getOwnerId()}
                </a>
                     </c:if>
                <c:if test="${sessionScope.get('user').getRole() != 'ADMIN'}">
                    ${deposit.getOwnerId()}
                </c:if>
            </td>
        </tr>
        <tr>
            <th><fmt:message key="page.message.account.status"/>:</th>
            <td>${deposit.getAccountStatus()}</td>
        </tr>
        <tr>
            <th><fmt:message key="page.message.deposit.amount"/>:</th>
            <td>${deposit.getDepositAmount()}</td>
        </tr>
        <tr>
            <th><fmt:message key="page.message.deposit.rate"/>:</th>
            <td>${deposit.getDepositRate()}</td>
        </tr>
        <tr>
            <th><fmt:message key="page.message.deposit.end.data"/>:</th>
            <td>${deposit.getDepositEndDate()}</td>
        </tr>
        </tbody>
    </table>
</div>
