<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>

<html>
<head>
    <title><fmt:message key="page.message.credit.request"/></title>
    <link href="<c:url value='/bootstrap/css/bootstrap.min.css' />" rel="stylesheet">

</head>
<body>
<jsp:include page="../parts/adminHeader.jsp"/>
<div class="container" style="margin-top: 60px">
    <div class="d-flex justify-content-center">
        <div class="col-md-8 col-md-offset-2">
            <h3><fmt:message key="page.message.credit.request.info"/>:</h3>
            <jsp:include page="../parts/creditRequestMainInfo.jsp"/>
            <c:set var="user" value="${applicant}" scope="request"/>
            <jsp:include page="../parts/userMainInfo.jsp"/>


        </div>
        <div class="col-md-8 col-md-offset-2">
            <h3><fmt:message key="page.message.applicant.credit.history"/>:</h3>
            <jsp:include page="../parts/creditsTable.jsp"/>


            <c:if test="${not empty answer}">
                <div class="alert alert-primary" role="alert">
                    <fmt:message key="page.message.${answer}"/>
                </div>
            </c:if>
            <c:if test="${empty answer and
                creditRequest.getCreditRequestStatus().toString().equals('PENDING')}">
                <form method="post" style="margin-bottom: 30px" name="form" autocomplete="off">
                    <button name="answer" value="approved" type="submit" class="btn btn-success"
                            style="margin-top:30px">
                        <fmt:message key="page.message.approve"/>
                    </button>
                    <button name="answer" value="rejected" type="submit" class="btn btn-danger" style="margin-top:30px">
                        <fmt:message key="page.message.reject"/>
                    </button>
                </form>
            </c:if>
        </div>
    </div>
</div>
<jsp:include page="../parts/footer.jsp"/>
</body>
</html>