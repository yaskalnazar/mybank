<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>

<div>
    <c:if test="${not empty deposits}">
        <table class="table">
            <thead>
            <tr>
                <td><fmt:message key="page.message.id"/></td>
                <td><fmt:message key="page.message.balance"/></td>
                <td><fmt:message key="page.message.closing.date"/></td>
                <td><fmt:message key="page.message.owner.id"/></td>
                <td><fmt:message key="page.message.account.status"/></td>
                <td><fmt:message key="page.message.deposit.amount"/></td>
                <td><fmt:message key="page.message.deposit.rate"/></td>
                <td><fmt:message key="page.message.deposit.end.data"/></td>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${deposits}" var="deposit">
                <tr>
                    <td><c:if test="${sessionScope.get('user').getRole() == 'ADMIN'}">
                        <a href="${pageContext.request.contextPath}/api/admin/account/deposit_page?id=${deposit.getId()}">
                                ${deposit.getId()}
                        </a>
                    </c:if>
                        <c:if test="${sessionScope.get('user').getRole() == 'USER'}">
                            <a href="${pageContext.request.contextPath}/api/user/account/deposit_page?id=${deposit.getId()}">
                                    ${deposit.getId()}
                            </a>
                        </c:if></td>
                    <td>${deposit.getBalance()}</td>
                    <td>${deposit.getClosingDate()}</td>
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
                    <td>${deposit.getAccountStatus()}</td>
                    <td>${deposit.getDepositAmount()}</td>
                    <td>${deposit.getDepositRate()}</td>
                    <td>${deposit.getDepositEndDate()}</td>
                </tr>
            </c:forEach>

            </tbody>
        </table>
    </c:if>
    <c:if test="${empty deposits}">
        <div class="alert alert-warning" role="alert">
            <fmt:message key="page.message.no.deposit"/>
        </div>
    </c:if>
</div>