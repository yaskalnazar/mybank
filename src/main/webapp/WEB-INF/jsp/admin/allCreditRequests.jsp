<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>

<html>
<head>
    <title><fmt:message key="page.message.credit.requests"/></title>
    <link href="<c:url value='/bootstrap/css/bootstrap.min.css' />" rel="stylesheet">

</head>
<body>
<div class="mx-3">
    <jsp:include page="../parts/adminHeader.jsp"/>
    <br>
    <h3><fmt:message key="page.message.${status}"/> <fmt:message key="page.message.credit.requests.lower"/>:</h3>

    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item">
                    <a class="nav-link" href="?requestStatus=pending"><fmt:message key="page.message.pending"/></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="?requestStatus=rejected"><fmt:message key="page.message.rejected"/></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="?requestStatus=approved"><fmt:message key="page.message.approved"/></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="?requestStatus=all"><fmt:message key="page.message.all"/></a>
                </li>
            </ul>
        </div>
    </nav>


    <c:if test="${not empty creditRequests}">
        <table class="table">
            <thead>
            <tr>
                <td><fmt:message key="page.message.id"/></td>
                <td><fmt:message key="page.message.applicant.id"/></td>
                <td><fmt:message key="page.message.credit.rate"/></td>
                <td><fmt:message key="page.message.credit.limit"/></td>
                <td><fmt:message key="page.message.creation.date"/></td>
                <td><fmt:message key="page.message.credit.requests.status"/></td>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${creditRequests}" var="creditRequest">
                <tr>
                    <td>
                        <a href="${pageContext.request.contextPath}/api/admin/credit_request?id=${creditRequest.getId()}">
                                ${creditRequest.getId()}
                        </a>
                    </td>
                    <td><a href="${pageContext.request.contextPath}/api/admin/user_page?id=${creditRequest.getApplicantId()}">
                            ${creditRequest.getApplicantId()}
                        </a>
                    </td>
                    <td>${creditRequest.getCreditRate()}</td>
                    <td>${creditRequest.getCreditLimit()}</td>
                    <td>${creditRequest.getCreationDate()}</td>
                    <td>${creditRequest.getCreditRequestStatus()}</td>
                </tr>
            </c:forEach>

            </tbody>
        </table>
    </c:if>
    <c:if test="${empty creditRequests}">
        <div class="alert alert-warning" role="alert">
            <fmt:message key="page.message.no.credit.requests"/>
        </div>
    </c:if>
</div>
<jsp:include page="../parts/footer.jsp"/>
</body>
</html>
