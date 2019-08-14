<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>

<div>
    <c:if test="${not empty transactions}">
        <table class="table">
            <thead>
            <tr>
                <td><fmt:message key="page.message.id"/></td>
                <td><fmt:message key="page.message.amount"/></td>
                <td><fmt:message key="page.message.date"/></td>
                <td><fmt:message key="page.message.sender.account.id"/></td>
                <td><fmt:message key="page.message.receiver.account.id"/></td>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${transactions}" var="transaction">
                <tr>
                    <td>${transaction.getId()}</td>
                    <td>${transaction.getTransactionAmount()}</td>
                    <td>${transaction.getDate()}</td>
                    <td>
                        <c:if test="${sessionScope.get('user').getRole() == 'ADMIN'}">
                            <a href="${pageContext.request.contextPath}/api/admin/user_page?id=${transaction.getSenderAccountId()}">
                                    ${transaction.getSenderAccountId()}
                            </a>
                        </c:if>
                        <c:if test="${sessionScope.get('user').getRole() != 'ADMIN'}">
                            ${transaction.getSenderAccountId()}
                        </c:if>
                    </td>
                    <td>
                            ${transaction.getReceiverAccountId()}
                    </td>
                </tr>
            </c:forEach>

            </tbody>
        </table>
    </c:if>
    <c:if test="${empty transactions}">
        <div class="alert alert-warning" role="alert">
            <fmt:message key="page.message.no.transactions"/>
        </div>
    </c:if>
</div>