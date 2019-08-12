package ua.yaskal.controller.command.user;

import org.apache.log4j.Logger;
import ua.yaskal.controller.JspPath;
import ua.yaskal.controller.command.Command;
import ua.yaskal.controller.command.admin.GetUserPageCommand;
import ua.yaskal.controller.util.ValidationUtil;
import ua.yaskal.model.dto.PaginationDTO;
import ua.yaskal.model.entity.Account;
import ua.yaskal.model.entity.CreditAccount;
import ua.yaskal.model.entity.Transaction;
import ua.yaskal.model.exceptions.message.key.AccessDeniedException;
import ua.yaskal.model.exceptions.message.key.no.such.NoSuchAccountException;
import ua.yaskal.model.service.AccountService;
import ua.yaskal.model.service.CreditService;
import ua.yaskal.model.service.TransactionService;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;

public class UserCreditPageCommand implements Command {
    private final static Logger logger = Logger.getLogger(GetUserPageCommand.class);
    private static final long ITEMS_PER_PAGE = 10;
    private ValidationUtil validationUtil;
    private CreditService creditService;
    private TransactionService transactionService;
    private AccountService accountService;

    public UserCreditPageCommand(ValidationUtil validationUtil, CreditService creditService,
                                 TransactionService transactionService, AccountService accountService) {
        this.validationUtil = validationUtil;
        this.creditService = creditService;
        this.transactionService = transactionService;
        this.accountService = accountService;
    }

    @Override
    public String execute(HttpServletRequest request) {

        if (!validationUtil.isContains(request, Collections.singletonList("id")) ||
                !validationUtil.isParamValid(request.getParameter("id"), "id")) {
            logger.warn("Incorrect id");
            throw new RuntimeException("Incorrect id " + request.getRequestURI());
        }

        long creditId = Long.parseLong(request.getParameter("id"));
        long userId = (long) request.getSession().getAttribute("userId");

        CreditAccount creditAccount;
        try {
            creditAccount = creditService.getById(creditId);
        } catch (NoSuchAccountException e) {
            throw new AccessDeniedException();
        }

        if (creditAccount.getOwnerId() != userId) {
            logger.error("User " + userId + " attempt to access account" + creditId + " without permission");
            throw new AccessDeniedException();
        }

        request.setAttribute("page", getPage(request, creditId));
        request.setAttribute("credit", creditAccount);
        request.setAttribute("activeUserAccounts",
                accountService.getAllByOwnerIdAndStatus(userId, Account.AccountStatus.ACTIVE));
        return JspPath.USER_CREDIT_PAGE;
    }

    private PaginationDTO<Transaction> getPage(HttpServletRequest request, long creditId) {
        long currentPage = validationUtil.isContains(request, Collections.singletonList("currentPage"))
                ? Long.parseLong(request.getParameter("currentPage")) : 1;

        PaginationDTO<Transaction> page = transactionService.getPageByAccountId(creditId, ITEMS_PER_PAGE, currentPage);

        List<Transaction> transactions = page.getItems();
        transactions.stream().forEachOrdered(x -> {
            if (x.getSenderAccountId() == creditId) {
                x.setTransactionAmount(x.getTransactionAmount().negate());
            }
        });
        page.setItems(transactions);
        return page;
    }

    public void setValidationUtil(ValidationUtil validationUtil) {
        this.validationUtil = validationUtil;
    }

    public void setCreditService(CreditService creditService) {
        this.creditService = creditService;
    }

    public void setTransactionService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }
}
