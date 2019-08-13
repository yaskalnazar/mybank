package ua.yaskal.controller.command.user;

import ua.yaskal.controller.JspPath;
import ua.yaskal.controller.command.Command;
import ua.yaskal.controller.util.ValidationUtil;
import ua.yaskal.model.entity.Account;
import ua.yaskal.model.entity.Transaction;
import ua.yaskal.model.service.AccountService;
import ua.yaskal.model.service.TransactionService;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * This command used for getting page for replenishing account and process result.
 * Required params: accountId, amount if new replenishment has been done;
 *
 * @author Nazar Yaskal
 */
public class ReplenishAccountCommand implements Command {
    private ValidationUtil validationUtil;
    private AccountService accountService;
    private TransactionService transactionService;
    private final static long WORKAROUND_ACCOUNT_ID = 12;

    public ReplenishAccountCommand(ValidationUtil validationUtil, AccountService accountService, TransactionService transactionService) {
        this.validationUtil = validationUtil;
        this.accountService = accountService;
        this.transactionService = transactionService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        long userId = (long) request.getSession().getAttribute("userId");

        if (validationUtil.isContains(request, Arrays.asList("accountId", "amount")) &&
                validationUtil.isRequestValid(request, Arrays.asList("accountId", "amount"))) {

            Transaction transaction = Transaction.getBuilder()
                    .setReceiverAccount(Long.parseLong(request.getParameter("accountId")))
                    .setTransactionAmount(new BigDecimal(request.getParameter("amount")))
                    .setSenderAccount(WORKAROUND_ACCOUNT_ID)
                    .setDate(LocalDateTime.now())
                    .build();

            transactionService.makeNewTransaction(transaction);
            request.setAttribute("replenishSuccess", true);
        }

        request.setAttribute("activeUserAccounts", accountService.getAllByOwnerIdAndStatus(userId, Account.AccountStatus.ACTIVE));
        return JspPath.USER_REPLENISH_ACCOUNT;
    }

    public void setValidationUtil(ValidationUtil validationUtil) {
        this.validationUtil = validationUtil;
    }

    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    public void setTransactionService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }
}
