package ua.yaskal.controller.command.admin;

import org.apache.log4j.Logger;
import ua.yaskal.controller.JspPath;
import ua.yaskal.controller.command.Command;
import ua.yaskal.controller.util.ValidationUtil;
import ua.yaskal.model.entity.Account;
import ua.yaskal.model.entity.CreditAccount;
import ua.yaskal.model.entity.DepositAccount;
import ua.yaskal.model.entity.Transaction;
import ua.yaskal.model.exceptions.AccessDeniedException;
import ua.yaskal.model.exceptions.no.such.NoSuchAccountException;
import ua.yaskal.model.service.AccountService;
import ua.yaskal.model.service.CreditService;
import ua.yaskal.model.service.DepositService;
import ua.yaskal.model.service.TransactionService;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;

public class AdminDepositPageCommand implements Command {
    private final static Logger logger = Logger.getLogger(GetUserPageCommand.class);
    private ValidationUtil validationUtil;
    private DepositService depositService;
    private TransactionService transactionService;
    private AccountService accountService;

    public AdminDepositPageCommand(ValidationUtil validationUtil, DepositService depositService, TransactionService transactionService, AccountService accountService) {
        this.validationUtil = validationUtil;
        this.depositService = depositService;
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

        if (validationUtil.isContains(request, Collections.singletonList("answer"))) {
            processAnswer(request);
        }

        long depositId = Long.parseLong(request.getParameter("id"));

        DepositAccount depositAccount;
        try {
            depositAccount = depositService.getById(depositId);
        } catch (NoSuchAccountException e) {
            throw new AccessDeniedException();
        }

        List<Transaction> transactions = transactionService.getAllByAccountId(depositId);
        transactions.stream().forEachOrdered(x -> {
            if (x.getSenderAccountId() == depositId) {
                x.setTransactionAmount(x.getTransactionAmount().negate());
            }
        });

        request.setAttribute("deposit", depositAccount);
        request.setAttribute("accountTransactions", transactions);

        return JspPath.ADMIN_DEPOSIT_PAGE;
    }

    private void processAnswer(HttpServletRequest request) {
        long depositId = Long.parseLong(request.getParameter("id"));
        String answer = request.getParameter("answer");

        if (answer.equals("block")) {
            logger.debug("Blocking deposit account " + depositId + " reason: " + request.getParameter("blockingReason"));
            accountService.updateAccountStatus(depositId, Account.AccountStatus.BLOCKED);
            request.setAttribute("answer", answer);
        } else if (answer.equals("unblock")) {
            logger.debug("Unblocking deposit account " + depositId);
            accountService.updateAccountStatus(depositId, Account.AccountStatus.ACTIVE);
            request.setAttribute("answer", answer);
        }


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

    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }
}
