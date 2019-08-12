package ua.yaskal.controller.command.admin;

import org.apache.log4j.Logger;
import ua.yaskal.controller.JspPath;
import ua.yaskal.controller.command.Command;
import ua.yaskal.controller.util.ValidationUtil;
import ua.yaskal.model.entity.Account;
import ua.yaskal.model.entity.CreditAccount;
import ua.yaskal.model.entity.Transaction;
import ua.yaskal.model.exceptions.AccessDeniedException;
import ua.yaskal.model.exceptions.no.such.NoSuchAccountException;
import ua.yaskal.model.service.AccountService;
import ua.yaskal.model.service.CreditService;
import ua.yaskal.model.service.TransactionService;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;

public class AdminCreditPageCommand implements Command {
    private final static Logger logger = Logger.getLogger(GetUserPageCommand.class);
    private ValidationUtil validationUtil;
    private CreditService creditService;
    private TransactionService transactionService;
    private AccountService accountService;

    public AdminCreditPageCommand(ValidationUtil validationUtil, CreditService creditService, TransactionService transactionService, AccountService accountService) {
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

        if(validationUtil.isContains(request, Collections.singletonList("answer"))){
            processAnswer(request);
        }

        long creditId = Long.parseLong(request.getParameter("id"));

        CreditAccount creditAccount;
        try {
            creditAccount = creditService.getById(creditId);
        } catch (NoSuchAccountException e){
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

        return JspPath.ADMIN_CREDIT_PAGE;
    }

    private void processAnswer(HttpServletRequest request){
        long creditId = Long.parseLong(request.getParameter("id"));
        String answer = request.getParameter("answer");

        if (answer.equals("block")) {
            logger.debug("Blocking credit account " + creditId + " reason: "+ request.getParameter("blockingReason"));
            accountService.updateAccountStatus(creditId, Account.AccountStatus.BLOCKED);
            request.setAttribute("answer", answer);
        } else if(answer.equals("unblock")){
            logger.debug("Unblocking credit account " + creditId);
            accountService.updateAccountStatus(creditId, Account.AccountStatus.ACTIVE);
            request.setAttribute("answer", answer);
        }


    }

    public void setValidationUtil(ValidationUtil validationUtil) {
        this.validationUtil = validationUtil;
    }

    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    public void setCreditService(CreditService creditService) {
        this.creditService = creditService;
    }

    public void setTransactionService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }
}
