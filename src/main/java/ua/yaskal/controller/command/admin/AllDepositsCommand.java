package ua.yaskal.controller.command.admin;

import ua.yaskal.controller.JspPath;
import ua.yaskal.controller.command.Command;
import ua.yaskal.model.service.DepositService;

import javax.servlet.http.HttpServletRequest;

public class AllDepositsCommand implements Command {
    private DepositService depositService;

    public AllDepositsCommand(DepositService depositService) {
        this.depositService = depositService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        request.setAttribute("deposits", depositService.getAll());
        return JspPath.ADMIN_ALL_DEPOSITS;
    }

    public void setDepositService(DepositService depositService) {
        this.depositService = depositService;
    }
}
