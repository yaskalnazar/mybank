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
    <div class="row" >
        <div class="col-md-8 col-md-offset-2">
            <h2 class="page-header">Register</h2>
            <form method="post" autocomplete="off" action="/mybank/guest/registration">
                <div class="form-group">
                    <label for="email">Email</label>
                    <input id="email" class="form-control"
                           type="email" name="email" placeholder="Email" value="${email}">
                    <p class="text-danger">
                        ${wrongEmail}
                    </p>
                </div>
                <div class="form-group">
                    <label for="password">Password</label>
                    <input id="password" class="form-control"
                           type="password" name="password" placeholder="Password" value="${password}">
                    <p class="text-danger">
                        ${wrongPassword}
                    </p>
                </div>
                <div class="form-group">
                    <label for="name">Name</label>
                    <input id="name" class="form-control"
                           type="text" name="name" placeholder="Name" value="${name}">
                    <p class="text-danger">
                        ${wrongName}
                    </p>
                </div>
                <div class="form-group">
                    <label for="surname">Surname</label>
                    <input id="surname" class="form-control"
                           type="text" name="surname" placeholder="Surname" value="${surname}">
                    <p class="text-danger">
                        ${wrongSurname}
                    </p>
                </div>
                <div class="form-group">
                    <label for="patronymic">Patronymic</label>
                    <input id="patronymic" class="form-control"
                           type="text" name="patronymic" placeholder="Patronymic" value="${patronymic}">
                    <p class="text-danger">
                        ${wrongPatronymic}
                    </p>
                </div>
                <button type="submit" class="btn btn-success" style="margin-top:30px">
                   Register
                </button>
                <a class="btn btn-secondary" style="margin-top:30px" href="/mybank/guest/login" role="button">
                    Login
                </a>
            </form>
        </div>
    </div>
</div>
</body>
</html>