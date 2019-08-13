package ua.yaskal.controller.command.admin;

import ua.yaskal.controller.JspPath;
import ua.yaskal.controller.command.Command;
import ua.yaskal.controller.util.ValidationUtil;
import ua.yaskal.model.dto.PaginationDTO;
import ua.yaskal.model.entity.DepositAccount;
import ua.yaskal.model.service.DepositService;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

public class AllDepositsCommand implements Command {
    private static final long ITEMS_PER_PAGE = 15;
    private ValidationUtil validationUtil;
    private DepositService depositService;

    public AllDepositsCommand(ValidationUtil validationUtil, DepositService depositService) {
        this.validationUtil = validationUtil;
        this.depositService = depositService;
    }

    public AllDepositsCommand(DepositService depositService) {
        this.depositService = depositService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        request.setAttribute("page", getPage(request));
        return JspPath.ADMIN_ALL_DEPOSITS;
    }

    private PaginationDTO<DepositAccount> getPage(HttpServletRequest request) {
        long currentPage = validationUtil.isContains(request, Collections.singletonList("currentPage"))
                ? Long.parseLong(request.getParameter("currentPage")) : 1;

        return depositService.getAllPage(ITEMS_PER_PAGE, currentPage);
    }

    public void setValidationUtil(ValidationUtil validationUtil) {
        this.validationUtil = validationUtil;
    }

    public void setDepositService(DepositService depositService) {
        this.depositService = depositService;
    }
}
