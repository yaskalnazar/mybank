package ua.yaskal.controller.command.admin;

import ua.yaskal.controller.JspPath;
import ua.yaskal.controller.command.Command;
import ua.yaskal.model.service.CreditService;

import javax.servlet.http.HttpServletRequest;

public class AllCreditsCommand implements Command {
    private CreditService creditService;

    public AllCreditsCommand(CreditService creditService) {
        this.creditService = creditService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        request.setAttribute("credits", creditService.getAll());
        return JspPath.ADMIN_ALL_CREDITS;
    }

    public void setCreditService(CreditService creditService) {
        this.creditService = creditService;
    }
}
