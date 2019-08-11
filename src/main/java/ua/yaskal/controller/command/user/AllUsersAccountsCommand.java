package ua.yaskal.controller.command.user;

import ua.yaskal.controller.JspPath;
import ua.yaskal.controller.command.Command;
import ua.yaskal.model.service.CreditService;
import ua.yaskal.model.service.DepositService;

import javax.servlet.http.HttpServletRequest;

public class AllUsersAccountsCommand implements Command {
    private DepositService depositService;
    private CreditService creditService;

    public AllUsersAccountsCommand(DepositService depositService, CreditService creditService) {
        this.depositService = depositService;
        this.creditService = creditService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        request.setAttribute("deposits", depositService.getAllByOwnerId(
                (long)request.getSession().getAttribute("userId")));
        request.setAttribute("credits", creditService.getAllByOwnerId(
                (long)request.getSession().getAttribute("userId")));
        return JspPath.USER_ALL_ACCOUNTS;
    }

    public void setDepositService(DepositService depositService) {
        this.depositService = depositService;
    }

    public void setCreditService(CreditService creditService) {
        this.creditService = creditService;
    }
}
