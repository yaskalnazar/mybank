<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>


<div class="container">
    <script src="<c:url value='/bootstrap/js/jquery-3.4.1.min.js' />" rel="stylesheet"></script>
    <script src="<c:url value='/bootstrap/js/bootstrap.min.js' />" rel="stylesheet"></script>
    <div class="modal fade" id="myModal2" role="dialog">
        <div class="modal-dialog  modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title"><fmt:message key="page.message.pay"/></h4>
                </div>
                <form method="post" name="form" autocomplete="off"
                      action="${pageContext.request.contextPath}/api/user/account/credit/pay_accrued_interest">
                    <div class="modal-body">
                        <div class="form-group">
                            <label><fmt:message key="page.message.from.the.account"/>:</label>
                            <select class="custom-select" name="senderAccountId" id="senderAccountId">
                                <c:forEach items="${activeUserAccounts}" var="senderAccountId">
                                    <option value="${senderAccountId.getId()}">
                                        <fmt:message key="page.message.id"/>: ${senderAccountId.getId()} &nbsp;
                                        [<fmt:message key="page.message.balance"/>: ${senderAccountId.getBalance()}]
                                    </option>
                                </c:forEach>
                            </select>
                            <input type="hidden" name="receiverAccountId" value="${credit.getId()}">
                            <input type="hidden" name="accruedInterestAmount" value="${credit.getAccruedInterest()}">
                        </div>
                    </div>
                    <div class="modal-footer">

                        <button type="button" class="btn btn-secondary" data-dismiss="modal">
                            <fmt:message key="page.message.close"/></button>
                        <button type="submit" class="btn btn-success">
                            <fmt:message key="page.message.submit"/>
                        </button>
                    </div>
                </form>
            </div>

        </div>
    </div>
    <script>
        $(document).ready(function () {
            $("#myBtn2").click(function () {
                $("#myModal2").modal({backdrop: false});
            });
        });
    </script>

</div>
