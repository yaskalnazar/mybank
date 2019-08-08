package ua.yaskal.controller.command.user;

import org.apache.log4j.Logger;
import ua.yaskal.controller.JspPath;
import ua.yaskal.controller.command.Command;
import ua.yaskal.controller.command.admin.GetUserPageCommand;
import ua.yaskal.controller.util.ValidationUtil;
import ua.yaskal.model.exceptions.AccessDeniedException;
import ua.yaskal.model.service.CreditService;
import ua.yaskal.model.service.TransactionService;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

public class UserCreditPageCommand implements Command {
    private ValidationUtil validationUtil = new ValidationUtil();
    private final static Logger logger = Logger.getLogger(GetUserPageCommand.class);
    private CreditService creditService = new CreditService();
    private TransactionService transactionService = new TransactionService();


    @Override
    public String execute(HttpServletRequest request) {

        if (!validationUtil.isContains(request, Arrays.asList("id")) ||
                !validationUtil.isParamValid(request.getParameter("id"), "id")) {
            logger.warn("Incorrect id");
            throw new RuntimeException("Incorrect id " + request.getRequestURI());
        }

        Long creditId = Long.parseLong(request.getParameter("id"));
        long userId = (long) request.getSession().getAttribute("userId");

        if (creditService.getById(creditId).getOwnerId() != userId) {
            logger.error("User " + userId + " attempt to access account" + creditId + " without permission");
            throw new AccessDeniedException();
        }

        request.setAttribute("credit", creditService.getById(Long.parseLong(request.getParameter("id"))));
        request.setAttribute("accountTransactions", transactionService.getAllByAccountId(creditId));
        /*request.setAttribute("receivedTransaction", transactionService.getAllByReceiverId(creditId));
        request.setAttribute("sentTransaction", transactionService.getAllBySenderId(creditId));*/


        return JspPath.USER_CREDIT_PAGE;
    }
}
