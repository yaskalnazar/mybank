package ua.yaskal.controller.command.user;

import org.apache.log4j.Logger;
import ua.yaskal.controller.JspPath;
import ua.yaskal.controller.command.Command;
import ua.yaskal.controller.command.admin.GetUserPageCommand;
import ua.yaskal.controller.util.ValidationUtil;
import ua.yaskal.model.entity.DepositAccount;
import ua.yaskal.model.entity.Transaction;
import ua.yaskal.model.exceptions.AccessDeniedException;
import ua.yaskal.model.exceptions.no.such.NoSuchAccountException;
import ua.yaskal.model.service.CreditService;
import ua.yaskal.model.service.DepositService;
import ua.yaskal.model.service.TransactionService;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

public class UserDepositPageCommand implements Command {
    private ValidationUtil validationUtil = new ValidationUtil();
    private final static Logger logger = Logger.getLogger(GetUserPageCommand.class);
    private DepositService depositService = new DepositService();
    private TransactionService transactionService = new TransactionService();



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
        } catch (NoSuchAccountException e){
            throw new AccessDeniedException();
        }

        if (depositAccount.getOwnerId() != userId) {
            logger.error("User " + userId + " attempt to access account" + depositId + " without permission");
            throw new AccessDeniedException();
        }

        List<Transaction> transactions = transactionService.getAllByAccountId(depositId);
        transactions.stream().forEachOrdered(x -> {
            if (x.getSenderAccountId() == depositId){
                x.setTransactionAmount(x.getTransactionAmount().negate());
            }
        });

        request.setAttribute("deposit",depositAccount);
        request.setAttribute("accountTransactions", transactions);


        return JspPath.USER_DEPOSIT_PAGE;
    }
}
