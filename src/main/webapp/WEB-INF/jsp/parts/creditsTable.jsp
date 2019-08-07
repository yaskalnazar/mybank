<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>

<div>
    <c:if test="${not empty credits}">
        <table class="table">
            <thead>
            <tr>
                <td><fmt:message key="page.message.id"/></td>
                <td><fmt:message key="page.message.balance"/></td>
                <td><fmt:message key="page.message.closing.date"/></td>
                <td><fmt:message key="page.message.owner.id"/></td>
                <td><fmt:message key="page.message.account.status"/></td>

                <td><fmt:message key="page.message.credit.limit"/></td>
                <td><fmt:message key="page.message.credit.rate"/></td>
                <td><fmt:message key="page.message.accrued.interest"/></td>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${credits}" var="credit">
                <tr>
                    <td>
                            ${credit.getId()}
                    </td>
                    <td>${credit.getBalance()}</td>
                    <td>${credit.getClosingDate()}</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/admin/user_page?id=${credit.getOwnerId()}">
                                ${credit.getOwnerId()}
                        </a>
                    </td>
                    <td>${credit.getAccountStatus()}</td>
                    <td>${credit.getCreditLimit()}</td>
                    <td>${credit.getCreditRate()}</td>
                    <td>${credit.getAccruedInterest()}</td>
                </tr>
            </c:forEach>

            </tbody>
        </table>
    </c:if>
    <c:if test="${empty credits}">
        <div class="alert alert-warning" role="alert">
            <fmt:message key="page.message.no.credits"/>
        </div>
    </c:if>
</div>