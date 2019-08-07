package ua.yaskal.controller.command.user;

import org.apache.log4j.Logger;
import ua.yaskal.controller.JspPath;
import ua.yaskal.controller.command.Command;
import ua.yaskal.controller.util.ValidationUtil;
import ua.yaskal.model.entity.Account;
import ua.yaskal.model.entity.Transaction;
import ua.yaskal.model.service.CreditService;
import ua.yaskal.model.service.DepositService;
import ua.yaskal.model.service.TransactionService;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReplenishAccountCommand implements Command {
    private final static Logger logger = Logger.getLogger(ReplenishAccountCommand.class);
    private ValidationUtil validationUtil = new ValidationUtil();
    private CreditService creditService = new CreditService();
    private DepositService depositService = new DepositService();
    private TransactionService transactionService = new TransactionService();

    @Override
    public String execute(HttpServletRequest request) {
        long userId = (long) request.getSession().getAttribute("userId");

        List<Account> activeUserAccounts = new ArrayList<>();
        activeUserAccounts.addAll(creditService.getAllByOwnerIdAndStatus(userId, Account.AccountStatus.ACTIVE));
        activeUserAccounts.addAll(depositService.getAllByOwnerIdAndStatus(userId, Account.AccountStatus.ACTIVE));

        request.setAttribute("activeUserAccounts", activeUserAccounts);

        if (validationUtil.isContains(request, Arrays.asList("accountId","amount"))){
            transactionService.makeNewTransaction(Transaction.getBuilder()
                    .setReceiverAccount(Long.parseLong(request.getParameter("accountId")))
                    .setTransactionAmount(new BigDecimal(request.getParameter("amount")))
                    .setSenderAccount(12)
                    .setDate(LocalDate.now())
                    .build());
            return JspPath.DEPOSIT_OPEN;
        }


        return JspPath.REPLENISH_ACCOUNT;
    }
}
