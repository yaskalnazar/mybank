<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Registration</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body>
<jsp:include page="../parts/guestHeader.jsp"/>

<div class="container" style="margin-top: 60px">
    <div class="row">
        <div class="col-md-8 col-md-offset-2">
            <c:if test="${not empty regSuccessfully}">
                <div class="alert alert-primary" role="alert">
                        ${regSuccessfully}
                </div>
            </c:if>
            <c:if test="${not empty wrongInput}">
                <div class="alert alert-danger" role="alert">
                        Wrong Input please try again
                </div>
            </c:if>
            <h2 class="page-header">Login</h2>
            <form method="post" autocomplete="off" action="/mybank/guest/login">
                <div class="form-group">
                    <label for="email">Email</label>
                    <input id="email" class="form-control"
                           type="email" name="email" placeholder="Email" value="${email}">

                </div>
                <div class="form-group">
                    <label for="password">Password</label>
                    <input id="password" class="form-control"
                           type="password" name="password" placeholder="Password">
                </div>
                <button type="submit" class="btn btn-success" style="margin-top:30px">
                    Login
                </button>
                <a class="btn btn-secondary" style="margin-top:30px" href="/mybank/guest/registration" role="button">
                    Register
                </a>
            </form>
        </div>
    </div>
</div>
</body>
</html>