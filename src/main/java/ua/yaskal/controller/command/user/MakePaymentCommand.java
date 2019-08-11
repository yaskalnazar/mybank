package ua.yaskal.controller.command.user;

import ua.yaskal.controller.JspPath;
import ua.yaskal.controller.command.Command;
import ua.yaskal.controller.util.ValidationUtil;
import ua.yaskal.model.entity.Account;
import ua.yaskal.model.entity.Payment;
import ua.yaskal.model.entity.Transaction;
import ua.yaskal.model.exceptions.NotEnoughMoneyException;
import ua.yaskal.model.exceptions.no.such.NoSuchActiveAccountException;
import ua.yaskal.model.service.AccountService;
import ua.yaskal.model.service.PaymentService;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

public class MakePaymentCommand implements Command {
    ValidationUtil validationUtil;
    PaymentService paymentService;
    AccountService accountService;

    public MakePaymentCommand(ValidationUtil validationUtil, PaymentService paymentService, AccountService accountService) {
        this.validationUtil = validationUtil;
        this.paymentService = paymentService;
        this.accountService = accountService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        long userId = (long) request.getSession().getAttribute("userId");

        if (validationUtil.isContains(request, Arrays.asList("payerAccountId", "requesterAccountId", "amount")) &&
                validationUtil.isRequestValid(request, Arrays.asList("payerAccountId", "requesterAccountId", "amount"))) {


            Payment payment = Payment.getBuilder()
                    .setAmount(new BigDecimal(request.getParameter("amount")))
                    .setPayerAccountId(Long.parseLong(request.getParameter("payerAccountId")))
                    .setRequesterAccountId(Long.parseLong(request.getParameter("requesterAccountId")))
                    .setPaymentStatus(Payment.PaymentStatus.PENDING)
                    .setDate(LocalDateTime.now())
                    .setMessage(request.getParameter("message"))
                    .build();

            try {
                paymentService.addNew(payment);
                request.setAttribute("paymentSuccess", true);
            } catch (NoSuchActiveAccountException e) {
                request.setAttribute("paymentError", e.getMessageKey());
            }

        }

        request.setAttribute("activeUserAccounts", accountService.getAllByOwnerIdAndStatus(userId, Account.AccountStatus.ACTIVE));
        return JspPath.MAKE_PAYMENT;
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
}
