package ua.yaskal.controller.command.user;

import org.apache.log4j.Logger;
import ua.yaskal.controller.command.Command;
import ua.yaskal.controller.util.ValidationUtil;
import ua.yaskal.model.dto.NewDepositContributionDTO;
import ua.yaskal.model.entity.Transaction;
import ua.yaskal.model.exceptions.message.key.AccessDeniedException;
import ua.yaskal.model.service.AccountService;
import ua.yaskal.model.service.DepositService;
import ua.yaskal.model.service.TransactionService;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * This command used for opening new deposit if old was finished.
 * Required params: senderAccountId, receiverAccountId, depositAmount,
 * depositRate if new deposit has been requested;
 *
 * @author Nazar Yaskal
 */
public class MakeNewContributionCommand implements Command {
    private final static Logger logger = Logger.getLogger(MakeNewContributionCommand.class);
    private final static long WORKAROUND_ACCOUNT_ID = 12;
    private ValidationUtil validationUtil;
    private AccountService accountService;
    private TransactionService transactionService;
    private DepositService depositService;

    public MakeNewContributionCommand(ValidationUtil validationUtil, AccountService accountService, TransactionService transactionService, DepositService depositService) {
        this.validationUtil = validationUtil;
        this.accountService = accountService;
        this.transactionService = transactionService;
        this.depositService = depositService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        if (validationUtil.isContains(request, Arrays.asList("senderAccountId", "receiverAccountId",
                "depositAmount", "depositRate", "monthsAmount")) &&
                validationUtil.isRequestValid(request, Arrays.asList("senderAccountId", "receiverAccountId",
                        "depositAmount", "depositRate", "monthsAmount"))) {


            long senderAccountId = Long.parseLong(request.getParameter("senderAccountId"));
            long depositId = Long.parseLong(request.getParameter("receiverAccountId"));
            BigDecimal depositAmount = new BigDecimal(request.getParameter("depositAmount"));
            BigDecimal depositRate = new BigDecimal(request.getParameter("depositRate"));
            int monthsAmount = Integer.parseInt(request.getParameter("monthsAmount"));
            long userId = (long) request.getSession().getAttribute("userId");

            if (accountService.getById(senderAccountId).getOwnerId() != userId) {
                logger.error("User " + userId + " attempt to access account" + senderAccountId + " without permission");
                throw new AccessDeniedException();
            }

            Transaction transaction = Transaction.getBuilder()
                    .setSenderAccount(senderAccountId)
                    .setTransactionAmount(depositAmount)
                    .setReceiverAccount(WORKAROUND_ACCOUNT_ID)
                    .setDate(LocalDateTime.now())
                    .build();

            NewDepositContributionDTO contributionDTO = new NewDepositContributionDTO(depositAmount, depositRate,
                    monthsAmount, depositId, transaction);

            logger.debug("Trying to pay new deposit for account " + depositId);
            depositService.newDepositContribution(contributionDTO);
        }
        return "redirect/mybank/api/user/account/deposit_page?id=" + request.getParameter("receiverAccountId");
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

    public void setDepositService(DepositService depositService) {
        this.depositService = depositService;
    }
}
