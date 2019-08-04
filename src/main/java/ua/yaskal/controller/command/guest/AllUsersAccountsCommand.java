package ua.yaskal.controller.command.guest;

import ua.yaskal.controller.JspPath;
import ua.yaskal.controller.command.Command;
import ua.yaskal.model.service.DepositService;

import javax.servlet.http.HttpServletRequest;

public class AllUsersAccountsCommand implements Command {
    private DepositService depositService = new DepositService();

    @Override
    public String execute(HttpServletRequest request) {
        request.setAttribute("deposits", depositService.getAllByOwnerId(
                (long)request.getSession().getAttribute("userId")));
        return JspPath.USER_ALL_ACCOUNTS;
    }
}
