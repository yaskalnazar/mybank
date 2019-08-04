package ua.yaskal.controller.command.admin;

import org.apache.log4j.Logger;
import ua.yaskal.controller.JspPath;
import ua.yaskal.controller.command.Command;
import ua.yaskal.controller.util.ValidationUtil;
import ua.yaskal.model.entity.CreditRequest;
import ua.yaskal.model.entity.User;
import ua.yaskal.model.service.CreditRequestService;
import ua.yaskal.model.service.CreditService;
import ua.yaskal.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

public class CreditRequestCommand implements Command {
    private final static Logger logger = Logger.getLogger(CreditRequestCommand.class);
    private CreditRequestService creditRequestService = new CreditRequestService();
    private ValidationUtil validationUtil = new ValidationUtil();
    private UserService userService = new UserService();
    private CreditService creditService = new CreditService();


    @Override
    public String execute(HttpServletRequest request) {
        logger.warn("_________---------------------------------------");
        logger.warn(request.getPathInfo());
        if (!validationUtil.isСontain(request, Arrays.asList("id"))) {
            logger.warn("No id in request");
            request.setAttribute("error", "page.message.no.id");
            return JspPath.ADMIN_CREDIT_REQUEST;
        }

        CreditRequest creditRequest = creditRequestService.getById(
                Long.parseLong(request.getParameter("id")));
        User applicant = userService.getById(creditRequest.getApplicantId());
        request.setAttribute("creditRequest", creditRequest);
        request.setAttribute("applicant", applicant);
        request.setAttribute("credits", creditService.getAllByOwnerId(applicant.getId()));

        return JspPath.ADMIN_CREDIT_REQUEST;

    }
}
