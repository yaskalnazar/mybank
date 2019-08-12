package ua.yaskal.controller.command.user;

import org.apache.log4j.Logger;
import ua.yaskal.controller.command.Command;
import ua.yaskal.controller.util.ValidationUtil;
import ua.yaskal.model.entity.Transaction;
import ua.yaskal.model.exceptions.AccessDeniedException;
import ua.yaskal.model.service.AccountService;
import ua.yaskal.model.service.CreditService;
import ua.yaskal.model.service.TransactionService;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

public class PayAccruedInterestCommand implements Command {
    private final static Logger logger = Logger.getLogger(PayAccruedInterestCommand.class);
    private final static long WORKAROUND_ACCOUNT_ID = 12;
    private ValidationUtil validationUtil;
    private AccountService accountService;
    private TransactionService transactionService;
    private CreditService creditService;

    public PayAccruedInterestCommand(ValidationUtil validationUtil, AccountService accountService, TransactionService transactionService, CreditService creditService) {
        this.validationUtil = validationUtil;
        this.accountService = accountService;
        this.transactionService = transactionService;
        this.creditService = creditService;
    }

    @Override
    public String execute(HttpServletRequest request) {


        if (validationUtil.isContains(request, Arrays.asList("senderAccountId", "receiverAccountId", "accruedInterestAmount")) &&
                validationUtil.isRequestValid(request, Arrays.asList("senderAccountId", "receiverAccountId", "accruedInterestAmount"))){

            long senderAccountId = Long.parseLong(request.getParameter("senderAccountId"));
            long receiverAccountId = Long.parseLong(request.getParameter("receiverAccountId"));
            BigDecimal amount = new BigDecimal(request.getParameter("accruedInterestAmount"));
            long userId = (long) request.getSession().getAttribute("userId");


            if (accountService.getById(senderAccountId).getOwnerId() != userId) {
                logger.error("User " + userId + " attempt to access account" + senderAccountId + " without permission");
                throw new AccessDeniedException();
            }

            Transaction transaction = Transaction.getBuilder()
                    .setSenderAccount(Long.parseLong(request.getParameter("senderAccountId")))
                    .setTransactionAmount(amount)
                    .setReceiverAccount(WORKAROUND_ACCOUNT_ID)
                    .setDate(LocalDateTime.now())
                    .build();

            logger.debug("Trying to pay accrued interest for account "+ receiverAccountId);
            transactionService.makeNewTransaction(transaction);
            creditService.reduceAccruedInterestById(receiverAccountId, amount);

        }

        return "redirect:/mybank/user/account/credit_page?id="+request.getParameter("receiverAccountId");
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

    public void setCreditService(CreditService creditService) {
        this.creditService = creditService;
    }
}
