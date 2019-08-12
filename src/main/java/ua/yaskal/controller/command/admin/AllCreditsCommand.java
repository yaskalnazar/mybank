package ua.yaskal.controller.command.admin;

import ua.yaskal.controller.JspPath;
import ua.yaskal.controller.command.Command;
import ua.yaskal.controller.util.ValidationUtil;
import ua.yaskal.model.dto.PaginationDTO;
import ua.yaskal.model.entity.CreditAccount;
import ua.yaskal.model.entity.Transaction;
import ua.yaskal.model.service.CreditService;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
public class AllCreditsCommand implements Command {
    private static final long ITEMS_PER_PAGE = 15;
    private ValidationUtil validationUtil;
    private CreditService creditService;

    public AllCreditsCommand(ValidationUtil validationUtil, CreditService creditService) {
        this.validationUtil = validationUtil;
        this.creditService = creditService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        request.setAttribute("page", getPage(request));
        return JspPath.ADMIN_ALL_CREDITS;
    }

    private PaginationDTO<CreditAccount> getPage(HttpServletRequest request) {
        long currentPage = validationUtil.isContains(request, Collections.singletonList("currentPage"))
                ? Long.parseLong(request.getParameter("currentPage")) : 1;

        return creditService.getAllPage( ITEMS_PER_PAGE, currentPage);
    }

    public void setCreditService(CreditService creditService) {
        this.creditService = creditService;
    }

    public void setValidationUtil(ValidationUtil validationUtil) {
        this.validationUtil = validationUtil;
    }
}
