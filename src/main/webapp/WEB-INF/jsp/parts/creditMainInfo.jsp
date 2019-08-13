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
            <td> ${credit.getId()}</td>
        </tr>
        <tr>
            <th><fmt:message key="page.message.balance"/>:</th>
            <td>${credit.getBalance()}</td>
        </tr>
        <tr>
            <th><fmt:message key="page.message.closing.date"/>:</th>
            <td>${credit.getClosingDate()}</td>
        </tr>
        <tr>
            <th><fmt:message key="page.message.owner.id"/>:</th>
            <td>
                <c:if test="${sessionScope.get('user').getRole() == 'ADMIN'}">
                    <a href="${pageContext.request.contextPath}/api/admin/user_page?id=${credit.getOwnerId()}">
                            ${credit.getOwnerId()}
                    </a>
                </c:if>
                <c:if test="${sessionScope.get('user').getRole() != 'ADMIN'}">
                    ${credit.getOwnerId()}
                </c:if>
            </td>
        </tr>
        <tr>
            <th><fmt:message key="page.message.account.status"/>:</th>
            <td>${credit.getAccountStatus()}</td>
        </tr>
        <tr>
            <th><fmt:message key="page.message.credit.limit"/>:</th>
            <td>${credit.getCreditLimit()}</td>
        </tr>
        <tr>
            <th><fmt:message key="page.message.credit.rate"/>:</th>
            <td>${credit.getCreditRate()}</td>
        </tr>
        <tr>
            <th><fmt:message key="page.message.accrued.interest"/>:</th>
            <td>
                <div class="form-inline">${credit.getAccruedInterest()}
                    <c:if test="${sessionScope.get('user').getRole() == 'USER' and
                    credit.getAccruedInterest() > 0 }">
                        <button type="button" class="btn btn-success ml-3" id="myBtn2">
                            <fmt:message key="page.message.pay"/>
                        </button>
                        <jsp:include page="../popUp/payAccruedInterest.jsp"/>
                    </c:if>
                </div>
            </td>
        </tr>
        </tbody>
    </table>
</div>
