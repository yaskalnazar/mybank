<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>


<div class="container">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
    <div class="modal fade" id="myModal2" role="dialog">
        <div class="modal-dialog  modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title"><fmt:message key="page.message.replenish.account"/></h4>
                </div>
                <div class="modal-body">
                    <p><fmt:message key="page.message.add.replenish.here"/></p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">
                        <fmt:message key="page.message.close"/></button>
                    <button type="submit" class="btn btn-success">
                        <fmt:message key="page.message.submit"/>
                    </button>
                </div>
            </div>

        </div>
    </div>
    <script>
        $(document).ready(function(){
            $("#myBtn2").click(function(){
                $("#myModal2").modal({backdrop: false});
            });
        });
    </script>

</div>
