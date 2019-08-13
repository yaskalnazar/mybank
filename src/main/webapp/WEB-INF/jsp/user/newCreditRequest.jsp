<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>

<html>
<head>
    <title><fmt:message key="page.message.credit.open"/></title>
    <link href="<c:url value='/bootstrap/css/bootstrap.min.css' />" rel="stylesheet">
    <script>
        function change() {
            var temp = document.getElementById("creditLimit").value;
            document.getElementById('requestTooLarge').innerHTML = '';
            document.getElementById("submit").disabled = false;
            if (temp < 5000)
                document.getElementById("creditRate").value = 0.05;
            else if (temp < 10000)
                document.getElementById("creditRate").value = 0.04;
            else if (temp < 50000)
                document.getElementById("creditRate").value = 0.03;
            else {
                document.getElementById("creditRate").value = 0;
                document.getElementById('requestTooLarge').innerHTML = 'credit request too large';
                document.getElementById("submit").disabled = true;
            }
        }
    </script>
</head>
<body>
<jsp:include page="../parts/userHeader.jsp"/>
<div class="container" style="margin-top: 60px">
    <div class="d-flex justify-content-center">
        <div class="col-md-8 col-md-offset-2">

            <c:if test="${not empty activeCreditAccounts}">
                <div class="row">
                    <div class="alert alert-warning" role="alert">
                        <h2><fmt:message key="page.message.already.have.active.credits"/>:</h2>
                        <c:set var="credits" value="${activeCreditAccounts}" scope="request"/>
                        <jsp:include page="../parts/creditsTable.jsp"/>
                    </div>
                </div>
            </c:if>
            <c:if test="${not empty activeCreditRequests}">
                <div class="row">
                    <div class="alert alert-warning" role="alert">
                        <h2><fmt:message key="page.message.already.have.active.credit.requests"/>:</h2><br>
                        <c:forEach items="${activeCreditRequests}" var="creditRequest">
                            <h4><fmt:message key="page.message.credit.request.info"/>:</h4>
                            <c:set var="creditRequest" value="${creditRequest}" scope="request"/>
                            <jsp:include page="../parts/creditRequestMainInfo.jsp"/>
                            <form method="post" name="form"  action="${pageContext.request.contextPath}/api/user/credit_request/close?id=${creditRequest.getId()}" autocomplete="off">
                                <button name="answer" value="rejected" type="submit" class="btn btn-danger" style="margin-top:30px">
                                    <fmt:message key="page.message.cancel"/>
                                </button>
                            </form>
                        </c:forEach>
                    </div>
                </div>
            </c:if>


            <c:if test="${empty activeCreditRequests and empty activeCreditAccounts}">
                <c:if test="${not empty creditRequestSuccess}">
                    <div class="alert alert-success" role="alert">
                        <fmt:message key="page.message.credit.request.success"/>
                    </div>
                </c:if>
                <c:if test="${not empty creditRequestError}">
                    <div class="alert alert-warning" role="alert">
                        <fmt:message key="page.message.open.error"/>
                    </div>
                </c:if>

                <c:if test="${not empty wrongInput}">
                    <div class="alert alert-warning" role="alert">
                            ${wrongInput}
                    </div>
                </c:if>


                <form method="post" style="margin-bottom: 30px" name="form" autocomplete="off">
                    <div class="form-group">
                        <p>
                            <label><fmt:message key="page.message.credit.limit"/>:
                                <input id="creditLimit" class="form-control" type="number" min="1000"
                                       name="creditLimit" required="required" oninput="change()"></label>
                        </p>
                        <label><fmt:message key="page.message.credit.rate"/>:
                            <input class="form-control" id="creditRate" type="number" name="creditRate" min="0"
                                   value="0.05" readonly>
                        </label>
                        <label id="requestTooLarge"></label>
                    </div>
                    <button type="submit" id="submit" class="btn btn-success" style="margin-top:30px">
                        <fmt:message key="page.message.submit"/>
                    </button>
                </form>
            </c:if>
        </div>
    </div>
</div>
<jsp:include page="../parts/footer.jsp"/>
</body>
</html>