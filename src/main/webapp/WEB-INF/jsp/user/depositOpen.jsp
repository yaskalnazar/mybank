<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>

<html>
<head>
    <title><fmt:message key="page.message.deposit.open"/></title>
    <link href="<c:url value='/bootstrap/css/bootstrap.min.css' />" rel="stylesheet">
    <script>
        function change() {
            var temp = document.getElementById("monthsAmount").value;
            document.getElementById("depositRate").value = temp / 200;
        }
    </script>
</head>
<body>
<jsp:include page="../parts/userHeader.jsp"/>
<div class="container" style="margin-top: 60px">
    <div class="d-flex justify-content-center">
        <div class="col-md-8 col-md-offset-2">
            <c:if test="${not empty depositSuccess}">
                <div class="alert alert-success" role="alert">
                    <fmt:message key="page.message.deposit.open.success"/>
                </div>
            </c:if>
            <c:if test="${not empty depositError}">
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
                        <label><fmt:message key="page.message.deposit.amount"/>:
                            <input class="form-control" type="number" min="1000" required="required"
                                   name="depositAmount"></label>
                    </p>
                    <label><fmt:message key="page.message.deposit.rate"/>
                        <input class="form-control" id="depositRate" type="number" name="depositRate" min="0"
                               value="0.015" readonly>
                    </label>
                    <label><fmt:message key="page.message.months.amount"/>
                        <select class="form-control" name="monthsAmount" id="monthsAmount" oninput="change()">
                            <option value=5>5 <fmt:message key="page.message.months"/></option>
                            <option value=6>6 <fmt:message key="page.message.months"/></option>
                            <option value=9>9 <fmt:message key="page.message.months"/></option>
                            <option value=12>12 <fmt:message key="page.message.months"/></option>
                        </select>
                    </label>
                </div>
                <button type="button" id="myBtn2" class="btn btn-success" style="margin-top:30px">
                    <fmt:message key="page.message.submit"/>
                </button>
                <jsp:include page="../popUp/replenish.jsp"/>
            </form>

        </div>
    </div>
</div>
<jsp:include page="../parts/footer.jsp"/>
</body>
</html>