package ua.yaskal.controller.command.admin;

import org.apache.log4j.Logger;
import ua.yaskal.controller.JspPath;
import ua.yaskal.controller.command.Command;
import ua.yaskal.controller.util.ValidationUtil;
import ua.yaskal.model.entity.CreditAccount;
import ua.yaskal.model.entity.CreditRequest;
import ua.yaskal.model.entity.User;
import ua.yaskal.model.service.CreditRequestService;
import ua.yaskal.model.service.CreditService;
import ua.yaskal.model.service.ScheduledService;
import ua.yaskal.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

public class CreditRequestCommand implements Command {
    private final static Logger logger = Logger.getLogger(CreditRequestCommand.class);
    private ValidationUtil validationUtil;
    private CreditRequestService creditRequestService;
    private UserService userService;
    private CreditService creditService;
    private ScheduledService scheduledService;

    public CreditRequestCommand(ValidationUtil validationUtil, CreditRequestService creditRequestService,
                                UserService userService, CreditService creditService, ScheduledService scheduledService) {
        this.validationUtil = validationUtil;
        this.creditRequestService = creditRequestService;
        this.userService = userService;
        this.creditService = creditService;
        this.scheduledService = scheduledService;
    }

    @Override
    public String execute(HttpServletRequest request) {

        String requestId = request.getParameter("id");
        if (!validationUtil.isContains(request, Collections.singletonList("id")) ||
                !validationUtil.isParamValid(requestId, "id")) {
            logger.warn("Incorrect id");
            throw new RuntimeException("Incorrect id " + request.getRequestURI());
        }

        if (validationUtil.isContains(request, Collections.singletonList("answer"))) {
            processAnswer(request);
        }

        CreditRequest creditRequest = creditRequestService.getById(
                Long.parseLong(request.getParameter("id")));
        User applicant = userService.getById(creditRequest.getApplicantId());
        request.setAttribute("creditRequest", creditRequest);
        request.setAttribute("applicant", applicant);
        request.setAttribute("credits", creditService.getAllByOwnerId(applicant.getId()));

        return JspPath.ADMIN_CREDIT_REQUEST;
    }

    private void processAnswer(HttpServletRequest request) {
        CreditRequest creditRequest = creditRequestService.getById(
                Long.parseLong(request.getParameter("id")));

        String answer = request.getParameter("answer");

        if (answer.equals("approved")) {
            creditRequestService.changeStatus(CreditRequest.CreditRequestStatus.APPROVED,
                    creditRequest.getId());

            creditRequest.setCreditRequestStatus(CreditRequest.CreditRequestStatus.APPROVED);

            CreditAccount creditAccount = creditService.addNew(creditRequest);
            scheduledService.scheduleAccounts(Collections.singletonList(creditAccount));

            logger.debug("Credit account " + creditAccount.getId() + " open");
            request.setAttribute("answer", "approved");
        } else if (answer.equals("rejected")) {
            creditRequestService.changeStatus(CreditRequest.CreditRequestStatus.REJECTED,
                    creditRequest.getId());

            creditRequest.setCreditRequestStatus(CreditRequest.CreditRequestStatus.REJECTED);
            request.setAttribute("answer", "rejected");

        }
    }

    public void setValidationUtil(ValidationUtil validationUtil) {
        this.validationUtil = validationUtil;
    }

    public void setCreditRequestService(CreditRequestService creditRequestService) {
        this.creditRequestService = creditRequestService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setCreditService(CreditService creditService) {
        this.creditService = creditService;
    }

    public void setScheduledService(ScheduledService scheduledService) {
        this.scheduledService = scheduledService;
    }
}
