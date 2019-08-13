<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>


<div class="container">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
    <script>
        function change() {
            var temp = document.getElementById("monthsAmount").value;
            document.getElementById("depositRate").value = temp / 200;
        }
    </script>
    <div class="modal fade" id="myModal2" role="dialog">
        <div class="modal-dialog  modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title"><fmt:message key="page.message.make.new.contribution"/></h4>
                </div>
                <form method="post" name="form" autocomplete="off"
                      action="${pageContext.request.contextPath}/user/account/deposit/make_new_contribution">
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
                            <input type="hidden" name="receiverAccountId" value="${deposit.getId()}">
                        </div>
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
