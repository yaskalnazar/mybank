package ua.yaskal.controller.command.admin;

import ua.yaskal.controller.JspPath;
import ua.yaskal.controller.command.Command;
import ua.yaskal.model.entity.CreditRequest;
import ua.yaskal.model.service.DepositService;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

public class AllDepositsCommand implements Command {
    private DepositService depositService = new DepositService();

    @Override
    public String execute(HttpServletRequest request) {
        request.setAttribute("deposits", depositService.getAll());
        return JspPath.ADMIN_ALL_DEPOSITS;
    }
}
