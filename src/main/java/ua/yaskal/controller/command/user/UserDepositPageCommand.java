package ua.yaskal.controller.command.user;

import org.apache.log4j.Logger;
import ua.yaskal.controller.JspPath;
import ua.yaskal.controller.command.Command;
import ua.yaskal.controller.command.admin.GetUserPageCommand;
import ua.yaskal.controller.util.ValidationUtil;
import ua.yaskal.model.dto.PaginationDTO;
import ua.yaskal.model.entity.DepositAccount;
import ua.yaskal.model.entity.Transaction;
import ua.yaskal.model.exceptions.message.key.AccessDeniedException;
import ua.yaskal.model.exceptions.message.key.no.such.NoSuchAccountException;
import ua.yaskal.model.service.DepositService;
import ua.yaskal.model.service.TransactionService;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class UserDepositPageCommand implements Command {
    private final static Logger logger = Logger.getLogger(GetUserPageCommand.class);
    private static final long ITEMS_PER_PAGE = 10;
    private ValidationUtil validationUtil;
    private DepositService depositService;
    private TransactionService transactionService;

    public UserDepositPageCommand(ValidationUtil validationUtil, DepositService depositService, TransactionService transactionService) {
        this.validationUtil = validationUtil;
        this.depositService = depositService;
        this.transactionService = transactionService;
    }

    @Override
    public String execute(HttpServletRequest request) {

        if (!validationUtil.isContains(request, Arrays.asList("id")) ||
                !validationUtil.isParamValid(request.getParameter("id"), "id")) {
            logger.warn("Incorrect id");
            throw new RuntimeException("Incorrect id " + request.getRequestURI());
        }

        long depositId = Long.parseLong(request.getParameter("id"));
        long userId = (long) request.getSession().getAttribute("userId");

        DepositAccount depositAccount;
        try {
            depositAccount = depositService.getById(depositId);
        } catch (NoSuchAccountException e) {
            throw new AccessDeniedException();
        }

        if (depositAccount.getOwnerId() != userId) {
            logger.error("User " + userId + " attempt to access account" + depositId + " without permission");
            throw new AccessDeniedException();
        }

        request.setAttribute("page", getPage(request, depositId));
        request.setAttribute("deposit", depositAccount);
        return JspPath.USER_DEPOSIT_PAGE;
    }

    private PaginationDTO<Transaction> getPage(HttpServletRequest request, long depositId) {
        long currentPage = validationUtil.isContains(request, Collections.singletonList("currentPage"))
                ? Long.parseLong(request.getParameter("currentPage")) : 1;

        PaginationDTO<Transaction> page = transactionService.getPageByAccountId(depositId, ITEMS_PER_PAGE, currentPage);

        List<Transaction> transactions = page.getItems();
        transactions.stream().forEachOrdered(x -> {
            if (x.getSenderAccountId() == depositId) {
                x.setTransactionAmount(x.getTransactionAmount().negate());
            }
        });
        page.setItems(transactions);
        return page;
    }

    public void setValidationUtil(ValidationUtil validationUtil) {
        this.validationUtil = validationUtil;
    }

    public void setDepositService(DepositService depositService) {
        this.depositService = depositService;
    }

    public void setTransactionService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }
}
