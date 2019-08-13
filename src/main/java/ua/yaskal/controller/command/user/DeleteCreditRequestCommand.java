package ua.yaskal.controller.command.user;

import ua.yaskal.controller.command.Command;
import ua.yaskal.controller.util.ValidationUtil;
import ua.yaskal.model.service.CreditRequestService;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

/**
 * This command used for deleting credit request.
 * Required params: id;
 *
 * @author Nazar Yaskal
 */
public class DeleteCreditRequestCommand implements Command {
    private ValidationUtil validationUtil;
    private CreditRequestService creditRequestService;

    public DeleteCreditRequestCommand(ValidationUtil validationUtil, CreditRequestService creditRequestService) {
        this.validationUtil = validationUtil;
        this.creditRequestService = creditRequestService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        if (validationUtil.isContains(request, Collections.singletonList("id")) &&
                validationUtil.isRequestValid(request, Collections.singletonList("id"))) {
            creditRequestService.delete(Long.parseLong(request.getParameter("id")));
        }
        return "redirect:/mybank/user/account/credit/open";
    }

    public void setValidationUtil(ValidationUtil validationUtil) {
        this.validationUtil = validationUtil;
    }

    public void setCreditRequestService(CreditRequestService creditRequestService) {
        this.creditRequestService = creditRequestService;
    }
}
