package ua.yaskal.controller.command.user;

import org.apache.log4j.Logger;
import ua.yaskal.controller.JspPath;
import ua.yaskal.controller.command.Command;
import ua.yaskal.controller.util.ValidationUtil;
import ua.yaskal.model.entity.Account;
import ua.yaskal.model.entity.Transaction;
import ua.yaskal.model.exceptions.AccessDeniedException;
import ua.yaskal.model.exceptions.NotEnoughMoneyException;
import ua.yaskal.model.exceptions.no.such.NoSuchActiveAccountException;
import ua.yaskal.model.service.AccountService;
import ua.yaskal.model.service.TransactionService;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

public class MakeTransactionCommand implements Command {
    private final static Logger logger = Logger.getLogger(MakeTransactionCommand.class);
    private ValidationUtil validationUtil;
    private AccountService accountService;
    private TransactionService transactionService;

    public MakeTransactionCommand(ValidationUtil validationUtil, AccountService accountService, TransactionService transactionService) {
        this.validationUtil = validationUtil;
        this.accountService = accountService;
        this.transactionService = transactionService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        long userId = (long) request.getSession().getAttribute("userId");


        if (validationUtil.isContains(request, Arrays.asList("senderAccountId", "receiverAccountId", "amount")) &&
                validationUtil.isRequestValid(request, Arrays.asList("senderAccountId", "receiverAccountId", "amount"))) {

            Long senderAccountId = Long.parseLong(request.getParameter("senderAccountId"));

            if (accountService.getById(senderAccountId).getOwnerId() != userId) {
                logger.error("User " + userId + " attempt to access account" + senderAccountId + " without permission");
                throw new AccessDeniedException();
            }

            Transaction transaction = Transaction.getBuilder()
                    .setReceiverAccount(Long.parseLong(request.getParameter("receiverAccountId")))
                    .setTransactionAmount(new BigDecimal(request.getParameter("amount")))
                    .setSenderAccount(senderAccountId)
                    .setDate(LocalDateTime.now())
                    .build();

            try {
                transactionService.makeNewTransaction(transaction);
                request.setAttribute("transactionSuccess", true);
            } catch (NotEnoughMoneyException e) {
                request.setAttribute("transactionError", e.getMessageKey());
            } catch (NoSuchActiveAccountException e) {
                request.setAttribute("transactionError", e.getMessageKey());
            }
        }


        request.setAttribute("activeUserAccounts", accountService.getAllByOwnerIdAndStatus(userId, Account.AccountStatus.ACTIVE));
        return JspPath.MAKE_TRANSACTION;

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
