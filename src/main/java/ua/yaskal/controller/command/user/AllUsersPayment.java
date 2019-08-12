package ua.yaskal.controller.command.user;

import org.apache.log4j.Logger;
import ua.yaskal.controller.JspPath;
import ua.yaskal.controller.command.Command;
import ua.yaskal.controller.util.ValidationUtil;
import ua.yaskal.model.entity.Payment;
import ua.yaskal.model.entity.Transaction;
import ua.yaskal.model.exceptions.AccessDeniedException;
import ua.yaskal.model.exceptions.NotEnoughMoneyException;
import ua.yaskal.model.service.AccountService;
import ua.yaskal.model.service.PaymentService;
import ua.yaskal.model.service.TransactionService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Arrays;

public class AllUsersPayment implements Command {
    private final static Logger logger = Logger.getLogger(AllUsersPayment.class);
    private ValidationUtil validationUtil;
    private PaymentService paymentService;
    private AccountService accountService;
    private TransactionService transactionService;

    public AllUsersPayment(ValidationUtil validationUtil, PaymentService paymentService, AccountService accountService, TransactionService transactionService) {
        this.validationUtil = validationUtil;
        this.paymentService = paymentService;
        this.accountService = accountService;
        this.transactionService = transactionService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        long userId = (long) request.getSession().getAttribute("userId");

        if (validationUtil.isContains(request, Arrays.asList("answer","paymentId"))) {
            processAnswer(request);
        }

        if (!validationUtil.isContains(request, Arrays.asList("user_status"))) {
            request.setAttribute("payments",
                   paymentService.getAllByPayerId(userId));
            request.setAttribute("user_status", "received");
            return JspPath.USER_ALL_PAYMENTS;
        }

        switch (request.getParameter("user_status")) {
            case "requester":
                request.setAttribute("payments",
                        paymentService.getAllByRequesterId(userId));
                request.setAttribute("user_status", "sent");
                break;
            default:
                request.setAttribute("payments",
                        paymentService.getAllByPayerId(userId));
                request.setAttribute("user_status", "received");
        }


        return JspPath.USER_ALL_PAYMENTS;
    }

    private void processAnswer(HttpServletRequest request) {
        String answer = request.getParameter("answer");

        if (answer.equals("paid")) {
            Payment payment = paymentService.getById(
                    Long.parseLong(request.getParameter("paymentId")));

            long userId = (long) request.getSession().getAttribute("userId");

            if (accountService.getById(payment.getPayerAccountId()).getOwnerId() != userId) {
                logger.error("User " + userId + " attempt to access account" + payment.getPayerAccountId() + " without permission");
                throw new AccessDeniedException();
            }

            Transaction transaction = Transaction.getBuilder()
                    .setReceiverAccount(payment.getRequesterAccountId())
                    .setTransactionAmount(payment.getAmount())
                    .setSenderAccount(payment.getPayerAccountId())
                    .setDate(LocalDateTime.now())
                    .build();

            try {
                transactionService.makeNewTransaction(transaction);
                paymentService.updateStatusById(Payment.PaymentStatus.PAID, payment.getId());
                request.setAttribute("paymentSuccess", true);
            } catch (NotEnoughMoneyException e) {
                request.setAttribute("paymentError", e.getMessageKey());
            }

        } else if (answer.equals("rejected")) {
            paymentService.updateStatusById(Payment.PaymentStatus.REJECTED,
                    Long.parseLong(request.getParameter("paymentId")));
        }
    }

    public void setValidationUtil(ValidationUtil validationUtil) {
        this.validationUtil = validationUtil;
    }

    public void setPaymentService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    public void setTransactionService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }
}
