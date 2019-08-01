<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Login in system</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body>
<jsp:include page="WEB-INF/jsp/parts/guestHeader.jsp"/>
        <h1>Вход в систему</h1><br/>
        <form method="post" action="mybank/guest/login">

            <input type="email" name="email"><br/>
            <input type="password" name="password"><br/><br/>
            <input class="button" type="submit" value="Войти">

        </form>
        <br/>
        <a href="${pageContext.request.contextPath}/logout">На головну</a>

</body>
</html>