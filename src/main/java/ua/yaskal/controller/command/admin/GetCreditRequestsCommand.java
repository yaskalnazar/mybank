package ua.yaskal.controller.command.admin;

import org.apache.log4j.Logger;
import ua.yaskal.controller.JspPath;
import ua.yaskal.controller.command.Command;
import ua.yaskal.controller.util.ValidationUtil;
import ua.yaskal.model.entity.CreditRequest;
import ua.yaskal.model.service.CreditRequestService;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collections;

/**
 * This command used to get all credit requests page for ADMIN.
 * Required @requestStatus for filtering answers;
 * @author Nazar Yaskal
 */
public class GetCreditRequestsCommand implements Command {
    private final static Logger logger = Logger.getLogger(GetCreditRequestsCommand.class);
    private ValidationUtil validationUtil;
    private CreditRequestService creditRequestService;

    public GetCreditRequestsCommand(ValidationUtil validationUtil, CreditRequestService creditRequestService) {
        this.validationUtil = validationUtil;
        this.creditRequestService = creditRequestService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        if (!validationUtil.isContains(request, Collections.singletonList("requestStatus"))) {
            request.setAttribute("creditRequests",
                    creditRequestService.getAllByStatus(CreditRequest.CreditRequestStatus.PENDING));
            request.setAttribute("status", "pending");
            return JspPath.ADMIN_ALL_CREDIT_REQUESTS;
        }

        switch (request.getParameter("requestStatus")) {
            case "all":
                request.setAttribute("creditRequests", creditRequestService.getAll());
                request.setAttribute("status", "all");
                break;
            case "rejected":
                request.setAttribute("creditRequests",
                        creditRequestService.getAllByStatus(CreditRequest.CreditRequestStatus.REJECTED));
                request.setAttribute("status", "rejected");
                break;
            case "approved":
                request.setAttribute("creditRequests",
                        creditRequestService.getAllByStatus(CreditRequest.CreditRequestStatus.APPROVED));
                request.setAttribute("status", "approved");
                break;
            default:
                request.setAttribute("creditRequests",
                        creditRequestService.getAllByStatus(CreditRequest.CreditRequestStatus.PENDING));
                request.setAttribute("status", "pending");
        }

        return JspPath.ADMIN_ALL_CREDIT_REQUESTS;

    }

    public void setValidationUtil(ValidationUtil validationUtil) {
        this.validationUtil = validationUtil;
    }

    public void setCreditRequestService(CreditRequestService creditRequestService) {
        this.creditRequestService = creditRequestService;
    }
}
