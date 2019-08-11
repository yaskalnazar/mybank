package ua.yaskal.controller.command.user;

import org.apache.log4j.Logger;
import ua.yaskal.controller.JspPath;
import ua.yaskal.controller.command.Command;
import ua.yaskal.controller.command.admin.GetUserPageCommand;
import ua.yaskal.controller.util.ValidationUtil;
import ua.yaskal.model.entity.CreditAccount;
import ua.yaskal.model.entity.DepositAccount;
import ua.yaskal.model.entity.Transaction;
import ua.yaskal.model.exceptions.AccessDeniedException;
import ua.yaskal.model.exceptions.no.such.NoSuchAccountException;
import ua.yaskal.model.service.CreditService;
import ua.yaskal.model.service.TransactionService;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class UserCreditPageCommand implements Command {
    private final static Logger logger = Logger.getLogger(GetUserPageCommand.class);
    private ValidationUtil validationUtil;
    private CreditService creditService;
    private TransactionService transactionService;

    public UserCreditPageCommand(ValidationUtil validationUtil, CreditService creditService, TransactionService transactionService) {
        this.validationUtil = validationUtil;
        this.creditService = creditService;
        this.transactionService = transactionService;
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
        } catch (NoSuchAccountException e){
            throw new AccessDeniedException();
        }

        if (creditAccount.getOwnerId() != userId) {
            logger.error("User " + userId + " attempt to access account" + creditId + " without permission");
            throw new AccessDeniedException();
        }

        List<Transaction> transactions = transactionService.getAllByAccountId(creditId);
        transactions.stream().forEachOrdered(x -> {
            if (x.getSenderAccountId() == creditId){
                x.setTransactionAmount(x.getTransactionAmount().negate());
            }
        });

        request.setAttribute("credit",creditAccount);
        request.setAttribute("accountTransactions", transactions);

        return JspPath.USER_CREDIT_PAGE;
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
}
