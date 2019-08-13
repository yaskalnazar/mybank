package ua.yaskal.controller.command.user;

import org.apache.log4j.Logger;
import ua.yaskal.controller.JspPath;
import ua.yaskal.controller.command.Command;
import ua.yaskal.controller.util.ValidationUtil;
import ua.yaskal.model.dto.CreditRequestDTO;
import ua.yaskal.model.entity.Account;
import ua.yaskal.model.entity.CreditAccount;
import ua.yaskal.model.entity.CreditRequest;
import ua.yaskal.model.service.CreditRequestService;
import ua.yaskal.model.service.CreditService;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * This command used for checking if the user can have a new credit,
 * notification result and process new request.
 * Required params: creditLimit, creditRate if new request has been sent;
 *
 * @author Nazar Yaskal
 */
public class NewCreditRequestCommand implements Command {
    private final static Logger logger = Logger.getLogger(NewCreditRequestCommand.class);
    private ValidationUtil validationUtil;
    private CreditRequestService creditRequestService;
    private CreditService creditService;

    public NewCreditRequestCommand(ValidationUtil validationUtil, CreditRequestService creditRequestService, CreditService creditService) {
        this.validationUtil = validationUtil;
        this.creditRequestService = creditRequestService;
        this.creditService = creditService;
    }

    @Override
    public String execute(HttpServletRequest request) {

        if (!isUserCanRequestCredit(request)) {
            return JspPath.USER_CREDIT_OPEN;
        }

        if (!validationUtil.isContains(request, Arrays.asList("creditLimit", "creditRate"))) {
            return JspPath.USER_CREDIT_OPEN;
        }

        if (!validationUtil.isRequestValid(request, Arrays.asList("creditLimit", "creditRate"))) {
            logger.warn("Credit open attempt with incorrect input" + request);
            request.setAttribute("wrongInput", "wrongInput");
            return JspPath.USER_CREDIT_OPEN;
        }

        CreditRequestDTO creditRequestDTO = new CreditRequestDTO(
                (long) request.getSession().getAttribute("userId"),
                new BigDecimal(request.getParameter("creditRate")),
                new BigDecimal(request.getParameter("creditLimit")),
                LocalDateTime.now());

        CreditRequest creditRequest = creditRequestService.createNew(creditRequestDTO);

        logger.debug("Credit request successfully (id:" + creditRequest.getId() + ")");
        request.setAttribute("creditRequestSuccess", creditRequest);
        return JspPath.USER_CREDIT_OPEN;
    }

    private boolean isUserCanRequestCredit(HttpServletRequest request) {
        long userId = (long) request.getSession().getAttribute("userId");

        return (isUserDontHaveActiveCredit(request, userId)
                && isUserDontHaveActiveRequests(request, userId));
    }

    private boolean isUserDontHaveActiveCredit(HttpServletRequest request, long userId) {
        List<CreditAccount> activeCreditAccounts = creditService.getAllByOwnerIdAndStatus(userId, Account.AccountStatus.ACTIVE);
        if (!activeCreditAccounts.isEmpty()) {
            logger.warn("Credit open attempt with activeCreditAccounts userId(" + userId + ")");
            request.setAttribute("activeCreditAccounts", activeCreditAccounts);
            return false;
        }
        return true;
    }

    private boolean isUserDontHaveActiveRequests(HttpServletRequest request, long userId) {
        List<CreditRequest> activeCreditRequests = creditRequestService.getAllByApplicantIdAndStatus(
                userId, CreditRequest.CreditRequestStatus.PENDING);
        if (!activeCreditRequests.isEmpty()) {
            logger.warn("Credit open attempt with activeCreditRequests userId(" + userId + ")");
            request.setAttribute("activeCreditRequests", activeCreditRequests);
            return false;
        }
        return true;
    }

    public void setValidationUtil(ValidationUtil validationUtil) {
        this.validationUtil = validationUtil;
    }

    public void setCreditRequestService(CreditRequestService creditRequestService) {
        this.creditRequestService = creditRequestService;
    }

    public void setCreditService(CreditService creditService) {
        this.creditService = creditService;
    }
}
